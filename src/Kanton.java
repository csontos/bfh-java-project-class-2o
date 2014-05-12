/*
Klasse:
Beschreibung: 

*/



public enum Kanton {
   AG, AI, AR, SZ, ZH;
   
   private String[] longNames = { "Aargau", "Appenzell Innerhoden", "Appenzell Ausserhoden", "Schwyz", "Zürich"};
   private Double[] kantSteuer = { 4.5, 4.5, 4.5, 4.0, 4.5 };
   
   public String toString() {
      return longNames[this.ordinal()];
   }
   public double steuerSatz() {
      return kantSteuer[ this.ordinal() ];
   }
}
