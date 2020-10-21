package com.mihey.blockchain;

import java.util.Date;
import java.util.Random;

public class Block {
    private int id;
    private int minerId;
    private final long timeStamp = new Date().getTime();
    private long genTime;
    private int magicNumber;
    private int numberOfZeros;
    private String previousHash;
    private String hash;

    public Block(Block prevBlock) {
        if (prevBlock == null) {
            id = 1;
            previousHash = "0";
        } else {
            id = prevBlock.getId() + 1;
            previousHash = prevBlock.getHash();
        }
        hash = mineBlock(numberOfZeros);
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public long getGenTime() {
        return genTime;
    }

    public int getNumberOfZeros() {
        return numberOfZeros;
    }

    private String calculateHash() {
        return StringUtil
                .applySha256(previousHash + id + timeStamp + magicNumber);
    }

    private String mineBlock(int numberOfZeros) {
        hash = calculateHash();
        Random r = new Random();
        String prefixString = new String(new char[numberOfZeros]).replace('\0', '0');
        while (!hash.substring(0, numberOfZeros).equals(prefixString)) {
            magicNumber = r.nextInt(99_999_999);
            hash = calculateHash();
        }
        genTime = (new Date().getTime() - timeStamp) / 1000;
        return hash;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Created by miner #" + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Magic number:" + magicNumber + "\n" +
                "Hash of the previous block: \n" + previousHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + genTime + " seconds";
    }
}
