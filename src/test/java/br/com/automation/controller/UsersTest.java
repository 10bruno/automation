package br.com.automation.controller;

import br.com.automation.util.TestConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsersTest {

    private Response res;

    @BeforeClass(description = "setup")
    void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
        res = given().param("page", 1).when().get();
    }

    @Test(testName = "1 - Test user list size Restassured Bruto + Hamcrest")
    void test1_1() {

        given().
                param("page", 1).
        when().
                get("https://reqres.in/api/users").
        then().
                statusCode(HttpStatus.SC_OK).
                body("data.id.size()", greaterThanOrEqualTo(TestConstants.SIZE_LIST)).
                body("support.url", is(TestConstants.SUPPORT_URL)).
                body("support.text", is(TestConstants.SUPPORT_TEXT));
    }

    @Test(testName = "2 - Test user list size Restassured + JUnit")
    void test1_2() {

        Integer size = getDataSize();
        String url = getSupportUrl();
        String text = getSupportText();

        assertEquals(HttpStatus.SC_OK, res.statusCode());
        assertTrue(size >= TestConstants.SIZE_LIST);
        assertEquals(TestConstants.SUPPORT_URL, url);
        assertEquals(TestConstants.SUPPORT_TEXT, text);
    }

    @Test(testName = "3 - Test user list size Restassured + AssertJ")
    void test1_3() {

        Integer size = getDataSize();
        String url = getSupportUrl();
        String text = getSupportText();

        assertThat(res.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(size).isGreaterThanOrEqualTo(TestConstants.SIZE_LIST);
        assertThat(url).isEqualTo(TestConstants.SUPPORT_URL);
        assertThat(text).isEqualTo(TestConstants.SUPPORT_TEXT);
    }

    @Test(testName = "4 - Test user list size Restassured + Hamcrest")
    void test1_4() {

        res.
        then().
                statusCode(HttpStatus.SC_OK).
                body("data.id.size()", greaterThanOrEqualTo(TestConstants.SIZE_LIST)).
                body("support.url", is(TestConstants.SUPPORT_URL)).
                body("support.text", is(TestConstants.SUPPORT_TEXT));
    }

    private Integer getDataSize(){
        return res.getBody().jsonPath().get("data.id.size()");
    }

    private String getSupportUrl() {
        return res.getBody().jsonPath().get("support.url");
    }

    private String getSupportText() {
        return res.getBody().jsonPath().get("support.text");
    }

}
