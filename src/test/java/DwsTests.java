import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DwsTests {
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    @Test
    @DisplayName("Проверка добавления книги в корзину")
    void checkaddNewBook(){
        String cook1 =  "fc06465c332dbd696c292ada76ec7d7733108ecefdc4475406a3ff997944f96b";
        String cook2 = "6baf52d2-ce23-4012-a5f6-918b4fe0c8fa";
        given()
                .contentType("application/json; charset=utf-8")
                .cookie("ARRAffinitySameSite", cook1)
                .cookie("Nop.customer", cook2)
                .when()
                .get("/books")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.head.title", is("Demo Web Shop. Books"));
    }

    @Test
    @DisplayName("Проверка подписки на новости")
    void checkSubscribeNewsLetter() {
        String cookieValue = "ED7FEC5B33AA2C06B23FA44165888CF64F07E7B7DF3" +
                "DE77A0CC221EA554FAA881502DF69805BA433BF1F0872D8600520D68" +
                "2690AE9B9BAE2B67B93765F7284674705DA37A6B489A82A3352434F0" +
                "C56FDC806CC04E17B2C3D7CAADF817599145AD381AB91CD35FA66477" +
                "F91F102501880BAC8B3292E39D62F23D4CBF8DFCA28F8A8524E26C9B2" +
                "EE64FB7A0B7DE693A9D3FED43ABBE8CA0BE3600C27A21BA888B3;",

                body = "email=gas%40mail.ru";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue)
                .body(body)
                .when()
                .post("/subscribenewsletter")
                .then()
                .log().all()
                .statusCode(200)
                .body("Success", is(true))
                .body("Result", is("Thank you for signing up! A verification email has been sent. We appreciate your interest."));

    }
}
