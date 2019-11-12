package DatenbankMethoden;

import logic.Kurs;
import logic.Person;

import java.sql.*;
import java.util.ArrayList;


public class DbPerson {


    /**
     * Lade alle Personen aus einem Kurs.
     *
     * @param kurs
     * @return
     * @throws SQLException
     */
    public ArrayList<Person> getListPersonen(Kurs kurs) throws SQLException {
        Statement stmt = null;
        String query =
                "select id, vorname, nachname, " +
                        "standort, vorkenntnisse, kurs_id " +
                        "from trainee_verwaltung.Person where kurs_id=" + kurs.getId();

        ArrayList<Person> personen = new ArrayList<>();
        stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            String vorname = rs.getString("vorname");
            String nachname = rs.getString("nachname");
            String standort = rs.getString("standort");
            String vorkenntnisse = rs.getString("vorkenntnisse");
            int kurs_id = rs.getInt("kurs_id");
            personen.add(new Person(id, vorname, nachname, standort, vorkenntnisse, kurs_id));
        }
        stmt.close();
        return personen;
    }

    /**
     * Lade alle Personen..vllt unnötig
     *
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
        stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            String vorname = rs.getString("vorname");
            String nachname = rs.getString("nachname");
            String standort = rs.getString("standort");
            String vorkenntnisse = rs.getString("vorkenntnisse");
            int kurs_id = rs.getInt("kurs_id");
            personen.add(new Person(id, vorname, nachname, standort, vorkenntnisse, kurs_id));
        }
        stmt.close();
        return personen;
    }

    public int addNewPerson(Person person) throws SQLException {
        int newPersonId = 0;

        String query = " insert into trainee_verwaltung.person (vorname, nachname, " +
                "standort, vorkenntnisse, kurs_id) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = Connector.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, person.getVorname());
        preparedStmt.setString(2, person.getNachname());
        preparedStmt.setString(3, person.getStandort());
        preparedStmt.setString(4, person.getVorkenntnisse());
        preparedStmt.setInt(5, person.getKursId());

        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();
        if (resultSet.next()) {
            newPersonId = resultSet.getInt(1);
        }

        preparedStmt.close();
        return newPersonId;
    }

    /**
     * Gib die Person, wie sie in der Datenbank sein sollte. Über die id der Person
     * wird die richtige Person mit den Werten im Objekt Person.
     *
     * @param person
     * @throws SQLException
     */
    public void editPerson(Person person) throws SQLException {
        Statement stmt = null;
        stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet uprs = stmt.executeQuery(
                "select * " +
                        "from trainee_verwaltung.Person" + " WHERE Person.id=" + person.getId() + ";");

        //Bewege cursor zum element, welches geändert werden soll.
        uprs.next();
        uprs.updateInt("id", person.getId());                                   //Da wie id wählen?
        uprs.updateString("vorname", person.getVorname());
        uprs.updateString("nachname", person.getNachname());
        uprs.updateString("standort", person.getStandort());
        uprs.updateString("vorkenntnisse", person.getVorkenntnisse());
        uprs.updateRow();
        uprs.beforeFirst();
        stmt.close();
    }


    public void deletePerson(Person person) throws SQLException {
        Statement stmt = null;
        stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeQuery(
                "delete FROM " + "trainee_verwaltung" +
                        ".Person where Person.id =" + person.getId());


        stmt.close();
    }


}
