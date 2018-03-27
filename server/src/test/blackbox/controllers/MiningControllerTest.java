package controllers;


import containers.Block;
import containers.RandomContainerCreator;
import containers.Transaction;
import containers.TransactionsList;
import io.restassured.response.Response;
import jdk.nashorn.api.scripting.JSObject;
import main.Application;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.HashMap;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Anastasiia Shalygina
 */


public class MiningControllerTest extends BaseTest {

    @Test
    public void checkWrongParams() {
        HashMap<String, String> query1 = new HashMap<>();
        query1.put("WrongParameter", "Value");

        Response response = sendPost("/calculatehash",query1);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }


    @Test
    public void checkWrongParams2() {
        HashMap<String, String> query2 = new HashMap<>();
        query2.put("Block", "Value1");
        query2.put("Block2","Value2");

        Response response = sendPost("/calculatehash",query2);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }

    @Test
    public void checkRequestData() {
        Block block = RandomContainerCreator.createBlockWithRandomHashCode();
        HashMap<String, String> query = new HashMap<>();
        query.put("Block", block.getJsonObject().toString());
        Response response = sendPost("/calculatehash", query);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, block.calculateHashCode(), "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}