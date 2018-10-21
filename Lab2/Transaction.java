import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Transaction {
    
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    
    /** Creation transaction */
    public Transaction(Output out) {
	inputs = new ArrayList<>();
	outputs = new ArrayList<>();
	outputs.add(out);
    }
    
    /** 1-1 transfer transaction */
    public Transaction(Input in, Output out) {
	inputs = new ArrayList<>();
	inputs.add(in);
	outputs = new ArrayList<>();
	outputs.add(out);
    }

    /** 1-2 transaction transaction */ 
    public Transaction(Input in, Output out1, Output out2) {
	inputs = new ArrayList<>();
	inputs.add(in);
	outputs = new ArrayList<>();
	outputs.add(out1);
	outputs.add(out2);
    }

    /** 2-1 transfer transaction */
    public Transaction(Input in1 , Input in2, Output out) {
	inputs = new ArrayList<>();
	inputs.add(in1);
	inputs.add(in2);
	outputs = new ArrayList<>();
	outputs.add(out);
    }

    public ArrayList<Input> getInputs() {
	return inputs;
    }

    public ArrayList<Output> getOutputs() {
	return outputs;
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
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE / 8); // Temporary memory
        b.putInt(in.getCoin()); 
        byte[] coinIn = b.array();
	for  (int i = 0; i < coinIn.length; i++)
	    sigData.add(coinIn[i]);
	
        for (Output op : outputs) {
            ByteBuffer bo = ByteBuffer.allocate(Integer.SIZE / 8);
            bo.putInt(op.getCoin());
            byte[] coinOut = bo.array();
            byte[] addressBytes = op.getRecipient().getEncoded();
            for (int i = 0; i < coinOut.length; i++)
                sigData.add(coinOut[i]);
            for (int i = 0; i < addressBytes.length; i++)
                sigData.add(addressBytes[i]);
        }
        
	
        byte[] sigD = new byte[sigData.size()];
        int i = 0;
        for (Byte sb : sigData)
            sigD[i++] = sb;
        return sigD;
    }
}
