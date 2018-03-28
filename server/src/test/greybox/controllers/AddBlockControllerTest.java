package controllers;

import containers.BlockChain;
import containers.Block;
import containers.TransactionsList;
import containers.Transaction;
import io.restassured.response.Response;
import main.Application;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

/**
 * @author Irina Tokareva
 * */

public class AddBlockControllerTest extends BaseTest {
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

        HashMap<String, String> query2 = new HashMap<String, String>();
        query2.put("Block", "Value");
        query2.put("AnotherParametersName", "Value");

        HashMap<String, String> query3 = new HashMap<String, String>();
        query3.put("Block", "IncorrectJson");

        transactions.addTransaction(new Transaction("ooooooooooooooo"));
        Block block = new Block(transactions, lastBlockHash);
        HashMap<String, String> query4 = new HashMap<String, String>();
        query4.put("Block", block.getJsonObject().toString());

        Block newBlock = new Block(transactions, "IncorrectHash");
        HashMap<String, String> query5 = new HashMap<String, String>();
        query5.put("Transactions", newBlock.getJsonObject().toString());

        return new Object[][] {
                {query1, HttpStatus.BAD_REQUEST.value(), "Wrong parameter's name or count of parameters."},
                {query2, HttpStatus.BAD_REQUEST.value(), "Wrong parameter's name or count of parameters."},
                {query3, HttpStatus.BAD_REQUEST.value(), "A JSONObject text must begin with '{' at 1 [character 2 line 1]"},
                {query4, HttpStatus.BAD_REQUEST.value(), "Wrong transactions."},
                {query5, HttpStatus.BAD_REQUEST.value(), "Wrong hash-code."}
        };
    }

    @Test(dataProvider = "httpCodesProvider")
    public void checkHttpCodes(HashMap<String, String> query, int rightStatus, String rightBody) {
        try {
            Response response = sendPost("/addblock", query);

            int responseStatus = response.statusCode();
            String responseHeader = response.getHeader("Access-Control-Allow-Origin");
            String responseBody = response.getBody().print().toString();

            Assert.assertEquals(responseStatus, rightStatus, "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, rightBody, "error");
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkDouble() {
        try {
            BlockChain blockChain = new BlockChain();
            blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
            String hashCode = blockChain.getChain().get(blockChain.getChain().size() - 1).getHashCode();
            TransactionsList transactionsList = new TransactionsList();
            transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);
            transactionsList.addTransaction(transactionsList.getTransactions().get(0));
            Block block = new Block(transactionsList, hashCode);

            ArrayList<HashMap.SimpleEntry<String, String>> query1 = new ArrayList<>();
            query1.add(new HashMap.SimpleEntry<>("Block", block.getJsonObject().toString()));
            query1.add(new HashMap.SimpleEntry<>("Block", block.getJsonObject().toString()));

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
    public void checkSuccessfulAdding() {
        try {

            Response response1 = sendPost("/getchain");
            String responseBody1 = response1.getBody().asString();
            BlockChain blockChainBefore = new BlockChain(responseBody1);
            int blockChainSizeBefore = blockChainBefore.size();
            Block lastBlock = blockChainBefore.getChain().get(blockChainBefore.getChain().size() - 1);

            Response response2 = sendPost("/gettransactions");
            String responseBody2 = response2.getBody().asString();
            TransactionsList transactionsListBefore = new TransactionsList(responseBody2);
            int transactionsListSizeBefore = transactionsListBefore.size();
            TransactionsList blockTransactions = new TransactionsList();
            blockTransactions.addTransaction(transactionsListBefore.getTransactions().get(0));

            ArrayList<HashMap.SimpleEntry<String, String>> query3 = new ArrayList<>();
            query3.add(new HashMap.SimpleEntry<>("Transactions", blockTransactions.getJsonArray().toString()));
            query3.add(new HashMap.SimpleEntry<>("Block", lastBlock.getJsonObject().toString()));
            Response response3 = sendPost("/mining", query3);
            String responseBody3 = response3.getBody().toString();
            Block createdBlock = new Block(responseBody3);

            ArrayList<HashMap.SimpleEntry<String, String>> query4 = new ArrayList<>();
            query4.add(new HashMap.SimpleEntry<>("Block", createdBlock.getJsonObject().toString()));
            Response response4 = sendPost("/addblock", query4);
            int responseStatus = response4.getStatusCode();
            String responseHeader = response4.getHeader("Access-Control-Allow-Origin");
            String responseBody = response4.getBody().asString();

            Response response5 = sendPost("/getchain");
            String responseBody5 = response5.getBody().asString();
            BlockChain blockChainAfter = new BlockChain(responseBody5);
            int blockChainSizeAfter = blockChainAfter.size();

            Response response6 = sendPost("/gettransactions");
            String responseBody6 = response6.getBody().asString();
            TransactionsList transactionsListAfter = new TransactionsList(responseBody6);
            int transactionsListSizeAfter = transactionsListAfter.size();

            Assert.assertEquals(responseStatus, HttpStatus.OK.value(), "error");
            Assert.assertEquals(responseHeader, "*", "error");
            Assert.assertEquals(responseBody, "Your block has been connected to chain.", "error");
            Assert.assertEquals(blockChainSizeAfter, blockChainSizeBefore + 1, "error");
            Assert.assertEquals(transactionsListSizeAfter, transactionsListSizeBefore - 1, "error");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
