import java.security.PublicKey;

public class Output {

     /** The coin transferred  */
     private int coin;
     
     /** The public key of the recipient */
     private PublicKey to;
     
     public Output(int coin, PublicKey to) {
	 this.coin = coin;
	 this.to = to;
     }

     public PublicKey getRecipient() {
	 return to;
     }

     public int getCoin() {
	 return coin;
     }
 }
