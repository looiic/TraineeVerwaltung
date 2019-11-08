package sample;

import DatenbankMethoden.DbKurs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import logic.Kurs;

import java.sql.SQLException;
import java.util.ArrayList;

public class KursListeCtrl {

    @FXML private TableView kursTabelle;

    @FXML
    public void initialize(){

        DbKurs dbKurs = new DbKurs();
        try {
            ArrayList<Kurs> kursListe = dbKurs.getKursListe();
            ObservableList<Kurs> obsList = FXCollections.observableArrayList(kursListe);
            kursTabelle.setItems(obsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
