package sample;

import DatenbankMethoden.DbPerson;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import logic.Person;

import java.sql.SQLException;

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
    public void initialize() {
    }


    /**
     * Speichert den Traineee in der Datenbank. Läd die TraineeListe neu und gibt andere Felder wieder frei.
     */
    @FXML
    public void saveEntry(Event e) throws SQLException {

        //Unterscheidung wenn Trainee neu hinzugefügt wird

        TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        Person selectedPerson = traineeListeCtrl.getSelectedPerson();

        DbPerson dbPerson = new DbPerson();

        if (traineeListeCtrl.getAddTrainee().selectedProperty().getValue()) {
            System.out.println("new person will be generated");
                selectedPerson = new Person();
                setChanges(selectedPerson);
                selectedPerson.setKursId(kursInfoCtrl.getSelectedKurs().getId());
            dbPerson.addNewPerson(selectedPerson);
                traineeListeCtrl.getAddTrainee().setSelected(false);
       }
            else{

            setChanges(selectedPerson);
            dbPerson.editPerson(selectedPerson);
            }


        traineeListeCtrl.reloadTraineeListe(kursInfoCtrl.getSelectedKurs());
        resetDisabledState(false);
    }



    /**
     * Löscht den Traineee in der Datenbank. Läd die TraineeListe neu und gibt andere Felder wieder frei.
     */
    @FXML
    public void deleteEntry(Event e) throws SQLException {

        TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        DbPerson dbPerson = new DbPerson();
        dbPerson.deletePerson(traineeListeCtrl.getSelectedPerson());

        traineeListeCtrl.reloadTraineeListe(kursInfoCtrl.getSelectedKurs());
        resetDisabledState(false);

    }

    /**
     * Bricht die Trainee Bearbeitung ab. Alle Felder außer In TraineeInfo werden wieder freigegeben. Eigene Felder werden wieder disabled.
     */
    @FXML
    public void cancelEntry(Event e) throws SQLException {
        resetDisabledState(false);

    }

    @FXML
    public void selectVorkenntnisse(Event e) {
        MenuItem item = (MenuItem)e.getSource();
        vorkenntnisseMenu.setText(item.getText());
    }


    /**
     * Übernimmt den UserInput für die ausgewählte Person
     *
     * @param selectedPerson
     */
    private void setChanges(Person selectedPerson) {
        selectedPerson.setNachname(nachnameField.getText());
        selectedPerson.setVorname(vornameField.getText());
        selectedPerson.setStandort(standortField.getText());
        selectedPerson.setVorkenntnisse(vorkenntnisseMenu.getText());
    }

    private void resetDisabledState(boolean bool) {
        TraineeListeCtrl traineeListeCtrl = ControllerManager.getTraineeListeCtrl();
        traineeListeCtrl.setTraineeListDisabled(bool);

        setTraineeInfoDisabled(!bool);

        KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
        kursInfoCtrl.setKursInfoDisabled(bool);

        KursListeCtrl kursListeCtrl = ControllerManager.getKursListeCtrl();
        kursListeCtrl.setKursListeDisabled(bool);
    }

    /**
     * Überschreibt die Felder mit der selektierten Person
     */
    public void setTraineeInfos() {
        nachnameField.setText("");
        vornameField.setText("");
        vorkenntnisseMenu.setText("Vorkenntnisse");
        standortField.setText("");
    }

    /**
     * Überschreibt die Felder mit der selektierten Person
     *
     * @param selectedPerson
     */
    public void setTraineeInfos(Person selectedPerson) {
        nachnameField.setText(selectedPerson.getNachname());
        vornameField.setText(selectedPerson.getVorname());
        vorkenntnisseMenu.setText(selectedPerson.getVorkenntnisse());
        standortField.setText(selectedPerson.getStandort());
    }

    public void setTraineeInfoDisabled(boolean bool) {
        idField.setDisable(bool);
        nachnameField.setDisable(bool);
        vornameField.setDisable(bool);
        standortField.setDisable(bool);
        vorkenntnisseMenu.setDisable(bool);
        kursId.setDisable(bool);
        cancelTrainee.setDisable(bool);
        saveTrainee.setDisable(bool);
        deleteTrainee.setDisable(bool);
    }
}
