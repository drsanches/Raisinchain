package containers

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
}
