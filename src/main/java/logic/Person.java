package logic;

public class Person {

    private int id;
    private String vorname;
    private String nachname;
    private Standort standort;
    private int vorkenntnisse;
    private int kursId;

    public Person() {
    }

    public Person(int id, String vorname, String nachname, Standort standort, int vorkenntnisse, int kursId) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.standort = standort;
        this.vorkenntnisse = vorkenntnisse;
        this.kursId = kursId;
    }

    public int getId() {
        return id;
    }


    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Standort getStandort() {
        return standort;
    }

    public void setStandort(Standort standort) {
        this.standort = standort;
    }

    public int getVorkenntnisse() {
        return vorkenntnisse;
    }

    public void setVorkenntnisse(int vorkenntnisse) {
        this.vorkenntnisse = vorkenntnisse;
    }

    public int getKursId() {
        return kursId;
    }


}
