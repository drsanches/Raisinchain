package helpers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.protocol.HTTP;

import java.util.HashMap;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class Requests {

    public static JsonPath getSmth() {
        Response response = given().when().get();
        response.prettyPrint();
        return response.then().extract().jsonPath();
    }

    public static void getWithHeader() {
        Response response = given().when().header("123", "3123").get();
    }

    public static void getWithQuery(HashMap<String, String> query) {
        ValidatableResponse response = given().when().queryParams(query).get().then().statusCode(HTTP_BAD_REQUEST);
    }

    public static JsonPath getPath(String path) {
        Response response = given().when().get(path);
        response.prettyPrint();
        return response.jsonPath();
    }

    public static JsonPath postSmth(HashMap<String, String> query) {
        Response response = given().queryParams(query).when().post();
        response.prettyPrint();
        return response.jsonPath();
    }





    private static RequestSpecification given(){
        return RestAssured.given().log().all();
    }
}
