package containers

import org.json.JSONObject
import spock.lang.*

//TO DO: write tests for exceptions

class BlockTest extends Specification {

    def "Ensure that method getHashCode returns field hashCode"() {

        given: "String hash-code and list of transactions"
        String hash = "qwerty";
        def transactions = Mock(TransactionsList)

        when: "We put its values to new block"
        Block block = new Block(transactions, hash)

        then: "Method getHashCode should return value of field hashCode"
        hash.equals(block.getHashCode());
    }

    def "Ensure that method getTransactions returns field transactions"() {

        given: "String hash-code and list of transactions"
        String hash = "qwerty";
        def transcriptions = Mock(TransactionsList)

        when: "We put its values to new block"
        Block block = new Block(transcriptions, hash)

        then: "Method getTransactions should return value of field transactions"
        block.getTransactions() == transcriptions;
    }

    def "Ensure that method CreateFirstBlock returnes right block"() {

        when: "We run method CreateFirstBlock"
        def  FirstBlock = Block.createFirstBlock()

        then: "The first block should fit the right first block"
        String text = "First transaction"
        text.equals(FirstBlock.getTransactionsList().getTransactions().last().getTransaction())

    }

    def "Ensure that method getJsonObject returnes right json object"() {

        given: "Create some block"
        Block block = Block.createFirstBlock()

        when: "We make json object from this block"
        JSONObject jsonObject = block.getJsonObject()

        then: "The block that we get from made json object should be original block"
        Block CreatedBlock = new Block(jsonObject.toString())
        String a = ""
        CreatedBlock.equals(block)
    }
}
