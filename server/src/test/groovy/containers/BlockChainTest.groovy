package containers

import spock.lang.Specification

class BlockChainTest extends Specification {
    Random rnd = new Random()


    def "Ensure that method getChain returns field ArrayList<Block>"() {
        given:"List of blocks"
//        List<Block> Array_List = [block()]
        List<Block> list = new ArrayList<Block>()

        when:"put values to new chain"
        BlockChain Block_Chain= new BlockChain(list)

        then: "Method getChain returns value of field ArrayList<Block>"
        list.equals(Block_Chain.getChain())
    }

    def "Ensure that method getPartChain returns field ArrayList<Block>"() {
        given:"List of blocks"
//        List<Block> Array_List = [block()]
        List<Block> list1 = [block()]
        List<Block> list3 = [block()]
        List<Block> list4 = [*list3, block()]
        List<Block> list2 = [*list1, *list4,]


        when:"put values to new chain"
        BlockChain Block_Chain= new BlockChain(list2)
        String h = list1.get(0).hashCode
        List<Block> y=Block_Chain.getPartOfChain(h)

        then: "Method getPartChain returns value of field ArrayList<Block>"
        list4.equals(y)
    }

    Block block() {
        String hash = "${rnd.nextInt()}"
        new Block(null, hash)
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

    def "Ensure that save and load works correctly"() {
        given: "BlockChain object that contains some blocks"
        BlockChain blockChain = new BlockChain()
        blockChain.add(block())
        blockChain.add(block())
        blockChain.add(block())

        when: "BlockChain saves to file"
        blockChain.saveToJsonFile("BlockChainTestJsonFile.json")

        and: "New BlockChain object loads from file"
        BlockChain newBlockChain = new BlockChain()
        newBlockChain.loadFromJsonFile("BlockChainTestJsonFile.json")

        then: "BlockChain objects are equals"
        newBlockChain.equals(blockChain)
    }
}
