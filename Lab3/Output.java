import java.security.PublicKey;

public class Output {

    /** Value in bitcoins of the output  */
    private double value;
     
    /** The address or public key of the recipient */
    private PublicKey address;
     
    public Output(double v, PublicKey addr) {
	value = v;
	address = addr;
    }

    public PublicKey getAddress() {
	return address;
    }

    public double getValue() {
	return value;
    }
}
