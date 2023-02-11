import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RegresTestwithLombok {

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
}




