package containers


import org.json.JSONException
import org.json.JSONObject
import spock.lang.*

/**
 * @author Irina Tokareva
 */

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
        TransactionsList transactions = new TransactionsList([new Transaction("1"), new Transaction("2")])

        when: "We put its values to new block"
        Block block = new Block(transactions, hash)

        then: "Method getTransactions should return value of field transactions"
        transactions.equals(block.getTransactionsList());
    }

    def "Ensure that method CreateFirstBlock returns right block"() {

        when: "We run method CreateFirstBlock"
        def  FirstBlock = Block.createFirstBlock()

        then: "The first block should fit the right first block"
        String text = "First transaction"
        List<Transaction> TransactionList = [new Transaction(text)]
        TransactionList.equals(FirstBlock.getTransactionsList().getTransactions())

    }

    def "Ensure that method getJsonObject returns right json object"() {

        given: "Create some block"
        Block block = Block.createFirstBlock()

        when: "We make json object from this block"
        JSONObject jsonObject = block.getJsonObject()

        then: "The block that we get from made json object should be original block"
        Block CreatedBlock = new Block(jsonObject.toString())
        CreatedBlock.equals(block)
    }

    def "getJsonObject: throwing an exception"() {
        given: "Block, which transactions' method getJsonObject throws an exception"
        String hashCode = "qwerty"
        TransactionsList transactions = Mock { getJsonArray() >> { throw new JSONException("Test") } }
        Block block = new Block(transactions, hashCode)

        when: "We try to make json object from the block"
        block.getJsonObject()

        then: "Method throws an exception"
        JSONException exception = thrown()
        exception.message == 'Test'
    }
}
