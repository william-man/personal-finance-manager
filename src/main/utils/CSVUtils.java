package main.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CSVUtils{

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // writes a list of transactions to a csv file

    public static void saveTransactionsToCSV(List<Transaction> transactions, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"ID", "Date", "Amount", "Category", "Type", "Description"}; // header row
            writer.writeNext(header);

            for (Transaction transaction : transactions) { // data rows, iterating over each transaction
                String[] row = {
                    transaction.getId(),
                    transaction.getDate().format(DATE_FORMATTER),
                    String.valueOf(transaction.getAmount()),
                    transaction.getCategory(),
                    transaction.getTransactionType().toString(),
                    transaction.getDescription().isEmpty() ? "" : transaction.getDescription()
                };
                writer.writeNext(row); // write each row to csv file
            }
        }
    }

    // reads list of transactions from csv; returns list of transactions, List<Transaction>

    public static List<Transaction> loadTransactionsFromCSV(String filePath) throws Exception{
        List<Transaction> transactions = new ArrayList<>();
        try(CSVReader reader  = new CSVReader(new FileReader(filePath))){ // read data from csv file
            String[] nextLine;
            reader.readNext();  // skip header

            while((nextLine = reader.readNext()) != null) { // read each subsequent line and parse data
                String id = nextLine[0];
                LocalDate date = LocalDate.parse(nextLine[1], DATE_FORMATTER);
                double amount = Double.parseDouble(nextLine[2]);
                String category = nextLine[3];
                TransactionType type = TransactionType.valueOf(nextLine[4]);
                String description = nextLine[5].isEmpty() ? "" : nextLine[5];

                Transaction transaction = new Transaction(id, date, amount, category, type, description);
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}
