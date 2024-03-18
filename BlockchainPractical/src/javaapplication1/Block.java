/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package javaapplication1;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author This PC
 */

public class Block {
    private int index;
    private long timestamp;
    private String data;
    private String previousHash;
    private String hash;
    private List<Transaction> transactions;
    private String merkleRoot;

    public Block(int index, long timestamp, String data, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>();
        this.hash = calculateHash();
        this.merkleRoot = calculateMerkleRoot();
    }

    // Create the hash of the block using sha-256 algo
    private String calculateHash() {
        String dataToHash = index + timestamp + data + previousHash + merkleRoot;
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(dataToHash.getBytes());
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String calculateMerkleRoot() {
        List<String> transactionHashes = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionHashes.add(transaction.getHash());
        }
        return computeMerkleRoot(transactionHashes);
    }

    private String computeMerkleRoot(List<String> hashes) {
        if (hashes.size() == 0) {
            return "";
        }
        if (hashes.size() == 1) {
            return hashes.get(0);
        }
        List<String> newHashes = new ArrayList<>();
        for (int i = 0; i < hashes.size() - 1; i += 2) {
            newHashes.add(sha256(hashes.get(i) + hashes.get(i + 1)));
        }
        if (hashes.size() % 2 == 1) {
            newHashes.add(sha256(hashes.get(hashes.size() - 1) + hashes.get(hashes.size() - 1)));
        }
        return computeMerkleRoot(newHashes);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
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

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        this.merkleRoot = calculateMerkleRoot();
        this.hash = calculateHash();
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }
}
