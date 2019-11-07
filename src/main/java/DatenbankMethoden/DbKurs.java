package DatenbankMethoden;

import java.sql.Connection;
import java.sql.*;

/**
Braucht Datenbanknamen und Connection von ausserhalb um zu funktionieren
*/
public class DbKurs {
    public static void viewTable(Connection con, String dbName)
            throws SQLException {
        Statement stmt = null;
        String query =
                "select id, jahrgang, raum, " +
                        "from " + dbName + ".Kurs";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String jahrgang = rs.getString("jahrgang");
                String raum = rs.getString("raum");
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


}

