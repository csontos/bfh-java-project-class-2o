import java.util.List;
import java.util.Collections;

/*
Klasse:
Beschreibung: Das ist die Klasse für Arbeitgeber SSL= Schuldner steruerbarer Leistung

*/
public class SSL {
	public final static String DISCRIMINATOR = "SSL";
	private static int ID; //wenn es diese nicht gibt, setzen
	private static String Firmenname;
	private static int Sitz;
	
	
	public SSL(int iD, String firmenname, int sitz) {
		
	}


	public static SSL getSSL( List<String> values ) {  
			
		int NewID = 0;
			
		if (values.size() == 2) {
			// NewID = Collections.max(QuellenSteuer.getSsls().getID()); // Das
			// müssen wir noch machen. Randomizaaa
		} else if (values.size() == 3) {
			NewID = Integer.parseInt(values.get(0));
		} else {
			throw new RuntimeException("Falsche Anzahl von Werten: "
					+ values.size() + "\n" + format());
		}
		try {
			int ID = NewID;
			String Firmenname = values.get(1);
			int Sitz = Integer.parseInt(values.get(2));
			return new SSL(ID, Firmenname, Sitz);
		} catch (RuntimeException r) {
			throw new RuntimeException("Error: " + r.getMessage() + "\n"
					+ format());
		}
	}

	   private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID; Name; Sitz";
		   }
	   
}
