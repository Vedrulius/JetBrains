package com.mihey.blockchain;

public class Main {
    public static void main(String[] args) {

        BlockChain blockChain = new BlockChain();
        for (int i = 0; i < 5; i++) {
            blockChain.addBlock();
        }
        blockChain.getBlockList().forEach(x -> {
            System.out.println(x);
            if (x.getGenTime() > 60) {
                System.out.println("N was decreased by 1");
            } else if (x.getGenTime() < 10) {
                System.out.println("N was increased to " + (x.getNumberOfZeros() + 1));
            } else {
                System.out.println("N stays the same");
            }
            System.out.println();
        });
    }
}

