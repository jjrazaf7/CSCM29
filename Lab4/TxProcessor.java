import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TxProcessor {

    private UTXOPool utxoPool;
    
    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxProcessor(UTXOPool utxoPool);
    
    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     * values; and false otherwise.
     */
    public boolean validTx(Transaction tx);
    
    /**
     * Handles each epoch by receiving an unordered array of proposed transactions,
     * checking each transaction for validity,
     * updating the UTXO pool as appropriate,
     * and returning a mutually valid array of accepted transactions. 
     */
    public Transaction[] processTxs(Transaction[] possibleTxs);

    public UTXOPool getUTXOPool();
}
