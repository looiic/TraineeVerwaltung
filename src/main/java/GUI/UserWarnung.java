package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** Klasse, wo ein Benutzer Warnungen erhalten kann*/
public class UserWarnung {

    /**Alarm */
    private Alert alert;

    /**Konstruktor */
    public UserWarnung(String userwarnung){
        alert = new Alert(Alert.AlertType.CONFIRMATION, userwarnung , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
    };

    /**Liefert den Alarm als Ergebnis zurück */
    public ButtonType getResult() {
       return alert.getResult();
    }
}
