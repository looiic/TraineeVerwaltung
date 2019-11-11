package sample;

import DatenbankMethoden.DbKurs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button btnAbbrechen;

    private Kurs selectedKurs;

    @FXML
    public void initialize() {
        btnBearbeiten.setVisible(true);
        btnSpeichern.setVisible(false);
        btnAbbrechen.setVisible(false);
    }

    @FXML
    public void setKursInfos(Kurs kurs) {
        kursField.setText(kurs.getJahrgang());
        raumField.setText(kurs.getRaum());
        anzahlTN.setText("tbd");
        selectedKurs = kurs;

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
    public void handleSpeichern(){
        DbKurs dbKurs = new DbKurs();
        this.selectedKurs.setJahrgang(kursField.getText());
        this.selectedKurs.setRaum(raumField.getText());
        try {
            this.selectedKurs = dbKurs.createKurs(this.selectedKurs);
            System.out.println("neue ID: " + selectedKurs.getId());
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
}
