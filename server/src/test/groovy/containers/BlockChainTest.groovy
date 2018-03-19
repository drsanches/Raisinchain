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

    def "Get json array"(){
        given:
        List<Block> list = new ArrayList<Block>()
        String a
        when:
        BlockChain Block_Chain=new BlockChain(list)
        then:
        a.equals(Block_Chain.getJsonArray().toString())
    }
}
