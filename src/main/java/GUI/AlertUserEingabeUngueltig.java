package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** Klasse welche einen Benutzer bei ungültiger Eingabe alarmiert. */
public class AlertUserEingabeUngueltig {

    /**Alarm */
    private Alert alert;

    /**Konstuktur */
    public AlertUserEingabeUngueltig(String userwarnung){
        alert = new Alert(Alert.AlertType.ERROR, userwarnung , ButtonType.OK);
        alert.showAndWait();
    };

}