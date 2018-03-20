package containers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import containersExceptions.BlockChainException;
import containersExceptions.TransactionException;
import org.json.*;
import java.io.*;

/**
 * @authors Alexander Voroshilov, Marina Krylova
 */

public class BlockChain {
    private ArrayList<Block> chain;

    public BlockChain() {
        chain = new ArrayList<Block>();
        chain.add(Block.createFirstBlock());
    }

    public BlockChain(ArrayList<Block> ch) {
        chain = ch;
    }

    public BlockChain(String jsonString) throws TransactionException {
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            String blockJsonString = jsonArray.get(i).toString();
            Block newBlock = new Block(blockJsonString);
            add(newBlock);
        }
    }

    public int sizeOfChain(){
        return chain.size();
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

        Boolean isFound = false;
        for (int i = 0; i < chain.size(); i++) {
            if (isFound)
                newChain.add(chain.get(i));

            if (chain.get(i).getHashCode().equals(hashCode))
                isFound = true;
        }

        if (isFound)
            return newChain;
        else {
            throw new BlockChainException("The chain does not contain this hash");
        }
    }

    public JSONArray getPartOfJsonArray(String hashCode) throws BlockChainException {
        ArrayList<Block> partOfChain = getPartOfChain(hashCode);
        BlockChain partOfBlockChain = new BlockChain(partOfChain);
        return partOfBlockChain.getJsonArray();
    }

    public void saveToJsonFile(String filename) throws org.json.JSONException, java.io.IOException {
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
