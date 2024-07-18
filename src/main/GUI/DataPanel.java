package main.GUI;

import javax.swing.*;

import main.GUI.components.AddTransactionPanel;
import main.GUI.components.RemoveTransactionPanel;
import main.utils.CSVUtils;
import main.utils.TransactionList;
import main.utils.Transaction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public class DataPanel extends JPanel{
    private JButton saveButton;
    private JButton loadButton;
    private JTextField balanceField;
    private JLabel balancLabel;
    private JButton submitBalance;
    private AddTransactionPanel addTransactionPanel;
    private RemoveTransactionPanel removeTransactionPanel;
    
    // Constructor for the setup data panel
    public DataPanel(MainFrame mainFrame, TransactionList transactionList, BigDecimal accountBalance, BalanceAndTransactions middleJPanel){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400,900));
        GridBagConstraints constraints = new GridBagConstraints();
        
        // initialise components
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        balancLabel = new JLabel("Enter Balance: ");
        balanceField = new JTextField(10);
        submitBalance = new JButton("Set balance");

        addTransactionPanel = new AddTransactionPanel(mainFrame, middleJPanel);
        removeTransactionPanel = new RemoveTransactionPanel(mainFrame);


        // constraints for layout
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(saveButton,constraints);

        constraints.gridx = 1; 
        constraints.gridy = 0;
        add(loadButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(balancLabel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(balanceField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        add(submitBalance,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(addTransactionPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(removeTransactionPanel, constraints);

        //save button action listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(mainFrame);
                if(option == JFileChooser.APPROVE_OPTION){
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try{
                        CSVUtils.saveTransactionsToCSV(transactionList.getTransactions(), filePath);
                        JOptionPane.showMessageDialog(mainFrame, "Data saved successfully");
                    } catch (IOException ex){
                        JOptionPane.showMessageDialog(mainFrame, "Error saving data: "+ ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //load button action listener
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(mainFrame);
                if(option == JFileChooser.APPROVE_OPTION){
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try{
                        List<Transaction> transactions = CSVUtils.loadTransactionsFromCSV(filePath);
                        transactionList.setTransactions(transactions);
                        mainFrame.updateTransactionsList(transactions);
                        JOptionPane.showMessageDialog(mainFrame, "Data loaded successfully.");
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(mainFrame, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }); 

        // submit balance button action listener
        submitBalance.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                try{
                    double accountBalance = Double.parseDouble(balanceField.getText());
                    mainFrame.updateAccountBalance(BigDecimal.valueOf(accountBalance));
                    mainFrame.recalculateTotals();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(mainFrame, "Invalid balance amount: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
