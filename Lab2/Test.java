import java.util.ArrayList;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.InvalidKeyException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

	// generating 3 rsa key pairs
	SampleKeyPairs skp = new SampleKeyPairs(3);
	
	PublicKey pkA = skp.getPeople().get(0).getPublic();
	PrivateKey skA = skp.getPeople().get(0).getPrivate();
	
	PublicKey pkB = skp.getPeople().get(1).getPublic();
	PrivateKey skB = skp.getPeople().get(1).getPrivate();

	
	
	// creating the ledger and 
	// adding accounts pkA, pkB and pkC into the ledger with initial balance of 15, 5, 10 resp. 
	Ledger ledger = new Ledger();
	ledger.addAccount(pkA, 15);
	ledger.addAccount(pkB, 5);
	ledger.print(ledger);

	
	// create a 1-1 transaction t1
	Input in = new Input(15, pkA);
	Output out = new Output(15, pkB);	
	Transaction t1 = new Transaction(in, out);

	
	// adds pkA's signature into the transaction t1
	byte[] sigD = t1.getRawDataToSign(0);
	byte[] sig = Crypto.sign(skA, sigD);
	in.addSignature(sig);

	Transaction[] l = new Transaction[1];

	TxProcessor txProcessor = new TxProcessor(ledger);

	// submit t1 to the transaction processor 
	l[0] = t1;
	ledger = txProcessor.processTxs(l);
	ledger.print(ledger);
    }
}
			      


