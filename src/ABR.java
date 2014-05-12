import java.util.List;

/*
Klasse:
Beschreibung: 

*/


public class ABR {

	public final static String DISCRIMINATOR = "ABR";
	private int ID;
	private double Bruttolohn;
	private QUP Qup;
	private SSL Ssl;
	private int Jahr;
	private int Monat;

	
	public int getID() {
		return ID;
	}

	public double getBruttolohn() {
		return Bruttolohn;
	}

	/*
	   public static ABR getABR( List<String> values ) {
		      if( values.size() != 6 ) {
		         throw new RuntimeException("Falsche Anzahl von Werten: " + values.size() + "\n" + format());
		      }
		      try {
		      int ID = Integer.parseInt( values.get(0) ); // Hier noch die IDs ect.. von den Objekten implementieren 
		      int qup.ID = Integer.parseInt(values.get(1));
		      int ssl.ID = Integer.parseInt(values.get(2));
		      int Jahr = values.get(3);		      
		      int Monat = values.get(4);
		      double Betrag = Integer.parseDouble(values.get(5));
		      return new ABR( ID, qup.ID, ssl.ID, Jahr, Monat, Betrag );
		      }
		      catch(RuntimeException r) {
		         throw new RuntimeException("Error: " + r.getMessage() + "\n" + format());
		      }
		   }

	   */
	   private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID, QUP_ID; SSL_ID; Jahr; Monat; Betrag";
		   }
	
}
