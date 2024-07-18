package main.GUI.components;

import javax.swing.*;

import main.GUI.MainFrame;
import main.utils.Transaction;
import main.utils.TransactionList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class RemoveTransactionPanel extends JPanel {
    private JTextField id;
    private JButton removeButton;

    // Constructor to set up the Remove Transaction panel
    public RemoveTransactionPanel(MainFrame mainFrame){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Remove Transaction"));
        GridBagConstraints removeTransactioConstraints = new GridBagConstraints();

        id = new JTextField(10);
        removeButton = new JButton("Submit");

        // Set constraints and add components to the panel
        removeTransactioConstraints.fill = 1;
        removeTransactioConstraints.gridx = 0;
        removeTransactioConstraints.gridy = 0;
        add(new JLabel("ID: "),removeTransactioConstraints);
        removeTransactioConstraints.gridx = 1;
        add(id,removeTransactioConstraints);

        removeTransactioConstraints.gridx = 1;
        removeTransactioConstraints.gridy = 1;
        add(removeButton, removeTransactioConstraints);

        // Remove button action listener
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(id.getText().isEmpty()){
                    JOptionPane.showMessageDialog(mainFrame, "ID field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String transactionId = id.getText();
                    TransactionList transactionList = mainFrame.getTransactions();

                    try{
                        transactionList.removeTransaction(transactionId);
                        List<Transaction> updatedList = transactionList.getTransactions();
                        mainFrame.updateTransactionsList(updatedList);
                        mainFrame.recalculateTotals();
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
