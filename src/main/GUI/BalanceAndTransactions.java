package main.GUI;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.utils.Transaction;
import main.utils.TransactionList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BalanceAndTransactions extends JPanel{
    private JLabel balance;
    private JPanel balancPanel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private TransactionList transactionList;
    
    // Constructor to set up the Balance and Transactions panel
    public BalanceAndTransactions(TransactionList transactionList){
        this.transactionList = transactionList;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400,900));
        setBorder((BorderFactory.createTitledBorder("Account Overview")));

        balancPanel = new JPanel(new FlowLayout());
        balance = new JLabel("Balance: £0.00");
        balance.setFont(new Font("Arial", Font.BOLD, 20));
        balancPanel.add(balance);
        add(balancPanel, BorderLayout.NORTH);

        // Set up the transaction table
        String[] columnNames = {"ID","Date","Amount", "Category", "Type","Description"};
        tableModel = new DefaultTableModel(columnNames,0);
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add sorting buttons to the header panel
        JPanel headerJPanel = new JPanel(new GridLayout(1,columnNames.length));
        for(String columnName : columnNames){
            if(columnName != "Description"){
                JButton headerButton = new JButton(columnName);
                headerButton.addActionListener(new ColumnSortAction(columnName));
                headerJPanel.add(headerButton);
            }
        }
        add(headerJPanel, BorderLayout.SOUTH);
    }

    // Method to set the displayed balance
    public void setAmount(String inpuString){
        balance.setText("Balance: " + "£" + inpuString);
    }

    // Method to update the transaction list in the table
    public void updateTransactionsList(List<Transaction> transactions){
        tableModel.setRowCount(0);
        for(Transaction transaction : transactions){
            Object[] row = {
                transaction.getId(),
                transaction.getDate().toString(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getTransactionType(),
                transaction.getDescription()
            };
            tableModel.addRow(row);
        }
    }
    
    // Inner class to handle column sorting actions
    private class ColumnSortAction implements ActionListener{
        private String columnName;

        public ColumnSortAction(String columnName){
            this.columnName = columnName;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            switch (columnName) {
                case "ID":
                    transactionList.sortById();
                    break;
                case "Date":
                    transactionList.sortByDate();
                    break;
                case "Amount":
                    transactionList.sortByAmount();
                    break;
                case "Category":
                    transactionList.sortByCategory();
                    break;
                case "Type":
                    transactionList.sortByType();
                    break;
                default:
                    break;
            }
            updateTransactionsList(transactionList.getTransactions());
        }
    }
}
