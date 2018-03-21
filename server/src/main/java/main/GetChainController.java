package main;

import containersExceptions.BlockChainException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;

/**
 * @author Alexander Voroshilov
 */
@RestController
public class GetChainController {

    @RequestMapping(value = "/getchain", method = RequestMethod.POST)
    public ResponseEntity getChain(WebRequest webRequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameters = webRequest.getParameterMap();

        try {
            if (parameters.size() == 1)
                if (parameters.containsKey("Hash-code") && (parameters.get("Hash-code").length == 1)) {
                    String hashCode = parameters.get("Hash-code")[0];
                    String responseBody = Application.blockChain.getPartOfJsonArray(hashCode).toString();
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .headers(responseHeaders)
                            .body(responseBody);
                }
            else
                if (parameters.size() == 0) {
                    String responseBody = Application.blockChain.getJsonArray().toString();
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .headers(responseHeaders)
                            .body(responseBody);
                }
        }
        catch(BlockChainException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(responseHeaders)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(responseHeaders)
                .body("Wrong parameter's name or count of parameters.");
    }
}
