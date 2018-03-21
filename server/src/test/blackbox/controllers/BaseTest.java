package controllers;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void initRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }
}
