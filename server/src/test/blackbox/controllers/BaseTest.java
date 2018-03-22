package controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import javafx.util.Pair;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Alexander Voroshilov
 * */
public class BaseTest {

    @BeforeMethod
    public void initRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    protected Response sendPost(String path, HashMap<String, String> query) {
        Response response = RestAssured
                .given().log().all().queryParams(query)
                .when().post(path);
        response.prettyPrint();
        return response;
    }

    protected Response sendPost(String path) {
        Response response = RestAssured
                .given().log().all()
                .when().post(path);
        response.prettyPrint();
        return response;
    }

    protected Response sendPost(String path, String body) {
        Response response = RestAssured
                .given().log().all().header("Content-Type", "application/x-www-form-urlencoded").body(body)
                .when().post(path);
        response.prettyPrint();
        return response;
    }

    protected Response sendPost(String path, ArrayList<Pair<String, String>> query) {
        String body = "";

        for (Pair<String, String> pair: query) {
            body += pair.getKey() + "=" + pair.getValue() + "&";
        }

        body = body.substring(0, body.length() - 1);
        return sendPost(path, body);
    }
}
