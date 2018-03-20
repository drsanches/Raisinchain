package main;

import containers.TransactionsList;
import containers.Transaction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;


/**
 * @author Marina Krylova
 */

@RestController
public class AddingTransactionController {

    @RequestMapping(value = "/addtransaction", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        Map<String, String[]> parameters = webrequest.getParameterMap();

        try {
            if ((parameters.size() == 1)&&(parameters.containsKey("Transaction"))&&(parameters.get("Transaction").length == 1)) {
                //get a list of transactions, add a new one and save them all to json file
                TransactionsList list = Application.transactionsList;
                list.addTransaction(new Transaction(parameters.get("Transaction")[0]));
                list.saveToJsonFile(Application.TRANSACTIONS_FILENAME);

                return new ResponseEntity<String>(HttpStatus.OK);
            } else  return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        catch(Exception ex){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

