package GUI.traineeInfo;

import DatenbankMethoden.DbPerson;
import DatenbankMethoden.DbStandort;
import GUI.*;
import GUI.kursInfo.KursInfoCtrl;
import GUI.kursListe.KursListeCtrl;
import GUI.traineeListe.TraineeListeCtrl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.Person;
import logic.Standort;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Controllerklasse von TraineeInfo */
public class TraineeInfoCtrl {

    @FXML private TextField idField;
    @FXML private TextFieldLimited nachnameField;
    @FXML private TextFieldLimited vornameField;
    @FXML private MenuButton standortField;
    @FXML private MenuButton vorkenntnisseMenu;
    @FXML private TextField kursId;
    @FXML private Button cancelTrainee;
    @FXML private Button saveTrainee;
    @FXML private Button deleteTrainee;

    /**
     * Liste aller Controller-Nodes dieses Controllers
     */
    private List<Control> traineeInfoControllers;
    private TraineeListeCtrl traineeListeCtrl;
    private KursInfoCtrl kursInfoCtrl;
    private KursListeCtrl kursListeCtrl;
    private Person selectedPerson;
    private DbPerson dbPerson;

    private String defaultValueVorkenntnisseField = "Vorkenntnisse";
    private String defaultValueStandortField = "Standort";

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
        traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        kursListeCtrl = ControllerManager.getKursListeCtrl();
        dbPerson = new DbPerson();

        DbStandort dbStandort = new DbStandort();

        initializeStandort(dbStandort);
    }

    /**
     * Initialisiert den Standort
     * @param dbStandort
     * @throws SQLException
     */
    private void initializeStandort(DbStandort dbStandort) throws SQLException {
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
    public void saveEntry() throws SQLException {

        if (traineeListeCtrl.getAddTrainee().selectedProperty().getValue()) {
            if (checkFelderNichtLeer()) {
                createNewTrainee();
                recoverDiseredGUIState();
            } else {
                traineeListeCtrl.addTrainee();
            }
        } else {
            if (checkFelderNichtLeer()) {
                editExistingTrainee();
                recoverDiseredGUIState();
            } else {
                traineeListeCtrl.editTrainee();
            }
        }

    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls. Läd TraineeListe neu.
     *
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
     * @throws SQLException SQL Exception
     */
    private void editExistingTrainee() throws SQLException {
        selectedPerson = traineeListeCtrl.getSelectedPerson();
        setChanges(selectedPerson);
        dbPerson.editPerson(selectedPerson);
    }

    /**
     * eine neue Person wird erstellt, mit entsprechender Kurs-Id verknüpft und in die Datenbank geladen
     *
     * @throws SQLException SQL Exception
     */
    private void createNewTrainee() throws SQLException {
        selectedPerson = new Person();
        setChanges(selectedPerson);
        selectedPerson.setKursId(kursInfoCtrl.getSelectedKurs().getId());
        selectedPerson.setId(dbPerson.addNewPerson(selectedPerson));
    }

    /**
     * Überprüft ob der User die 4 Eingabefelder ausgefüllt bzw. selektiert hat. Bei Vorkenntnissen und Standort wird geprüft ob der default-Wert besteht.
     *
     * @return boolean
     */
    private boolean checkFelderNichtLeer() {

        String nachname = nachnameField.getText();
        String vorname = vornameField.getText();
        String vorkenntnisse = vorkenntnisseMenu.getText();
        String standort = standortField.getText();

        if (nachname.length() == 0 || vorname.length() == 0 || vorkenntnisse.equals(defaultValueVorkenntnisseField) || standort.equals(defaultValueStandortField)) {
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
    public void deleteEntry() throws SQLException {
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
    public void cancelEntry() {
        resetDisabledState(false);
    }

    /**
     * Schreibt den Text des gewählten Items in den MenuButton
     *
     * @param e Event, der behandelt werden soll
     */
    @FXML
    public void selectVorkenntnisse(Event e) {
        MenuItem item = (MenuItem) e.getSource();
        vorkenntnisseMenu.setText(item.getText());
    }

    /**
     * Übernimmt den UserInput für die ausgewählte Person
     *
     * @param selectedPerson Person Objekt für wechles man die UserInputs setzten will
     */
    private void setChanges(Person selectedPerson) {
        selectedPerson.setNachname(nachnameField.getText());
        selectedPerson.setVorname(vornameField.getText());
        selectedPerson.setStandort(standortField.getText());
        selectedPerson.setVorkenntnisse(vorkenntnisseMenu.getText());
    }


    //TODO: Refactoring

    /**
     * Reaktiviert andere Control(s) und deaktiviert die eigenen.
     *
     * @param bool Boolean auf was man die Disabled states setzten möchte
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
        vorkenntnisseMenu.setText(defaultValueVorkenntnisseField);
        standortField.setText(defaultValueStandortField);
    }

    /**
     * Überschreibt die Felder mit der selektierten Person
     *
     * @param selectedPerson Person Objekt welches angezeigt werden soll
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
     * @param bool Boolean ob es disabled werden soll
     */
    public void setTraineeInfoDisabled(boolean bool) {
        for (Control node : traineeInfoControllers) {
            node.setDisable(bool);
        }
    }

    /**
     * Button wird via Boolean-Eingabe aktiviert resp. deaktiviert
     *
     * @param bool Boolean ob es disabled werden soll
     */
    public void handleDeleteTraineeButton(boolean bool) {
        deleteTrainee.setDisable(bool);
    }
}
