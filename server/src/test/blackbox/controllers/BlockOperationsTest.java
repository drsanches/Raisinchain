package controllers;

import containers.Block;
import containers.BlockChain;
import containers.TransactionsList;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockOperationsTest extends controllers.BaseTest {

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
