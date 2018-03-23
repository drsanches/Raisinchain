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

@RestController
public class MiningController {

    @RequestMapping(value = "/gethash", method = RequestMethod.POST)
    public ResponseEntity<String> addTransactionListandBlock(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webrequest.getParameterMap();

        try {
            //return hash-code if webrequest containes only one key "Block" with one String value
            if ((parameters.size() == 1)&&(parameters.containsKey("Block"))&&(parameters.get("Block").length == 1)) {
                Block block = new Block(parameters.get("Block")[0]);
               // block.CalculateHashCode();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body(block.calculateHashCode());

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
