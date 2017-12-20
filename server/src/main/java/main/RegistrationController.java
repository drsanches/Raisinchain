package main;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationController {

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public HttpStatus registration(@RequestParam(value="Nickname", required = false) String nickname,
                                   @RequestParam(value="PublicKey", required = false) String publicKey,
                                   @RequestParam(value="PrivateKey", required = false) String privateKey,
                                   @RequestParam(value="PublicKeyHash", required = false) String publicKeyHash) {
        try {
            writeNewUserToDB(nickname, publicKey, privateKey, publicKeyHash);
            return HttpStatus.OK;
        }
        catch(Exception e) {
            return HttpStatus.UNAUTHORIZED;
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