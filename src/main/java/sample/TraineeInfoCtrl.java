package sample;

import DatenbankMethoden.DbPerson;
import DatenbankMethoden.DbStandort;
import javafx.event.Event;
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
    public void initialize() throws SQLException {
        traineeInfoControllers = new ArrayList<Control>() {
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
        System.out.println(vorkenntnisseMenu.pressedProperty().getValue());

        traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        kursListeCtrl = ControllerManager.getKursListeCtrl();
        dbPerson = new DbPerson();

        DbStandort dbStandort = new DbStandort();

        for (Standort standort : dbStandort.getStandorte()) {
            MenuItem menuItem = new MenuItem(standort.getStandort());
            menuItem.setOnAction(event -> {
                MenuItem source = (MenuItem) event.getSource();
                standortField.setText(source.getText());
            });
            standortField.getItems().add(menuItem);
        }
    }


    /**
     * Handler für Speichern Button gedrückt.
     * Unterscheidet ob ein neuer Trainee angelegt werden soll oder überschrieben wird.
     * Prüft auf vollständige Benutzereingaben. Speichert den (neuen) Trainee in der Datenbank. Lädt die TraineeListe neu und setzt den gewünschten GUI-Status.
     */
    @FXML
    public void saveEntry(Event e) throws SQLException {

            if (traineeListeCtrl.getAddTrainee().selectedProperty().getValue()) {
                if (checkFelderNichtLeer()) {
                    createNewTrainee();
                    recoverDiseredGUIState();
                }else{
                    traineeListeCtrl.addTrainee(e);
                }
            } else {
                if (checkFelderNichtLeer()) {
                    editExistingTrainee();
                    recoverDiseredGUIState();
                }
                else{
                    traineeListeCtrl.editTrainee(e);
                }
            }

        }

    /**
     * Setzt den gewünschten Zustand der GUI Controls. Läd TraineeListe neu.
     * @throws SQLException
     */
    private void recoverDiseredGUIState() throws SQLException {
        traineeListeCtrl.reloadTraineeListe(kursInfoCtrl.getSelectedKurs());
        traineeListeCtrl.getAddTrainee().setSelected(false);
        resetDisabledState(false);
    }


    /**
     * Änderungen für in Tabelle ausgewählte Person werden in Datenbank vorgenommen.
     *
     * @throws SQLException
     */
    private void editExistingTrainee() throws SQLException {
        selectedPerson = traineeListeCtrl.getSelectedPerson();
        setChanges(selectedPerson);
        dbPerson.editPerson(selectedPerson);
    }

    /**
     * eine neue Person wird erstellt, mit entsprechender Kurs-Id verknüpft und in die Datenbank geladen
     *
     * @throws SQLException
     */
    private void createNewTrainee() throws SQLException {
        selectedPerson = new Person();
        setChanges(selectedPerson);
        selectedPerson.setKursId(kursInfoCtrl.getSelectedKurs().getId());
        dbPerson.addNewPerson(selectedPerson);
    }

    /**
     * Überprüft ob der User die 4 Eingabefelder ausgefüllt bzw. selektiert hat. Bei Vorkenntnissen und Standort wird geprüft ob der default-Wert besteht.
     * @return boolean
     */
    private boolean checkFelderNichtLeer() {

        String nachname = nachnameField.getText();
        String vorname = vornameField.getText();
        String vorkenntnisse = vorkenntnisseMenu.getText();
        String standort = standortField.getText();

        if (nachname.length() == 0 || vorname.length() == 0 || vorkenntnisse == "Vorkenntnisse" || standort == "Standort") {
            AlertUserEingabeUngueltig alertUserEingabeUngueltig = new AlertUserEingabeUngueltig("" +
                    "Die Felder dürfen nicht leer sein.");
            return false;
        } else {
            return true;
        }
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
     *
     * @param e
     */
    @FXML
    public void selectVorkenntnisse(Event e) {
        MenuItem item = (MenuItem) e.getSource();
        vorkenntnisseMenu.setText(item.getText());
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
     *
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

    /**
     * Button wird via Boolean-Eingabe aktiviert resp. deaktiviert
     *
     * @param bool
     */
    public void handleDeleteTraineeButton(boolean bool) {
        deleteTrainee.setDisable(bool);
    }
}
