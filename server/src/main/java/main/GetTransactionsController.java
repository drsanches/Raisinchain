package main;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import containers.Transaction;
import containers.TransactionsList;
import containersExceptions.BlockChainException;

@RestController
public class GetTransactionsController {

    @RequestMapping(value = "/gettransactions", method = RequestMethod.POST)
    public ResponseEntity getTransaction(WebRequest webRequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webRequest.getParameterMap();

        if ((parameters.size() == 0)) {
            String responseBody = Application.transactionsList.getJsonArray().toString();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(responseHeaders)
                    .body(responseBody);
        }
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(responseHeaders)
                    .body("Wrong count of parameters.");
    }
}