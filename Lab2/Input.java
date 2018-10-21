import java.util.Arrays;
import java.security.PublicKey;

public class Input {

    /** The public key of the sender */ 
    private PublicKey from;
     
    /** The coin to be transferred */
    private int coin;

    /** The signature produced to check validity */
    private byte[] signature;
	
    public Input(int coin, PublicKey from) {
	this.coin = coin;
	this.from = from;
    }
    
    public PublicKey getSender() {
	return from;
    }

    public int getCoin() {
	return coin;
    }

    public void addSignature(byte[] sig) {
	if (sig == null)
	    signature = null;
	else
	    signature = Arrays.copyOf(sig, sig.length);
    }

    public byte[] getSignature() {
	return signature;
    }
 }

