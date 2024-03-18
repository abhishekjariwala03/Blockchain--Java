/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 *
 * @author This PC
 */

public class Transaction {

    private String transactionId;
    private String from;
    private String to;
    private double amount;
    private String hash;

    public Transaction(String from, String to, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.hash = calculateHash(); // Here creating hash value using from, to and amount field...!!
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public String getHash() {
        return hash;
    }

    private String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((from + to + Double.toString(amount)).getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}