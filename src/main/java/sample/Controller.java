package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    public Button addTrainee;
    @FXML
    public Label testLabel;
    @FXML private ListView kursList;

    public Label getTestLabel() {
        return testLabel;
    }

    public Controller() {
    }

    @FXML
    public void initialize() {
        ObservableList<String> obsList = FXCollections.observableArrayList (
                "Test1", "Test2", "Test3");
        kursList.setItems(obsList);
        testLabel.setText("I am no ToolTip");
        addTrainee.addEventHandler(MouseEvent.MOUSE_ENTERED,new ShowToolTipHandler(testLabel));
    }

    public void addPerson(ActionEvent actionEvent) {
        //to be defined...
    }

    public void addKurs(ActionEvent actionEvent) {
        //to be defined...
    }

}
