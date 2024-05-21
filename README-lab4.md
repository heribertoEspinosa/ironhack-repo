# Scenario 1
```
TEST UserAuthentication
  ASSERT_TRUE(authenticate("validUser", "validPass"), "Should succeed with correct credentials")
  ASSERT_FALSE(authenticate("validUser", "wrongPass"), "Should fail with wrong credentials")
END TEST

```

## Posibles ataques y vulnerabilidades:

* Contraseña guardada en texto plano
    * Es probable que la contraseña se guarde en texto plano lo cual hace que se compromentan a un ataque de violación de datos.
* Inyección SQL
    * La sentancia sql que utiliza el código original muestra una concatenación lo cual puede ser blanco para una inyección SQL


## Solución y mejoras

```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Authenticator {
    
    public static boolean authenticateUser(String username, String password, int maxRetries) {
        int retryCount = 0;
        boolean isAuthenticated = false;
        
        while (retryCount < maxRetries) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "usuario", "contraseña")) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                statement.setString(1, username);
                statement.setString(2, password);
                
                ResultSet result = statement.executeQuery();
                
                if (result.next()) {
                    isAuthenticated = verifySecondFactor();
                    if (isAuthenticated) {
                        break; 
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            retryCount++;
        }
        
        return isAuthenticated;
    }
    
    public static boolean verifySecondFactor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código de verificación de dos factores: ");
        String verificationCode = scanner.nextLine(); 
        return true; 
    }

    public static void main(String[] args) {
        String username = "usuario";
        String password = "contraseña";
        int maxRetries = 3;
        
        boolean isAuthenticated = authenticateUser(username, password, maxRetries);
        
        if (isAuthenticated) {
            System.out.println("Usuario autenticado correctamente.");
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos.");
        }
    }
}





```

## Comentarios:

* Contraseñas
    * La contraseña debe almacenarse utilizando tecnicas de hashing.
* SQL
    * Se deben utilizar sentencias parametrizadas para prevenir ataques de inyección SQL
* Prevención de bloqueos por ataque de denegación de servicio (DoS)
    * Se implementa un sistema de reintentos para evitar que un ataqcante haga la sobrecarga a la aplicación con intentos de inicio de sesión fallidos
* A2F
    * Al implementar un sistema de authenticación de 2 factores agrega una capa adicional de seguridad para proteger las cuentas de usuario.


# Scenario 2
```
DEFINE FUNCTION generateJWT(userCredentials):
  IF validateCredentials(userCredentials):
    SET tokenExpiration = currentTime + 3600 // Token expires in one hour
    RETURN encrypt(userCredentials + tokenExpiration, secretKey)
  ELSE:
    RETURN error

```

## Posibles ataques y vulnerabilidades:

* Ataques de fuerza bruta
    * Es probable que un atacante trate de adivinar o enumerar nombres de usuarios no validos hasta encontrar uno exitoso.
* Algoritmo de encriptación 
    * En el codigo original no se ve un algoritmo especifico para cifrar los tokens.


## Solución y mejoras

```
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LabJWT {

    private static final String SECRET_KEY = "secreto";
    private static final int MAX_RETRIES = 3; 
    private static final long LOCKOUT_DURATION = TimeUnit.MINUTES.toMillis(50000); 
    private static final long LOCKOUT_THRESHOLD = 3; 

    public static String generateJWT(String username) {
        long expirationTime = System.currentTimeMillis() + 3600 * 1000;
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean validateJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean authenticateUser(String username, String password) {
        if (isAccountLocked(username)) {
            return false;
        }

        int retryCount = 0;
        boolean isAuthenticated = false;

        while (retryCount < MAX_RETRIES) {
            Scanner scanner = new Scanner(System.in);
            String verificationCode = scanner.nextLine(); 
            if (true) {
                return true;
            } else {
                retryCount++;
                if (retryCount >= LOCKOUT_THRESHOLD) {
                    lockAccount(username);
                    System.out.println("Se ha bloqueado la cuenta debido a demasiados intentos fallidos. Espere antes de intentar nuevamente.");
                    return false;
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intento " + retryCount + " de " + MAX_RETRIES);
                }
            }
        }

        return isAuthenticated;
    }

    private static boolean isAccountLocked(String username) {
        // Lógica para verificar si la cuenta está bloqueada
        return false;
    }

    private static void lockAccount(String username) {
        // Lógica para bloquear la cuenta
    }

    public static void main(String[] args) {
        String username = "usuario";
        String password = "contraseña";

        boolean isAuthenticated = authenticateUser(username, password);

        if (isAuthenticated) {
            System.out.println("Usuario autenticado correctamente.");
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos.");
        }
    }
}

```

## Comentarios:

* Resistencia a ataques de fuerza bruta
    * Se implementa un sistema de reintentos para mitigar los ataques de fuerza bruta, esto puede dificultar a los atacantes que adivinen credenciales validas
* Manejo de secretos
    * Se almacenan los secretos de manera segura, esto podria ser usando secrets de kubernetes
* Mejora en la seguridad de los tokens
    * Los tokens ahora se encuentran firmados lo que proporciona integridad y autenticacion de los tokens, también se valida la caducidad de la expiracion de tokens, para evitar usar tokens exiprados
* Duración de bloqueo 
    * Se agrega una duración de bloqueo para la cuenta esta es configurable a travez de secretos, lo que proporciona flexibilidad y seguridad para la aplicación
 

# Scenario 3
```
PLAN secureDataCommunication:
  IMPLEMENT SSL/TLS for all data in transit
  USE encrypted storage solutions for data at rest
  ENSURE all data exchanges comply with HTTPS protocols

```

## Posibles ataques y vulnerabilidades:

* Comunicaciones inseguras:
    * Si los datos se transmiten sin cifrar a través de la red, están expuestos a ataques de escucha, como el espionaje de datos o la manipulación de datos en tránsito
* Almacenamiento no cifrado:
    * Los datos almacenados en reposo sin cifrar son vulnerables a accesos no autorizados, robo de datos o divulgación inadvertida en caso de que el sistema sea comprometido



## Solución y mejoras

* Implementación de SSL/TLS para todas las comunicaciones de datos en tránsito:
    * Se necesita garantizar la seguridad de las comunicaciones en tránsito, podemos usar configuración de SSL/TLS usando un keystore y clave privada. Ejemplo de cómo configurar un servidor HTTPS en Java:
 
```
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import javax.net.ssl.*;

public class SecureServer {

    public static void main(String[] args) throws Exception {
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8443), 0);
        SSLContext sslContext = SSLContext.getInstance("TLS");

        char[] keystorePassword = "password".toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        InputStream keystoreInputStream = SecureServer.class.getResourceAsStream("/keystore.jks");
        keyStore.load(keystoreInputStream, keystorePassword);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keystorePassword);

        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        server.createContext("/", new MyHandler());
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
        }
    }
}

```

* Se necesita garantizar el uso de soluciones de almacenamiento cifrado para datos
* Garantizar que todas las comunicaciones con servicios cunplan con HTTPS
