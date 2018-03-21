package containers

import containersExceptions.TransactionException
import spock.lang.Specification

/**
 * @author Anastasiia Shalygina
 */

class TransactionTest extends Specification {
    def "Ensure that method getTransaction returns transaction"() {
        given:"the string"
        String str = "test_transaction"

        when: "we create a transaction"
        Transaction test_tr = new Transaction(str)

        then: "method getTransaction returns the same not null string"
        (str.equals(test_tr.getTransaction())) && (test_tr.getTransaction() != null)

  }

    def "Test for createFirstTransaction method" () {
        given: "first transaction"
        Transaction first_tr = Transaction.createFirstTransaction()

        expect: "first transaction is a string and not null"
        first_tr != null
    }

    def "Test for equals method" () {
        given: "2 equal transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("1transaction")

        expect: "method equals return true"
        tr1.equals(tr2)

    }

    /**
     * @author Alexander Voroshilov
     */
    def "Transaction constructor: throwing an exception"() {
        given: "null string of transaction"
        String transactionString = null

        when: "user creates transaction with this string"
        Transaction transaction = new Transaction(transactionString)

        then: "method throws an exception"
        TransactionException exception = thrown()
    }
}
