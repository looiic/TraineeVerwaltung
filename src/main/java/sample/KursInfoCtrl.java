package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.Kurs;

public class KursInfoCtrl {

    @FXML TextField kursField;
    @FXML TextField kursId;
    @FXML TextField raumField;
    @FXML TextField anzahlTN;

    @FXML
    public void setKursInfos(Kurs kurs) {
        kursField.setText(kurs.getJahrgang());
        kursId.setText(Integer.toString(kurs.getId()));
        raumField.setText(kurs.getRaum());
        anzahlTN.setText("tbd");
    }
}
