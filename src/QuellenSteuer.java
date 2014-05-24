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

import javax.swing.text.html.HTMLDocument.Iterator;

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
	
	public static List<QUP> getQups() {
		return qups;
	}
	
	public static List<ABR> getAbrs() {
		return abrs;
	}
	
	public static void main(String[] args) {
		/* eigene Methode "waitforInput" erstellen mit Code oder While Schleife
		*   Hier wird gepr�ft, ob bereits Argumente mitgegeben werden, falls nicht via Scanner einlesen. Sollange nicht exit oder sonst irgendwas gew�hlt wird,
		*   soll die Eingabe von Argumenten wiederholt werden.
		*/
		
		waitforInput(args);
	
	}

	public static void waitforInput(String[] args) {	
		
		/*
		 * Falls beim Aufruf keine Argumente mitgegeben werden, k�nnen diese auf der Komandozeile eingegeben werden
		 */
		if( args.length==0 ) {
			help();
			Scanner input = new Scanner(System.in);
			System.out.print("Bitte Auswahl angeben: "); 
			String s = input.nextLine();
			
			//Bei der Eingabe "exit" schliesst sich das Programm
			if(s.equals("exit")){
				System.out.println("Das Programm wurde beendet");
				System.exit(-1);
			}
			
			/*
			 * Der eigegenene String wird nach dem Leerzeichen getrennt und die einzelnen Elemente als die Argumente des Programms definiert
			 */
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
					waitforInput(new String[0]);
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
				System.out.println("Falsche Anzahl von Argumenten f�r exp.");
				waitforInput(new String[0]);
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
							+ " kann nicht ge�ffnet werden.");
					waitforInput(new String[0]);
				}
			} else {
				export(discriminator, System.out);
			}
			
		} else if (cmd.equals("show")) {
			Scanner sc = null;
			sc = new Scanner(System.in);

			if (args.length > 3) {
				System.out.println("Falsche Anzahl von Argumenten f�r show.");
				waitforInput(new String[0]);
			}

			show(sc);

//			if (sc != null)
//				sc.close();
		} else if (cmd.equals("del")) {
			String discriminator = null;
			
			Scanner sc = null;
			sc = new Scanner(System.in);
			
			if (args.length != 1) {
				System.out.println("Falsche Anzahl von Argumenten f�r del.");
				waitforInput(new String[0]);
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
         line = line.substring(colonPosition + 1).trim();

         /*
          * alle Tokens einlesen, Trenner ist ";"
          */
         List<String> tokens = new LinkedList<String>();
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
            System.out.println("Parsing error. Kein g�ltiger Discriminator: "
                  + discriminator);
         }
      }
      System.out.println("Anzahl der Zeilen: " + line_ct);
      System.out.println("Anzahl der Datens�tze: " + imp_ct);
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
	         /*
	          * Leere Zeilen oder Kommentarzeilen ignorieren
	          */
	         if (line.length() == 0 || line.charAt(0) == '#')
	            continue;
	         if( line.charAt(0)==EOF_CHAR )
	            break;
	         
	         line_ct++;

	        String[] discriminator = line.split(" ");

	     	if (discriminator[0] == "GEM" || discriminator[0].equals(Gemeinde.DISCRIMINATOR)) {
	     		
	     		if(discriminator.length == 1){
	     			for(int i = 0; i < gems.size(); i++){
	     				boolean match = false;
	     					
	     				for(int j = 0; j < qups.size(); j++){
	     					if(qups.get(j).getWohnort() == gems.get(i).getBfs())
	     						match = true;
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
	     			
	     			int bfsid = 0;
     				try{
     					bfsid = Integer.parseInt(discriminator[2]);
     				} 
     				catch (RuntimeException re) {
     					System.out.println("BFS ID hat kein valides Format");
     					waitforInput(new String[0]);
     				}
	     			
	     			for(int i = 0; i < gems.size(); i++){
	     				if(gems.get(i).getBfs() == bfsid){
	     					gems.remove(gems.get(i));
	     				}
	     			}
	     		}
	          
	     	} else if (discriminator[0] == "QUP" || discriminator[0].equals(QUP.DISCRIMINATOR)) {
	     		
	     		//ToDo: QUP l�schen handhaben
	     		
	      	} else {
	            System.out.println("Parsing error. Kein g�ltiger Discriminator: " + discriminator);
	     	}
	     	System.out.println("Anzahl der Zeilen: " + line_ct);
	     	System.out.println("Anzahl der Datens�tze: " + imp_ct);
	   }
		
		//ToDo: Pr�fung f�r alle Argumente
		
	}
	
	
	private static void show(Scanner sc) {
		// Abbruchbedingungen definieren
		int line_ct = 0;
		int imp_ct = 0;

		String line = "";
		while (true) {
			System.out.println("Eingabe:");
			line = sc.nextLine();

			if (line == null)
				break;
			/*
			 * Leere Zeilen oder Kommentarzeilen ignorieren
			 */
			if (line.length() == 0 || line.charAt(0) == '#')
				continue;
			if (line.charAt(0) == EOF_CHAR)
				break;
			line_ct++;

			/*
			 * Format f�r die Anzeige mit "show" // GEM // GEM k // GEM b // QUP
			 * // QUP k // SSL // SSL k // ABR // ABR kanton_kuerzel
			 */

			String[] discriminator = line.split(" ");
			// String discriminator; // suche ":" in "GEM: 2732; AG; Uezwil"
			// discriminator = line.substring(0).trim();
			// System.out.println(discriminator);

			/*
			 * alle Tokens einlesen, Trenner ist ";"
			 */
			List<String> tokens = new LinkedList<String>();

			if (discriminator[0] == "GEM"
					|| discriminator[0].equals(Gemeinde.DISCRIMINATOR)) {
				if(discriminator.length == 1){
					Collections.sort(gems);
					System.out.println(gems.toString());
				}
				else {
					
					if (discriminator[1].equals("k")) {
						Collections.sort(gems, Gemeinde.GEM_K);
						System.out.println(gems.toString());
					}
					if (discriminator[1].equals("b")) {
						Collections.sort(gems, Gemeinde.GEM_B);
						System.out.println(gems.toString());
					}
				}
			}
			

			if (discriminator[0] == "QUP"
					|| discriminator[0].equals(QUP.DISCRIMINATOR)) {
				if(discriminator.length == 1){
					Collections.sort(qups);
					System.out.println(qups.toString());
				}
				else {
					
					if (discriminator[1].equals("k")) {
						Collections.sort(qups, QUP.QUP_K);
						System.out.println(qups.toString());
					}
				}
				
				if (discriminator[0] == "SSL"
						|| discriminator[0].equals(SSL.DISCRIMINATOR)) {
					if(discriminator.length == 1){
						Collections.sort(ssls);
						System.out.println(ssls.toString());
					}
					else {
						
						if (discriminator[1].equals("k")) {
							Collections.sort(ssls, SSL.SSL_K);
							System.out.println(ssls.toString());
						}
					}
				}
				
			
				if (discriminator[0] == "ABR"
					|| discriminator[0].equals(ABR.DISCRIMINATOR)) {
					if(discriminator.length == 1){
						Collections.sort(ssls);
						System.out.println(ssls.toString());
					}
					else {
						if (discriminator[1].equals("k")) {
							Collections.sort(ssls, SSL.SSL_K);
							System.out.println(ssls.toString());
						}
					}
				}
				
			}
			/*
			 * else if (discriminator[1].equals("id")){ // Hier nach
			 * kanton_k�rzel abfragen }
			 */

			else {
				System.out.println("Parsing error. Kein g�ltiger Discriminator: "
								+ discriminator);
			}
			System.out.println("Anzahl der Zeilen: " + line_ct);
			System.out.println("Anzahl der Datens�tze: " + imp_ct);
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
				+ "del  : L�schen von Datens�tzen:\n"
				+ "   del GEM     : L�schen aller Gemeinden (auf die keine Beziehungen existieren)\n"
				+ "   del GEM bfs : L�schen der Gemeinde (falls keine Beziehung darauf existiert)\n"
				+ "   del QUP     : L�schen aller QUPs (auf die keine Beziehung existieren)\n"
				+ "   del QUP id  : L�schen des QUPs (falls keine Beziehung darauf existiert)\n"
				+ "   del SSL     : L�schen aller SSLs (auf die keine Beziehung existieren)\n"
				+ "   del SSL id  : L�schen des SSLs (falls keine Beziehung darauf existiert)\n"
				+ "   del ABR     : L�schen aller ABRs.\n"
				+ "   del ABR id  : L�schen des ABRs\n"
				+ "stat : Statistik\n"
				+ "   stat BUND jahr : Bundesstatistik f�r das Jahr\n"
				+ "            Summe Bruttolohn aller QUPs und Summe Quellensteuerbetrag f�r Bund\n"
				+ "   stat KANT id   : Kantonsstatistik f�r das Jahr \n"
				+ "            Summe Bruttol�hne aller im Kanton ans�ssigen QUPs\n"
				+ "            Summe Bruttol�hne aller QUPs, deren SSL im Kanton ans�ssig ist\n"
				+ "            Summe Bruttol�hne aller QUPs, von deren ABR Quellensteuer an den Kanton abgef�hrt wird\n"
				+ "            Summe Quellensteuerbetrag, den Kanton erh�lt\n"
				+ "   stat GEM bfs jahr  : Gemeindestatistik der Gemeinde f�r das Jahr\n"
				+ "            Summe Bruttolohn aller ABRs von QUPs in dem Jahr mit Wohnsitz in Gemeinde\n"
				+ "            Summe Bruttolohn aller ABRs von SSLs in dem Jahr mit Sitz in Gemeinde\n"
				+ "            Betrag Quellensteuer, den die Gemeinde in dem Jahr erh�lt\n"
				+ "   stat QUP id  jahr  : Individuelle Statistik eines QUPs f�r das Jahr\n"
				+ "            Bruttojahreslohn des QUP, Quellensteuer und wieviel Quellensteuer Bund, Kanton(e) und Gemeinde(n) erhalten\n"
				+ "   stat SSL id  jahr  : Individuelle Statistik eines SSL f�r das Jahr\n"
				+ "            Summe Bruttol�hne aller QUPs, deren ABRs beim SSL erfolgen, sowie Summe Quellensteuer mit Aufteilung an Bund, Kantone und Gemeinden."

		;
		System.out.println(s);*/
	}

}
