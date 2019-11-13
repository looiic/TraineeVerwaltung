package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DatenbankMethoden.Connector;

import java.sql.SQLException;

/**Die Mainklasse der Anwendung. */
public class Main extends Application {

    /** Starten der grafischen Benutzeroberfläche */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader  loader = new FXMLLoader(getClass().getResource("main.fxml"));
        MainCtrl mainController = new MainCtrl();
        ControllerManager.setMainCtrl(mainController);
        loader.setController(mainController);
        Parent root = loader.load();

        primaryStage.setTitle("Trainee Verwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /** Schließen der Verbindung zur Datenbank */
    @Override
    public void stop() throws SQLException {
        Connector.closeConn();
    }

    /**Main-Funktion zum Starten der Anwendung. */
    public static void main(String[] args) {
        launch(args);
    }
}
