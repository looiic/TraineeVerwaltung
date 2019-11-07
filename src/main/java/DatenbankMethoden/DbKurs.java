//package DatenbankMethoden;
//
//import java.sql.Connection;
//import java.sql.*;
//
///**
//Braucht Datenbanknamen und Connection von ausserhalb um zu funktionieren
//*/
//public class DbKurs {
//    public static logic.Person viewTable(Connection con, String dbName)
//            throws SQLException {
//
////        Statement stmt = null;
////        String query =
////                "select id, vorname, nachname, " +
////                        "standort, vorkenntnisse, kurs_id " +
////                        "from " + dbName + ".Person";
////
////        try {
////            stmt = con.createStatement();
////            ResultSet rs = stmt.executeQuery(query);
////            while (rs.next()) {
////                int id = rs.getInt("id");
////                String vorname = rs.getString("vorname");
////                String nachname = rs.getString("nachname");
////                String standort = rs.getString("standort");
////                int vorkenntnisse = rs.getInt("vorkenntnisse");
////                int kurs_id = rs.getInt("kurs_id");
////                //System.out.println(coffeeName + "\t" + supplierID +
////                        "\t" + price + "\t" + sales +
////                        "\t" + total);
////            }
////        } catch (SQLException e ) {
////            JDBCTutorialUtilities.printSQLException(e);
////        } finally {
////            if (stmt != null) { stmt.close(); }
////        }
//    }
//}
//
