package main.GUI;

import javax.swing.*;

import main.utils.Transaction;
import main.utils.TransactionList;
import main.utils.TransactionType;

import java.awt.*;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainFrame extends JFrame{
    private TransactionList transactionList;
    private BigDecimal accountBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private DataPanel leftJPanel;
    private BalanceAndTransactions middleJPanel;
    private DrawPanel rightJPanel;
    
    // Constructor to setup GUI for the application
    public MainFrame(){
        setTitle("Personal Finance Manager");
        setSize(1800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // initialise transaction list and balances
        transactionList = new TransactionList();
        accountBalance = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);
        totalIncome = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);
        totalExpense = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);

        // panels for adding transactions, viewing the list and changes on a time series chart
        middleJPanel = new BalanceAndTransactions(transactionList);
        leftJPanel = new DataPanel(this , transactionList, accountBalance, middleJPanel);
        rightJPanel = new DrawPanel(transactionList);

        add(leftJPanel,BorderLayout.WEST);
        add(middleJPanel, BorderLayout.CENTER);
        add(rightJPanel, BorderLayout.EAST);
    }

    // Getter for account balance
    public double getAccountBalance(){
        return accountBalance.doubleValue();
    }
    // Getter for transaction list
    public TransactionList getTransactions(){
        return transactionList;
    }
    // update balance and show changes in the middle panel
    public void updateAccountBalance(BigDecimal balance){
        this.accountBalance = balance.setScale(2, RoundingMode.HALF_UP);
        middleJPanel.setAmount(String.valueOf(this.accountBalance));;
    }
    //update transaction list and show changes in the middle and right panel
    public void updateTransactionsList(List<Transaction> transactions){
        this.transactionList.setTransactions(transactions);
        middleJPanel.updateTransactionsList(transactionList.getTransactions());
        rightJPanel.updateChartData(transactionList.getTransactions());
    }
    // method to calculate total income, expense and update balance
    public void recalculateTotals(){
        for(Transaction transaction : transactionList.getTransactions()){
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
            if(transaction.getTransactionType() == TransactionType.INCOME){
                totalIncome = totalIncome.add(amount);
            } else {
                totalExpense = totalExpense.add(amount);
            }
        }
        updateAccountBalance(accountBalance.add(totalIncome).subtract(totalExpense));
    }
    // main method to run application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
