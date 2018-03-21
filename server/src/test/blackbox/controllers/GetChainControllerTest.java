package controllers;

import containers.BlockChain;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import main.Application;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Random;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

/**
 * @author Alexander Voroshilov
 * */
public class GetChainControllerTest extends BaseTest {

    private Response sendPost(HashMap<String, String> query) {
        Response response = RestAssured
                .given().log().all().queryParams(query)
                .when().post("/getchain");
        response.prettyPrint();
        return response;
    }

    private Response sendPost() {
        Response response = RestAssured
                .given().log().all()
                .when().post("/getchain");
        response.prettyPrint();
        return response;
    }

    @DataProvider
    private Object[][] badRequestProvider() throws Exception {
        BlockChain blockChain = new BlockChain();
        blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);

        HashMap<String, String> query1 = new HashMap<String, String>();
        query1.put("WrongParameterName", "Value");

        HashMap<String, String> query2 = new HashMap<String, String>();
        query2.put("Hash-code", "Incorrect hash code");

        HashMap<String, String[]> query3 = new HashMap<String, String[]>();
        query3.put("Hash-code", new String[]{
                blockChain.getChain().get(0).getHashCode(),
                blockChain.getChain().get(0).getHashCode()
        });

        HashMap<String, String> query4 = new HashMap<String, String>();
        query4.put("Hash-code", blockChain.getChain().get(0).getHashCode());
        query4.put("AnotherParameter", "Value");

        return new Object[][] {{query1}, {query2}, {query3}, {query4}};
    }

    @Test(dataProvider = "badRequestProvider")
    public void checkBadRequest(HashMap<String, String> query) {
        Response response = sendPost(query);
        int responseStatus = response.statusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        Assert.assertEquals(responseHeader, "*", "error");
        Assert.assertEquals(responseStatus, HTTP_BAD_REQUEST, "error");
    }

    @Test
    public void checkWholeChain() {
        Response response = sendPost();
        String responseBody = response.getBody().asString();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        try {
            JSONArray responseJsonArray = new JSONArray(responseBody.toString());
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            JSONArray rightJsonArray = blockChain.getJsonArray();
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
        if (blockChain.sizeOfChain() - 2 > 0)
            randomIndex = random.nextInt(blockChain.sizeOfChain() - 2) + 1;

        String[] hashCodes = new String[] {
                blockChain.getChain().get(0).getHashCode(),
                blockChain.getChain().get(randomIndex).getHashCode(),
                blockChain.getChain().get(blockChain.sizeOfChain() - 1).getHashCode()
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
        Response response = sendPost(query);
        String responseBody = response.getBody().asString();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        try {
            JSONArray responseJsonArray = new JSONArray(responseBody.toString());
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseJsonArray.toString(), correctJsonString, "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
