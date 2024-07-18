package main.utils;

import java.util.*;

public class TransactionList{
    private List<Transaction> transactions;

    public TransactionList(){
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) throws Exception{
        transactions.add(transaction);
    }

    public void removeTransaction(String id) throws Exception{
        if(id.isEmpty()){
            throw new Exception("ID field is empty");
        } else {
            Transaction searchResult = findTransactionByID(id);
            if(searchResult != null){
                transactions.remove(searchResult);
            } else {
                throw new Exception(String.format("Transaction with ID: %s is not found.", id));
            }
        }
    }
    
    public Transaction findTransactionByID(String id){
        for(Transaction transaction : transactions){
            if(transaction.getId().equals(id)){
                return transaction;
            }
        }
        return null;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public void sortById(){
        transactions.sort(Transaction.IdComparator);
    }

    public void sortByDate(){
        transactions.sort(Transaction.DateComparator);
    }

    public void sortByAmount(){
        transactions.sort(Transaction.AmountComparator);
    }

    public void sortByCategory(){
        transactions.sort(Transaction.CategoryComparator);
    }

    public void sortByType(){
        transactions.sort(Transaction.TypeComparator);
    }
}
