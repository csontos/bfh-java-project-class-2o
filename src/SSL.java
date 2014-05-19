import java.util.List;
import java.util.Collections;

/*
Klasse:
Beschreibung: Das ist die Klasse für Arbeitgeber SSL= Schuldner steruerbarer Leistung

*/
public class SSL implements Comparable{
	public final static String DISCRIMINATOR = "SSL";
	private static int ID; //wenn es diese nicht gibt, setzen
	private static String Firmenname;
	private static int Sitz;
	
	public SSL(int iD, String firmenname, int sitz) {
		
	}


	public static SSL getSSL( List<String> values ) {  
		int NewID = 0;
		String Name;
		int bfs;
			
		if (values.size() == 2) {
			if(QuellenSteuer.getSsls().size() == 0) {
				NewID = 1;
			} else {
				Collections.sort(QuellenSteuer.getSsls());
				int LargestId = QuellenSteuer.getSsls().get(QuellenSteuer.getSsls().size()-1).getID();
				System.out.println(LargestId);
				NewID = LargestId++;
			}
			Name = values.get(0);
			bfs = Integer.parseInt(values.get(1));
		} else if (values.size() == 3) {
			NewID = Integer.parseInt(values.get(0));
			Name = values.get(1);
			bfs = Integer.parseInt(values.get(2));
		} else {
			throw new RuntimeException("Falsche Anzahl von Werten: "
					+ values.size() + "\n" + format());
		}
		try {
			int ID = NewID;
			String Firmenname = Name;
			int Sitz = bfs;
			return new SSL(ID, Firmenname, Sitz);
		} catch (RuntimeException r) {
			throw new RuntimeException("Error: " + r.getMessage() + "\n"
					+ format());
		}
	}
	
	public static int getID() {
		return ID;
	}

	private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID; Name; Sitz";
		   }


	public int compareTo(Object o) {
		SSL that = (SSL)o;
		return this.getID() - that.getID();
	}	   
}
