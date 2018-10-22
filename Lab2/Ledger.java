import java.util.Hashtable;
import java.util.Map;
import java.util.ArrayList;
import java.security.PublicKey;

public class Ledger {

    /** 
     * The current ledger, with each account's address mapped to its account balance.
     */ 
    private Hashtable<PublicKey, Integer> H;

    /** 
     * Creates a new ledger
     */
    public Ledger() {
	H = new Hashtable<PublicKey, Integer>();
    }

    /** 
     * Creates a new ledger that is a copy of {@code ledger}
     */
    public Ledger(Ledger ledger) {
	H = new Hashtable<PublicKey, Integer>(ledger.H);
    }

    

    /** 
     * Adds a mapping from new account's address {@code owner} to its account balance {@code balance} into the ledger. 
     */
    public void addAccount(PublicKey owner, int balance) {
	H.put(owner, balance);
    }

    /** 
     * @return true if the account {@code account} exists in the ledger.
     */
    public boolean exists(PublicKey account) {
	return H.containsKey(account);
    }


    /** 
     * @return the balance for this account {@code account}
     */
    public int getBalance(PublicKey account) {
	return H.get(account);
    }

    /** 
     * Adds coin (which might be negative) to balance for {@code recipient}
     */
    public void addToBalance(PublicKey recipient, int coin) {
	H.replace(recipient, getBalance(recipient) + coin);
    }

    /** 
     * Updates the ledger by applying {@code tx}.
     */
    public void update(Transaction tx) {
	// Case 1: creation transaction
	if (tx.getInputs().isEmpty() && tx.getOutputs().size() == 1) {
	    // checks if recipient already exists
	    PublicKey recipient = tx.getOutputs().get(0).getRecipient();
	    int coin = tx.getOutputs().get(0).getCoin();
	    if (exists(recipient)) {
		addToBalance(recipient, coin);
	    }
	    else
		addAccount(recipient, coin);
	}

	// This second part deals with the transfer transaction
	else {
	    // Case 2: 1-1 transfer transaction
	    if (tx.getInputs().size() == 1 && tx.getOutputs().size() == 1) {
		PublicKey sender = tx.getInputs().get(0).getSender();
		int coin = tx.getInputs().get(0).getCoin();
		PublicKey recipient = tx.getOutputs().get(0).getRecipient();
		addToBalance(sender, -coin);
		addToBalance(recipient, coin);
	    }
	    // Case 3: 2-1 transfer transaction
	    if (tx.getInputs().size() == 2 && tx.getOutputs().size() == 1) {
		PublicKey sender1 = tx.getInputs().get(0).getSender();
		int coin1 = tx.getInputs().get(0).getCoin();
		PublicKey sender2 = tx.getInputs().get(1).getSender();
		int coin2 = tx.getInputs().get(1).getCoin();
		PublicKey recipient = tx.getOutputs().get(0).getRecipient();
		addToBalance(sender1, -coin1);
		addToBalance(sender2, -coin2);
		addToBalance(recipient, coin1 + coin2);
	    }
	    // Case 3: 1-2 transfer transaction
	    if (tx.getInputs().size() == 1 && tx.getOutputs().size() == 2) {
		PublicKey sender = tx.getInputs().get(0).getSender();
		PublicKey recipient1 = tx.getOutputs().get(0).getRecipient();
		int coin1 = tx.getOutputs().get(0).getCoin();
		PublicKey recipient2 = tx.getOutputs().get(1).getRecipient();
		int coin2 = tx.getOutputs().get(1).getCoin();
		addToBalance(sender, -(coin1 + coin2));
		addToBalance(recipient1, coin1);
		addToBalance(recipient2, coin2);
	    }
	}
    }

    /** 
     * Prints the current state of the ledger. 
     */
    
    public void print(Ledger ledger) {
	System.out.println("");
	
	for (Map.Entry<PublicKey, Integer> entry : ledger.H.entrySet()) {
	    PublicKey key = entry.getKey();
	    int value = entry.getValue();
	    System.out.println("The balance for " + key + " is " + value);
	}

	System.out.println("");
    }
    
}
