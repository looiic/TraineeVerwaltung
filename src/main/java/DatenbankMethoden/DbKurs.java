package DatenbankMethoden;

import javafx.animation.ScaleTransition;
import logic.Connector;
import logic.Kurs;
import logic.Standort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//ToDo: Aktuelles Problem: Die KursListe ist nicht immer konsistent mit der DB. Dies kann eine Fehleranfälligkeit sein!


public class DbKurs {

    public static void main(String[] args) throws SQLException {
        new DbKurs().testMethodePradeep();
    }

    private void testMethodePradeep() throws SQLException {
        ArrayList<Kurs> kursListe = getKursListe();
        printKursListe(kursListe);
        kursListe.get(0).setJahrgang("neuerJahrgangEditiert");
        editKurs(kursListe.get(0));
        printKursListe(getKursListe());
        System.out.println("Jetzt wurde deleted");
        deleteKurs(kursListe.get(0));
        printKursListe(getKursListe());
    }

    private void printKursListe(ArrayList<Kurs> kurse){
        for (Kurs kurs:kurse){
            System.out.println("id: " + kurs.getId());
            System.out.println("Jahrgang: " + kurs.getJahrgang());
            System.out.println("Raum: " + kurs.getRaum());
        }
    }

    public static ArrayList<Kurs> getKursListe() throws SQLException {
        Statement stmt = null;
        String query =
                "select * " +"from trainee_verwaltung.Kurs";

        ArrayList<Kurs> kursListe = new ArrayList<>();

        try {
            stmt = Connector.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String jahrgang = rs.getString("jahrgang");
                String raum = rs.getString("raum");
                kursListe.add(new Kurs(id,jahrgang,raum));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return kursListe;
    }

    /**
     * Übergebe dieser Methode einen veränderten Kurs. Anhand der id des jeweiligen Kurses
     * wird jener Kurs in der Datenbank nach dem übergebenen Objekt Kurs verändert.
     *
     * PS: Habe nur Author hinzugefügt, damit ihr wisst zu wen ihr euch bei Fragen zu dieser
     * Methode wenden könnt ;)
     * @param kurs
     * @throws SQLException
     * @Author Pradeep
     */
    public static void editKurs(Kurs kurs) throws SQLException{
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery("SELECT * FROM trainee_verwaltung.kurs" +
                    " WHERE kURS.ID=" + kurs.getId() + ";");
            uprs.next();
            uprs.updateInt("id", kurs.getId());
            uprs.updateString("jahrgang", kurs.getJahrgang());
            uprs.updateString("raum", kurs.getRaum());
            uprs.updateRow();
            uprs.beforeFirst();
        } catch (SQLException e){
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close();}
        }
    }

    public static void changeJahrgang(Kurs kurs)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + "trainee_verwaltung" + ".Kurs " + "WHERE Kurs.jahrgang =" + kurs.getJahrgang());

            //Bewege cursor zum element, welches geändert werden soll.
            uprs.next();
            uprs.updateString("jahrgang", kurs.getJahrgang());
            uprs.insertRow();
            uprs.beforeFirst();
        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public static void changeRaum(Kurs kurs)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + "trainee_verwaltung" + ".Kurs" + "WHERE raum =" + kurs.getRaum());

            //Bewege cursor zum element, welches geändert werden soll.
            uprs.next();
            uprs.updateString("raum", kurs.getRaum());
            uprs.insertRow();
            uprs.beforeFirst();
        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void deleteKurs(Kurs kurs) throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.executeQuery(
                    "delete FROM " + "trainee_verwaltung" +
                            ".Kurs where Kurs.id ="+kurs.getId());

        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }


    public Kurs createKurs(Kurs kurs) throws SQLException {
        Statement stmt = null;
        Kurs neuerKurs = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //TODO: mit prepared Statements ersetzen
            ResultSet resultSet = stmt.executeQuery("insert into trainee_verwaltung.kurs (jahrgang, raum) values" +
                    " ('" + kurs.getJahrgang() + "', '" + kurs.getRaum () + "');");

            //TODO: neu erstellter Datensatz zurückliefern damit man vorne die ID hat
//            while(resultSet.next()){
//                neuerKurs.setId(resultSet.getInt("id"));
//                neuerKurs.setRaum(resultSet.getString("raum"));
//                neuerKurs.setJahrgang(resultSet.getString("jahrgang"));
//            }

        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
            return neuerKurs;
    }
}

