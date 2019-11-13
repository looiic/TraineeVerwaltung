package sample;

import DatenbankMethoden.DbKurs;
import DatenbankMethoden.DbPerson;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import logic.Kurs;

import java.sql.SQLException;
import java.util.ArrayList;

public class KursInfoCtrl {


    @FXML
    private TextField kursField;
    @FXML
    private TextField raumField;
    @FXML
    private TextField anzahlTN;

    @FXML
    private Button btnBearbeiten;
    @FXML
    private Button btnSpeichern;
    @FXML
    private Button btnLoeschen;
    @FXML
    private Button btnAbbrechen;

    private Kurs selectedKurs;
    private StringProperty anzahlTeilnehmer = new SimpleStringProperty();

    public KursInfoCtrl() {
    }

    @FXML
    public void initialize() throws SQLException {
        Kurs selectedKurs = ControllerManager.getKursListeCtrl().getSelectedKurs();

        anzahlTN.textProperty().bind(setAnzahlTeilnehmer(selectedKurs));
        if(selectedKurs != null){
            setKursInfos(selectedKurs);
        }
    }

    public StringProperty setAnzahlTeilnehmer(Kurs selectedKurs) throws SQLException {
        DbPerson dbPerson = new DbPerson();
        try {
            int temp = dbPerson.getListPersonen(selectedKurs).size();
            anzahlTeilnehmer.setValue(Integer.toString(temp));

        }catch (SQLException e) {
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
    public void setKursInfos(Kurs kurs) throws SQLException {
        selectedKurs = kurs;

        kursField.setText(kurs.getJahrgang());
        raumField.setText(kurs.getRaum());
        setAnzahlTeilnehmer(selectedKurs);

    }

    public void neuerKurs() throws SQLException {
        this.selectedKurs = new Kurs();
        setAnzahlTeilnehmer(selectedKurs);
        kursField.clear();
        raumField.clear();
        ControllerManager.getTraineeListeCtrl().clearTable();
        resetDisabledState(true);
        btnBearbeiten.setDisable(true);
        btnLoeschen.setDisable(true);
    }

    @FXML
    public void handleBearbeiten() {
        resetDisabledState(true);
        btnBearbeiten.setDisable(true);
    }

    private boolean checkFelderNichtLeer(String jahrgangName, String raumName){


        if (jahrgangName.length() == 0 || raumName.length() == 0) {
            AlertUserEingabeUngueltig alertUserEingabeUngueltig = new AlertUserEingabeUngueltig("" +
                    "Die Felder dürfen nicht leer sein.");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void handleSpeichern() throws SQLException {

        DbKurs dbKurs = new DbKurs();

        String jahrgangName = kursField.getText();
        String raumName = raumField.getText();

        if (checkFelderNichtLeer(jahrgangName,raumName)){
            this.selectedKurs.setJahrgang(jahrgangName);
            this.selectedKurs.setRaum(raumName);
            if (this.selectedKurs.getId() == 0) {
                this.selectedKurs.setId(dbKurs.createKurs(this.selectedKurs));
            } else {
                dbKurs.editKurs(this.selectedKurs);
            }
            ControllerManager.getKursListeCtrl().initialize();
            ControllerManager.getKursListeCtrl().setSelectedKurs();

                resetDisabledState(false);
        } else {
            neuerKurs();
        }
    }

    @FXML
    public void handleLoeschen() throws SQLException {
        if (this.selectedKurs.getId() != 0) {
            DbKurs dbKurs = new DbKurs();
            UserWarnung warnung = new UserWarnung("Willst du den kompletten Kurs und alle Teilnehmer wirklich löschen?");
            if (warnung.getResult() == ButtonType.YES) {

                dbKurs.deleteKurs(this.selectedKurs);
                KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
                kursListeCtrl.initialize();
                selectedKurs = kursListeCtrl.getSelectedKurs();
                setKursInfos(selectedKurs);
                ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);
            }
        }
        resetDisabledState(false);
    }

    @FXML
    public void handleAbbrechen(){

        DbKurs dbKurs = new DbKurs();
        try {
            if (this.selectedKurs.getId() == 0) {
                KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
                TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();

                selectedKurs = kursListeCtrl.getSelectedKurs();
                traineeListeCtrl.reloadTraineeListe(selectedKurs);
                setKursInfos(selectedKurs);

            } else {
                this.selectedKurs = dbKurs.getKurs(this.selectedKurs.getId());
                kursField.setText(selectedKurs.getJahrgang());
                raumField.setText(selectedKurs.getRaum());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resetDisabledState(false);
    }

    public void setKursInfoDisabled(boolean b) {
        kursField.setDisable(b);
        raumField.setDisable(b);
        btnAbbrechen.setDisable(b);
        btnBearbeiten.setDisable(b);
        btnSpeichern.setDisable(b);
        btnLoeschen.setDisable(b);
    }

    public void setDisableBtnBearbeiten(boolean b) {
        btnBearbeiten.setDisable(b);

    }

    public Kurs getSelectedKurs() {
        return this.selectedKurs;
    }

    private void resetDisabledState(boolean bool) {
        TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        traineeListeCtrl.setTraineeListDisabled(bool);

        setKursInfoDisabled(!bool);
        btnBearbeiten.setDisable(false);

        KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
        kursListeCtrl.setKursListeDisabled(bool);
    }

}
