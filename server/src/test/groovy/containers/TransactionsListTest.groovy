package containers

import jdk.nashorn.api.scripting.JSObject
import org.json.JSONArray
import spock.lang.Specification

/**
 * @author Anastasiia Shalygina
 */
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

    def "Test for areListsEqual method method" () {
        given: "2 equal list of transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("2transaction")
        TransactionsList list = new TransactionsList()
        list.addTransaction(tr1)
        list.addTransaction(tr2)
        TransactionsList list1 = new TransactionsList()
        list1 = list

        expect: "method equals return true"
        list.areListsEqual(list,list1) && list.areListsEqual(list1,list)

    }

    def "Ensure that save and load to JSON file works correctly"() {
        given: "non-empty list of transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("2transaction")
        TransactionsList list = new TransactionsList()
        list.addTransaction(tr1)
        list.addTransaction(tr2)


        when: "we save list of transactions to JSON file"
        list.saveToJsonFile("TransactionListTestFile.json")

        and: "list of transactions loads from the file"
        TransactionsList list2 = new TransactionsList()
        list2.loadFromJsonFile("TransactionListTestFile.json")

        then: "lists are equals"
        list2.equals(list)
    }

    def "Test for createFirstTransactionsList method" () {
        given: "first transaction list"
        TransactionsList first_list = TransactionsList.createFirstTransactionsList()

        when: "we create another list with 'first transaction'"
        Transaction first_tr = Transaction.createFirstTransaction()
        TransactionsList tr_list = new TransactionsList()
        tr_list.addTransaction(first_tr)

        then: "lists are equal"
        tr_list.equals(first_list)

    }

}