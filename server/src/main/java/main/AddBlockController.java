package main;

import containers.Block;
import containers.BlockChain;
import containers.TransactionsList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;
import java.util.List;

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
            if ((parameters.size() == 2)&&(parameters.containsKey("Transactions"))&&(parameters.get("Transactions").length == 1
            &&(parameters.containsKey("Hash"))&&(parameters.get("Hash")).length == 1)) {

                BlockChain blockChain = new BlockChain();
                blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
                TransactionsList transactionsList = new TransactionsList();
                transactionsList.loadFromJsonFile(Application.TRANSACTIONS_FILENAME);

                try {
                    TransactionsList blockTransactions = new TransactionsList(parameters.get("Transactions")[0]);
                }
                catch (Exception exception) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .headers(responseHeaders)
                            .body(exception.getMessage());
                }

                TransactionsList blockTransactions = new TransactionsList(parameters.get("Transactions")[0]);
                String hashCode = parameters.get("Hash")[0];
                Block block = new Block(blockTransactions, hashCode);

                String lastBlockHash = blockChain.getChain().get(blockChain.getChain().size() - 1).calculateHashCode();
                Block bl = blockChain.getChain().get(blockChain.getChain().size() - 1);

                // check whether hash code of the last blockchain's block match hash in received block or not
                if (lastBlockHash.equals(block.getHashCode())) {
                    //check whether all transactions from received block are in the list of transactions or not
                    for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                        if(!transactionsList.contains(blockTransactions.getTransactions().get(i))) {
                            return ResponseEntity
                                    .status(HttpStatus.BAD_REQUEST)
                                    .headers(responseHeaders)
                                    .body("Wrong transactions.");
                        }
                    }
                    for (int i = 0; i < blockTransactions.getTransactions().size(); i++) {
                        transactionsList.removeTransaction(blockTransactions.getTransactions().get(i));
                    }
                    transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);
                    blockChain.add(block);
                    blockChain.saveToJsonFile(Application.BLOCKCHAIN_FILENAME);
                }
                else {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .headers(responseHeaders)
                            .body("Wrong hash code was sent or block where you wanted to connect has already been connected.");
                }
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
