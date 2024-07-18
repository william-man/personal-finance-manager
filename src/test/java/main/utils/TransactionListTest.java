package test.java.main.utils;

import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import main.utils.TransactionList;
import main.utils.TransactionType;
import main.utils.Transaction;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class TransactionListTest {
    
    private TransactionList transactionList;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    // Initialise a new TransactionList with new transactions before each test
    @BeforeEach
    public void setup(){
        transactionList = new TransactionList();
        transaction1 = new Transaction("1", LocalDate.of(2023, 6, 15), 100.0, "Groceries", TransactionType.EXPENSE, "Grocery shopping");
        transaction2 = new Transaction("2", LocalDate.of(2023, 6, 16), 200.0, "Utilities", TransactionType.EXPENSE, "Electricity bill");
        transaction3 = new Transaction("3", LocalDate.of(2023, 6, 17), 300.0, "Salary", TransactionType.INCOME, "Monthly salary");
    }

    //  Test adding a transaction to the list
    @Test
    public void testAddTransaction() throws Exception{
        transactionList.addTransaction(transaction1);
        transactionList.addTransaction(transaction2);
        List<Transaction> transactions = transactionList.getTransactions();

        // check the transactions were added
        assertTrue(transactions.contains(transaction1));
        assertTrue(transactions.contains(transaction2));
        assertEquals(2, transactions.size());
    }

    // Test removing a transaction from the list
    @Test
    public void testRemoveTransaction() throws Exception{
        transactionList.addTransaction(transaction1);
        transactionList.addTransaction(transaction2);
        transactionList.removeTransaction("1");
        List<Transaction> transactions = transactionList.getTransactions();

        // check the list size is reduced by 1 and the transaction no longer exists
        assertEquals(1, transactions.size());
        assertFalse(transactions.contains(transaction1));
        assertTrue(transactions.contains(transaction2));
    }

    // Test the thrown exception if id is not found for removing a transaction
    @Test
    public void testRemoveTransactionNotFound(){
        Exception exception = assertThrows(Exception.class, ()->{
            transactionList.removeTransaction("1");
        });

        // check the thrown exception returns the correct message
        assertEquals("Transaction with ID: 1 is not found.", exception.getMessage());
    }

    // Test transaction is found by id
    @Test
    public void testFindTransactionById() throws Exception{
        transactionList.addTransaction(transaction1);
        transactionList.addTransaction(transaction2);
        Transaction result = transactionList.findTransactionByID("2");

        // check the correct transaction was found
        assertNotNull(result);
        assertEquals(result, transaction2);
    }

    // Test soring by ID 
    @Test
    public void testTransactionListSortById() throws Exception{
        transactionList.addTransaction(transaction1);
        transactionList.addTransaction(transaction2);
        transactionList.addTransaction(transaction3);
        transactionList.sortById();

        // check the transactions are sorted by ID
        List<Transaction> transactions = transactionList.getTransactions();
        assertEquals("1", transactions.get(0).getId());
        assertEquals("2", transactions.get(1).getId());
        assertEquals("3", transactions.get(2).getId());
    }
}
