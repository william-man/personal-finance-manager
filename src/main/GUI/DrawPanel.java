package main.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import main.utils.Transaction;
import main.utils.TransactionList;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


public class DrawPanel extends JPanel {
    private TimeSeriesCollection dataset;
    
    public DrawPanel(TransactionList transactionList){
         setLayout(new BorderLayout());

        // Create a dataset
        dataset = new TimeSeriesCollection();

        // Create a chart based on the dataset
        JFreeChart chart = createChart(dataset);

        // Wrap the chart in a ChartPanel and add it to the panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 900));
        add(chartPanel, BorderLayout.CENTER);

        JButton updateButton = new JButton("Update Chart");
        updateButton.addActionListener(e -> updateChartData(transactionList.getTransactions()));
        add(updateButton, BorderLayout.SOUTH);

        updateChartData(transactionList.getTransactions());
    }

    private JFreeChart createChart(TimeSeriesCollection dataset) {
        // Create a time series chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Transaction Chart", // Chart title
                "Month", // X-axis Label
                "Amount", // Y-axis Label
                dataset, // Dataset for the chart
                true, // Show legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        return chart;
    }

    public void updateChartData(List<Transaction> transactions) {
        dataset.removeAllSeries();

        TimeSeries incomeSeries = new TimeSeries("INCOME");
        TimeSeries expenSeries = new TimeSeries("EXPENSE");

        // Add the updated transaction data to the dataset
        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            String type = transaction.getTransactionType().toString();
            LocalDate date = transaction.getDate();

            if(date != null && amount != 0.0){
                Day day = new Day(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
                if(type.equals("INCOME")){
                    incomeSeries.addOrUpdate(day,amount);
                } else if(type.equals("EXPENSE")){
                    expenSeries.addOrUpdate(day,amount);
                }
            }
            
        }
        dataset.addSeries(incomeSeries);
        dataset.addSeries(expenSeries);
    }

    // test data set contents by printing to console.
/* 
    private void printDatasetContents() {
        int seriesCount = dataset.getSeriesCount();
        
        System.out.println("Dataset Contents:");
        for (int series = 0; series < seriesCount; series++) {
            TimeSeries timeSeries = dataset.getSeries(series);
            System.out.println("Series: " + timeSeries.getKey());
            for (int item = 0; item < timeSeries.getItemCount(); item++) {
                System.out.println("Date: " + timeSeries.getTimePeriod(item) + ", Value: " + timeSeries.getValue(item));
            }
        }
    }
*/
}
