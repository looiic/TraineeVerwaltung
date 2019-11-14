package DTO;

/** Die Kursklasse zur Erzeugung von Kursen innerhalb der Traineeverwaltung*/
public class Kurs {

    /** Eindeutige Identifikationskennung eines Kurses*/
    private int id;
    /**Jahrgang eines Kurses */
    private String jahrgang;
    /**Raum eines Kurses */
    private String raum;

    /**Default-Konstuktor */
    public Kurs() {
    }

    /**Konstuktor */
    public Kurs(int id, String jahrgang, String raum) {
        this.id = id;
        this.jahrgang = jahrgang;
        this.raum = raum;
    }

    /** Konstruktor **/
    public Kurs(String jahrgang, String raum) {
        this.jahrgang = jahrgang;
        this.raum = raum;
    }

    /** Liefert eine Identifikationskennung eines Kurses zurück */
    public int getId() {
        return id;
    }

    /**Setzt eine Identifikationskennung.*/
    public void setId(int id) {
        this.id = id;
    }

    /** Liefert einen Jahrgang eines Kurses zurück **/
    public String getJahrgang() {
        return jahrgang;
    }

    /** Setzt einen Jahrgang eines Kurses */
    public void setJahrgang(String jahrgang) {
        this.jahrgang = jahrgang;
    }

    /** Liefert einen Raum eines Kurses zurück **/
    public String getRaum() {
        return raum;
    }

    /** Setzt einen Raum eines Kurses */
    public void setRaum(String raum) {
        this.raum = raum;
    }
}
