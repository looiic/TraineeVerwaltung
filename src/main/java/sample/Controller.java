package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    public Button buttonAddPerson;
    @FXML
    public Label testLabel;

    public Label getTestLabel() {
        return testLabel;
    }

    public Controller() {
    }

//    EventHandler<MouseEvent> eventMouseTouch = new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent e) {
//            String test = showTooltip(e);
//            //System.out.println(buttonAddPerson.getTooltip().getText());
//            testLabel.setText(test);
//            //System.out.println(test);
//        }
//    };
    @FXML
    public void initialize() {
        testLabel.setText("I am no ToolTip");
        buttonAddPerson.addEventHandler(MouseEvent.MOUSE_ENTERED,new ShowToolTipHandler(testLabel));
    }

    public void addPerson(ActionEvent actionEvent) {
        //to be defined...
    }

    public void addKurs(ActionEvent actionEvent) {
        //to be defined...
    }

}
