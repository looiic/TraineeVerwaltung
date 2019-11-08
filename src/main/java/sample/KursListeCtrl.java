package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class KursListeCtrl {

    @FXML private ListView kursList;

    @FXML
    public void initialize(){

        ObservableList<String> obsList = FXCollections.observableArrayList (
                "Test1", "Test2", "Test3");
        kursList.setItems(obsList);

    }

}
