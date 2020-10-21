package com.mihey.blockchain;

import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {
    private int minerId;
    private BlockChain blockChain;

    @Override
    public Block call() throws Exception {
        return null;
    }
}
