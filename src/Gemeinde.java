/*
Klasse:
Beschreibung: 

*/


import java.util.List;

public class Gemeinde {
   public final static String DISCRIMINATOR = "GEM";
   
   private int bfs;        //BFS-Gemeindenummer
   private String name;
   private Kanton kanton;
   

   public Gemeinde(int bfs, String name, Kanton kanton) {
      this.bfs=bfs; this.name=name; this.kanton=kanton;
   }
   
   public Gemeinde(int bfs, String name, String kanton) {
      this(bfs, name, Kanton.valueOf( kanton ));
   }
   
   public static Gemeinde getGemeinde( List<String> values ) {
      if( values.size() != 3 ) {
         throw new RuntimeException("Falsche Anzahl von Werten: " + values.size() + "\n" + format());
      }
      try {
      int bfs = Integer.parseInt( values.get(0) );
      String kanton = values.get(1);
      String name = values.get(2);
      return new Gemeinde( bfs, name, kanton );
      }
      catch(RuntimeException r) {
         throw new RuntimeException("Error: " + r.getMessage() + "\n" + format());
      }
   }
   
   public String toString(){
      return bfs + "; " + kanton.name() + "; " + name;
   }
   
   public double steuerGemeinde() {
      return 0.0;
   }
   public double steuerKanton() {
    return this.kanton.steuerSatz();
   }
   public double steuerBund() {
      return 0.0;
   }
   public double steuerAlle() {
      return steuerGemeinde() + steuerKanton() + steuerBund();
   }
   
   public boolean equals( Object o) {
      if( this==o ) return true;
      if( o==null || this.getClass()!=o.getClass() ) return false;
      Gemeinde that = (Gemeinde)o;
      return that.bfs==this.bfs;
   }

   public int hashCode() {
      return bfs;
   }
   
   private static String format() {
      return "Erwartetes Format:\n"+ DISCRIMINATOR + ":" + " bfs_nummer; kanton_kuerzel; gemeinde_name";
   }
}