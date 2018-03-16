package main;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class getChainController {

    @RequestMapping(value = "/getchain", method = RequestMethod.POST)
    public ResponseEntity<String> registration(@RequestParam(value="Hash-code", required = false) String hashCode) {
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