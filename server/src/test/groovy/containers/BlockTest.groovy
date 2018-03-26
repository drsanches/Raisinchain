package containers

import containersExceptions.BlockException
import org.json.JSONException
import org.json.JSONObject
import spock.lang.*

/**
 * @author Irina Tokareva
 */

class BlockTest extends Specification {

    def "Ensure that method CreateFirstBlock returns right block"() {

        when: "We run method CreateFirstBlock"
        def  FirstBlock = Block.createFirstBlock()

        then: "The first block should fit the right first block"
        String text = "First transaction"
        List<Transaction> TransactionList = [new Transaction(text)]
        TransactionList.equals(FirstBlock.getTransactionsList().getTransactions())

    }

    /**
     * @author Alexander Voroshilov
     * */
    def "equals"() {
        given: "list of transactions and hash-code"
        TransactionsList transactionsList = new TransactionsList()
        transactionsList.addTransaction(new Transaction("rt1"))
        transactionsList.addTransaction(new Transaction("rt2"))
        transactionsList.addTransaction(new Transaction("rt3"))
        String hashCode = "hash"

        when: "user creates two blocks with this transactions and hash"
        Block block1 = new Block(transactionsList, hashCode)
        Block block2 = new Block(transactionsList, hashCode)

        then: "they are equal"
        block1.equals(block2)
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

    def "Ensure that method getHashCode returns field hashCode"() {

        given: "String hash-code and list of transactions"
        String hash = "qwerty";
        def transactions = Mock(TransactionsList)

        when: "We put its values to new block"
        Block block = new Block(transactions, hash)

        then: "Method getHashCode should return value of field hashCode"
        hash.equals(block.getHashCode());
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

    def "Block constructor with transactionsList and Hash: throwing an exception"() {

        given: "Hash and transactions list, which length is more than maximum length for transactions in block"
        TransactionsList transactionsList = RandomContainerCreator.createTransactionsList(Block.MAX_TRANSACTIONS_COUNT + 5)
        String hash = "qwerty"

        when: "We try to create a block with these parameters"
        Block block = new Block(transactionsList, hash)

        then: "Constructor throws an exception"
        BlockException exception = thrown()
        exception.message == "Too many transactions. Maximum count of transactions is " + Block.MAX_TRANSACTIONS_COUNT
    }

    def "Block constructor with String json object: throwing an exception"() {
        // Assuming max_transactions_count = 10

        given: "String of json object, where length of transactions list is more than maximum length for transactions in block"
        String str = "{\"Transactions\":[\"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", \"8\", \"9\", \"10\", \"11\"," +
                " \"12\", \"13\",\"14\", \"15\"],\"Hash-code\":\"728335462\"}"

        when: "We try to create a block with this parameter"
        Block block = new Block(str)

        then: "Constructor throws an exception"
        BlockException exception = thrown()
        exception.message == "Too many transactions. Maximum count of transactions is " + Block.MAX_TRANSACTIONS_COUNT
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
