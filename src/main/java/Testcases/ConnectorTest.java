package Testcases;

import DatenbankMethoden.Connector;
import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectorTest {

    Connection connection;

    @Before
    public void before() throws SQLException {
        connection = Connector.getConn();
    }

    @After
    public void after() throws SQLException {
        Connector.closeConn();
    }
}