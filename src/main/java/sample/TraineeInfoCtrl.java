package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import logic.Person;

public class TraineeInfoCtrl {

    @FXML private TextField idField;
    @FXML private TextField nachnameField;
    @FXML private TextField vornameField;
    @FXML private TextField standortField;
    @FXML private MenuButton vorkenntnisseMenu;
    @FXML private TextField kursId;
    @FXML private Button cancelTrainee;
    @FXML private Button saveTrainee;
    @FXML private Button deleteTrainee;


    @FXML
    public void cancelEntry(ActionEvent actionEvent) {
        idField.setText(null);
        idField.setDisable(true);
        nachnameField.setText(null);
        nachnameField.setDisable(true);
        vornameField.setText(null);
        vornameField.setDisable(true);
        standortField.setText(null);
        standortField.setDisable(true);
        vorkenntnisseMenu.setText("Vorkenntnisse");
        vorkenntnisseMenu.setDisable(true);
        kursId.setText(null);
        kursId.setDisable(true);
        cancelTrainee.setDisable(true);
        saveTrainee.setDisable(true);
        deleteTrainee.setDisable(true);
    }

    /**
     * Überschreibt die Felder mit der selektierten Person
     * @param selectedPerson
     */
    public void setTraineeInfos(Person selectedPerson) {
        nachnameField.setText(selectedPerson.getNachname());
        vornameField.setText(selectedPerson.getVorname());
        Integer vorkenntnisse = (Integer) selectedPerson.getVorkenntnisse(); //correct later
        vorkenntnisseMenu.setText(vorkenntnisse.toString());
        standortField.setText(selectedPerson.getStandort());

    }
}
