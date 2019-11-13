package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainCtrl {

    @FXML private HBox hbox;

    /**
     * Für die 4 Nodes 'kursliste', 'kursinfo', 'traineeliste' und 'traineeinfo' werden die FXML-Files geladen,
     * plus zusätzlich wird der jeweilige Controller für das FXML-File gesetzt.
     */
    @FXML
    public void initialize() {
        try {
            FXMLLoader kursListeLoader = new FXMLLoader(getClass().getResource("kursliste.fxml"));
            KursListeCtrl kursListeController = new KursListeCtrl();
            kursListeLoader.setController(kursListeController);
            Parent kursListe = kursListeLoader.load();
            hbox.getChildren().add(kursListe);

            ControllerManager.setKursListeCtrl(kursListeController);

            VBox vBox = new VBox();

            FXMLLoader kursInfoLoader = new FXMLLoader(getClass().getResource("kursinfo.fxml"));
            KursInfoCtrl kursInfoController = new KursInfoCtrl();
            kursInfoLoader.setController(kursInfoController);
            Parent kursInfo = kursInfoLoader.load();
            vBox.getChildren().add(kursInfo);

            ControllerManager.setKursInfoCtrl(kursInfoController);

            FXMLLoader traineeListeLoader = new FXMLLoader(getClass().getResource("traineeliste.fxml"));
            TraineeListeCtrl traineeListeController = new TraineeListeCtrl();
            traineeListeLoader.setController(traineeListeController);
            Parent traineeListe = traineeListeLoader.load();
            vBox.getChildren().add(traineeListe);

            ControllerManager.setTraineeListeCtrl(traineeListeController);

            FXMLLoader traineeInfoLoader = new FXMLLoader(getClass().getResource("traineeinfo.fxml"));
            TraineeInfoCtrl traineeInfoController = new TraineeInfoCtrl();
            traineeInfoLoader.setController(traineeInfoController);
            Parent traineeInfo = traineeInfoLoader.load();
            vBox.getChildren().add(traineeInfo);

            ControllerManager.setTraineeInfoCtrl(traineeInfoController);

            hbox.getChildren().add(vBox);

        } catch (IOException e) {
            System.err.println("Folgender Fehler ist aufgetreten: "+e);
            e.printStackTrace();
        }
    }
}
