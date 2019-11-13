package logic;

/** Die Personenklasse zur Erzeugung von Personen innerhalb der Traineeverwaltung*/
public class Person {

    /** Eindeutige Indentifikationskennung einer Person */
    private int id;
    /** Vorname einer Person */
    private String vorname;
    /**Nachname einer Person */
    private String nachname;
    /**Standort einer Person */
    private String standort;
    /**Vorkenntisse einer Person */
    private String vorkenntnisse;
    /**Eindeutige Identifikationskennung eines Kurses */
    private int kursId;

    /** Default-Konstruktor */
    public Person() {
    }

    /** Konstuktur */
    public Person(String vorname, String nachname, String standort, String vorkenntnisse, int kursId) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.standort = standort;
        this.vorkenntnisse = vorkenntnisse;
        this.kursId = kursId;
    }

    /** Konstuktor */
    public Person(int id, String vorname, String nachname, String standort, String vorkenntnisse, int kursId) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.standort = standort;
        this.vorkenntnisse = vorkenntnisse;
        this.kursId = kursId;
    }


    /** Liefert die Indentifikationskennung einer Person zurück */
    public int getId() {
        return id;
    }

    /** Liefert den Vornamen einer Person zurück */
    public String getVorname() {
        return vorname;
    }

    /** Setzt den Vornamen  einer Person */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /** Liefert den Nachnamen einer Person zurück */
    public String getNachname() {
        return nachname;
    }

    /** Setzt den Nachname  einer Person */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /** Liefert den Standort einer Person zurück */
    public String getStandort() {
        return standort;
    }

    /** Setzt den Standort  einer Person */
    public void setStandort(String standort) {
        this.standort = standort;
    }

    /** Liefert die Vorkenntnisse einer Person zurück */
    public String getVorkenntnisse() {
        return vorkenntnisse;
    }

    /** Setzt die Vorkenntnisse  einer Person */
    public void setVorkenntnisse(String vorkenntnisse) {
        this.vorkenntnisse = vorkenntnisse;
    }

    /** Liefert die Identifikationskennung eines Kurses zurück */
    public int getKursId() {
        return kursId;
    }
    /** Setzt die Identifikationskennung einer Person */
    public void setId(int id) {
        this.id = id;
    }

    /** Setzt die Identifikationskennung eines Kurses */
    public void setKursId(int id) {
        this.kursId = id;
    }
}
