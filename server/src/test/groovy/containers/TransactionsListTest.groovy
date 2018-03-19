package containers

import spock.lang.Specification

class TransactionsListTest extends Specification {
    def "Test for AddTransaction method"() {
        given: "list of transactions"
        TransactionsList tr_list = new TransactionsList()

        when: "we add new transaction to list of transactions"
        Transaction new_tr = new Transaction("new_transaction")
        tr_list.addTransaction(new_tr)

        then: "transaction added to the list"
        tr_list.sizeOfList() == 1

    }

    /*  def "Test for AddTransaction method"() {
           given: "list of transactions"
           TransactionsList tr_list = Mock()
           //int size = tr_list.sizeOfList()
   
           when: "we add new transaction to list of transactions"
           Transaction new_tr = new Transaction("new_transaction")
           1*tr_list.addTransaction() >> 'new_tr'
   
           then: "transaction added to the list"
           tr_list.addTransaction() == 'new_tr'
   
   
       }*/

    def "Test for removeTransaction method"() {
        given: "non-empty list of transactions"
        TransactionsList tr_list = new TransactionsList()
        Transaction new_tr = new Transaction("new_transaction")
        Transaction new_tr2 = new Transaction("second_transaction")
        tr_list.addTransaction(new_tr)
        tr_list.addTransaction(new_tr2)

        when: "we remove a transaction"
        tr_list.removeTransaction(new_tr)

        then: "the list became shorter for one transaction"
        tr_list.sizeOfList() == 1

    }

 

    def "CreateFirstTransactionsList"() {
    }

    def "GetJsonArray"() {
    }

    def "SaveToJsonFile"() {
    }

    def "LoadFromJsonFile"() {
    }
}
