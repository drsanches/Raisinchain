package controllers;

import containers.TransactionsList;
import io.restassured.response.Response;
import main.Application;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;

/**
 * @author Ilya
 * */

public class GetTransactionsControllerTest extends BaseTest {

    @Test
    public void checkData() {
        Response response = sendPost("/gettransactions");

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().asString();

        try {
            TransactionsList transactionsList = new TransactionsList();
            transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            String rightString = transactionsList.getJsonArray().toString();
            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, rightString, "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkCountOfParam() {
        HashMap<String, String> query = new HashMap<>();
        query.put("Parameter", "Value");

        Response response = sendPost("/gettransactions", query);

        int responseStatus = response.getStatusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
        Assert.assertEquals(responseHeader, "*", "error");
    }
}
