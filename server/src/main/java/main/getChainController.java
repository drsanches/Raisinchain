package main;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class getChainController {

    @RequestMapping(value = "/getchain", method = RequestMethod.POST)
    public String getChain(@RequestParam(value="Hash-code", required = false) String hashCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
            return Application.blockChain.getJsonArray().toString();
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }
}
