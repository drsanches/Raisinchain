package main;

import containers.TransactionsList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import containers.Transaction;


@RestController
public class AddingTransactionController {

    @RequestMapping(value = "/addtransaction", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(@RequestBody String transaction) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");


        try {
            
            //get a list of transactions, add a new one and save them all to json file
            TransactionsList list = Application.transactionsList;
            list.addTransaction(new Transaction(transaction));
            list.saveToJsonFile(Application.TRANSACTIONS_FILENAME);

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(org.springframework.http.converter.HttpMessageNotReadableException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        catch(java.io.IOException ex){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

