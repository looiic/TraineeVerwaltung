package sample;

import DatenbankMethoden.DbKurs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import logic.Kurs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KursListeCtrl {

    @FXML private TableView kursTabelle;
    @FXML private Button addKurs;


    @FXML
    public void initialize() {
        DbKurs dbKurs = new DbKurs();
        try {
            List<Kurs> kursListe = dbKurs.getKursListe();
            ObservableList<Kurs> obsList = FXCollections.observableArrayList(kursListe);
            kursTabelle.setItems(obsList);
            kursTabelle.getSelectionModel().select(0);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void eventNeuerKurs(){
        ControllerManager.getKursInfoCtrl().neuerKurs();
    }

    /**
     * Handler setzt die ausgewählten Kursinfos und läd die Teilnehmerliste neu
     * @param e
     * @throws SQLException
     */
    @FXML
    public void handleMouseClick(Event e) throws SQLException {
        Object selectedItem = kursTabelle.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof  Kurs){
            Kurs selectedKurs = (Kurs) selectedItem;
            KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
            kursInfoCtrl.setKursInfos(selectedKurs);

            ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);


        }
    }

    public void setKursListeDisabled(boolean b) {
        kursTabelle.setDisable(b);
        addKurs.setDisable(b);
    }

    public Kurs getSelectedKurs(){
        Object selectedItem = kursTabelle.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof Kurs){
            return (Kurs) selectedItem;
        }else{
            return null;
        }
    }

}
