package containers

import jdk.nashorn.api.scripting.JSObject
import org.json.JSONArray
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

 

    /*def "CreateFirstTransactionsList"() {
    }*/

    def "Test for getJsonArray method"() {
        given: "non-empty list of transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("2transaction")
        TransactionsList list = new TransactionsList()
        list.addTransaction(tr1)
        list.addTransaction(tr2)

        when: "we apply the method to the list"
        Object jarray = list.getJsonArray()

        then: "returns non-empty JASONArray"
        jarray instanceof JSONArray

    }

    def "Test for sizeOfList method" () {
        given: "list of 2 transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("2transaction")
        TransactionsList list = new TransactionsList()
        list.addTransaction(tr1)
        list.addTransaction(tr2)

        expect: "size of list = 2"
        list.sizeOfList() == 2
    }

    def "Test for saveToJsonFile method"() {

    }

    def "loadFromJsonFile"() {
    }
}
