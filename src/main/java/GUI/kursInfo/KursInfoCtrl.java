package GUI.kursInfo;

import DatenbankMethoden.DbKurs;
import DatenbankMethoden.DbPerson;
import GUI.AlertUserEingabeUngueltig;
import GUI.ControllerManager;
import GUI.traineeListe.TraineeListeCtrl;
import GUI.UserWarnung;
import GUI.kursListe.KursListeCtrl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import logic.Kurs;

import java.sql.SQLException;

/** Controllerklasse von KursInfo*/
public class KursInfoCtrl {


    @FXML private TextField kursField;
    @FXML private TextField raumField;
    @FXML private TextField anzahlTN;

    @FXML private Button btnBearbeiten;
    @FXML private Button btnSpeichern;
    @FXML private Button btnLoeschen;
    @FXML private Button btnAbbrechen;

    private Kurs selectedKurs;
    private StringProperty anzahlTeilnehmer;
    private DbPerson dbPerson;
    private DbKurs dbKurs;


    /** Initialisierungsmethode */
    @FXML
    public void initialize() {
        selectedKurs = ControllerManager.getKursListeCtrl().getSelectedKurs();
        anzahlTeilnehmer = new SimpleStringProperty();
        dbKurs = new DbKurs();

        anzahlTN.textProperty().bind(setAnzahlTeilnehmer(selectedKurs));
        if (selectedKurs != null) {
            setKursInfos(selectedKurs);
        }
    }

    /**
     * Ermittelt die Anzahl der Personen in dem ausgewählten Kurs.
     * Wenn kein Kurs ausgewählt wir bzw. der Kurs nicht existiert wird "0" als Wert gesetzt
     *
     * @param selectedKurs
     * @return StringProperty
     */
    public StringProperty setAnzahlTeilnehmer(Kurs selectedKurs) {
        dbPerson = new DbPerson();
        try {
            anzahlTeilnehmer.setValue(Integer.toString(dbPerson.getListPersonen(selectedKurs).size()));
        } catch (SQLException e) {
            anzahlTeilnehmer.setValue("0");
        }
        return anzahlTeilnehmer;
    }

    /**
     * Informationen vom übergebenen Kurs in die Textfelder des GUIs laden
     *
     * @param kurs Kurs Objekt, das angezeigt werden soll
     * @throws SQLException SQL Exception
     */
    @FXML
    public void setKursInfos(Kurs kurs) {
        selectedKurs = kurs;
        kursField.setText(kurs.getJahrgang());
        raumField.setText(kurs.getRaum());
        setAnzahlTeilnehmer(selectedKurs);
    }

