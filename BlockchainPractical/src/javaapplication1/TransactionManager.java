/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author This PC
 */

public class TransactionManager {
    private String filename;
    private List<Transaction> transactions;

    public TransactionManager(String filename) {
        this.filename = filename;
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    private void loadTransactions() {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    transactions.add(new Transaction(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
    }

    private void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getFrom() + "," + transaction.getTo() + "," + transaction.getAmount() + "," + transaction.getTransactionId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions available yet.");
        } else {
            System.out.println("\nTransactions:");
            for (Transaction transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getTransactionId());
                System.out.println("From: " + transaction.getFrom());
                System.out.println("To: " + transaction.getTo());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println();
            }
        }
    }
}