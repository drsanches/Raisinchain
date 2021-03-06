package application.controllers;


import application.services.*;
import containers.Transaction;
import containers.TransactionsList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


/**
 * @author Marina Krylova
 */
@RestController
public class AddTransactionController {

    @Autowired
    KafkaTransactionProducer producer;

    @RequestMapping(value = "/addtransaction", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(WebRequest webrequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        Map<String, String[]> parameters = webrequest.getParameterMap();


        try {
//            return ok if webrequest containes only one key 'Transaction" with one String value
            if ((parameters.size() == 1)&&(parameters.containsKey("Transaction"))&&(parameters.get("Transaction").length == 1)) {
                //get a list of transactions, add a new one and save them all to json file

                producer.send(parameters.get("Transaction")[0]);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("Transaction was created.");

                //return bad_request if bumber of parameters or parameter name are wrong
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
