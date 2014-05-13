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

	
	   public static ABR getABR( List<String> values ) {
		      if( values.size() != 6 ) {
		         throw new RuntimeException("Falsche Anzahl von Werten: " + values.size() + "\n" + format());
		      }
		      try {
		      int ID = Integer.parseInt( values.get(0) ); // Hier noch die IDs ect.. von den Objekten implementieren
		      int tmpq = Integer.parseInt(values.get(1));
		      //if (QUP.getID(values) // Prüfen ob QUP ID existiert?
		      //int Qup.ID = Integer.parseInt(values.get(1));
		      int tmps = Integer.parseInt(values.get(2));
		      // int Ssl.ID = Integer.parseInt(values.get(2));
		      // Prüfen ob SSL ID existiert?
		      int Jahr = Integer.parseInt(values.get(3));		      
		      int Monat = Integer.parseInt(values.get(4));
		      double Betrag = Double.parseDouble(values.get(5));
		      //return new ABR( ID, Qup.ID, Ssl.ID, Jahr, Monat, Betrag );
		      return new ABR( ID, Qup.ID, Ssl.ID, Jahr, Monat, Betrag );
		      }
		      catch(RuntimeException r) {
		         throw new RuntimeException("Error: " + r.getMessage() + "\n" + format());
		      }
		   }

	   
	   private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID, QUP_ID; SSL_ID; Jahr; Monat; Betrag";
		   }
	
}
