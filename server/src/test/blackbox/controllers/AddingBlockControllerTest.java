package controllers;

import containers.BlockChain;
import containers.Transaction;
import containers.TransactionsList;
import io.restassured.response.Response;
import main.Application;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

/**
 * @author Irina Tokareva
 * */

public class AddingBlockControllerTest extends BaseTest {
    @DataProvider
    private Object[][] httpCodesProvider() throws Exception {
        BlockChain blockChain = new BlockChain();
        blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
        String lastBlockHash = blockChain.getChain().get(blockChain.getChain().size() - 1).calculateHashCode();
        TransactionsList transactionsList = new TransactionsList();
        transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
        TransactionsList transactions = new TransactionsList();
        transactions.addTransaction(transactionsList.getTransactions().get(0));

        HashMap<String, String> query1 = new HashMap<String, String>();
        query1.put("WrongParameterName", "Value");
        query1.put("Hash", "Value");

        HashMap<String, String> query2 = new HashMap<String, String>();
        query2.put("Transactions", "Value");
        query2.put("WrongParameterName", "Value");

        HashMap<String, String> query3 = new HashMap<String, String>();
        query3.put("Transactions", "Value");
        query3.put("Hash", "Value");
        query3.put("AnotherParametersName", "Value");

        HashMap<String, String> query4 = new HashMap<String, String>();
        query4.put("Transactions", "[\"hello\",\"Hello, world!\",\"Hello, world!\",\"transaction7\"");
        query4.put("Hash", lastBlockHash);

        HashMap<String, String> query5 = new HashMap<String, String>();
        query5.put("Transactions", "[\"ooooooooooooo\"]");
        query5.put("Hash", lastBlockHash);

        HashMap<String, String> query6 = new HashMap<String, String>();
        query6.put("Transactions", transactions.getJsonArray().toString());
        query6.put("Hash", "IncorrectHash");

        return new Object[][] {
                {query1, HttpStatus.BAD_REQUEST.value(), "Wrong parameter's name or count of parameters."},
                {query2, HttpStatus.BAD_REQUEST.value(), "Wrong parameter's name or count of parameters."},
                {query3, HttpStatus.BAD_REQUEST.value(), "Wrong parameter's name or count of parameters."},
                {query4, HttpStatus.BAD_REQUEST.value(), "Expected a ',' or ']' at 55 [character 56 line 1]"},
                {query5, HttpStatus.BAD_REQUEST.value(), "Wrong transactions."},
                {query6, HttpStatus.OK.value(), "Wrong hash code was sent or block where you wanted to connect has already been connected."}
        };
    }

    @Test(dataProvider = "httpCodesProvider")
    public void checkHttpCodes(HashMap<String, String> query, int rightStatus, String rightBody) {
        Response response = sendPost("/addblock", query);

        int responseStatus = response.statusCode();
        String responseHeader = response.getHeader("Access-Control-Allow-Origin");
        String responseBody = response.getBody().print().toString();

        Assert.assertEquals(responseStatus, rightStatus, "error");
        Assert.assertEquals(responseHeader, "*", "error");
        Assert.assertEquals(responseBody, rightBody, "error");
    }

    @Test
    public void checkDoubleHash() {
        try {
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            String hashCode = blockChain.getChain().get(0).getHashCode();
            TransactionsList transactionsList = new TransactionsList();
            transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            transactionsList.addTransaction(transactionsList.getTransactions().get(0));

            ArrayList<HashMap.SimpleEntry<String, String>> query1 = new ArrayList<>();
            query1.add(new HashMap.SimpleEntry<>("Transactions", transactionsList.getJsonArray().toString()));
            query1.add(new HashMap.SimpleEntry<>("Hash", hashCode));
            query1.add(new HashMap.SimpleEntry<>("Hash", hashCode));

            Response response = sendPost("/addblock", query1);

            int responseStatus = response.getStatusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");
            String responseBody = response.getBody().print().toString();

            Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, "Wrong parameter's name or count of parameters.", "error");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkDoubleTransactions() {
        try {
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            String hashCode = blockChain.getChain().get(0).getHashCode();
            TransactionsList transactionsList = new TransactionsList();
            transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            transactionsList.addTransaction(transactionsList.getTransactions().get(0));

            ArrayList<HashMap.SimpleEntry<String, String>> query2 = new ArrayList<>();
            query2.add(new HashMap.SimpleEntry<>("Transactions", transactionsList.getJsonArray().toString()));
            query2.add(new HashMap.SimpleEntry<>("Transactions", transactionsList.getJsonArray().toString()));
            query2.add(new HashMap.SimpleEntry<>("Hash", hashCode));

            Response response = sendPost("/addblock", query2);

            int responseStatus = response.getStatusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");
            String responseBody = response.getBody().print().toString();

            Assert.assertEquals(responseStatus, HttpStatus.BAD_REQUEST.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, "Wrong parameter's name or count of parameters.", "error");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkSuccessfulAdding() {
        try {

            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            String lastBlockHash = blockChain.getChain().get(blockChain.getChain().size() - 1).calculateHashCode();
            int chainSize = blockChain.sizeOfChain();

            TransactionsList transactionsList = new TransactionsList();
            transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            TransactionsList transactions = new TransactionsList();
            transactions.addTransaction(transactionsList.getTransactions().get(0));
            int transactionsListSize = transactionsList.sizeOfList();

            ArrayList<HashMap.SimpleEntry<String, String>> query = new ArrayList<>();
            query.add(new HashMap.SimpleEntry<>("Transactions", transactions.getJsonArray().toString()));
            query.add(new HashMap.SimpleEntry<>("Hash", lastBlockHash));

            Response response = sendPost("/addblock", query);

            int responseStatus = response.getStatusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");
            String responseBody = response.getBody().asString();

            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, "Your block has been connected to chain.", "error");
//            Assert.assertEquals(blockChain.sizeOfChain(), chainSize + 1, "error");
//            Assert.assertEquals(transactionsList.sizeOfList(), transactionsListSize - 1, "error");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
