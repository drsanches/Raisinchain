package containers

import containersExceptions.TransactionsListException
import org.json.JSONArray
import org.json.JSONException
import spock.lang.Specification

class TransactionsListTest extends Specification {
    /**
     * @author Alexander Voroshilov
     */
    def "getTransactions"() {
        given: "not empty List of transactions"
        ArrayList<Transaction> transactions = new ArrayList<>()
        transactions.add(new Transaction("tr1"))
        transactions.add(new Transaction("tr2"))
        transactions.add(new Transaction("tr3"))

        when: "user creates TransactionsList with this list of transactions"
        TransactionsList transactionsList = new TransactionsList(transactions)

        then: "TransactionsList contains this list of transactions"
        transactionsList.getTransactions().equals(transactions)
    }


   /**
    * @author Anastasiia Shalygina
    */

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

    /**
     * @author Irina Tokareva
     */
    def "removeTransaction: throwing an exception"() {

        given: "Transactions' list and some transaction, which is not is that list"
        TransactionsList transactions = new TransactionsList([new Transaction("t"), new Transaction("r")])
        Transaction tr = new Transaction("a")

        when: "We run method removeTransaction"
        transactions.removeTransaction(tr)

        then: "Method should throw an exception"
        TransactionsListException exception = thrown()
        exception.message == "Transaction list does not contain this transaction."
    }

   /**
    * @author Anastasiia Shalygina
    */
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

    def "Test for equals method" () {
        given: "2 equal list of transactions"
        Transaction tr1 = new Transaction("1transaction")
        Transaction tr2 = new Transaction("2transaction")
        TransactionsList list1 = new TransactionsList()
        list1.addTransaction(tr1)
        list1.addTransaction(tr2)
        TransactionsList list2 = new TransactionsList()
        list2.addTransaction(tr1)
        list2.addTransaction(tr2)

        expect: "method equals return true"
        list1.equals(list2)
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

    /**
     * @author Irina Tokareva
     */
    def "loadFromJsonFile: throwing an exception"() {

        given: "Blockchain, which method getJsonArray throws an exception and a filename"
        String filemane = "TestForLoad.json"
        TransactionsList transactions = new TransactionsList([new Transaction("t"), new Transaction("r")])

        when: "We run method saveToJsonFile"
        transactions.loadFromJsonFile(filemane)

        then: "Method throws an exception"
        JSONException exception = thrown()
    }
}