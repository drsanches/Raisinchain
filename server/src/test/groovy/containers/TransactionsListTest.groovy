package containers

import spock.lang.Specification

class TransactionsListTest extends Specification {
    def "Test for AddTransaction method"() {
        given: "list of transactions"
        Transaction tr = new Transaction("first_transaction")
        ArrayList<Transaction> list = new ArrayList<Transaction>()
        TransactionsList tr_list = new TransactionsList(list)

        when: "we add new transaction to list of transactions"
        Transaction new_tr = new Transaction("new_transaction")
        tr_list.addTransaction(new_tr)

        then: "transaction added to the list"
        true


    }

    def "RemoveTransaction"() {
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
