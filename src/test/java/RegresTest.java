
import lombok.LombokUserData;
import model.User;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Spec.Specs.request;
import static Spec.Specs.responce;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    void checkRegisterSuccessful(){
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
    void createUser() {
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
    void checkUnknownUser() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/users/16")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка существующего цвета с помощью Groovy запроса")
    void checkWithGroovy(){
        given()
                .when()
                .spec(request)
                .get("/unknown")
                .then()
                .log().body()
                .body("data.findAll{it.id == 2}.name", hasItem("fuchsia rose"));
    }
    @Test
    @DisplayName("Проверка запроса при помощи модели Lombok")
    void checkwithLombok() {
        UserData data = given()
                .spec(request)
                .when()
                .get("/users/3")
                .then()
                .spec(responce)
                .log().all()
                .extract().as(UserData.class);
        assertEquals(data.getData().getId(), 3);
        assertEquals(data.getData().getFirstName(), "Emma");
    }

}




