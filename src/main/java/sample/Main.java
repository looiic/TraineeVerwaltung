package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Person;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader  loader = new FXMLLoader(getClass().getResource("main.fxml"));
        MainCtrl mainController = new MainCtrl();
        ControllerManager.setMainCtrl(mainController);
        loader.setController(mainController);
        Parent root = loader.load();

        //Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        //test commit Nadine
        //test commit oliver
        //test commit pradeep
    }

    public static void main(String[] args) {
        launch(args);
        new Person();
    }
}
