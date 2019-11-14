package GUI.traineeListe;

import DatenbankMethoden.DbKurs;
import DatenbankMethoden.DbPerson;
import GUI.ControllerManager;
import GUI.kursInfo.KursInfoCtrl;
import GUI.kursListe.KursListeCtrl;
import GUI.traineeInfo.TraineeInfoCtrl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import DTO.Kurs;
import DTO.Person;

import java.sql.SQLException;
import java.util.List;

/** Die Klasse beschreibt den Controller der TraineeListe */
public class TraineeListeCtrl {

    @FXML private TableView tableView;
    @FXML private Button editTrainee;
    @FXML private Label editTraineeInfo;
    @FXML private ToggleButton addTrainee;


    private DbPerson dbPerson;
    private Person selectedPerson;


    @FXML
    public void initialize() throws SQLException {
        editTrainee.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        dbPerson = new DbPerson();
        DbKurs dbKurs = new DbKurs();

        List<Person> personenListe = dbPerson.getListPersonen(dbKurs.getKursListe().get(0));
        ObservableList<Person> obsList = FXCollections.observableArrayList(personenListe);
        tableView.setItems(obsList);

    }

    /**
     * Lädt alle Personen des ausgewählten Kurses und fügt sie der Tabelle hinzu
     *
     * @param kurs Kurs Objekt für welche die Trainees neu geladen werden sollen
     * @throws SQLException SQL Exception
     */
    public void reloadTraineeListe(Kurs kurs) throws SQLException {
        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        List<Person> personenListe = dbPerson.getListPersonen(kurs);
        ObservableList<Person> obsList = FXCollections.observableArrayList(personenListe);
        tableView.setItems(obsList);
        kursInfoCtrl.setAnzahlTeilnehmer(kursInfoCtrl.getSelectedKurs());
    }

    /**
     * in Tabelle angeklickte Person wird ausgelesen. Und für Weiterverarbeitung in Panel traineeInfo weitergegeben.
     */
    @FXML
    public void chooseTrainee() {
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Person) {
            selectedPerson = (Person) selectedItem;
            TraineeInfoCtrl personInfoCtrl = ControllerManager.getTraineeInfoCtrl();
            personInfoCtrl.setTraineeInfos(selectedPerson);
        }
    }

    /**
     * Erstellt einen neues Trainee-Objekt. Deaktiviert den Löschen-Button. Alles weitere passiert später
     */
    @FXML
    public void addTrainee() {
        TraineeInfoCtrl personInfoCtrl = ControllerManager.getTraineeInfoCtrl();
        personInfoCtrl.clearTraineeInfos();
        tableView.getSelectionModel().clearSelection();
        setEnabledState();
        personInfoCtrl.handleDeleteTraineeButton(true);
    }

    /**
     * in Tabelle angeklickte Person wird ausgelesen.
     */
    @FXML
    public void editTrainee() {
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Person) {
            setTraineeListDisabled(true);
            tableView.getSelectionModel().clearSelection();

            setEnabledState();

        }
    }


    private void setEnabledState() {
        setTraineeListDisabled(true);

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
        tableView.getSelectionModel().clearSelection();
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
