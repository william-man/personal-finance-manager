package main.utils;

import java.time.LocalDate;
import java.util.*;

/**
 * Transaction
 */
public class Transaction implements Comparable<Transaction>{
    private String id;
    private LocalDate date;
    private double amount;
    private String category;
    private TransactionType type;
    private String description;

    public Transaction(String id, LocalDate date, double amount, String category, TransactionType type, String description ){
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public String getId(){
        return id;
    }

    public LocalDate getDate(){
        return date;
    }

    public double getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }

    public TransactionType getTransactionType(){
        return type;
    }

    public String getDescription(){
        return description;
    }

    public String toString(){
        return String.format("ID: %s, Date: %s, amount: %s, Category: %s, Type: %s, Description: %s", id, date, amount, category, type, description);
    }

    public int compareTo(Transaction transaction){
        return this.id.compareTo(transaction.id);
    }

    public static Comparator<Transaction> IdComparator = new Comparator<Transaction>() {
        public int compare(Transaction tran1, Transaction tran2){
            return Integer.compare(Integer.parseInt(tran1.getId()), Integer.parseInt(tran2.getId()));
        }
    };

    public static Comparator<Transaction> DateComparator = new Comparator<Transaction>() {
        public int compare(Transaction tran1, Transaction tran2){
            return tran1.getDate().compareTo(tran2.getDate());
        }
    };

    public static Comparator<Transaction> AmountComparator = new Comparator<Transaction>() {
        public int compare(Transaction tran1, Transaction tran2){
        return Double.compare(tran1.getAmount(), tran2.getAmount());
        }
    };

    public static Comparator<Transaction> CategoryComparator = new Comparator<Transaction>() {
        public int compare(Transaction tran1, Transaction tran2){
            return tran1.getCategory().compareTo(tran2.getCategory());
        }
    };

    public static Comparator<Transaction> TypeComparator = new Comparator<Transaction>() {
        public int compare(Transaction tran1, Transaction tran2){
            return tran1.getTransactionType().compareTo(tran2.getTransactionType());
        }
    };

    

}