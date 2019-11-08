package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.Kurs;

public class KursInfoCtrl {

    @FXML TextField kursField;
    @FXML TextField kursId;
    @FXML TextField raumField;
    @FXML TextField anzahlTN;

    private Kurs selectedKurs;

    @FXML
    public void initialize(){
    }


    public void setKursInfos(Kurs kurs) {
        System.out.println(kursField);
        kursField.setText(kurs.getJahrgang());
        kursId.setText(Integer.toString(kurs.getId()));
        raumField.setText(kurs.getRaum());
        anzahlTN.setText("tbd");
        selectedKurs = kurs;
    }

}
