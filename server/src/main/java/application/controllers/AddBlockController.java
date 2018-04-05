package application.controllers;

import application.Application;
import application.services.KafkaBlockProducer;
import containers.Block;
import containers.BlockChain;
import containers.TransactionsList;
import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import containersExceptions.TransactionsListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @authors Irina Tokareva, Marina Krylova
 */

@RestController
public class AddBlockController {


    @Autowired
    KafkaBlockProducer blockProducer;

    @RequestMapping(value = "/addblock", method = RequestMethod.POST)
    public ResponseEntity<String> addBlock(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webrequest.getParameterMap();

        if (Application.blockChain.size() == 1){
            String firstBlock = Application.blockChain.getChain().get(0).getJsonObject().toString();
            blockProducer.send(firstBlock);
        }
        try {
            if ((parameters.size() == 0)) {

                BlockChain blockChain = Application.blockChain;
                TransactionsList transactionsList = Application.transactionsList;

                if (transactionsList.size() >= 3) {

                    TransactionsList firstThreeTransactions = new TransactionsList();
                    for (int i = 0; i < 3; i++) {
                        firstThreeTransactions.addTransaction(transactionsList.getTransactions().get(i));
                    }

                    String hashOfLastBlock = blockChain.getChain().get(blockChain.size() - 1).calculateHashCode();
                    Block new_block = new Block(firstThreeTransactions, hashOfLastBlock);
                    new_block.mining();

                    TransactionsList blockTransactions = new_block.getTransactionsList();

                    for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                        if (!transactionsList.contains(blockTransactions.getTransactions().get(i))) {
                            return ResponseEntity
                                    .status(HttpStatus.BAD_REQUEST)
                                    .headers(responseHeaders)
                                    .body("Wrong transactions.");
                        }
                    }

                    blockProducer.send(new_block.getJsonObject().toString());

                    for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                        transactionsList.removeTransaction(blockTransactions.getTransactions().get(i));
                    }
                    transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);

                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .headers(responseHeaders)
                            .body("Your block has been connected to chain.");
                } else return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(responseHeaders)
                        .body("Request cannot contain parameters.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(responseHeaders)
                .body("Wrong parameter's name or count of parameters.");
    }
}
