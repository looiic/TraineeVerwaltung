package DatenbankMethoden;

import logic.Connector;
import logic.Kurs;
import java.sql.*;
import java.util.ArrayList;


/**
Braucht Datenbanknamen und Connection von ausserhalb um zu funktionieren
*/
public class DbKurs {
    public ArrayList<Kurs> getKursListe()
            throws SQLException {
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

    public static void changeJahrgang(Kurs kurs)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + "trainee_verwaltung" + ".Kurs" + "WHERE jahrgang =" + kurs.getJahrgang());

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
                    " ('" + kurs.getJahrgang() + "', '" + kurs.getRaum() + "');");

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

