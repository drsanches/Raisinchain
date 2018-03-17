package containers
import spock.lang.*

import javax.validation.constraints.Null

class BlockTest extends Specification {
    def "Ensure that method getHashCode returns field hashCode"() {

        given: "String hash-code and list of transactions"
        String hash = "qwerty";
        def transactions = Mock(TransactionsList);

        when: "We put its values to new block"
        Block block = new Block(transactions, hash);

        then: "Method getHashCode should return value of field hashCode"
        hash.equals(block.getHashCode());
    }

    def "Ensure that method getTransactions returns field transactions"() {

        given: "String hash-code and list of transactions"
        String hash = "qwerty";
        def transcriptions = Mock(TransactionsList);

        when: "We put its values to new block"
        Block block = new Block(transcriptions, hash);

        then: "Method getTransactions should return value of field transactions"
        block.getTransactions() == transcriptions;
    }

    def "CreateFirstBlock"() {
    }
}
