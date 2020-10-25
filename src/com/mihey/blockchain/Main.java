package com.mihey.blockchain;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int threads = Runtime.getRuntime().availableProcessors();
        BlockChain blockChain = new BlockChain();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Set<Callable<Block>> miners = new HashSet<>();
        for (int i = 1; i <= threads; i++) {
            Miner miner = new Miner(i, blockChain);
            miners.add(miner);
        }
        for (int i = 0; i < 10; i++) {
            Block currentBlock = executor.invokeAny(miners);
            blockChain.addBlock(currentBlock);
            System.out.println(currentBlock);
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
        executor.shutdownNow();
    }
}

