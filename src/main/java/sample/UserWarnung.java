package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UserWarnung {
    private Alert alert;

    public UserWarnung(String userwarnung){
        alert = new Alert(Alert.AlertType.CONFIRMATION, userwarnung , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
    };

    public ButtonType getResult() {
       return alert.getResult();
    }
}
