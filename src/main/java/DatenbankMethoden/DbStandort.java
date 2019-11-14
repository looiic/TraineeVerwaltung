package DatenbankMethoden;


import DTO.Standort;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DbStandort verwaltet alle Datenbankabfragen zur Datenbanktabelle Standort.
 */
public class DbStandort {


    /**
     * Liefert alle Standorte aus der Datenbank zurück.
     *
     * @return standort
     * @throws SQLException SQL Exception
     */
    public List<Standort> getStandorte() throws SQLException {
        Statement stmt;
        String query = "select * from trainee_verwaltung.standort";

        ArrayList<Standort> standort = new ArrayList<>();
        stmt = Connector.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            standort.add(new Standort(rs.getString("standort")));
        }
        stmt.close();
        return standort;
    }

}
