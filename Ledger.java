import java.util.TreeMap;
import java.util.Map;

public class Ledger {

    /** 
     * The current ledger, with each account's name mapped to its account balance.
     */ 
    private TreeMap<String, Integer> H;

    /** 
     * Creates a new ledger
     */
    public Ledger() {
	H = new TreeMap<String, Integer>();
    }

    /** 
     * Creates a new ledger that is a copy of {@code ledger}
     */
    public Ledger(Ledger ledger) {
	H = new TreeMap<String, Integer>(ledger.H);
    }

    

    /** 
     * Adds a mapping from new account's name {@code owner} to its account balance {@code balance} into the ledger. 
     */
    public void addAccount(String owner, int balance) {
	H.put(owner, balance);
    }

    /** 
     * @return true if the account {@code account} exists in the ledger.
     */
    public boolean exists(String account) {
	return H.containsKey(account);
    }


    /** 
     * @return the balance for this account {@code account}
     */
    public int getBalance(String account) {
	return H.get(account);
    }

    /** 
     * Update the balance of {@code recipient}
     */
    public void updateBalance(String recipient, int coin) {
	H.replace(recipient, getBalance(recipient) + coin);
    }

    /** 
     * Updates the ledger by applying {@code tx}.
     */
    public void update(Transaction tx) {
	// This first part checks if it is a create transaction
	if (tx.getSender() == null) {
	    if (exists(tx.getRecipient())) {
		updateBalance(tx.getRecipient(), tx.getCoin());
	    }
	    else 
		addAccount(tx.getRecipient(), tx.getCoin());
	}

	// This second part deals with the transfer transaction
	else {
      	    updateBalance(tx.getSender(), -tx.getCoin());
	    updateBalance(tx.getRecipient(), tx.getCoin());
	}
    }

    /** 
     * Prints the current state of the ledger. 
     */
    public void print(Ledger ledger) {
	System.out.println("");
	
	for (Map.Entry<String, Integer> entry : ledger.H.entrySet()) {
	    String key = entry.getKey();
	    int value = entry.getValue();
	    System.out.println("The balance for " + key + " is " + value); 
	}

	System.out.println("");
    }
}
