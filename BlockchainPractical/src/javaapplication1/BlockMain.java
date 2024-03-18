/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package javaapplication1;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author This PC
 */

public class BlockMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BlockChain blockchain = new BlockChain("blockchain.json");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Blockchain");
            System.out.println("2. Add a new Block");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewBlockchain(blockchain);
                    break;
                case 2:
                    addBlock(scanner, blockchain);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
            }
        }
    }

    private static void viewBlockchain(BlockChain blockchain) {
    if (blockchain.getChain().isEmpty()) {
        System.out.println("Blockchain is empty.");
    } else {
        System.out.println("\nBlockchain:");
        for (Block block : blockchain.getChain()) {
            System.out.println("Index: " + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Data: " + block.getData());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Merkle Root: " + block.getMerkleRoot());

            System.out.println("\n=================================================================");
            System.out.println("Transactions:");
            List<Transaction> transactions = block.getTransactions();
            for (Transaction transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getTransactionId());
                System.out.println("From: " + transaction.getFrom());
                System.out.println("To: " + transaction.getTo());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Hash: " + transaction.getHash());
                System.out.println();
            }
            System.out.println("\n=================================================================");
        }
    }
}

    private static void addBlock(Scanner scanner, BlockChain blockchain) {
        System.out.print("Enter data for the new block: ");
        String data = scanner.nextLine();

        Block lastBlock = blockchain.getLastBlock();
        int newIndex = lastBlock != null ? lastBlock.getIndex() + 1 : 1;

        Block newBlock = new Block(newIndex, System.currentTimeMillis(), data, lastBlock != null ? lastBlock.getHash() : "0"); //HEre it return last block hash if available or else return 0

        while (true) {
            System.out.println("Add Transaction to Block:");
            System.out.print("Enter sender: ");
            String from = scanner.nextLine();
            System.out.print("Enter receiver: ");
            String to = scanner.nextLine();
            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Transaction newTransaction = new Transaction(from, to, amount);
            newBlock.addTransaction(newTransaction);

            System.out.print("Add another transaction to this block? (yes/no): ");
            String addAnother = scanner.nextLine();
            if (!addAnother.equalsIgnoreCase("yes")) {
                break;
            }
        }

        blockchain.addBlock(newBlock);
        System.out.println("New block added successfully.");
    }
}
