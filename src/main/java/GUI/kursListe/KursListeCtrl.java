package GUI.kursListe;

import DatenbankMethoden.DbKurs;
import GUI.ControllerManager;
import GUI.kursInfo.KursInfoCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import logic.Kurs;

import java.sql.SQLException;
import java.util.List;

/**Controllerklasse der Kursliste */
public class KursListeCtrl {

    @FXML private TableView kursTabelle;
    @FXML private Button addKurs;

    /**
     * In einer Tabelle werden die bestehenden Kurse aus der Datenbank geladen.
     * Exception Handling: Falls keine initiale Verbindung zur Datenbank hergestellt werden kann, zeigt ein Popup-Fenster die Fehlermeldung an
     */
    @FXML
    public void initialize() {
        DbKurs dbKurs = new DbKurs();
        try {
            List<Kurs> kursListe = dbKurs.getKursListe();
            ObservableList<Kurs> obsList = FXCollections.observableArrayList(kursListe);
            kursTabelle.setItems(obsList);
            kursTabelle.getSelectionModel().select(0);

        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Folgender Fehler ist aufgetreten:" + "\n\n" + e.getMessage());
            error.showAndWait();
        }
    }

    @FXML
    public void eventNeuerKurs() throws SQLException {
        ControllerManager.getKursInfoCtrl().neuerKurs();
    }

    /**
     * Handler setzt die ausgewählten Kursinfos und läd die Teilnehmerliste neu
     * @param e Event, der zu behandeln ist
     * @throws SQLException SQL Exception
     */
    @FXML
    public void handleMouseClick(Event e) throws SQLException {
        Object selectedItem = kursTabelle.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof  Kurs){
            Kurs selectedKurs = (Kurs) selectedItem;
            KursInfoCtrl kursInfoCtrl = ControllerManager.getKursInfoCtrl();
            kursInfoCtrl.setKursInfos(selectedKurs);

            ControllerManager.getTraineeListeCtrl().reloadTraineeListe(selectedKurs);


        }
    }

    public void setKursListeDisabled(boolean b) {
        kursTabelle.setDisable(b);
        addKurs.setDisable(b);
    }

    public Kurs getSelectedKurs(){
        Object selectedItem = kursTabelle.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof Kurs){
            return (Kurs) selectedItem;
        }else{
            return null;
        }
    }

    public void setSelectedKurs() {
        kursTabelle.getSelectionModel().selectLast();
    }
}
