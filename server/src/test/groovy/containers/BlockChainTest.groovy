package containers

import containersExceptions.BlockChainException
import org.json.JSONException
import spock.lang.Specification

/**
 * @author Ilya Kreshkov, Irina Tokareva
 */


class BlockChainTest extends Specification {
    Random rnd = new Random()

    Block block() {
        String hash = "${rnd.nextInt()}"
        new Block(new TransactionsList([new Transaction("t")]), hash)
    }

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

    def "Ensure that method add new block to chain"() {
        given:"List of blocks and block"
        BlockChain blockChain = new BlockChain()
        Block block = Block.createFirstBlock()

        when:"put values to new chain, add new block"
        blockChain.add(block)

        then: "Method add new block"
        block.equals(blockChain.getChain().last())
    }

    def "Ensure that save and load works correctly"() {
        given: "BlockChain object that contains some blocks"
        TransactionsList transactionsList = new TransactionsList()
        transactionsList.addTransaction(new Transaction("tr1"))
        transactionsList.addTransaction(new Transaction("tr2"))

        BlockChain blockChain = new BlockChain()
        blockChain.add(new Block(transactionsList, "1"))
        blockChain.add(new Block(transactionsList, "2"))
        blockChain.add(new Block(transactionsList, "3"))

        when: "BlockChain saves to file"
        blockChain.saveToJsonFile("BlockChainTestJsonFile.json")

        and: "New BlockChain object loads from file"
        BlockChain newBlockChain = new BlockChain()
        newBlockChain.loadFromJsonFile("BlockChainTestJsonFile.json")

        then: "BlockChain objects are equals"
        newBlockChain.equals(blockChain)
    }

    def "Get json array"() {
        given:
        List<Block> list = new ArrayList<Block>()
        when:
        BlockChain Block_Chain=new BlockChain(list)
        then:
        list.toString().equals(Block_Chain.getJsonArray().toString())
    }
    
    def "getJsonArray: throwing a json exception"() {
        given: "Blockchain, which block's method getJsonObject throws an exception"
        Block block = Mock{getJsonObject() >> { throw new JSONException("Test") }}
        BlockChain blockchain = new BlockChain()
        blockchain.add(block)

        when: "We try to make json object from the blockchain"
        blockchain.getJsonArray()

        then: "Method throws an exception"
        JSONException exception = thrown()
        exception.message == 'Test'
    }

    def "getPartOfJsonArray"() {
        given:"List of blocks"
        List<Block> list1 = [block()]
        List<Block> list3 = [block()]
        List<Block> list4 = [*list3, block()]
        List<Block> list2 = [*list1, *list4,]


        when:"put values to new chain"
        BlockChain Block_Chain= new BlockChain(list2)
        String h = list1.get(0).hashCode
        String y = Block_Chain.getPartOfJsonArray(h).toString()
        BlockChain w = new BlockChain(list4)
        BlockChain q = new BlockChain(y)

        then: "Method getPartChain returns value of field ArrayList<Block>"
        w.equals(q)

    }

    def "getPartOfArray: throwing BlockChainException"() {
        given: "Whole blockchain and hash-code from the user's last block, which is not in that chain"
        List<Block> list = [block(), block(), block()]
        BlockChain blockChain = new BlockChain(list)
        String hashCode = "qwerty"

        when: "We don't find user's block in our chain by given hash-code"
        blockChain.getPartOfJsonArray(hashCode)

        then: "Method throws an exception"
        BlockChainException exception = thrown()
        exception.message == "The chain does not contain this hash"
    }

    def "getPartOfJsonArray: throwing BlockChainException"() {
        given: "Hash-code from the user's last block, which is not in that chain"
//        List<Block> list = [block(), block(), block()]
//        BlockChain blockChain = new BlockChain(list)
        String hashCode = "qwerty"

        and: "Blockchain: method getPartOfChain throws an exception"
        BlockChain blockChain = Mock{ getPartOfChain(hashCode) >> { throw new BlockChainException() } }

        when: "We try to run method getPartOfJsonArray"
        blockChain.getPartOfJsonArray(hashCode)

        then: "Method throws an exception"
        BlockChainException exception = thrown()
        exception.message == "The chain does not contain this hash"
    }
}
