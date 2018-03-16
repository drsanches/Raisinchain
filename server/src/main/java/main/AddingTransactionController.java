package main;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AddingTransactionController {

    @RequestMapping(value = "/addtransaction", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(@RequestParam(value="Transaction", required = true) String trans) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");


        try {
            
            return new ResponseEntity<String>(HttpStatus.OK.toString(), responseHeaders, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST.toString(), responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }


}
