package DatenbankMethoden;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Die Connectorklasse zur Verwaltung der Datenbankverbindung. Hierbei kann immer nur eine Verbindung hergestellt werden.
 */
public final class Connector {

    /** Verbindungspunkt */
    private static Connection conn;

    /** Default-Konstruktur */
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
