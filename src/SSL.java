import java.util.Comparator;
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
	
	   final static Comparator SSL_K = new SSLkComp();
	
	
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
	
	   public String toString(){
		   return Firmenname + "; " + Sitz;
	   }
	
	   public int compareTo( Object o ) {
		   SSL that = (SSL)o;
		   /*  Sortierung: name - sitz
		    * 
		    */
		   int cmp = this.Firmenname.compareTo(that.Firmenname);
		   if (cmp != 0)
			   return cmp;
		   
		   cmp = this.Sitz - that.Sitz;
		   if (cmp != 0)
			   return cmp;
		   
		   return 0;
	   }
	   
	   static class SSLkComp implements Comparator {
		   /*  Sortierung: Kanton - Name - bfs
		    */
			public int compare(Object o1, Object o2) {
				SSL s1 = (SSL)o1;
				SSL s2 = (SSL)o2;
				
				int cmp = s1.Sitz - s2.Sitz;
				if (cmp != 0)
					return cmp;
				
				cmp = s1.Firmenname.compareTo(s2.Firmenname);
				if (cmp != 0)
					return cmp;
				
				return 0;
			}
	   }

	   private static String format() {
		      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " ID; Name; Sitz";
		   }
	   
}
