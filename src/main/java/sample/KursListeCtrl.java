package sample;

import DatenbankMethoden.DbKurs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import logic.Kurs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class KursListeCtrl {

    @FXML private TableView kursTabelle;



    @FXML public void initialize(){


//        FXMLLoader fxmlLoader = new FXMLLoader();
//        try {
//            Pane p = fxmlLoader.load(getClass().getResource("kursinfo.fxml").openStream());
//            kursInfoCtrl = fxmlLoader.getController();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        DbKurs dbKurs = new DbKurs();
        try {
            ArrayList<Kurs> kursListe = dbKurs.getKursListe();
            ObservableList<Kurs> obsList = FXCollections.observableArrayList(kursListe);
            kursTabelle.setItems(obsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMouseClick(Event e){
        System.out.println(e);
        Object selectedItem = kursTabelle.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof  Kurs){
            Kurs selectedKurs = (Kurs) selectedItem;
            KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
            kursInfoCtrl.setKursInfos(selectedKurs);
        }
    }

}
