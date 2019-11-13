package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUserEingabeUngueltig {

    private Alert alert;

    public AlertUserEingabeUngueltig(String userwarnung){
        alert = new Alert(Alert.AlertType.ERROR, userwarnung , ButtonType.OK);
        alert.showAndWait();
    };

}