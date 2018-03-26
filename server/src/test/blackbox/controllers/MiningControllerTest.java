package controllers;


import containers.Block;
import containers.Transaction;
import containers.TransactionsList;
import io.restassured.response.Response;
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

        Response response = sendPost("/gethash",query1);

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

        Response response = sendPost("/gethash",query2);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }
/*
    @Test
    public void checkRequestData() {
        Response response = sendPost("/gethash");

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            TransactionsList transactionsList = new TransactionsList();
            transactionsList.addTransaction(new Transaction("tr1"));
            transactionsList.addTransaction(new Transaction("tr2"));
            Block block = new Block(transactionsList.getJsonArray().toString());

            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, block.getJsonObject(), "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

*/
}