public class Transaction {

    /** The coin to be transfered  */
    private int coin;

    /** The creator of the transaction */ 
    private String from;

    /** The recipient of the coin in this transaction */
    private String to;


    /**
     * Creates a new transaction 
     */ 
    public Transaction(int coin, String from, String to) {
	if (from == null) 
	    this.from = null;
	else
	    this.from = from;
	this.to = to;
	this.coin = coin;
    }

    public void addCoin(int newCoin) {
	coin = newCoin;
    }

    public void addSender(String sender) {
	from = sender;
    }

    public void addRecipient(String recipient) {
	to = recipient;
    }

    public int getCoin() {
	return coin;
    }

    public String getSender() {
	return from;
    }

    public String getRecipient() {
	return to;
    }
}
