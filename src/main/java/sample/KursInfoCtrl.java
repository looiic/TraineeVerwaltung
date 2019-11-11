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
        btnBearbeiten.setVisible(false);
        btnSpeichern.setVisible(false);
        btnLoeschen.setVisible(false);
        btnAbbrechen.setVisible(false);
    }

    @FXML
    public void setKursInfos(Kurs kurs) {
        selectedKurs = kurs;

        btnBearbeiten.setVisible(true);
        btnLoeschen.setVisible(true);
        kursField.setText(kurs.getJahrgang());
        raumField.setText(kurs.getRaum());
        anzahlTN.setText("");

    }

    public void neuerKurs() {
        this.selectedKurs = new Kurs();

        kursField.clear();
        raumField.clear();
        ControllerManager.getTraineeListeCtrl().clearTable();

        btnBearbeiten.setVisible(false);
        btnAbbrechen.setVisible(true);
        btnSpeichern.setVisible(true);

        kursField.setDisable(false);
        raumField.setDisable(false);
    }

    @FXML
    public void handleBearbeiten() {
        btnBearbeiten.setVisible(false);
        btnLoeschen.setVisible(false);
        btnAbbrechen.setVisible(true);
        btnSpeichern.setVisible(true);

        kursField.setDisable(false);
        raumField.setDisable(false);
    }

    @FXML
    public void handleSpeichern() {
        btnBearbeiten.setVisible(true);
        btnLoeschen.setVisible(true);
        btnAbbrechen.setVisible(false);
        btnSpeichern.setVisible(false);

        kursField.setDisable(true);
        raumField.setDisable(true);

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
    }

    @FXML
    public void handleAbbrechen() {
        btnBearbeiten.setVisible(true);
        btnLoeschen.setVisible(true);
        btnAbbrechen.setVisible(false);
        btnSpeichern.setVisible(false);

        kursField.setDisable(true);
        raumField.setDisable(true);
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
    }

    public void setKursInfoDisabled(boolean b) {
        kursField.setDisable(b);
        raumField.setDisable(b);
        btnAbbrechen.setDisable(b);
        btnBearbeiten.setDisable(b);
        btnSpeichern.setDisable(b);
    }

    public Kurs getSelectedKurs() {
        return this.selectedKurs;
    }
}
