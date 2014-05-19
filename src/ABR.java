import java.util.List;

/*
Klasse:
Beschreibung: 

*/


public class ABR implements Comparable{

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
		   	int NewID;
		   	int tmpq;
		   	int tmps;
		   	int Jahr;
		   	int Monat;
		   	double Betrag;
		   
		   
		   	if( values.size() == 5 ){
		   		  //ID generieren (siehe SSL)
		   	} else if( values.size() == 6 ) {
		   		NewID = Integer.parseInt( values.get(0) ); // Hier noch die IDs ect.. von den Objekten implementieren
		   		tmpq = Integer.parseInt(values.get(1));
		    	//if (QUP.getID(values) // Prüfen ob QUP ID existiert?
		    	//int Qup.ID = Integer.parseInt(values.get(1));
		    	tmps = Integer.parseInt(values.get(2));
		    	// int Ssl.ID = Integer.parseInt(values.get(2));
		    	// Prüfen ob SSL ID existiert?
		    	Jahr = Integer.parseInt(values.get(3));		      
		    	Monat = Integer.parseInt(values.get(4));
		    	Betrag = Double.parseDouble(values.get(5));  
		      } else {
		    	  throw new RuntimeException("Falsche Anzahl von Werten: " + values.size() + "\n" + format());
		      }
		   	  try {
		    	  
		    	  //return new ABR( ID, Qup.ID, Ssl.ID, Jahr, Monat, Betrag );
		    	  // return new ABR( ID, Qup.ID, Ssl.ID, Jahr, Monat, Betrag );
		    	  return null;
		      }
		      catch(RuntimeException r) {
		         throw new RuntimeException("Error: " + r.getMessage() + "\n" + format());
		      }  
	   }
	   
	   private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID, QUP_ID; SSL_ID; Jahr; Monat; Betrag";
		   }

	public int compareTo(Object o) {
		ABR that = (ABR)o;
		return this.ID - that.ID;
	}
	
}
