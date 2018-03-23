package controllers;



import containers.TransactionsList;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import main.Application;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Marina Krylova
 * */

public class AddTransactionControllerTest extends BaseTest {


    @DataProvider
    private Object[][] badRequestProvider() throws Exception {

        TransactionsList transactionsList = new TransactionsList();
        transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);

        HashMap<String, String> query1 = new HashMap<String, String>();
        query1.put("WrongParameterName", "Value");

        HashMap<String, String> query2 = new HashMap<String, String>();
        query2.put("Transaction", "Value1");
        query2.put("Second transaction", "Value2");


        return new Object[][]{{query1}, {query2}};

    }

    @DataProvider
    private Object[][] transactionProvider() throws Exception {

        TransactionsList transactionsList = new TransactionsList();
        transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);

        HashMap<String, String> query = new HashMap<String, String>();
        query.put("Transaction", "ValueOfTransaction");

        return new Object[][]{{query}};

    }

    @Test(dataProvider = "badRequestProvider")
    public void checkBadRequest(HashMap<String, String> query) {

        Response response = sendPost("/addtransaction", query);
        int responseStatus = response.statusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        Assert.assertEquals(responseHeader, "*", "error: wrong header");
        Assert.assertEquals(responseStatus, HTTP_BAD_REQUEST, "error: wrong request");
    }

    @Test(dataProvider = "transactionProvider")
    public void checkOkRequest(HashMap<String, String> query) {

        try {

            TransactionsList trList = new TransactionsList();

            trList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            int size1 = trList.sizeOfList() + 1;

            Response response = sendPost("/addtransaction", query);


            trList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            int size2 = trList.sizeOfList();

            int responseStatus = response.statusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");

            Assert.assertEquals(responseHeader, "*", "error: wrong header");
            Assert.assertEquals(size2, size1, "error: transaction was not written");
            Assert.assertEquals(responseStatus, HTTP_OK, "error");


        } catch (Exception e) {

            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

}

//    @Test(dataProvider = "transactionProvider")
//    public void checkInternalServerErrorRequest(HashMap<String, String> query){
//
//        TO DO: write a test for HTTP_INTERNAL_SERVER_ERROR case
//
//    }
//
//
//}
