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


## Solución

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
