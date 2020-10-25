package com.mihey.blockchain;

import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {
    private final int minerId;
    private final BlockChain blockChain;

    public Miner(int minerId, BlockChain blockChain) {
        this.minerId = minerId;
        this.blockChain = blockChain;
    }

    @Override
    public Block call() throws Exception {
        return generateNextBlock();
    }

    Block generateNextBlock() {
        if (blockChain.getBlockList().isEmpty()) {
            return new Block(null, minerId, 0);
        } else {
            long genTime = blockChain.getBlockList().get(blockChain.getBlockList().size() - 1).getGenTime();
            int numberOfZeros = blockChain.getBlockList().get(blockChain.getBlockList().size() - 1).getNumberOfZeros();

            if (genTime < 3) {
                numberOfZeros++;
            }
            if (genTime > 6) {
                numberOfZeros--;
            }
            return new Block(blockChain.getBlockList().get(blockChain.getBlockList().size() - 1), minerId, numberOfZeros);
        }
    }
}
