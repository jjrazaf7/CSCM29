import java.util.Arrays;
import java.security.PublicKey;

public class Input {

    /** hash of the Transaction whose output is being used */
    private byte[] prevTxHash;

    /** used output's index in the previous transaction */
    private int outputIndex;

    /** The signature produced to check validity */
    private byte[] signature;
	
    public Input(byte[] prevHash, int index) {
	if (prevHash == null) 
	    prevTxHash = null;
	else
	    prevTxHash = Arrays.copyOf(prevHash, prevHash.length);
	outputIndex = index;
    }

   
    public void addSignature(byte[] sig) {
	if (sig == null)
	    signature = null;
	else
	    signature = Arrays.copyOf(sig, sig.length);
    }

    public int getOutputIndex() {
	return outputIndex;
    }

    public byte[] getPrevTxHash() {
	return prevTxHash;
    }


    public byte[] getSignature() {
	return signature;
    }
 }

