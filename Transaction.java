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

    public void setCoin(int newCoin) {
	coin = newCoin;
    }

    public void setSender(String sender) {
	from = sender;
    }

    public void setRecipient(String recipient) {
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
