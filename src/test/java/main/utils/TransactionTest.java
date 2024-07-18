package test.java.main.utils;


import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;

import main.utils.Transaction;
import main.utils.TransactionType;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class TransactionTest {
    Transaction transaction1;
    Transaction transaction2;
    
    // Initialise transactions before test
    @BeforeEach
    public void setup(){
        transaction1 = new Transaction("1", LocalDate.of(2023, 6, 15), 100.0, "Groceries", TransactionType.EXPENSE, "Grocery shopping");
        transaction2 = new Transaction("2", LocalDate.of(2023, 6, 16), 200.0, "Utilities", TransactionType.INCOME, "Electricity bill");
    }
    
    // Test creation and getters for a transaction
    @Test
    public void testTransactionCreation(){
        String id = "1";
        LocalDate date = LocalDate.of(2023,6,15);
        double amount = 100.0;
        String category = "Groceries";
        TransactionType type = TransactionType.EXPENSE;
        String description = "Grocery shopping";

        assertEquals(id, transaction1.getId());
        assertEquals(date, transaction1.getDate());
        assertEquals(amount, transaction1.getAmount());
        assertEquals(category, transaction1.getCategory());
        assertEquals(type, transaction1.getTransactionType());
        assertEquals(description, transaction1.getDescription());
    }

    // Test the toString method of the Transaction class
    @Test
    public void testTransactionToString(){
        String expextedString = "ID: 2, Date: 2023-06-16, amount: 200.0, Category: Utilities, Type: INCOME, Description: Electricity bill";
        assertEquals(expextedString, transaction2.toString());
    }

    // Test compare method for ID
    @Test
    public void testTransactionCompareById(){
        assertTrue(Transaction.IdComparator.compare(transaction1,transaction2) < 0);
        assertTrue(Transaction.IdComparator.compare(transaction2,transaction1) > 0);
        assertEquals(0, Transaction.IdComparator.compare(transaction1, transaction1));
    }

    // Test compare method for Date
    @Test
    public void testTransactionCompareByDate(){
        assertTrue(Transaction.DateComparator.compare(transaction1, transaction2) < 0);
        assertTrue(Transaction.DateComparator.compare(transaction2, transaction1) > 0);
        assertEquals(0, Transaction.DateComparator.compare(transaction1, transaction1));
    }

    // Test compare method for Amount
    @Test
    public void testTransactionCompareByAmount(){
        assertTrue(Transaction.AmountComparator.compare(transaction1, transaction2) < 0);
        assertTrue(Transaction.AmountComparator.compare(transaction2, transaction1) > 0);
        assertEquals(0, Transaction.AmountComparator.compare(transaction1, transaction1));
    }

    // Test compare method for Catgory
    @Test
    public void testTransactionCompareByCategory(){
        assertTrue(Transaction.CategoryComparator.compare(transaction1, transaction2) < 0);
        assertTrue(Transaction.CategoryComparator.compare(transaction2, transaction1) > 0);
        assertEquals(0, Transaction.CategoryComparator.compare(transaction1, transaction1));
    }

    // Test compare method for Type
    @Test
    public void testTransactionCompareByType(){
        assertTrue(Transaction.TypeComparator.compare(transaction2, transaction1) < 0);
        assertTrue(Transaction.TypeComparator.compare(transaction1, transaction2) > 0);
        assertEquals(0, Transaction.TypeComparator.compare(transaction1, transaction1));
    }
}
