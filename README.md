# Test 1
```
TEST UserAuthentication
  ASSERT_TRUE(authenticate("validUser", "validPass"), "Should succeed with correct credentials")
  ASSERT_FALSE(authenticate("validUser", "wrongPass"), "Should fail with wrong credentials")
END TEST

```
## Refactor

```
import spock.lang.Specification

class UserAuthenticationTest extends Specification {

    @Subject
    def userService = UserService()

    def "should succeed with correct credentials"() {
        given:
        def username = TestConstants.DEFAULT_SUCCESS_USERNAME
        def pass = TestConstants.DEFAULT_SUCCESS_PASS

        when: "the authenticate method is called"
        def result = userService.authenticateUser(username, pass)

        then: "authentication is successful"
        result == true
    }

    def "should fail with incorrect password - Error"() {
        given:
        def username = TestConstants.DEFAULT_FAILED_USERNAME
        def pass = TestConstants.DEFAULT_FAILED_PASS

        when: "the authenticate method is called"
        def result = userService.authenticateUser(username, pass)

        then: "authentication is successful"
        result == false
    }

}


```

> Analisis: 
* CLARIDAD: El nombre de la prueba no es claro 
* SRP: El Test realiza mas de 2 tareas (happy path y failed path)

#### Comentarios

* El test original no es claro, contenia un nombre en el metodo que no daba detalles de lo que se hacía, y dentro de él realizaba multiples tareas. En la corrección se refactorizo usando un service para el llamado a la auntentocacion de un usuario, un metodo que realiza el happy path y otro metodo que realiza el sad path, con esto cumplios con el principio single, también se apoya de un util llamado TestConstants el cual almacena los datos requeridos para la prueba, con esto es mas clara hace que el código sea más flexible y fácil de mantener, permitiendo cambiar nuevas implementaciones.


# Test 2
```
TEST DataProcessing
  DATA data = fetchData()
  TRY
    processData(data)
    ASSERT_TRUE(data.processedSuccessfully, "Data should be processed successfully")
  CATCH error
    ASSERT_EQUALS("Data processing error", error.message, "Should handle processing errors")
  END TRY
END TEST

```
## Refactor

```
import spock.lang.Specification

class DataProcessingTest extends Specification {

 @Subject
    def dataService = DataService()

    def "should process data successfully" {
        given: 
        Data data = TestUtil.fetchData()

        when:
        dataService.processData(data)

        then: "the data should be processed successfully"
        data.processedSuccessfully == true
    }

    def "should handle data processing errors" {
        given: 
        Data data = TestUtil.fetchDataError()

        when:
        dataService.processData(data)

        then:
        def error = thrown(CustomerDataException)
        error.message == "Data processing error"
    }

}


```

> Analisis: 
* CLARIDAD: El nombre de la prueba no es claro 
* SRP: El Test trata de probar 2 escenarios diferentes una de ellas es el caso de un exception

#### Comentarios

* El test original realiza una prueba con 2 escenarios distintos, uno de ellos con una exception, de la misma forma que se refactorizó el test 1, se crearon archivos auxiliares (Test.Util), 2 metodos para cada escenario para hacer que el test sea mas claro y  mejoró la cohesión y la facilidad de mantenimiento del código al tener metodos que se centran en una única responsabilidad.


# Test 3
```
TEST UIResponsiveness
  UI_COMPONENT uiComponent = setupUIComponent(1024)
  ASSERT_TRUE(uiComponent.adjustsToScreenSize(1024), "UI should adjust to width of 1024 pixels")
END TEST

```
## Refactor

```

class UIResponsivenessTest extends Specification {


    @Subject
    def uiComponent =UiComponent()


    def cleanup() {
    }

    def "UI should adjust to screen width of 1024 pixels" {
        given:
        def pixels = TestConstants.DEFAULT_PIXELS

        when:
        def = result = uiComponent.adjustsToScreenSize(pixels)

        then: 
        result == true
    }

    def "UI should adjust to screen width of 1080 pixels" {
        given:
        def pixels = TestConstants.DEFAULT_OTHER_PIXELS

        when:
        def = result = uiComponent.adjustsToScreenSize(pixels)

        then: 
        result == true
    }

}

class TestConstants {
    public static final String DEFAULT_PIXELS = "1024"
    public static final String DEFAULT_OTHER_PIXELS = "1080"
]


```

> Analisis: 
* CLARIDAD: El nombre de la prueba no es claro 
* La estructura del test no esta bien definida

#### Comentarios

* El test original realiza una prueba la cual no muestra una estructura clara. Se realiza un test con 2 metodos y una mejor estructura, usando un Desarrollo guiado por comportamiento (BDD).
