package br.com.automation.controller;

import br.com.automation.util.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TestControllerGetController {

    @BeforeClass
    void setup(){
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void testResponseListUsers() {

        given().
        when().
                get("/users?page=1").
        then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "page", is(1),
                        "per_page", is(6),
                        "total", is(12),
                        "total_pages", is(2),
                        "support.url", is("https://reqres.in/#support-heading"),
                        "support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!")

                );
    }

    @Test
    void testResponseTokenLogin() {

        var user = new User("eve.holt@reqres.in", "cityslicka");

        given().
                contentType(ContentType.JSON).
                body(user).
        when().
                post("/login").
        then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "token", is("QpwL5tke4Pnpja7X4")
                );
    }
}
