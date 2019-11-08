package DatenbankMethoden;

import logic.Connector;
import logic.Person;
import logic.Standort;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbPerson {

    //Für Testzwecke

    public static void main(String[] args) throws SQLException {
        new DbPerson().testMethodePradeep();
    }

    public void testMethodePradeep() throws SQLException {
        Connection con = Connector.getConn();
        String dbName = "trainee_verwaltung";
        List<Person> personen = getListPersonen();
        for (Person person:personen) {
            System.out.println(person);
        }
        Person testPerson = new Person(15, "vorname", "nachname", "basilea", 3, 1);
        insertNewPerson(con, dbName, testPerson);
        System.out.println("Neue Person hinzugefügt");
        personen = getListPersonen();
        for (Person person:personen) {
            System.out.println("Vorkentnisse: " + person.getVorkenntnisse());
            System.out.println("Standort: " + person.getStandort());
            System.out.println("Nachname: " + person.getNachname());
            System.out.println("Vorkentnisse: " + person.getVorname());
            System.out.println("Vorkentnisse: " + person.getId());
            System.out.println("Vorkentnisse: " + person.getKursId());
        }
    }

    public ArrayList<Person> getListPersonen()
            throws SQLException {
        Statement stmt = null;
        String query =
                "select id, vorname, nachname, " +
                        "standort, vorkenntnisse, kurs_id " +
                        "from trainee_verwaltung.Person";

        ArrayList<Person> personen = new ArrayList<>();
        try {
            stmt = Connector.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");
                String standort = rs.getString("standort");
                int vorkenntnisse = rs.getInt("vorkenntnisse");
                int kurs_id = rs.getInt("kurs_id");
                personen.add(new Person(id, vorname, nachname, standort, vorkenntnisse, kurs_id));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return personen;
    }

    public static void insertNewPerson(Connection con, String dbName, Person person)
            throws SQLException {

        Statement stmt = null;
        try {
            stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + dbName +
                            ".Person");

            uprs.moveToInsertRow();
            uprs.updateInt("id", person.getId());
            uprs.updateString("vorname", person.getVorname());
            uprs.updateString("nachname", person.getNachname());
            uprs.updateString("standort", person.getStandort());
            uprs.updateInt("vorkenntnisse", person.getVorkenntnisse());

            uprs.insertRow();
            uprs.beforeFirst();

        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

}
