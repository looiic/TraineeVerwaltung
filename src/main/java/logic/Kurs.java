package logic;

public class Kurs {
    private int id;
    private String jahrgang;
    private String raum;

    public Kurs() {
    }

    public Kurs(int id, String jahrgang, String raum) {
        this.id = id;
        this.jahrgang = jahrgang;
        this.raum = raum;
    }

    public Kurs(String jahrgang, String raum) {
        this.jahrgang = jahrgang;
        this.raum = raum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJahrgang() {
        return jahrgang;
    }

    public void setJahrgang(String jahrgang) {
        this.jahrgang = jahrgang;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }
}
