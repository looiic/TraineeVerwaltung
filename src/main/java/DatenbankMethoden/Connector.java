package DatenbankMethoden;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public final class Connector {

    private static Connection conn;


    private Connector() {

    }

    /**
     * Gibt Connection zurueck
     *
     * @return
     * @throws Exception
     */
    public static Connection getConn() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainee_verwaltung",
                    "root", "root");
        }
        return conn;
    }

    public static void closeConn() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

}
