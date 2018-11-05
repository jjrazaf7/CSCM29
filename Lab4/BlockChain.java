// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory
// as it would cause a memory overflow.

import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {

    private class BlockNode {
        public Block b;
        public BlockNode parent;
        public ArrayList<BlockNode> children;
        public int height;
        // utxo pool for making a new block on top of this block
        private UTXOPool uPool;

        public BlockNode(Block b, BlockNode parent, UTXOPool uPool);

        public UTXOPool getUTXOPoolCopy();
    }

    private HashMap<ByteArrayWrapper, BlockNode> blockChain;
    private BlockNode maxHeightNode;
    private TransactionPool txPool;

    public static final int CUT_OFF_AGE = 10;


    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock);

    /**
     * Get the maximum height block
     */
    public Block getMaxHeightBlock();

    /**
     * Get the UTXOPool for mining a new block on top of max height block
     */
    public UTXOPool getMaxHeightUTXOPool();

    /**
     * Get the transaction pool to mine a new block
     */
    public TransactionPool getTransactionPool();

    /**
     * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
     * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
     * <p>
     * <p>
     * For example, you can try creating a new block over the genesis block (block height 2) if the
     * block chain height is {@code <=
     * CUT_OFF_AGE + 1}. As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block
     * at height 2.
     *
     * @return true if block is successfully added
     */
    public boolean addBlock(Block block);

    /**
     * Add a transaction to the transaction pool
     */
    public void addTransaction(Transaction tx);

    private void addCoinbaseToUTXOPool(Block block, UTXOPool utxoPool);

    private static ByteArrayWrapper wrap(byte[] arr);
}
