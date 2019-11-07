package logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Person {

    private int id;
    private String vorname;
    private String nachname;
    private Standort standort;
    private int vorkenntnisse;
    private int kursId;

    public Person() {

        //-----ACHTUNG---------
        //dieser Code ist nur vorübergehend zum Zeigen wie man eine Connection macht!!!!!!!!
//        try {
//            Connection conn = Connector.getConn();
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM PERSON");
//            while(resultSet.next()){
//                System.out.println(resultSet.getString(1) + " " +
//                        resultSet.getString(2) + " " +
//                        resultSet.getString(3) + " " +
//                        resultSet.getString(4) + " " +
//                        resultSet.getString(5) + " " +
//                        resultSet.getString(6));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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
