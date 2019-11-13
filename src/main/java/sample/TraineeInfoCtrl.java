package sample;

import DatenbankMethoden.DbPerson;
import DatenbankMethoden.DbStandort;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.Person;
import logic.Standort;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraineeInfoCtrl {

    @FXML
    private TextField idField;
    @FXML
    private TextFieldLimited nachnameField;
    @FXML
    private TextFieldLimited vornameField;
    @FXML
    private MenuButton standortField;
    @FXML
    private MenuButton vorkenntnisseMenu;
    @FXML
    private TextField kursId;
    @FXML
    private Button cancelTrainee;
    @FXML
    private Button saveTrainee;
    @FXML
    private Button deleteTrainee;

    /**
     * Liste aller Controller-Nodes dieses Controllers
     */
    List<Control> traineeInfoControllers;
    private TraineeListeCtrl traineeListeCtrl;
    private KursInfoCtrl kursInfoCtrl;
    private KursListeCtrl kursListeCtrl;
    private Person selectedPerson;
    private DbPerson dbPerson;

    @FXML
    public void initialize() {
        traineeInfoControllers = new ArrayList<Control>(){
            {
                add(nachnameField);
                add(vornameField);
                add(standortField);
                add(vorkenntnisseMenu);
                add(cancelTrainee);
                add(saveTrainee);
                add(deleteTrainee);
            }
        };
        traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        kursListeCtrl = ControllerManager.getKursListeCtrl();
        dbPerson = new DbPerson();

        DbStandort dbStandort = new DbStandort();
        try {
            for(Standort standort : dbStandort.getStandorte()){
                MenuItem menuItem = new MenuItem(standort.getStandort());
                menuItem.setOnAction(event -> {
                    MenuItem source = (MenuItem) event.getSource();
                    standortField.setText(source.getText());
                });
                standortField.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Speichert den Trainee in der Datenbank. Lädt die TraineeListe neu und gibt andere Felder wieder frei.
     */
    @FXML
    public void saveEntry(Event e) throws SQLException {

        if (traineeListeCtrl.getAddTrainee().selectedProperty().getValue()) {
            createNewTrainee();
        }
        else
            {
                editExistingTrainee();
            }

        traineeListeCtrl.reloadTraineeListe(kursInfoCtrl.getSelectedKurs());
        traineeListeCtrl.getAddTrainee().setSelected(false);
        resetDisabledState(false);
    }

    private void editExistingTrainee() throws SQLException {
        selectedPerson = traineeListeCtrl.getSelectedPerson();
        setChanges(selectedPerson);
        dbPerson.editPerson(selectedPerson);
    }

    private void createNewTrainee() throws SQLException {
        selectedPerson = new Person();
        setChanges(selectedPerson);
        selectedPerson.setKursId(kursInfoCtrl.getSelectedKurs().getId());
        dbPerson.addNewPerson(selectedPerson);
    }


    /**
     * Löscht den Traineee in der Datenbank. Läd die TraineeListe neu und gibt andere Felder wieder frei.
     */
    @FXML
    public void deleteEntry(Event e) throws SQLException {
        String name = traineeListeCtrl.getSelectedPerson().getVorname() + " " + traineeListeCtrl.getSelectedPerson().getNachname();
        UserWarnung warnung = new UserWarnung("Willst du " + name + " wirklich löschen?");
        if (warnung.getResult() == ButtonType.YES) {
            dbPerson.deletePerson(traineeListeCtrl.getSelectedPerson());
            traineeListeCtrl.reloadTraineeListe(kursInfoCtrl.getSelectedKurs());
            resetDisabledState(false);
        }
    }

    /**
     * Bricht die Trainee Bearbeitung ab. Gibt andere Felder wieder frei.
     */
    @FXML
    public void cancelEntry(Event e) throws SQLException {
        resetDisabledState(false);
    }

    /**
     * Schreibt den Text des gewählten Items in den MenuButton
     * @param e
     */
    @FXML
    public void selectVorkenntnisse(Event e) {
        MenuItem item = (MenuItem) e.getSource();
        vorkenntnisseMenu.setText(item.getText());
    }

    @FXML
    public void selectStandort(Event e){
        System.out.println(e);
    }

    /**
     * Übernimmt den UserInput für die ausgewählte Person
     *
     * @param selectedPerson
     */
    private void setChanges(Person selectedPerson) {
        selectedPerson.setNachname(nachnameField.getText());
        selectedPerson.setVorname(vornameField.getText());
        selectedPerson.setStandort(standortField.getText());
        selectedPerson.setVorkenntnisse(vorkenntnisseMenu.getText());
    }

    /**
     * Reaktiviert andere Control(s) und deaktiviert die eigenen.
     * @param bool
     */
    private void resetDisabledState(boolean bool) {
        traineeListeCtrl.setTraineeListDisabled(bool);
        kursListeCtrl.setKursListeDisabled(bool);
        kursInfoCtrl.setDisableBtnBearbeiten(false);

        setTraineeInfoDisabled(!bool);
    }

    /**
     * Leert die Felder wo ein Trainee bearbeitet werden kann
     */
    public void clearTraineeInfos() {
        nachnameField.setText("");
        vornameField.setText("");
        vorkenntnisseMenu.setText("Vorkenntnisse");
        standortField.setText("");
    }

    /**
     * Überschreibt die Felder mit der selektierten Person
     *
     * @param selectedPerson
     */
    public void setTraineeInfos(Person selectedPerson) {
        nachnameField.setText(selectedPerson.getNachname());
        vornameField.setText(selectedPerson.getVorname());
        vorkenntnisseMenu.setText(selectedPerson.getVorkenntnisse());
        standortField.setText(selectedPerson.getStandort());
    }

    /**
     * Deaktiviert alle eigenen Control(s)
     *
     * @param bool
     */
    public void setTraineeInfoDisabled(boolean bool) {
        for (Control node : traineeInfoControllers) {
            node.setDisable(bool);
        }
    }

    public void handleDeleteTraineeButton(boolean bool) {
        deleteTrainee.setDisable(bool);
    }
}
