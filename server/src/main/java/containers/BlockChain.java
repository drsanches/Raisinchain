package containers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import containersExceptions.BlockChainException;
import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import org.json.*;
import java.io.*;

/**
 * @author Alexander Voroshilov
 */
public class BlockChain {
    private ArrayList<Block> chain;

    public BlockChain() {
        chain = new ArrayList<Block>();
        chain.add(Block.createFirstBlock());
    }

    public BlockChain(ArrayList<Block> ch) throws BlockChainException {
        chain = ch;

        if (!isCorrect()) {
            chain = null;
            throw new BlockChainException("Chain is not correct.");
        }
    }

    public BlockChain(String jsonString) throws TransactionException, JSONException, BlockException, BlockChainException {
        JSONArray jsonArray = new JSONArray(jsonString);

        if (jsonArray.length() == 0)
            throw new BlockChainException("The string representation of the chain does not contain blocks.\n" +
                    "The chain must contain at least one block.");

        chain = new ArrayList<Block>();
        String blockJsonString = jsonArray.get(0).toString();
        Block newBlock = new Block(blockJsonString);
        chain.add(newBlock);

        for (int i = 1; i < jsonArray.length(); i++) {
            blockJsonString = jsonArray.get(i).toString();
            newBlock = new Block(blockJsonString);
            add(newBlock);
        }
    }

    /**
     * @author Marina Krylova
     */
    public int sizeOfChain() {
        return chain.size();
    }

    /**
     * @author Marina Krylova
     */
    @Override
    public boolean equals(Object b) {
        BlockChain ch = (BlockChain) b;
        if (this.sizeOfChain() == ch.sizeOfChain()) {
            for (int i=0; i<ch.sizeOfChain(); i++) {
                if (!chain.get(i).equals(ch.chain.get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    public void add(Block block) throws BlockChainException {
        String a = chain.get(chain.size() - 1).calculateHashCode();
        if (!block.getHashCode().equals(chain.get(chain.size() - 1).calculateHashCode()))
            throw new BlockChainException("Wrong hash-code.");

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
        JSONArray jsonArray = new JSONArray();

        for (Block block: partOfChain)
            jsonArray.put(block.getJsonObject());

        return jsonArray;
    }

    public boolean isCorrect() {
        for (int i = 1; i < sizeOfChain(); i++)
            if (!chain.get(i).isCorrect(chain.get(i - 1)))
                return false;
        return true;
    }

    public void saveToJsonFile(String filename) throws java.io.IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(getJsonArray().toString());
        writer.close();
    }

    public void loadFromJsonFile(String filename) throws java.io.IOException, org.json.JSONException, TransactionException, BlockException, BlockChainException {
        chain.clear();
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(jsonString);

        chain.add(new Block(jsonArray.get(0).toString()));

        for (int i = 1; i < jsonArray.length(); i++) {
            String blockJsonString = jsonArray.get(i).toString();
            Block newBlock = new Block(blockJsonString);
            add(newBlock);
        }
    }
}
