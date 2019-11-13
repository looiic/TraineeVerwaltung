package logic;

public class Standort {

    /** Beschreibung des Standort */
    private String standort;

    /**Default-Konstuktor */
    public Standort() {
    }

    /**Konstuktor */
    public Standort(String standort) {
        this.standort = standort;
    }

    /** Liefert die Beschreibung des Standortes zurück */
    public String getStandort() {
        return standort;
    }

    /** Setzt den Standort */
    public void setStandort(String standort) {
        this.standort = standort;
    }
}
