package DatenbankMethoden;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Die Connectorklasse zur Verwaltung der Datenbankverbindung. Hierbei kann immer nur eine Verbindung hergestellt werden.
 */
public final class Connector {

    private static String url = "jdbc:mysql://localhost:3306/trainee_verwaltung";
    private static String user = "root";
    private static String password = "root";

    /** Verbindungspunkt */
    private static Connection conn;

    /** Default-Konstruktur */
    private Connector() {

    }

    /**
     * Stellt eine Verbindung zu einer Datenbank her.
     *
     * @return connection
     * @throws SQLException SQL Exception
     */
    public static Connection getConn() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    /**
     * Schließt eine Verbindung zu Datenbank.
     *
     * @throws SQLException SQL Exception
     */
    public static void closeConn() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

}
