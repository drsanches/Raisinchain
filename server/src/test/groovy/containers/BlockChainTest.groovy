package containers

import spock.lang.Specification

class BlockChainTest extends Specification {
    def "Ensure that method getChain returns field ArrayList<Block>"() {
        given:"List of blocks"
        ArrayList<Block> Array_List =Mock()
        when:"put values to new chain"
        BlockChain Block_Chain= new BlockChain(Array_List)
        then: "Method getChain returns value of field ArrayList<Block>"
        Array_List.equals(Block_Chain.getChain())
    }

    def "Ensure that method add new block to chain"() {
        given:"List of blocks and block"
        Block block =Mock()
        when:"put values to new chain, add new block"
        BlockChain Block_Chain= new BlockChain()
        Block_Chain.add(block)
        then: "Method add new block"
        block.equals(Block_Chain.getChain().last())

    }
}
