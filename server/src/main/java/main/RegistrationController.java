package main;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationController {

    @Mapping(value = "/reg")
    public ResponseEntity<String> registrationSubmit(@RequestParam(value="Nickname", required = true) String nickname,
                                       @RequestParam(value="PublicKey", required = true) String publicKey,
                                       @RequestParam(value="PrivateKey", required = true) String privateKey,
                                       @RequestParam(value="PublicKeyHash", required = true) String publicKeyHash) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");


        try {
            writeNewUserToDB(nickname, publicKey, privateKey, publicKeyHash);
            return new ResponseEntity<String>(HttpStatus.OK.toString(), responseHeaders, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST.toString(), responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    public void writeNewUserToDB(String nickname, String publicKey,
                                 String privateKey, String publicKeyHash) throws Exception {
        String nicknameHash = String.valueOf(nickname.hashCode());

        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase( "raisinchain" );
        MongoCollection collection = db.getCollection("wallets");

        Document user = new Document("_id", nicknameHash)
                .append("PublicKey", publicKey)
                .append("PrivateKey", privateKey)
                .append("PublicKeyHash", publicKeyHash);

        collection.insertOne(user);
    }
}