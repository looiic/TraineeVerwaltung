package sample;

import DatenbankMethoden.DbKurs;
import DatenbankMethoden.DbPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import logic.Kurs;
import logic.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public class TraineeListeCtrl {

    @FXML private TableView tableView;

    DbPerson dbPerson;


    @FXML public void initialize(){




        dbPerson = new DbPerson();
        try {
            ArrayList<Person> personenListe = dbPerson.getListPersonen();
            ObservableList<Person> obsList = FXCollections.observableArrayList(personenListe);
            tableView.setItems(obsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Läd alle Personen des ausgewählten Kurses und fügt sie der Tabelle hinzu
     * @param kurs
     * @throws SQLException
     */
    public void reloadTraineeListe(Kurs kurs) throws SQLException {
        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        ArrayList<Person> personenListe = dbPerson.getListPersonen(kurs);
        ObservableList<Person> obsList = FXCollections.observableArrayList(personenListe);
        tableView.setItems(obsList);
    }



    @FXML
    public void handleMouseClick(Event e){
        System.out.println(e);
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof  Person){
            Person selectedPerson = (Person) selectedItem;
            TraineeInfoCtrl personInfoCtrl = ControllerManager.getTraineeInfoCtrl();
            personInfoCtrl.setTraineeInfos(selectedPerson);
        }
    }

    public void clearTable() {
        tableView.getItems().clear();
    }
}
