import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
//import static org.apache.commons.codec.digest.UnixCrypt.body;


public class RegresTest {
    @Test
    @DisplayName("Проверка существования аккаунта с логами")
    void checkNameUserswithLog(){
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    @DisplayName("Проверка существования аккаунта с данной электронной почтой")
    void checkUnLogin(){
        String str = "{ \"email\": \"peter@klaven\" }";
        given()
                .contentType(JSON)
                .body(str)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Проверка присвоения токена при запросе")
    void checkRegisterSuss(){
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Проверка создания нового аккаунта")
    void creatUser() {
        String user = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .when()
                .contentType(JSON)
                .body(user)
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));

    }
    @Test
    @DisplayName("Проверка запроса данных у несуществующего аккаунта")
    public void checkUnknownUser() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/users/16")
                .then()
                .statusCode(404);
    }
}




