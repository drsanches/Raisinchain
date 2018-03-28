package controllers

import containers.TransactionsList;
import containers.Transaction;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static java.net.HttpURLConnection.HTTP_OK;


class TransactionsOperationsTest extends controllers.BaseTest {

    @Test
    public void checkOkRequest() {

        //send a request to /gettransactions to get an actual list of transactions
        Response responseOfList1 = sendPost("/gettransactions");
        String responseHeader = responseOfList1.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseOfList1.statusCode(), HTTP_OK, "error");
        Assert.assertEquals(responseHeader, "*", "error: wrong header");

        String List1 = responseOfList1.getBody().asString();
        TransactionsList list1 = new TransactionsList(List1);
        int sizeOfList1 = list1.size();

        //create a new transaction
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("Transaction", "The Beatles");

        //send the transaction to /addtransaction
        Response addTransactionResponse = sendPost("/addtransaction", query);
        responseHeader = addTransactionResponse.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(addTransactionResponse.statusCode(), HTTP_OK, "error");
        Assert.assertEquals(responseHeader, "*", "error: wrong header");


        //send a request to /gettransactions for a new list of transactions, it must be changed
        Response responseOfList2 = sendPost("/gettransactions");
        responseHeader = responseOfList2.getHeader("Access-Control-Allow-Origin");

        Assert.assertEquals(responseOfList2.statusCode(), HTTP_OK, "error");
        Assert.assertEquals(responseHeader, "*", "error: wrong header");

        String List2 = responseOfList2.getBody().asString();
        TransactionsList list2 = new TransactionsList(List2);

        int sizeOfList2 = list2.size();

        String tr = query.get("Transaction");

        boolean isTransactionAdded = list2.contains(new Transaction(tr));

        Assert.assertEquals(sizeOfList2 , sizeOfList1 + 1, "error: transaction was not written");
        Assert.assertEquals(isTransactionAdded , true, "error: transaction was not written");




    }
}


