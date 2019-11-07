package DatenbankMethoden;

import logic.Kurs;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
Braucht Datenbanknamen und Connection von ausserhalb um zu funktionieren
*/
public class DbKurs {

    public static List<Kurs> viewTable(Connection con, String dbName)
            throws SQLException {
        Statement stmt = null;
        String query =
                "select id, jahrgang, raum, " +
                        "from " + dbName + ".Kurs";

        List<Kurs> kursListe = new ArrayList<>();



        try {
            stmt = con.createStatement();
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


}

