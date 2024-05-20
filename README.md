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

* El test original no es claro, contenia un nombre en el metodo que no daba detalles de lo que se hacía, y dentro de él realizaba multiples tareas. En la corrección se refactorizo usando un service para el llamado a la auntentocacion de un usuario, un metodo que realiza el happy path y otro metodo que realiza el sad path, con esto cumplios con el principio single, y la prueba unitaria es mas clara
