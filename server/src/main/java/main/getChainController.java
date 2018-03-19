package main;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetChainController extends Exception{

    @RequestMapping(value = "/getchain", method = RequestMethod.POST)
    public ResponseEntity getChain(@RequestParam(value="Hash-code", required = false) String hashCode) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
        String responseBody = Application.blockChain.getJsonArray().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(responseBody);
        }
        catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(responseHeaders)
                    .body(e.getMessage());
        }

    }
}
