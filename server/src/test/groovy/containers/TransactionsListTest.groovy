package containers

import containersExceptions.TransactionsListException
import org.json.JSONArray
import org.json.JSONException
import spock.lang.Specification

class TransactionsListTest extends Specification {

    private static final TransactionsList LIST1 = RandomContainerCreator.createTransactionsList(2);
    private static final TransactionsList LIST2 = RandomContainerCreator.createTransactionsList(2);
    private static final TransactionsList LIST3 = RandomContainerCreator.createTransactionsList(3);
    private static final TransactionsList LIST4 = LIST3;




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
     * @author Irina Tokareva
     */
    def "Test for contains method" () {
        given: "A list of 1 transaction"
        Transaction tr1 = new Transaction("1transaction")
        TransactionsList list = new TransactionsList([tr1])

        expect: "method contains return true"
        list.contains(tr1)

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
        tr_list.size() == 1

    }

    def "Test for removeTransaction method"() {
        given: "non-empty list of transactions"
        TransactionsList tr_list = new TransactionsList()
        Transaction new_tr = new Transaction("new_transaction")
        Transaction new_tr2 = new Transaction("second_transaction")
        tr_list.addTransaction(new_tr)
        tr_list.addTransaction(new_tr2)

        when: "we remove a transaction"
        tr_list.removeTransaction(new Transaction("new_transaction"))

        then: "the list became shorter for one transaction"
        tr_list.size() == 1

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
        list.size() == 2
    }

    /**
     * @author Marina Krylova
     */

    def "Test for equals method" () {
        given: "2 equal list of transactions"
        TransactionsList tr1 = LIST3;

        expect: "compare different lists"

        result == tr1.equals(param)

        where: "parameters and result"
        param | result
        LIST1 | false
        LIST2 | false
        LIST4 | true

    }

    /**
     * @author Anastasiia Shalygina
     */

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
     * @author Irina Tokareva
     */
    def "loadFromJsonFile: throwing an exception"() {

        given: "Name of file with incorrect json"
        String filemane = "TestForLoad.json"
        TransactionsList transactions = new TransactionsList()

        when: "We run method loadFromJsonFile"
        transactions.loadFromJsonFile(filemane)

        then: "Method throws an exception"
        JSONException exception = thrown()
    }

    /**
     * @author Irina Tokareva
     */
    def "TransactionsList constructor: throwing json exception"() {

        given: "Incorrect json"
        String str = "sdfghjkl"

        when: "We try to create TransactionsList object"
        TransactionsList trL = new TransactionsList(str)

        then: "Constructor throws an exception"
        JSONException exception = thrown()
    }
}