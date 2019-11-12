package DatenbankMethoden;


import logic.Kurs;
import logic.Person;
import java.sql.*;
import java.util.ArrayList;


public class DbKurs {


    /**
     * Holt sich aus der Datenbank trainee_verwaltung alle Elemente
     * aus der Tabelle kurs, aus diesen werden Kurs-Objekte erstellt,
     * welche in einer ArrayList<Kurs> gespeichert werden.
     *
     * @return ArrayList<Kurs> mit allen Kursen aus der Datenbank
     * @throws SQLException
     */
    public ArrayList<Kurs> getKursListe() throws SQLException {
        Statement stmt = null;
        String query =
                "select * from trainee_verwaltung.kurs;";

        ArrayList<Kurs> kursListe = new ArrayList<>();

        stmt = Connector.getConn().createStatement();
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

    public Kurs getKurs(int id) throws SQLException{
        Statement stmt = null;
        String query =
                "select * from trainee_verwaltung.kurs where id = " + id + ";";

        Kurs kurs = new Kurs();

        stmt = Connector.getConn().createStatement();
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
     * Übergebe dieser Methode einen veränderten Kurs. Anhand der id des jeweiligen Kurses
     * wird jener Kurs in der Datenbank nach dem übergebenen Objekt Kurs verändert.
     * <p>
     * PS: Habe nur Author hinzugefügt, damit ihr wisst zu wen ihr euch bei Fragen zu dieser
     * Methode wenden könnt ;)
     *
     * @param kurs
     * @throws SQLException
     * @Author Pradeep
     */
    public void editKurs(Kurs kurs) throws SQLException {
        Statement stmt = null;
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
            stmt.close();

    }


    public void deleteKurs(Kurs kurs) throws SQLException {
        deleteAllTraineesFromKurs(kurs);
        Statement stmt = null;
        stmt = Connector.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeQuery(
                "delete FROM " + "trainee_verwaltung" +
                        ".Kurs where Kurs.id =" + kurs.getId());

            stmt.close();

    }

    private void deleteAllTraineesFromKurs(Kurs kurs) throws SQLException {
        DbPerson dbPerson = new DbPerson();
        ArrayList<Person> personen = dbPerson.getListPersonen(kurs);
        for (Person person : personen) {
            dbPerson.deletePerson(person);
        }
    }


    public int createKurs(Kurs kurs) throws SQLException {
        int newKursId = 0;

        String query = " insert into trainee_verwaltung.kurs (jahrgang, raum) values (?, ?)";
        PreparedStatement preparedStmt = Connector.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, kurs.getJahrgang());
        preparedStmt.setString(2, kurs.getRaum());

        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();
        if (resultSet.next()) {
            newKursId = resultSet.getInt(1);
        }

        preparedStmt.close();
        return newKursId;
    }
}

