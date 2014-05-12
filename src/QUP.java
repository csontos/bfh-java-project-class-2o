import java.util.List;

/*
 Klasse:
 Beschreibung: Das ist die Klasse für den Quellensteuerpflichten (QUP)

 */
public class QUP {
	public final static String DISCRIMINATOR = "QUP";
	private int ID;
	private String Name;
	private String Vorname;
	private boolean Ansaessig;
	private int Wohnort;
	private int Kinder;

	public QUP(int ID, String name, String vorname, int wohnort) {
		this.ID = ID;
		this.Name = name;
		this.Vorname = vorname;
		this.Wohnort = wohnort;
	}

	public int getKinder() {
		return Kinder;
	}

	public int getWohnort() {
		return Wohnort;
	}

	public boolean isAnsaessig() {
		return Ansaessig;
	}

	public String getVorname() {
		return Vorname;
	}

	public String getName() {
		return Name;
	}

	public static QUP getQUP(List<String> values) {
		if (values.size() != 4) {
			throw new RuntimeException("Falsche Anzahl von Werten: "
					+ values.size() + "\n" + format());
		}
		try {
			int ID = Integer.parseInt(values.get(0));
			String name = values.get(1);
			String vorname = values.get(2);
			int wohnort = Integer.parseInt(values.get(3));
			return new QUP(ID, name, vorname, wohnort);
		} catch (RuntimeException r) {
			throw new RuntimeException("Error: " + r.getMessage() + "\n"
					+ format());
		}
	}

	private static String format() {
		return "Erwartetes Format:\n" + DISCRIMINATOR + ":"
				+ " ID; Name; Vorname; Wohnort";
	}

}
