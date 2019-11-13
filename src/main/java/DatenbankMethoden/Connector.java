package DatenbankMethoden;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public final class Connector {

    private static Connection conn;


    private Connector() {

    }

    /**
     * Stellt eine Verbindung zu einer Datenbank her.
     *
     * @return connection
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainee_verwaltung",
                    "root", "root");
        }
        return conn;
    }

    /**
     * Schließt eine Verbindung zu Datenbank.
     *
     * @throws SQLException
     */
    public static void closeConn() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

}
