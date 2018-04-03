package application.controllers;

import application.Application;
import containers.Block;
import containers.BlockChain;
import containers.TransactionsList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;

/**
 * @author Irina Tokareva
 */

@RestController
public class AddBlockController {

    @RequestMapping(value = "/addblock", method = RequestMethod.POST)
    public ResponseEntity<String> addBlock(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webrequest.getParameterMap();

        try {
            if ((parameters.size() == 1)&&(parameters.containsKey("Block"))&&(parameters.get("Block").length == 1)) {

                BlockChain blockChain = Application.blockChain;
                TransactionsList transactionsList = Application.transactionsList;

                try {
                    Block block = new Block(parameters.get("Block")[0]);
                }
                catch (Exception exception) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .headers(responseHeaders)
                            .body(exception.getMessage());
                }

                Block block = new Block(parameters.get("Block")[0]);
                TransactionsList blockTransactions = block.getTransactionsList();

                for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                    if(!transactionsList.contains(blockTransactions.getTransactions().get(i))) {
                        return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .headers(responseHeaders)
                                .body("Wrong transactions.");
                    }
                }

                try {
                    blockChain.add(block);
                    blockChain.saveToJsonFile(Application.BLOCKCHAIN_FILENAME);
                }
                catch (Exception exception) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .headers(responseHeaders)
                            .body(exception.getMessage());
                }

                for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                    transactionsList.removeTransaction(blockTransactions.getTransactions().get(i));
                }
                transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("Your block has been connected to chain.");
            }
            else return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(responseHeaders)
                    .body("Wrong parameter's name or count of parameters.");
        }
        catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(responseHeaders)
                    .body(exception.getMessage());
        }
    }
}
