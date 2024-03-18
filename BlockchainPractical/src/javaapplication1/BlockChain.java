/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package javaapplication1;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;

/**
 *
 * @author This PC
 */

public class BlockChain {
    private List<Block> chain;
    private String filename;
    private Gson gson;

    // Constructor
    public BlockChain(String filename) {
        this.chain = new ArrayList<>();
        this.filename = filename;
        this.gson = new Gson();
        initializeChain();
    }

    public List<Block> getChain() {
        return chain;
    }

    private void initializeChain() {
        try {
            File file = new File(filename);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                Block[] blocks = gson.fromJson(bufferedReader, Block[].class);
                if (blocks != null) {
                    chain.addAll(Arrays.asList(blocks));
                }
                bufferedReader.close();
            } else {
                System.out.println("Blockchain file not found. Creating a new one.");
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBlock(Block newBlock) {
        chain.add(newBlock);
        writeToFile();
    }

    private void writeToFile() {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            gson.toJson(chain, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block getLastBlock() {
        if (!chain.isEmpty()) {
            return chain.get(chain.size() - 1);
        }
        return null;
    }
}
