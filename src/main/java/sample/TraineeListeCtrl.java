package sample;

import DatenbankMethoden.DbKurs;
import DatenbankMethoden.DbPerson;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import logic.Kurs;
import logic.Person;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class TraineeListeCtrl {

    @FXML
    private TableView tableView;
    @FXML
    private Button editTrainee;
    @FXML
    private Label editTraineeInfo;
    @FXML
    private ToggleButton addTrainee;


    DbPerson dbPerson;


    private Person selectedPerson;


    @FXML
    public void initialize() {
        editTrainee.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        //tableView.getSelectionModel().selectedIndexProperty().getValue().bind();
        dbPerson = new DbPerson();
        DbKurs dbKurs = new DbKurs();

        try {
            ArrayList<Person> personenListe = dbPerson.getListPersonen(dbKurs.getKursListe().get(0));
            ObservableList<Person> obsList = FXCollections.observableArrayList(personenListe);
            tableView.setItems(obsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Läd alle Personen des ausgewählten Kurses und fügt sie der Tabelle hinzu
     *
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
    public void chooseTrainee(Event e) {
        System.out.println(e);
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Person) {
            selectedPerson = (Person) selectedItem;
            TraineeInfoCtrl personInfoCtrl = ControllerManager.getTraineeInfoCtrl();
            personInfoCtrl.setTraineeInfos(selectedPerson);
        }
    }

    /**
     * Erstellt einen neues Trainee-Objekt. Alles weitere passiert später
     */
    @FXML
    public void addTrainee(Event e) throws SQLException {
        setEnabledState();
    }


    @FXML
    public void editTrainee(Event e) {
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Person) {
            setTraineeListDisabled(true);
            tableView.getSelectionModel().clearSelection();

            setEnabledState();

        }
    }

    private void setEnabledState() {
        TraineeInfoCtrl personInfoCtrl = ControllerManager.getTraineeInfoCtrl();
        personInfoCtrl.setTraineeInfoDisabled(false);

        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        kursInfoCtrl.setKursInfoDisabled(true);

        KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
        kursListeCtrl.setKursListeDisabled(true);
    }

    public void setTraineeListDisabled(boolean bool){
        tableView.setDisable(bool);
        addTrainee.setDisable(bool);
        //editTrainee.setDisable(bool);
    }

    public void clearTable() {
        tableView.getItems().clear();
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public ToggleButton getAddTrainee() {
        return addTrainee;
    }
}
