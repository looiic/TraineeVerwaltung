package sample;

import DatenbankMethoden.DbKurs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import logic.Kurs;

import java.sql.SQLException;

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

    @FXML
    public void initialize() {
        Kurs selectedKurs = ControllerManager.getKursListeCtrl().getSelectedKurs();
        if(selectedKurs != null){
            setKursInfos(selectedKurs);
        }
    }

    @FXML
    public void setKursInfos(Kurs kurs) {
        selectedKurs = kurs;

        kursField.setText(kurs.getJahrgang());
        raumField.setText(kurs.getRaum());
        anzahlTN.setText("");

    }

    public void neuerKurs() {
        this.selectedKurs = new Kurs();

        kursField.clear();
        raumField.clear();
        ControllerManager.getTraineeListeCtrl().clearTable();
        resetDisabledState(true);
        btnBearbeiten.setDisable(true);
    }

    @FXML
    public void handleBearbeiten() {
        resetDisabledState(true);
    }

    @FXML
    public void handleSpeichern() {

        DbKurs dbKurs = new DbKurs();
        this.selectedKurs.setJahrgang(kursField.getText());
        this.selectedKurs.setRaum(raumField.getText());
        try {
            if (this.selectedKurs.getId() == 0) {
                this.selectedKurs.setId(dbKurs.createKurs(this.selectedKurs));
            } else {
                dbKurs.editKurs(this.selectedKurs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControllerManager.getKursListeCtrl().initialize();

        resetDisabledState(false);

    }

    @FXML
    public void handleLoeschen() {
        if (this.selectedKurs.getId() != 0) {
            DbKurs dbKurs = new DbKurs();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Möchten Sie diesen Kurs wirklich löschen?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    dbKurs.deleteKurs(this.selectedKurs);
                    ControllerManager.getKursListeCtrl().initialize();
                    this.setKursInfos(new Kurs());
                    ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        resetDisabledState(false);

    }

    @FXML
    public void handleAbbrechen() {


        DbKurs dbKurs = new DbKurs();
        try {
            if (this.selectedKurs.getId() == 0) {
                kursField.clear();
                raumField.clear();
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
