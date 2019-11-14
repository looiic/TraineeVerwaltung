package Testcases;

import DatenbankMethoden.Connector;
import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;

/**Testklasse zum Testen der Datenbankverbindung */
public class ConnectorTest {

    Connection connection;

    /**Methode zuerst eine Datenbankverbinding */
    @Before
    public void before() throws SQLException {
        connection = Connector.getConn();
    }

    /** Methode testet darauf das Schließen der Datenbankverbindung */
    @After
    public void after() throws SQLException {
        Connector.closeConn();
    }
}