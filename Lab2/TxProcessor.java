import java.util.Arrays;
import java.util.ArrayList;
import java.security.PublicKey;

public class TxProcessor {

    private Ledger ledger;
    
    /** Creates a ledger whose current history is {@code ledger}. */
    public TxProcessor(Ledger ledger); 

    /** 
     * This method checks if transaction {@code tx} is valid or not.
     * There are four cases to consider in order to check for the validity of {@code tx}: 
     * (1) {@code tx} is a creation transaction
     * (2) {@code tx} is a 1-1 transfer transaction
     * (3) {@code tx} is a 2-1 transfer transaction 
     * (4) {@code tx} is a 1-2 transfer transaction
     *
     * A creation transaction is valid if the coin credited to the recipient in the transaction is non-negative
     * A 1-1 transfer transaction is valid if the following conditions hold: 
     * (1) all coins are non-negative and equal
     * (2) the public key in the input has enough balance 
     * (3) the signature  is valid for the message (obtained using getRawDataToSign) under the public key in the input
     *
     * A 1-2 transfer transaction is valid if the following conditions hold: 
     * (1) all coins involved in the transaction are non-negative
     * (2) the sum of the coins in the outputs is equal to the coin in the input
     * (3) the public key in the input has enough balance
     * (4) the signature  is valid for the message (obtained using getRawDataToSign) under the public key in the input
     * 
     * A 2-1 transfer transaction is valid if the following conditions hold: 
     * (1) all coins involved in the transaction are non-negative
     * (2) the sum of the coins in the inputs is equal to the coin in the output
     * (3) the public keys in the input have enough balance 
     * (4) the signatures are valid for the respective messages under the respective public keys in the input
     *
     * @return true if all the above  conditions are met, otherwise, false.
     */
    public boolean validTx(Transaction tx); 
	
	


       
    /**
     * Handles each epoch by receiving an unordered array of proposed transactions,
     * checking each transaction for validity,
     * updating the the ledger as appropriate,
     * and returning the updated ledger. 
     */
    public Ledger processTxs(Transaction[] possibleTxs); 
}
