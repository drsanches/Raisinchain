package main;

import containers.Block;
import containers.Transaction;
import containers.TransactionsList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Anastasiia Shalygina
 */

@RestController
public class MiningController {

    @RequestMapping(value = "/mining", method = RequestMethod.POST)
    public ResponseEntity<String> addTransactionListandBlock(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webrequest.getParameterMap();

        try {
            if ((parameters.size() == 2)&&(parameters.containsKey("Block"))&&(parameters.containsKey("TransactionsList"))&&(parameters.get("Block").length == 1)&&(parameters.get("TransactionsList").length == 1)) {

                try {
                    Block block = new Block(parameters.get("Block")[0]);
                    TransactionsList transactionsList = new TransactionsList(parameters.get("TransactionsList")[0]);
                }
                catch (Exception exception) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .headers(responseHeaders)
                            .body(exception.getMessage());
                }


                Block block = new Block(parameters.get("Block")[0]);
                TransactionsList transactionsList = new TransactionsList(parameters.get("TransactionsList")[0]);
                String ex_hash = block.calculateHashCode();
                Block new_block = new Block(transactionsList, ex_hash);

                new_block.mining();

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body(new_block.getJsonObject().toString());

                //return bad_request if number of parameters or parameter name are wrong
            } else  return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(responseHeaders)
                    .body("Wrong parameter's name or count of parameters.");
        }
        catch(Exception ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(responseHeaders)
                    .body("Something is wrong with our server.");
        }

    }

}
