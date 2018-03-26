package containers

import containersExceptions.BlockChainException
import main.Application
import org.json.JSONException
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Ilya Kreshkov
 */

class BlockChainTest extends Specification {

    private static final BLOCKCHAIN1 = RandomContainerCreator.createBlockChain(3);
    private static final BLOCKCHAIN2 = BLOCKCHAIN1;
    private static final BLOCKCHAIN3 = RandomContainerCreator.createBlockChain(3)
    private static final BLOCKCHAIN4 = RandomContainerCreator.createBlockChain(5)

    /**
     * @author Marina Krylova
     */
    def "size"() {
        given: "BlockChain with the first block"
        BlockChain blockChain = new BlockChain()

        when: "user adds one more block into blockchain"
        TransactionsList tr = new TransactionsList()
        blockChain.add(new Block(new TransactionsList("[transaction]"), blockChain.chain[0].calculateHashCode()))

        then: "size of blockchain and size of chain are equals"
        blockChain.sizeOfChain() == 2
    }

    /**
     * @author Marina Krylova
     */
    def "equals"() {
        given: "Blockchain of 3 blocks"
        BlockChain blockChain = BLOCKCHAIN1;


        expect: "compare this blockchain with params"

        result == blockChain.equals(param)

        where: "list of parameters and result"
        param       | result
        BLOCKCHAIN2 | true  //same blockchain
        BLOCKCHAIN3 | false //different blockchain with the same length
        BLOCKCHAIN4 | false //blockchain of different length

    }

    /**
     * @author Marina Krylova
     */
    def "Ensure that method adds new block to chain"() {

        given:"Blockchain and a block"
        BlockChain blockChain = new BlockChain()
        Block b = new Block(new TransactionsList("[transaction]"), blockChain.chain[0].calculateHashCode())

        when:"add the block to the chain"
        blockChain.add(b)


        then: "Method adds a new block"
        b.equals(blockChain.getChain().last())
    }

    /**
     * @author Alexander Voroshilov
     */
    def "Ensure that method getChain returns field ArrayList<Block>"() {
        given:"List of blocks"
//        List<Block> Array_List = [block()]
        List<Block> list = new ArrayList<Block>()

        when:"put values to new chain"
        BlockChain Block_Chain = new BlockChain(list)

        then: "Method getChain returns value of field ArrayList<Block>"
        list.equals(Block_Chain.getChain())
    }

    def "Get json array"() {
        given:
        List<Block> list = new ArrayList<Block>()
        when:
        BlockChain Block_Chain = new BlockChain(list)
        then:
        list.toString().equals(Block_Chain.getJsonArray().toString())
    }

    /**
     * @author Marina Krylova
     */
    def "Ensure that save and load works correctly"() {
        given: "BlockChain object that contains some blocks"
        BlockChain blockChain = RandomContainerCreator.createBlockChain(3)

        when: "BlockChain saves to file"
        blockChain.saveToJsonFile("BlockChainTestJsonFile.json")

        and: "New BlockChain object loads from file"
        BlockChain newBlockChain = new BlockChain()
        newBlockChain.loadFromJsonFile("BlockChainTestJsonFile.json")

        then: "BlockChain objects are equals"
        newBlockChain.equals(blockChain)
    }

    def "Ensure that method getPartChain returns field ArrayList<Block>"() {

        given:"List of blocks"
        BlockChain blockChain = RandomContainerCreator.createBlockChain(3)

        when:"get a part of the chain"

        String h = blockChain.chain.get(0).hashCode

        ArrayList<Block> y = blockChain.getPartOfChain(h)
        ArrayList<Block> list = new ArrayList<Block>()
        list.add(blockChain.chain.get(1))
        list.add(blockChain.chain.get(2))

        then: "Method getPartChain returns a right part of the chain"
        list.equals(y)

    }

    def "getPartOfJsonArray"() {
        given:"List of blocks"
        BlockChain blockChain = RandomContainerCreator.createBlockChain(3)

        when:"put values to new chain"
        String h = blockChain.chain.get(0).hashCode
        String y = blockChain.getPartOfJsonArray(h).toString()

        List<Block> w = blockChain.getPartOfChain(h)
        List<Block> q = new ArrayList<Block>()
        q.add(blockChain.chain.get(1))
        q.add(blockChain.chain.get(2))


        then: "Method getPartChain returns value of field ArrayList<Block>"
        w.equals(q)

    }

    /**
     * @author Alexander Voroshilov
     */
    def "getJsonArray: throwing a json exception"() {
        given: "blockchain with block that throws an exception in getJsonObject method"
        BlockChain blockChain = RandomContainerCreator.createBlockChain()
        def block1 = Mock(Block)
        block1.getHashCode() >> blockChain.getChain().get(blockChain.sizeOfChain() - 1).calculateHashCode()
        block1.getJsonObject() >> { throw new org.json.JSONException("Test") }
        blockChain.add(block1)

        when: "user try to make json object from the blockchain"
        blockChain.getJsonArray()

        then: "method throws an exception"
        JSONException exception = thrown()
        exception.message.equals('Test')
    }

    /**
     * @author Alexander Voroshilov
     */
    def "getPartOfArray: throwing BlockChainException"() {
        given: "Whole blockchain and invalid hash-code"
        BlockChain blockChain = RandomContainerCreator.createBlockChain()
        String hashCode = "Invalid hash-code"

        when: "user try to get part of the chain for this hash"
        blockChain.getPartOfJsonArray(hashCode)

        then: "method throws an exception"
        BlockChainException exception = thrown()
    }

    /**
     * @author Irina Tokareva
     */
    def "getPartOfJsonArray: throwing BlockChainException"() {
        given: "Whole blockchain and hash-code from the user's last block, which is not in that chain"
        BlockChain blockChain = new BlockChain();
        blockChain.loadFromJsonFile(Application.BLOCKCHAIN_FILENAME);
        String hashCode = "qwerty"

        when: "We try to run method getPartOfJsonArray"
        blockChain.getPartOfJsonArray(hashCode)

        then: "Method throws an exception"
        BlockChainException exception = thrown()
        exception.message == "The chain does not contain this hash"
    }

    /**
     * @author Irina Tokareva
     */
    def "saveToJsonFile: throwing an exception"() {

        given: "Blockchain, which method getJsonArray throws an exception and a filename"
        String filemane = "BlockChainTestJsonFile.json"
        BlockChain blockChain = new BlockChain()
        Block mockedBlock = Mock{getJsonObject() >> { throw new JSONException("Test") }
            getHashCode() >> blockChain.getChain().get(0).calculateHashCode()}
        blockChain.add(mockedBlock)

        when: "We run method saveToJsonFile"
        blockChain.saveToJsonFile(filemane)

        then: "Method throws an exception"
        JSONException exception = thrown()
        exception.message == "Test"
    }

    /**
     * @authors Irina Tokareva, Marina Krylova
     */
    def "loadFromJsonFile: throwing an exception"() {

        given: "Blockchain, which method getJsonArray throws an exception and a filename"
        String filemane = "TestForLoad.json"
        BlockChain blockChain = RandomContainerCreator.createBlockChain(3)


        when: "We run method saveToJsonFile"
        blockChain.loadFromJsonFile(filemane)

        then: "Method throws an exception"
        JSONException exception = thrown()
    }
}
