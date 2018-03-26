package containers;

import containersExceptions.BlockChainException;
import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import java.util.Random;


/**
 * @author Alexander Voroshilov
 * */
public class RandomContainerCreator {
    public final static int MIN_TRANSACTION_LENGTH = 1;
    public final static int MAX_TRANSACTION_LENGTH = 10;
    public final static int MIN_TRANSACTIONS_LIST_LENGTH = 1;
    public final static int MAX_TRANSACTIONS_LIST_LENGTH = 10;
    public final static int MIN_BLOCKCHAIN_LENGTH = 1;
    public final static int MAX_BLOCKCHAIN_LENGTH = 10;

    public static Transaction createTransaction(int length) {
        String transaction = "";
        Random random = new Random();

        for (int i = 0; i < length; i++)
            transaction += String.valueOf(random.nextInt(10));

        try {
            return new Transaction(transaction);
        }
        catch (TransactionException e) {
            return null;
        }
    }

    public static Transaction createTransaction() {
        Random random = new Random();
        int length = random.nextInt(MAX_TRANSACTION_LENGTH - MIN_TRANSACTION_LENGTH) + MIN_TRANSACTION_LENGTH;
        return createTransaction(length);
    }

    public static TransactionsList createTransactionsList(int length) {
        TransactionsList transactionsList = new TransactionsList();

        for (int i = 0; i < length; i++)
            transactionsList.addTransaction(createTransaction());

        return transactionsList;
    }

    public static TransactionsList createTransactionsList() {
        Random random = new Random();
        int length = random.nextInt(MAX_TRANSACTIONS_LIST_LENGTH - MIN_TRANSACTIONS_LIST_LENGTH) + MIN_TRANSACTIONS_LIST_LENGTH;
        return createTransactionsList(length);
    }

    public static Block createBlock(Block previousBlock) {
        Random random = new Random();
        int listLength = random.nextInt(Block.MAX_TRANSACTIONS_COUNT) + 1;
        TransactionsList transactionsList = createTransactionsList(listLength);
        String hashCode = previousBlock.calculateHashCode();

        try {
            return new Block(transactionsList, hashCode);
        }
        catch (BlockException e) {
            return null;
        }
    }

    public static Block createBlockWithRandomHashCode() {
        Random random = new Random();
        int listLength = random.nextInt(Block.MAX_TRANSACTIONS_COUNT) + 1;
        TransactionsList transactionsList = createTransactionsList(listLength);

        String hashCode = "";
        for (int i = 0; i < random.nextInt(10) + 5; i++)
            hashCode += String.valueOf(random.nextInt(10));

        try {
            return new Block(transactionsList, hashCode);
        }
        catch (BlockException e) {
            return null;
        }
    }

    public static BlockChain createBlockChain(int length) {
        BlockChain blockChain = new BlockChain();

        try {
            for (int i = 1; i < length; i++)
                blockChain.add(createBlock(blockChain.getChain().get(i - 1)));

            return blockChain;
        }
        catch (BlockChainException e) {
            return null;
        }
    }

    public static BlockChain createBlockChain() {
        Random random = new Random();
        int length = random.nextInt(MAX_BLOCKCHAIN_LENGTH- MIN_BLOCKCHAIN_LENGTH) + MIN_BLOCKCHAIN_LENGTH;
        return createBlockChain(length);
    }
}
