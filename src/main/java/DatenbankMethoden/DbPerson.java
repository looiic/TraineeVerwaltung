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
        Person testPerson = new Person("test", "wegen", "id", 2, 3);
        insertNewPerson(testPerson);
        System.out.println("Neue Person hinzugefügt");
        personen = getListPersonen();
        for (Person person:personen) {
            System.out.println("Vorkentnisse: " + person.getVorkenntnisse());
            System.out.println("Standort: " + person.getStandort());
            System.out.println("Nachname: " + person.getNachname());
            System.out.println("Vorname: " + person.getVorname());
            System.out.println("Id: " + person.getId());
            System.out.println("KursId: " + person.getKursId());
        }
        System.out.println("\n\nJetzt wird eine Person verändert!\n\n");

        personen.get(2).setNachname("neuerName");

        for (Person person:personen) {
            System.out.println("Vorkentnisse: " + person.getVorkenntnisse());
            System.out.println("Standort: " + person.getStandort());
            System.out.println("Nachname: " + person.getNachname());
            System.out.println("Vorname: " + person.getVorname());
            System.out.println("Id: " + person.getId());
            System.out.println("KursId: " + person.getKursId());
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
                System.out.println("Aktuelle id:" + id);
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

    public static void insertNewPerson(Person person)
            throws SQLException {

        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + "trainee_verwaltung" +
                            ".Person");

            uprs.moveToInsertRow();
            uprs.updateInt("id", person.getId());                                   //Da wie id wählen?
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

    /**Gib die Person, wie sie in der Datenbank sein sollte. Über die id der Person
     * wird die richtige Person mit den Werten im Objekt Person.
     * @param person
     * @throws SQLException
     */

    public static void modifyPerson(Person person)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + "trainee_verwaltung" + ".Person" + "WHERE id =" + person.getId());

            //Bewege cursor zum element, welches geändert werden soll.
                uprs.next();
                uprs.updateInt("id", person.getId());                                   //Da wie id wählen?
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
