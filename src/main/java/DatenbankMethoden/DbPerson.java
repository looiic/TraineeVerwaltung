package DatenbankMethoden;

import logic.Connector;
import logic.Kurs;
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

        System.out.println("Neue Person hinzugefügt");
        List<Person> personen = getListPersonen();
        printPersonenListe(personen);
        /*System.out.println("\n\nJetzt wird eine Person gelöscht!\n\n");
        System.out.println(getListPersonen().size());
        deletePerson(personen.get(0));
        System.out.println(getListPersonen().size());
        printPersonenListe(personen);

         */
        deletePerson(personen.get(0));

        System.out.println("\n\nJetzt wird eine Person verändert!\n\n");

        personen.get(0).setNachname("neuerName");
        editPerson(personen.get(0));
        personen = getListPersonen();

        printPersonenListe(personen);
    }

    private void printPersonenListe(List<Person> personen) throws SQLException {
        for (Person person : personen) {
            System.out.println("Vorkentnisse: " + person.getVorkenntnisse());
            System.out.println("Standort: " + person.getStandort());
            System.out.println("Nachname: " + person.getNachname());
            System.out.println("Vorname: " + person.getVorname());
            System.out.println("Id: " + person.getId());
            System.out.println("KursId: " + person.getKursId());
        }
        personen = getListPersonen();
        for (Person person:personen) {
            System.out.println("Vorkentnisse: " + person.getVorkenntnisse());
            System.out.println("Standort: " + person.getStandort());
            System.out.println("Nachname: " + person.getNachname());
            System.out.println("Vorname: " + person.getVorname());
            System.out.println("Id: " + person.getId());
            System.out.println("KursId: " + person.getKursId());
        }

    }

    /**
     * Lade alle Personen aus einem Kurs.
     * @param kurs
     * @return
     * @throws SQLException
     */
    public ArrayList<Person> getListPersonen(Kurs kurs) throws SQLException {
        Statement stmt = null;
        String query =
                "select id, vorname, nachname, " +
                        "standort, vorkenntnisse, kurs_id " +
                        "from trainee_verwaltung.Person where kurs_id="+kurs.getId();

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

    /**
     * Lade alle Personen..vllt unnötig
     * @return
     * @throws SQLException
     */
    public ArrayList<Person> getListPersonen() throws SQLException {
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

    public static void addNewPerson(Person person) throws SQLException {

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
    public static void editPerson(Person person) throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "select * " +
                            "from trainee_verwaltung.Person" + " WHERE Person.id=" + person.getId()+";");

            //Bewege cursor zum element, welches geändert werden soll.
                uprs.next();
                uprs.updateInt("id", person.getId());                                   //Da wie id wählen?
                uprs.updateString("vorname", person.getVorname());
                uprs.updateString("nachname", person.getNachname());
                uprs.updateString("standort", person.getStandort());
                uprs.updateInt("vorkenntnisse", person.getVorkenntnisse());
                uprs.updateRow();
                uprs.beforeFirst();


        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }


    public void deletePerson(Person person) throws SQLException {
        Statement stmt = null;
        try {
            stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.executeQuery(
                    "delete FROM " + "trainee_verwaltung" +
                            ".Person where Person.id ="+person.getId());


        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }


}
