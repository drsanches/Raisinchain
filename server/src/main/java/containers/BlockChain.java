package containers;

import java.util.ArrayList;
import org.json.*;

public class BlockChain {
    private ArrayList<Block> chain;

    public BlockChain() {
        chain = new ArrayList<Block>();
        chain.add(Block.createFirstBlock());
    }

    public BlockChain(ArrayList<Block> ch) {
        chain = ch;
    }

    public ArrayList<Block> getChain() {
        return chain;
    }

    public void add(Block block) {
        chain.add(block);
    }


}