    /**
     * Ermöglicht es dem Benutzer einen neuen Kurs anzulegen
     */
    public void neuerKurs() {
        this.selectedKurs = new Kurs();
        setAnzahlTeilnehmer(selectedKurs);
        clearKursInfos();
        recoverGUIStateNewKurs();
    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls nach dem setzen eines neuen Kurses
     */
    private void recoverGUIStateNewKurs() {
        recoverGUIStateBearbeiten();
        ControllerManager.getTraineeListeCtrl().clearTable();
        btnLoeschen.setDisable(true);
    }

    /**
     * Löscht die Benutzereingaben
     */
    private void clearKursInfos() {
        kursField.clear();
        raumField.clear();
    }

    /**
     * Gibt die Bearbeitung eines Kurses frei
     */
    @FXML
    public void handleBearbeiten() {
        recoverGUIStateBearbeiten();
    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls nach dem bearbeiten eines Kurses
     */
    private void recoverGUIStateBearbeiten() {
        resetDisabledState(true);
        btnBearbeiten.setDisable(true);
    }

    /**
     * Prüft ob der User etwas eingegeben hat
     *
     * @return boolean
     */
    private boolean checkFelderNichtLeer() {
        String jahrgangName = kursField.getText();
        String raumName = raumField.getText();
        if (jahrgangName.length() == 0 || raumName.length() == 0) {
            AlertUserEingabeUngueltig alertUserEingabeUngueltig = new AlertUserEingabeUngueltig("" +
                    "Die Felder dürfen nicht leer sein.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Unterscheidet ob ein Kurs geupdated wird oder ein neuer Kurs erstellt wird.
     * Benutzerangaben werden überprüft
     *
     * @throws SQLException
     */
    @FXML
    public void handleSpeichern() throws SQLException {

        if (this.selectedKurs.getId() == 0) {
            if (checkFelderNichtLeer()) {
                createNewKurs();
                recoverGUIStateSpeichern();
            } else {
                neuerKurs();
            }
        } else {
            if (checkFelderNichtLeer()) {
                editExistingKurs();
                recoverGUIStateSpeichern();

            } else {
                handleBearbeiten();
            }
        }
    }

    /**
     * Ändert den ausgewählten Kurs
     * @throws SQLException
     */
    public void editExistingKurs() throws SQLException {
        this.selectedKurs.setJahrgang(kursField.getText());
        this.selectedKurs.setRaum(raumField.getText());
        dbKurs.editKurs(this.selectedKurs);
    }

    /**
     * Erstellt einen neuen Kurs
     * @throws SQLException
     */
    public void createNewKurs() throws SQLException {
        this.selectedKurs.setJahrgang(kursField.getText());
        this.selectedKurs.setRaum(raumField.getText());
        this.selectedKurs.setId(dbKurs.createKurs(this.selectedKurs));
        ControllerManager.getKursListeCtrl().setSelectedKurs();
    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls nach dem speichern eines Kurses
     */
    private void recoverGUIStateSpeichern() {
        ControllerManager.getKursListeCtrl().initialize();


        resetDisabledState(false);
    }

    /**
     * Löscht den Kurs
     *
     * @throws SQLException
     */
    @FXML
    public void handleLoeschen() throws SQLException {
        UserWarnung warnung = new UserWarnung("Willst du den kompletten Kurs und alle Teilnehmer wirklich löschen?");
        if (warnung.getResult() == ButtonType.YES) {

            dbKurs.deleteKurs(this.selectedKurs);
            recoverGUIStateLoeschen();
        }
    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls nach dem löschen eines Kurses
     */
    private void recoverGUIStateLoeschen() throws SQLException {
        KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
        kursListeCtrl.initialize();
        selectedKurs = kursListeCtrl.getSelectedKurs();
        setKursInfos(selectedKurs);
        ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);
        resetDisabledState(false);
    }


    /**
     * Bricht das Bearbeiten ab.
     * Unterscheidet ob ein Kurs existiert oder setzt den Kurs der noch ausgewählt ist.
     *
     * @throws SQLException
     */
    @FXML
    public void handleAbbrechen() throws SQLException {

        if (this.selectedKurs.getId() == 0) {
            selectedKurs = ControllerManager.getKursListeCtrl().getSelectedKurs();
            recoverGUIStateAbbrechen();

        } else {
            recoverGUIStateAbbrechen();
        }
    }

    /**
     * Setzt den gewünschten Zustand der GUI Controls nach dem Abbrechen
     */
    private void recoverGUIStateAbbrechen() throws SQLException {
        ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);
        setKursInfos(selectedKurs);
        resetDisabledState(false);
    }

    /**
     * Aktiviert/Deaktiviert alle Controls
     *
     * @param b
     */
    public void setKursInfoDisabled(boolean b) {
        kursField.setDisable(b);
        raumField.setDisable(b);
        btnAbbrechen.setDisable(b);
        btnBearbeiten.setDisable(b);
        btnSpeichern.setDisable(b);
        btnLoeschen.setDisable(b);
    }

    /**
     * Bearbeiten Button muss hier gesondert behandelt werden
     *
     * @param b
     */
    public void setDisableBtnBearbeiten(boolean b) {
        btnBearbeiten.setDisable(b);
    }

    public Kurs getSelectedKurs() {
        return this.selectedKurs;
    }

    /**
     * Reaktiviert andere Control(s) und deaktiviert die eigenen.
     *
     * @param bool Boolean auf was man die Disabled states setzten möchte
     */
    private void resetDisabledState(boolean bool) {
        TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        traineeListeCtrl.setTraineeListDisabled(bool);

        setKursInfoDisabled(!bool);
        btnBearbeiten.setDisable(false);

        KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
        kursListeCtrl.setKursListeDisabled(bool);
    }

}
