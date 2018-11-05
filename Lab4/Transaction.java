import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Transaction {

    /** hash of the transaction, its unique id */
    private byte[] hash;
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private boolean coinbase;
    
    /** Creation (or coinbase) transaction of value {@code coin} and calls finalize on it */
    public Transaction(double coin, PublicKey address) {
	coinbase = true;
	inputs = new ArrayList<>();
	outputs = new ArrayList<>();
	addOutput(coin, address);
	finalize();
    }
    
    /** 1-1 transfer transaction */
    public Transaction(Input in, Output out) {
	coinbase = false;
	inputs = new ArrayList<>();
	outputs = new ArrayList<>();
	inputs.add(in);
	outputs.add(out);
    }

    /** 1-2 transaction transaction */ 
    public Transaction(Input in, Output out1, Output out2) {
	coinbase = false;
	inputs = new ArrayList<>();
	outputs = new ArrayList<>();
	inputs.add(in);
	outputs.add(out1);
	outputs.add(out2);
    }

    /** 2-1 transfer transaction */
    public Transaction(Input in1 , Input in2, Output out) {
	coinbase = false;
	inputs = new ArrayList<>();
	outputs = new ArrayList<>();
	inputs.add(in1);
	inputs.add(in2);
	outputs.add(out);
    }

    public Transaction(Transaction tx) { 
	hash = tx.hash.clone();
	inputs = new ArrayList<Input>(tx.inputs);
	outputs = new ArrayList<Output>(tx.outputs);
	coinbase = false;
    }

    public void addOutput(double value, PublicKey address) {
	Output out = new Output(value, address);
	outputs.add(out);
    }

    /** 
     * Computes the hash of tx = getRawRx() = inputs^outputs, 
     * which can then be used as transID for tx
     */
    public void finalize() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getRawTx());
            hash = md.digest();
        } catch (NoSuchAlgorithmException x) {
            x.printStackTrace(System.err);
        }
    }

    public byte[] getHash() {
        return hash;
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }
    
    public ArrayList<Output> getOutputs() {
        return outputs;
    }

    public Input getInput(int index) {
        if (index < inputs.size()) {
            return inputs.get(index);
        }
        return null;
    }


    public Output getOutput(int index) {
        if (index < outputs.size()) {
            return outputs.get(index);
        }
        return null;
    }
    
    /** 
     * Computes message = inputs[index]^outputs, which is then used in
     * verifySignature(PublicKey pubKey, byte[] message, byte[] signature)
     */ 
    public byte[] getRawDataToSign(int index) {
        // the input at position "index" in the list of inputs and all outputs
        ArrayList<Byte> sigData = new ArrayList<Byte>();
        
	if (index > inputs.size())
            return null;

        Input in = inputs.get(index); 
        byte[] prevTxHash = in.getPrevTxHash();
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE / 8); // Temporary memory
        b.putInt(in.getOutputIndex()); 
        byte[] outputIndex = b.array();

	
        if (prevTxHash != null)
            for (int i = 0; i < prevTxHash.length; i++)
                sigData.add(prevTxHash[i]);
        for (int i = 0; i < outputIndex.length; i++)
            sigData.add(outputIndex[i]);
	
        for (Output op : outputs) {
            ByteBuffer bo = ByteBuffer.allocate(Double.SIZE / 8);
            bo.putDouble(op.getValue());
            byte[] value = bo.array();
            byte[] addressBytes = op.getAddress().getEncoded();
            for (int i = 0; i < value.length; i++)
                sigData.add(value[i]);

            for (int i = 0; i < addressBytes.length; i++)
                sigData.add(addressBytes[i]);
        }
        
	
        byte[] sigD = new byte[sigData.size()];
        int i = 0;
        for (Byte sb : sigData)
            sigD[i++] = sb;
        return sigD;
    }

    /** 
     *   Computes rawTx = inputs^outputs, i.e. transaction without transID
     */
    public byte[] getRawTx() {
        ArrayList<Byte> rawTx = new ArrayList<Byte>();
        for (Input in : inputs) {
            byte[] prevTxHash = in.getPrevTxHash();
            ByteBuffer b = ByteBuffer.allocate(Integer.SIZE / 8);
            b.putInt(in.getOutputIndex());
            byte[] outputIndex = b.array();
            byte[] signature = in.getSignature();
            if (prevTxHash != null)
                for (int i = 0; i < prevTxHash.length; i++)
                    rawTx.add(prevTxHash[i]);
            for (int i = 0; i < outputIndex.length; i++)
                rawTx.add(outputIndex[i]);
            if (signature != null)
                for (int i = 0; i < signature.length; i++)
                    rawTx.add(signature[i]);
        }
        for (Output op : outputs) {
            ByteBuffer b = ByteBuffer.allocate(Double.SIZE / 8);
            b.putDouble(op.getValue());
            byte[] value = b.array();
            byte[] addressBytes = op.getAddress().getEncoded();
            for (int i = 0; i < value.length; i++) {
                rawTx.add(value[i]);
            }
            for (int i = 0; i < addressBytes.length; i++) {
                rawTx.add(addressBytes[i]);
            }

        }
        byte[] tx = new byte[rawTx.size()];
        int i = 0;
        for (Byte b : rawTx)
            tx[i++] = b;
        return tx;
    }

    public boolean isCoinbase() {
	return coinbase;
    }

    public int numInputs() {
        return inputs.size();
    }

    public int numOutputs() {
        return outputs.size();
    }
}
