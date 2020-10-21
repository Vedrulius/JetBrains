package com.mihey.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockChain {
    private List<Block> blockList;

    public BlockChain() {
        blockList = new ArrayList<>();
    }

    public void addBlock() {
        int threads=Runtime.getRuntime().availableProcessors();
        ExecutorService executor= Executors.newFixedThreadPool(threads);
        List<Callable<Block>> miners = new ArrayList<>();
        if (blockList.isEmpty()) {
            blockList.add(new Block(null));
        } else {
            blockList.add(new Block(blockList.get(blockList.size() - 1)));
        }
    }

    public List<Block> getBlockList() {
        return List.copyOf(blockList);
    }

}
