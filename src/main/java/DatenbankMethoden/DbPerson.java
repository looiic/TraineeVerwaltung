package DatenbankMethoden;

import DTO.Kurs;
import DTO.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DbPerson verwaltet alle Datenbankabfragen zur Datenbanktabelle Person.
 */
public class DbPerson {


    /**
     * Lade alle Personen aus einem Kurs.
     *
     * @param kurs Der Kurs für welchen man die Trainees haben will
     * @return Liste von den Trainees für einen Kurs
     * @throws SQLException SQL Exception
     */
    public List<Person> getListPersonen(Kurs kurs) throws SQLException {
        String query =
                "select id, vorname, nachname, " +
                        "fk_standort, vorkenntnisse, fk_kurs_id " +
                        "from trainee_verwaltung.Person where fk_kurs_id=" + kurs.getId();

        ArrayList<Person> personen = new ArrayList<>();
        Statement stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            String vorname = rs.getString("vorname");
            String nachname = rs.getString("nachname");
            String standort = rs.getString("fk_standort");
            String vorkenntnisse = rs.getString("vorkenntnisse");
            int kursId = rs.getInt("fk_kurs_id");
            personen.add(new Person(id, vorname, nachname, standort, vorkenntnisse, kursId));
        }
        stmt.close();
        return personen;
    }


    /**
     * Es wird eine neue Person in die Datenbanktabelle Person hinzugefügt.
     *
     * @param person Person Objekt, das man in die DB speichern will
     * @return Neu generiete ID für das Person Objekt
     * @throws SQLException SQL Exception
     */
    public int addNewPerson(Person person) throws SQLException {
        int newPersonId = 0;

        String query = " insert into trainee_verwaltung.person (vorname, nachname, " +
                "fk_standort, vorkenntnisse, fk_kurs_id) values (?, ?, ?, ?, ?)";
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
     * @param person Person Objekt das man aktualisieren will in der DB
     * @throws SQLException SQL Exception
     */
    public void editPerson(Person person) throws SQLException {
        Statement stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet uprs = stmt.executeQuery(
                "select * " +
                        "from trainee_verwaltung.Person" + " WHERE Person.id=" + person.getId() + ";");

        //Bewege cursor zum element, welches geändert werden soll.
        uprs.next();
        uprs.updateInt("id", person.getId());                                   //Da wie id wählen?
        uprs.updateString("vorname", person.getVorname());
        uprs.updateString("nachname", person.getNachname());
        uprs.updateString("fk_standort", person.getStandort());
        uprs.updateString("vorkenntnisse", person.getVorkenntnisse());
        uprs.updateRow();
        uprs.beforeFirst();
        stmt.close();
    }


    /**
     * Entfernt eine Person aus der Datenbanktabelle Person.
     *
     * @param person Person Objekt, das man aus der DB löchen will
     * @throws SQLException SQL Exception
     */
    public void deletePerson(Person person) throws SQLException {
        Statement stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeQuery(
                "delete FROM " + "trainee_verwaltung" +
                        ".Person where Person.id =" + person.getId());
        stmt.close();
    }
}
