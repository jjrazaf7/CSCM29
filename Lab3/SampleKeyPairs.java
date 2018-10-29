import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import LabUtils.CryptoUtils;

public class SampleKeyPairs {
    private ArrayList<KeyPair> people;
    private HashMap<PublicKey, Integer> pubKeyMapping;

    /** 
     * This constructor generates k rsa key pairs
     */
    public SampleKeyPairs(int k) throws NoSuchAlgorithmException {
        this.generate(k);
    }

    public SampleKeyPairs(ArrayList<KeyPair> p, HashMap<PublicKey, Integer> h) {
        this.people = p;
        this.pubKeyMapping = h;
    }

    private void generate(int k) throws NoSuchAlgorithmException {
        this.people = new ArrayList<KeyPair>();
        this.pubKeyMapping = new HashMap<PublicKey, Integer>();
        byte[] initialKey = new byte[32];
        int i = 0;
        while (i < initialKey.length) {
            initialKey[i] = (byte)i;
            ++i;
        }
        SecureRandom prg = new SecureRandom(initialKey);
        int numSizeBits = 2048;
        int i2 = 0;
        while (i2 < k) {
            byte[] key = new byte[32];
            prg.nextBytes(key);
            System.out.println("Generating key pair " + i2 + " of " + k);
            KeyPair rp = CryptoUtils.generateKeyPair(numSizeBits);
            this.people.add(rp);
            this.pubKeyMapping.put(rp.getPublic(), i2);
            ++i2;
        }
    }

    public ArrayList<KeyPair> getPeople() {
        return this.people;
    }

    public int getPerson(PublicKey addr) {
        Integer index = this.pubKeyMapping.get(addr);
        if (index == null) {
            return -1;
        }
        return index;
    }
}
