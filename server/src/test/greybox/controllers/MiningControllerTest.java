package controllers;


import containers.Block;
import containers.RandomContainerCreator;
import containers.TransactionsList;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.HashMap;

/**
 * @author Anastasiia Shalygina
 */


public class MiningControllerTest extends BaseTest {

    @Test
    public void checkWrongParams() {
        HashMap<String, String> query1 = new HashMap<>();
        query1.put("WrongParameter", "Value");
        query1.put("Block", "Value2");

        Response response = sendPost("/mining",query1);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }


    @Test
    public void checkWrongParams2() {
        HashMap<String, String> query2 = new HashMap<>();
        query2.put("Block", "Value1");
        query2.put("TransactionsList","Value2");
        query2.put("Block", "Value3");

        Response response = sendPost("/mining",query2);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }

    @Test
    public void checkRequestData() {
        Block block = RandomContainerCreator.createBlockWithRandomHashCode();
        TransactionsList transactionsList = RandomContainerCreator.createTransactionsList(5);
        HashMap<String, String> query = new HashMap<>();
        query.put("Block", block.getJsonObject().toString());
        query.put("TransactionsList", transactionsList.getJsonArray().toString());
        Response response = sendPost("/mining", query);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            String hash = block.calculateHashCode();
            Block new_block = new Block(transactionsList, hash);
            new_block.mining();
            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody,new_block.getJsonObject().toString() , "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}