package DatenbankMethoden;


import DTO.Kurs;
import DTO.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DbKurs verwaltet alle Datenbankabfragen zur Datenbanktabelle Kurs.
 */
public class DbKurs {


    /**
     * Holt sich aus der Datenbank trainee_verwaltung alle Elemente
     * aus der Tabelle kurs, aus diesen werden Kurs-Objekte erstellt,
     * welche in einer ArrayList<Kurs> gespeichert werden.
     *
     * @return ArrayList<Kurs> mit allen Kursen aus der Datenbank
     * @throws SQLException SQL Exception
     */
    public List<Kurs> getKursListe() throws SQLException {
        String query =
                "select * from trainee_verwaltung.kurs;";

        ArrayList<Kurs> kursListe = new ArrayList<>();

        Statement stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            String jahrgang = rs.getString("jahrgang");
            String raum = rs.getString("raum");
            kursListe.add(new Kurs(id, jahrgang, raum));
        }
        stmt.close();

        return kursListe;
    }

    /**
     * Holt sich aus der Datenbank die zur id zugehörigen Reihe, macht aus den Werten
     * ein Kurs-Objekt und gibt diese zurück.
     *
     * @param id Die ID des Kurses den man aus der DB lesen will
     * @return Das Kurs Objekt aus der DB
     * @throws SQLException SQL Exception
     */
    public Kurs getKurs(int id) throws SQLException{
        String query =
                "select * from trainee_verwaltung.kurs where id = " + id + ";";

        Kurs kurs = new Kurs();

        Statement stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            kurs.setId(rs.getInt("id"));
            kurs.setJahrgang(rs.getString("jahrgang"));
            kurs.setRaum(rs.getString("raum"));
        }
        stmt.close();

        return kurs;
    }

    /**
     * Dieser Methode wird ein veränderter Kurs übergeben. Anhand der id des jeweiligen Kurses
     * wird jener Kurs in der Datenbank nach dem übergebenen Kurs-Objekt verändert.
     *
     * @param kurs Der Kurs der aktualisiert werden soll in der DB
     * @throws SQLException SQL Exception
     */
    public void editKurs(Kurs kurs) throws SQLException {
        Statement stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet uprs = stmt.executeQuery("SELECT * FROM trainee_verwaltung.kurs" +
                " WHERE kURS.ID=" + kurs.getId() + ";");
        uprs.next();
        uprs.updateInt("id", kurs.getId());
        uprs.updateString("jahrgang", kurs.getJahrgang());
        uprs.updateString("raum", kurs.getRaum());
        uprs.updateRow();
        uprs.beforeFirst();
            stmt.close();

    }

    /**
     * Nach der id des übergebenen Kurses wird jener Kurs in der Datenbank gelöscht.
     * @param kurs Der Kurs der gelöscht werden soll
     * @throws SQLException SQL Exception
     */
    public void deleteKurs(Kurs kurs) throws SQLException {
        deleteAllTraineesFromKurs(kurs);
        Statement stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery(
                "delete FROM " + "trainee_verwaltung" +
                        ".Kurs where Kurs.id =" + kurs.getId());
        stmt.close();
    }

    /**
     * Sucht sich alle Trainees des angegebenen Kurses heraus und löscht all jene einzeln.
     * Anwendung beim Löschen von einem Kurs.
     * @param kurs Der Kurs von dem alle Trainees gelöscht werden soll
     * @throws SQLException SQL Exception
     */
    private void deleteAllTraineesFromKurs(Kurs kurs) throws SQLException {
        DbPerson dbPerson = new DbPerson();
        List<Person> personen = dbPerson.getListPersonen(kurs);
        for (Person person : personen) {
            dbPerson.deletePerson(person);
        }
    }

    /**
     * Es wird ein neuer Kurs erzeugt.
     *
     * @param kurs Der Kurs der erstellt werden soll
     * @return Die neu generierte ID von der Datenbank
     * @throws SQLException SQL Exception
     */
    public int createKurs(Kurs kurs) throws SQLException {
        int newKursId;

        String query = " insert into trainee_verwaltung.kurs (jahrgang, raum) values (?, ?)";
        PreparedStatement preparedStmt = Connector.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, kurs.getJahrgang());
        preparedStmt.setString(2, kurs.getRaum());

        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();
        resultSet.next();
        newKursId = resultSet.getInt(1);


        preparedStmt.close();
        return newKursId;
    }
}

