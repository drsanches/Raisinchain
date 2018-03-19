package containers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.*;
import java.io.*;

public class BlockChain {
    private ArrayList<Block> chain;

    public BlockChain() {
        chain = new ArrayList<Block>();
        chain.add(Block.createFirstBlock());
    }

    public BlockChain(ArrayList<Block> ch) {
        chain = ch;
    }

    public void add(Block block) {
        chain.add(block);
    }

    public ArrayList<Block> getChain() {
        return chain;
    }

    public JSONArray getJsonArray() throws  org.json.JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Block block: chain)
            jsonArray.put(block.getJsonObject());

        return jsonArray;
    }

    public ArrayList<Block> getPartOfChain(String hashCode) throws BlockChainException {
        ArrayList<Block> newChain = new ArrayList<Block>();

        Boolean isFind = false;
        for (int i = 0; i < chain.size(); i++) {
            if (isFind)
                newChain.add(chain.get(i));

            if (chain.get(i).getHashCode().equals(hashCode))
                isFind = true;
        }

        if (isFind)
            return newChain;
        else {
            //TODO: Throw some exception
            throw new BlockChainException("Wrong hash");
        }
    }

    public JSONArray getPartOfJsonArray(String hashCode) throws BlockChainException {
        ArrayList<Block> partOfChain = getPartOfChain(hashCode);
        BlockChain partOfBlockChain = new BlockChain(partOfChain);
        return partOfBlockChain.getJsonArray();
    }

    public void saveToJsonFile(String filename) throws org.json.JSONException, java.io.IOException{
        FileWriter writer = new FileWriter(filename);
        writer.write(getJsonArray().toString());
        writer.close();
    }

    public void loadFromJsonFile(String filename) throws java.io.IOException, org.json.JSONException, TransactionException {
        chain.clear();
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            String blockJsonString = jsonArray.get(i).toString();
            Block newBlock = new Block(blockJsonString);
            add(newBlock);
        }
    }
}

class BlockChainException extends Exception {
    public BlockChainException(String message) {
        super(message);
    }
}
