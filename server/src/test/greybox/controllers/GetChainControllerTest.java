package controllers;

import containers.BlockChain;
import io.restassured.response.Response;
import application.Application;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

/**
 * @author Alexander Voroshilov
 * */
public class GetChainControllerTest extends BaseTest {

    @DataProvider
    private Object[][] httpCodesProvider() throws Exception {
        BlockChain blockChain = new BlockChain();
        blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);

        HashMap<String, String> query1 = new HashMap<String, String>();
        query1.put("WrongParameterName", "Value");

        HashMap<String, String> query2 = new HashMap<String, String>();
        query2.put("Hash-code", "Incorrect hash code");

        HashMap<String, String> query3 = new HashMap<String, String>();
        query3.put("Hash-code", blockChain.getChain().get(0).getHashCode());
        query3.put("AnotherParameter", "Value");

        return new Object[][] {
                {query1, HttpStatus.BAD_REQUEST.value()},
                {query2, HttpStatus.NOT_FOUND.value()},
                {query3, HttpStatus.BAD_REQUEST.value()}
        };
    }

    @Test(dataProvider = "httpCodesProvider")
    public void checkHttpCodes(HashMap<String, String> query, int rightStatus) {
        Response response = sendPost("/getchain", query);

        int responseStatus = response.statusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, rightStatus, "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }

    @Test
    public void checkDoubleHashCode() {
        try {
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            String hashCode = blockChain.getChain().get(0).getHashCode();

            ArrayList<HashMap.SimpleEntry<String, String>> query = new ArrayList<>();
            query.add(new HashMap.SimpleEntry<>("Hash-code", hashCode));
            query.add(new HashMap.SimpleEntry<>("Hash-code", hashCode));

            Response response = sendPost("/getchain", query);

            int responseStatus = response.getStatusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");

            Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkWholeChain() {
        Response response = sendPost("/getchain");

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            JSONArray responseJsonArray = new JSONArray(responseBody.toString());
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            JSONArray rightJsonArray = blockChain.getJsonArray();
            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseJsonArray.toString(), rightJsonArray.toString(), "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @DataProvider
    private Object[][] partOfChainProvider() throws Exception {
        BlockChain blockChain = new BlockChain();
        blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
        Random random = new Random();
        int randomIndex = 0;
        if (blockChain.size() - 2 > 0)
            randomIndex = random.nextInt(blockChain.size() - 2) + 1;

        String[] hashCodes = new String[] {
                blockChain.getChain().get(0).getHashCode(),
                blockChain.getChain().get(randomIndex).getHashCode(),
                blockChain.getChain().get(blockChain.size() - 1).getHashCode()
        };

        return new Object[][] {
                {hashCodes[0], blockChain.getPartOfJsonArray(hashCodes[0]).toString()},
                {hashCodes[1], blockChain.getPartOfJsonArray(hashCodes[1]).toString()},
                {hashCodes[2], blockChain.getPartOfJsonArray(hashCodes[2]).toString()}
        };
    }

    @Test(dataProvider = "partOfChainProvider")
    public void checkPartOfChain(String hashCode, String correctJsonString) {
        HashMap<String, String> query = new HashMap<>();
        query.put("Hash-code", hashCode);

        Response response = sendPost("/getchain", query);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            JSONArray responseJsonArray = new JSONArray(responseBody.toString());
            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseJsonArray.toString(), correctJsonString, "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
