package test.java.main.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.utils.CSVUtils;
import main.utils.Transaction;
import main.utils.TransactionType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVUtilsTest {
    
    private List<Transaction> transactions;
    private Path tempDirectory;
    private String tempFilePath;


    // Initialise each test with a list of transactions to be used to save into the dummy csv
    @BeforeEach
    public void setup() throws Exception{
        transactions = new ArrayList<>();
        transactions.add(new Transaction("1", LocalDate.of(2023, 6, 15), 100.0, "Groceries", TransactionType.EXPENSE, "Grocery shopping"));
        transactions.add(new Transaction("2", LocalDate.of(2023, 6, 16), 200.0, "Utilities", TransactionType.EXPENSE, "Electricity bill"));
        transactions.add(new Transaction("3", LocalDate.of(2023, 6, 17), 300.0, "Salary", TransactionType.INCOME, "Monthly salary"));

        // create temporary directory and dummy csv file for testing
        tempDirectory = Files.createTempDirectory("csvutils_test");
        tempFilePath = tempDirectory.resolve("test_transactions.csv").toString();
    }

    // After each test, remove the dummy csv
    @AfterEach
    public void removeTemp() throws Exception{
        Files.deleteIfExists(Paths.get(tempFilePath));
        Files.delete(tempDirectory);
    }

    // Test the saving method works by saving to a temporary file
    @Test
    public void testSaveTransactionsToCSV() throws Exception {
        CSVUtils.saveTransactionsToCSV(transactions, tempFilePath);
        // check file exists and is not empty
        File file = new File(tempFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    // Test the loading method works by loading the temporary file after creating one
    @Test
    public void testLoadTransactionsFromCSV() throws Exception {
        CSVUtils.saveTransactionsToCSV(transactions, tempFilePath);

        List<Transaction> result = CSVUtils.loadTransactionsFromCSV(tempFilePath);

        // Check the loaded file transactions match the original
        assertEquals(transactions.size(), result.size());

        for(int i = 0; i<transactions.size(); i++){
            Transaction original = transactions.get(i);
            Transaction loaded = result.get(i);

            assertEquals(original.getId(), loaded.getId());
            assertEquals(original.getDate(), loaded.getDate());
            assertEquals(original.getAmount(), loaded.getAmount());
            assertEquals(original.getCategory(), loaded.getCategory());
            assertEquals(original.getTransactionType(), loaded.getTransactionType());
            assertEquals(original.getDescription(), loaded.getDescription());
        }
    }
}
