package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUserEingabeUngueltig {

    public AlertUserEingabeUngueltig(String userwarnung){
        Alert alert = new Alert(Alert.AlertType.ERROR, userwarnung, ButtonType.OK);
        alert.showAndWait();
    };

}