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

    public void addBlock(Block block) {
        blockList.add(block);
    }

    public List<Block> getBlockList() {
        return List.copyOf(blockList);
    }

}
