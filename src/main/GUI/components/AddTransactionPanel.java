package main.GUI.components;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.utils.*;
import main.GUI.BalanceAndTransactions;
import main.GUI.MainFrame;

public class AddTransactionPanel extends JPanel{
    private JTextField id;
    private JFormattedTextField dateField;
    private JTextField amount;
    private JTextField category;
    private JComboBox<TransactionType> typeComboBox;
    private JTextField description;
    private JButton addButton;
    
    // Constructor to setup add transaction panel
    public AddTransactionPanel(MainFrame mainFrame, BalanceAndTransactions middleJPanel){
        
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Add Transaction"));
        GridBagConstraints addTransactioConstraints = new GridBagConstraints();

        id = new JTextField(10);

        // Date formatter

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter df = new DateFormatter(format);
        dateField = new JFormattedTextField(new DefaultFormatterFactory(df));
        dateField.setValue(new Date());

        amount = new JTextField(10);
        category = new JTextField(10);
        typeComboBox = new JComboBox<>(TransactionType.values());
        description = new JTextField(10);


        // component constraints and add to panel
        addTransactioConstraints.fill = 1;
        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 0;
        add(new JLabel("ID: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(id,addTransactioConstraints);

        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 1;
        
        add(new JLabel("Date: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(dateField,addTransactioConstraints);

        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 2;
        add(new JLabel("Amount: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(amount,addTransactioConstraints);

        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 3;
        add(new JLabel("Category: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(category,addTransactioConstraints);

        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 4;
        add(new JLabel("Type: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(typeComboBox,addTransactioConstraints);

        addTransactioConstraints.gridx = 0;
        addTransactioConstraints.gridy = 5;
        add(new JLabel("Description: "),addTransactioConstraints);
        addTransactioConstraints.gridx = 1;
        add(description,addTransactioConstraints);

        addTransactioConstraints.gridx = 1;
        addTransactioConstraints.gridy = 6;
        addButton = new JButton("Submit");
        add(addButton,addTransactioConstraints);

        // add button action listener
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                    if(id.getText().isEmpty() || dateField.getText().isEmpty() || amount.getText().isEmpty() || category.getText().isEmpty() || typeComboBox.getSelectedItem() == null){
                        throw new Exception("All fields except description must no be empty.");
                    } else if(Double.parseDouble(amount.getText()) < 0){
                        throw new Exception("Amount cannot be a negative value.");
                    } else {

                        // extract and parse input data
                        String newID = id.getText();
                        
                        Date date = (Date) dateField.getValue();
                        LocalDate newDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        double newAmount = Double.parseDouble(amount.getText());
                        String newCategory = category.getText();
                        TransactionType newType = (TransactionType) typeComboBox.getSelectedItem();
                        String newDescription = description.getText();
                        
                        // create new transaction and add to list
                        Transaction newTransaction = new Transaction(newID, newDate, newAmount, newCategory, newType, newDescription);
                        
                        TransactionList transactionList = mainFrame.getTransactions();
                        transactionList.addTransaction(newTransaction);
                        List<Transaction> updTransactions = transactionList.getTransactions();
                        mainFrame.updateTransactionsList(updTransactions);
                        mainFrame.recalculateTotals();
                    }
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(mainFrame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                } finally{
                    resetFieldInputs();
                }
            }
        });
    }

    // field reset method
    public void resetFieldInputs(){
        id.setText("");
        dateField.setText("");
        amount.setText("");
        category.setText("");
        typeComboBox.setSelectedItem(TransactionType.INCOME);
        description.setText("");
    }
}
