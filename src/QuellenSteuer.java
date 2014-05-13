/*
Klasse: QuellenSteuer
Beschreibung: 

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class QuellenSteuer {

	private final static char EOF_CHAR = '-'; // signalisiert Ende der Eingabe

	// Daten werden in List verwaltet
	private static List<Gemeinde> gems = new LinkedList<Gemeinde>();
	private static List<SSL> ssls = new LinkedList<SSL>();
	private static List<QUP> qups = new LinkedList<QUP>();
	private static List<ABR> abrs = new LinkedList<ABR>();

	public static List<SSL> getSsls() {
		return ssls;
	}
	
	public static void main(String[] args) {
		/** eigene Methode "waitforInput" erstellen mit Code oder While Schleife
		*   Hier wird geprüft, ob bereits Argumente mitgegeben werden, falls nicht via Scanner einlesen. Sollange nicht exit oder sonst irgendwas gewählt wird,
		*   soll die Eingabe von Argumenten wiederholt werden.
		*/
		
		waitforInput(args);
	
	}

	public static void waitforInput(String[] args) {	
		
		if( args.length==0 ) {
			help();
			Scanner input = new Scanner(System.in);
			System.out.print("Bitte Auswahl angeben: "); 
			String s = input.nextLine();
			
			if(s.equals("exit")){
				System.out.println("Das Programm wurde beendet");
				System.exit(-1);
			}
			
			args = s.split(" ");   
	    }

		String cmd = args[0];

		if (cmd.equals("imp")) {
			Scanner sc = null;
			if (args.length > 1) { // Einlesen von Datei
				File f = null;
				try {
					f = new File(args[1].trim());
					sc = new Scanner(f);
				} catch (FileNotFoundException fnfe) {
					System.out.println("Datei " + f.getName()
							+ " kann nicht gefunden werden.");
					System.exit(-1);
				}
			} else { // Einlesen von stdin
				sc = new Scanner(System.in);
			}

			imp(sc);

//			if (sc != null)
//				sc.close();
			
		} else if (cmd.equals("exp")) {
			PrintStream out = null;
			String discriminator = null;

			if (args.length <= 1) {
				System.out.println("Falsche Anzahl von Argumenten für exp.");
				System.exit(-1);
			}

			discriminator = args[1].trim();

			if (args.length > 2) {
				File f = null;
				try {
					f = new File(args[2].trim());
					out = new PrintStream(f);
					export(discriminator, out);
					out.close();
				} catch (FileNotFoundException fnfe) {
					System.out.println("Datei " + f.getName()
							+ " kann nicht geöffnet werden.");
					System.exit(-1);
				}
			} else {
				export(discriminator, System.out);
			}
			
		} else if (cmd.equals("show")) {
			Scanner sc = null;
			sc = new Scanner(System.in);

			if (args.length > 3) {
				System.out.println("Falsche Anzahl von Argumenten für show.");
				System.exit(-1);
			}

			show(sc);

//			if (sc != null)
//				sc.close();
		} else if (cmd.equals("del")) {
			String discriminator = null;
			
			Scanner sc = null;
			sc = new Scanner(System.in);
			
			if (args.length != 1) {
				System.out.println("Falsche Anzahl von Argumenten für del.");
				System.exit(-1);
			}
			
			//Del Funktion aufrufen
			del(sc);
			
		}

		else {
			System.out.println("Falsche Argumentliste");
		}
		
		waitforInput(new String[0]);
			
	}
	
	private static void export(String discriminator, PrintStream out) {
		out.println("# exporting '" + discriminator + "' at "
				+ new java.util.Date());

		if (discriminator == "ALL"
				|| discriminator.equals(Gemeinde.DISCRIMINATOR)) {
			for (Gemeinde g : gems)
				out.println(Gemeinde.DISCRIMINATOR + ": " + g);
		}
		 if( discriminator== "ALL" || discriminator.equals(SSL.DISCRIMINATOR)) {
		 for( SSL s : ssls )
			 out.println( SSL.DISCRIMINATOR + ": " + s );
		 }
		 if( discriminator== "ALL" || discriminator.equals(QUP.DISCRIMINATOR)) {
		 for( QUP q : qups )
			 out.println( QUP.DISCRIMINATOR + ": " + q );
		 }
		 if( discriminator== "ALL" || discriminator.equals(ABR.DISCRIMINATOR)) {
		 for( ABR a : abrs )
			 out.println( ABR.DISCRIMINATOR + ": " + a );
		 }
	}

	private static void imp( Scanner sc ) {
      int line_ct = 0;
      int imp_ct = 0;
      
      String line = "";
      
      while( true ) {
         System.out.println("Eingabe: ");
         line = sc.nextLine();
         
         if( line==null )
            break;
         if (line.length() == 0 || line.charAt(0) == '#') // Leere Zeilen oder Kommentarzeilen ignorieren
            continue;
         if( line.charAt(0)==EOF_CHAR )
            break;
         
         line_ct++;
         
         // Format:
         // GEM: 2732; AG; Uezwil;
         // SSL:
         // QUP:
         // ABR:

         String discriminator; // suche ":" in "GEM: 2732; AG; Uezwil"
         int colonPosition;
         if ((colonPosition = line.indexOf(":")) < 0) {
            System.out.println(
                  "Parsing-Fehler. Kein Discriminator in Zeile " + line);
            continue;
         }
         discriminator = line.substring(0, colonPosition).trim();
         //System.out.println(discriminator);
         line = line.substring(colonPosition + 1).trim();

         List<String> tokens = new LinkedList<String>(); // alle Tokens
                                                         // einlesen, Trenner
                                                         // ist ";"
         while (true) {
            int delimPos = line.indexOf(";");
            if (delimPos < 0) {
               tokens.add(line.trim());
               break; // kein ";" mehr
            } else {
               tokens.add(line.substring(0, delimPos).trim());
               line = line.substring(delimPos + 1);
            }
         }

         if (discriminator.equals("GEM")) {
            try {
               Gemeinde g = Gemeinde.getGemeinde(tokens);
               if( gems.contains( g )) {
                  System.out.println("Eine Gemeinde mit dieser BFS-Nummer existiert bereits.");
                  continue;
               }
               gems.add(g);
               imp_ct++;
               System.out.println(g);
            } catch (RuntimeException re) {
               System.out.println("Error: " + re.getMessage());
            }
         }
          else if ( discriminator.equals("SSL")) {
        	  try {
        		  SSL s = SSL.getSSL(tokens);
        		  if( ssls.contains( s )) {
        			  System.out.println("Ein Arbeitgeber mit dieser ID existiert bereits.");
        			  continue;
        		  }
        		  ssls.add(s);
                  imp_ct++;
                  System.out.println(s);
        	  } catch (RuntimeException re) {
                  System.out.println("Error: " + re.getMessage());
              }
          }
          else if ( discriminator.equals("QUP")) {
         	  try {
        		  QUP q = QUP.getQUP(tokens);
        		  if( qups.contains( q )) {
        			  System.out.println("Der Quellensteuerpflichtige mit dieser ID existiert bereits.");
        			  continue;
        		  }
        		  qups.add(q);
                  imp_ct++;
                  System.out.println(q);
        	  } catch (RuntimeException re) {
                  System.out.println("Error: " + re.getMessage());
              }
        	  
          }

          else if ( discriminator.equals("ABR")) {
         	  try {
        		  ABR a = ABR.getABR(tokens);
        		  if( abrs.contains( a )) {
        			  System.out.println("Die Abrechnung mit dieser ID existiert bereits.");
        			  continue;
        		  }
        		  abrs.add(a);
                  imp_ct++;
                  System.out.println(a);
        	  } catch (RuntimeException re) {
                  System.out.println("Error: " + re.getMessage());
              }
        	  
          }
         else {
            System.out.println("Parsing error. Kein gültiger Discriminator: "
                  + discriminator);
         }
      }
      System.out.println("Anzahl der Zeilen: " + line_ct);
      System.out.println("Anzahl der Datensätze: " + imp_ct);
   }
	
	private static void del(Scanner sc) {
		  int line_ct = 0;
	      int imp_ct = 0;
	      
	      String line = "";
	      
	      while( true ) {
	         System.out.println("Eingabe:");
	         line = sc.nextLine();
	         
	         if( line==null )
	            break;
	         if (line.length() == 0 || line.charAt(0) == '#') // Leere Zeilen oder Kommentarzeilen ignorieren
	            continue;
	         if( line.charAt(0)==EOF_CHAR )
	            break;
	         
	         line_ct++;
	         
	         // Ausgabe gemäss C1 Aufgabe im Unterricht vom 12.05.2014

	        String[] discriminator = line.split(" ");

	     	if (discriminator[0] == "GEM" || discriminator[0].equals(Gemeinde.DISCRIMINATOR)) {
	     		
	     		if(discriminator.length == 1){
	     			for(int i = 0; i < gems.size(); i++){
	     				boolean match = false;
	     					
	     				for(int j = 0; j < qups.size(); j++){
	     					if(qups.get(j).getWohnort() == gems.get(i).getBfs()) match = true;
	     				}
	     					
	     				if(match == false){
	     					gems.remove(i);
	     				}
	     			}
	     		}
	     		else if(discriminator[1].equals("bfs")){
	     			if(discriminator.length != 3){
	     				System.out.println("Keine BFS Nummer eingegeben. Bitt geben Sie einen Befehl im Format GEM bfs <BFS NR>");
	     				waitforInput(new String[0]);
	     			}
	     			//ToDo: GEM mit BFS ID auslesen und löschen
	     		}
	          
	     	} else if (discriminator[0] == "QUP" || discriminator[0].equals(QUP.DISCRIMINATOR)) {
	     		
	     		//ToDo: QUP löschen handhaben
	     		
	      	}else {
	            System.out.println("Parsing error. Kein gültiger Discriminator: " + discriminator);
	     	}
	     	System.out.println("Anzahl der Zeilen: " + line_ct);
	     	System.out.println("Anzahl der Datensätze: " + imp_ct);
	   }
		
		//ToDo: Prüfung für alle Argumente
		
	}
	
	private static void show( Scanner sc ) {
	      int line_ct = 0;
	      int imp_ct = 0;
	      
	      String line = "";
	      
	      while( true ) {
	         System.out.println("Eingabe:");
	         line = sc.nextLine();
	         
	         if( line==null )
	            break;
	         if (line.length() == 0 || line.charAt(0) == '#') // Leere Zeilen oder Kommentarzeilen ignorieren
	            continue;
	         if( line.charAt(0)==EOF_CHAR )
	            break;
	         
	         line_ct++;
	         
	         // Format:
	         // GEM
	         // GEM k
	         // GEM b
	         // QUP
	         // QUP k
	         // SSL
	         // SSL k
	         // ABR
	         // ABR kanton_kuerzel
	         
	         // Ausgabe gemäss C1 Aufgabe im Unterricht vom 12.05.2014

	         String discriminator; // suche ":" in "GEM: 2732; AG; Uezwil"
	         discriminator = line.substring(0).trim();
	         //System.out.println(discriminator);

	         List<String> tokens = new LinkedList<String>(); // alle Tokens
	                                                         // einlesen, Trenner
	                                                         // ist ";"

	     	if (discriminator == "GEM" || discriminator.equals(Gemeinde.DISCRIMINATOR)) {
	     		
	     		Collections.sort(gems);
	     		System.out.println(gems.toString());
	     		// Darf nur ausgeführt werden, falls "GEM k" gewählt ist.
	     		Collections.sort(gems, Gemeinde.GEM_K);
	     		System.out.println(gems.toString());
	     	// Darf nur ausgeführt werden, falls "GEM b" gewählt ist.
	     		Collections.sort(gems, Gemeinde.GEM_B);
	     		System.out.println(gems.toString());
	          
	     	} 
	     	/*else if (discriminator == "QUP" || discriminator.equals(QUP.DISCRIMINATOR)) {
	     		
	     		Collections.sort(qups);
	     		System.out.println(qups.toString());
	          
	     	} 
	     	else if (discriminator == "SSL" || discriminator.equals(SSL.DISCRIMINATOR)) {
	     		
	     		Collections.sort(ssls);
	     		System.out.println(ssls.toString());
	          
	     	} */
	     	else {
	            System.out.println("Parsing error. Kein gültiger Discriminator: " + discriminator);
	        }
	      System.out.println("Anzahl der Zeilen: " + line_ct);
	      System.out.println("Anzahl der Datensätze: " + imp_ct);
	   }
	}

	
	
	private static void help() {
		/* 'QUP: qup_id; name; vorname; wohnort(bfs_nr); ...' (bei nicht vorhandener qup_id wird diese automatisch vergeben)\n"
				+ "        'SSL: ssl_id; name; sitz(bfs_nr); ...(bei nicht vorhandener ssl_id wird diese automatisch vergeben)\n"
				+ "        'ABR: abr_id; qup(qup_id); ssl(ssl_id); jahr; monat; betrag...' (bei nicht vorhandender abr_id wird diese automatisch vergeben)\n"
				+ "exp  : Exportieren von Daten in Datei oder nach stdout\n"
				+ "   exp ALL      : Exportieren aller Daten in Datei oder nach stdout\n"
				+ "   exp GEM      : Exportieren aller Gemeinde-Daten in Datei oder nach stdout\n"
				+ "   exp QUP      : Exportieren aller QUP-Daten in Datei oder nach stdout\n"
				+ "   exp SSL      : Exportieren aller SSL-Daten in Datei oder nach stdout\n"
				+ "   exp ABR      : Exportieren aller ABR-Daten in Datei oder nach stdout\n"
				+ "show : Anzeige von Daten mit verschiedenen Sortierkriterien:\n"
				+ "   show GEM     : Standardsortierung: bfs - name - kanton\n"
				+ "   show GEM k   : Sortierung: kanton - name - bfs\n"
				+ "   show GEM b   : Sortierung: bfs - name - kanton\n"
				+ "   show QUP     : Sortierung: name - vorname\n"
				+ "   show QUP k   : Sortierung: kanton der wohngemeinde - name - vorname\n"
				+ "   show SSL     : Sortierung: name - sitz\n"
				+ "   show SSL k   : Sortierung: kanton des sitzes - name\n"
				+ "   show ABR     : Anzeige aller Abrechnungen. Sortierung nach steuerlich relevantem Sitz \n"
				+ "   show ABR kanton_kuerel   : Anzeige aller Abrechnungen mit steuerl. relev. Sitz im Kanton\n"
				+ "del  : Löschen von Datensätzen:\n"
				+ "   del GEM     : Löschen aller Gemeinden (auf die keine Beziehungen existieren)\n"
				+ "   del GEM bfs : Löschen der Gemeinde (falls keine Beziehung darauf existiert)\n"
				+ "   del QUP     : Löschen aller QUPs (auf die keine Beziehung existieren)\n"
				+ "   del QUP id  : Löschen des QUPs (falls keine Beziehung darauf existiert)\n"
				+ "   del SSL     : Löschen aller SSLs (auf die keine Beziehung existieren)\n"
				+ "   del SSL id  : Löschen des SSLs (falls keine Beziehung darauf existiert)\n"
				+ "   del ABR     : Löschen aller ABRs.\n"
				+ "   del ABR id  : Löschen des ABRs\n"
				+ "stat : Statistik\n"
				+ "   stat BUND jahr : Bundesstatistik für das Jahr\n"
				+ "            Summe Bruttolohn aller QUPs und Summe Quellensteuerbetrag für Bund\n"
				+ "   stat KANT id   : Kantonsstatistik für das Jahr \n"
				+ "            Summe Bruttolöhne aller im Kanton ansässigen QUPs\n"
				+ "            Summe Bruttolöhne aller QUPs, deren SSL im Kanton ansässig ist\n"
				+ "            Summe Bruttolöhne aller QUPs, von deren ABR Quellensteuer an den Kanton abgeführt wird\n"
				+ "            Summe Quellensteuerbetrag, den Kanton erhält\n"
				+ "   stat GEM bfs jahr  : Gemeindestatistik der Gemeinde für das Jahr\n"
				+ "            Summe Bruttolohn aller ABRs von QUPs in dem Jahr mit Wohnsitz in Gemeinde\n"
				+ "            Summe Bruttolohn aller ABRs von SSLs in dem Jahr mit Sitz in Gemeinde\n"
				+ "            Betrag Quellensteuer, den die Gemeinde in dem Jahr erhält\n"
				+ "   stat QUP id  jahr  : Individuelle Statistik eines QUPs für das Jahr\n"
				+ "            Bruttojahreslohn des QUP, Quellensteuer und wieviel Quellensteuer Bund, Kanton(e) und Gemeinde(n) erhalten\n"
				+ "   stat SSL id  jahr  : Individuelle Statistik eines SSL für das Jahr\n"
				+ "            Summe Bruttolöhne aller QUPs, deren ABRs beim SSL erfolgen, sowie Summe Quellensteuer mit Aufteilung an Bund, Kantone und Gemeinden."

		;
		System.out.println(s);*/
	}

}
