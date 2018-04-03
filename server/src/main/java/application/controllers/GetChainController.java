package application.controllers;

import application.Application;
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


        String responseBody = "";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            switch (parameters.size()) {
                case 0:
                    httpStatus = HttpStatus.OK;
                    responseBody = Application.blockChain.getJsonArray().toString();
                    break;
                case 1:
                    if (parameters.containsKey("Hash-code")) {
                        if (parameters.get("Hash-code").length == 1) {
                            String hashCode = parameters.get("Hash-code")[0];
                            httpStatus = HttpStatus.OK;
                            responseBody = Application.blockChain.getPartOfJsonArray(hashCode).toString();
                        }
                        else {
                            httpStatus = HttpStatus.BAD_REQUEST;
                            responseBody = "The server expects only one hash-code.";
                        }
                    }
                    else {
                        httpStatus = HttpStatus.BAD_REQUEST;
                        responseBody = "Wrong parameter's name.";
                    }
                    break;
                default:
                    httpStatus = HttpStatus.BAD_REQUEST;
                    responseBody = "Wrong count of parameters.";
                    break;
            }
        }
        catch (BlockChainException e) {
            httpStatus = HttpStatus.NOT_FOUND;
            responseBody = e.getMessage();
        }
        finally {
            return ResponseEntity
                    .status(httpStatus)
                    .headers(responseHeaders)
                    .body(responseBody);
        }
    }
}
