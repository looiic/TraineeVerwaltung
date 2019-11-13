package GUI;

import GUI.kursInfo.KursInfoCtrl;
import GUI.kursListe.KursListeCtrl;
import GUI.main.MainCtrl;
import GUI.traineeInfo.TraineeInfoCtrl;
import GUI.traineeListe.TraineeListeCtrl;

/** Klasse zur Verwaltung aller Controller der Anwendung */
public class ControllerManager {

    static private MainCtrl mainCtrl;
    static private KursListeCtrl kursListeCtrl;
    static private KursInfoCtrl kursInfoCtrl;
    static private TraineeListeCtrl traineeListeCtrl;
    static private TraineeInfoCtrl traineeInfoCtrl;


    /** Liefert den Controller der main zurück */
    public static MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**Es wird ein Controller in der Main gesetzt */
    public static void setMainCtrl(MainCtrl mainCtrl) {
        ControllerManager.mainCtrl = mainCtrl;
    }

    /** Liefert den Controller von kursListe zurück */
    public static KursListeCtrl getKursListeCtrl() {
        return kursListeCtrl;
    }

    /**Es wird  ein Controller kursListe gesetzt*/
    public static void setKursListeCtrl(KursListeCtrl kursListeCtrl) {
        ControllerManager.kursListeCtrl = kursListeCtrl;
    }

    /** Liefert den Controller von kursInfo zurück */
    public static KursInfoCtrl getKursInfoCtrl() {
        return kursInfoCtrl;
    }

    /**Es wird ein Controller kursInfo gesetzt */
    public static void setKursInfoCtrl(KursInfoCtrl kursInfoCtrl) {
        ControllerManager.kursInfoCtrl = kursInfoCtrl;
    }

    /** Liefert den Controller von traineeListe zurück */
    public static TraineeListeCtrl getTraineeListeCtrl() {
        return traineeListeCtrl;
    }

    /** Es wird ein Controller traineeListe gesetzt*/
    public static void setTraineeListeCtrl(TraineeListeCtrl traineeListeCtrl) {
        ControllerManager.traineeListeCtrl = traineeListeCtrl;
    }

    /** Liefert den Controller von traineeInfo zurück */
    public static TraineeInfoCtrl getTraineeInfoCtrl() {
        return traineeInfoCtrl;
    }

    /** Es wird  ein Controller traineeInfo gesetzt */
    public static void setTraineeInfoCtrl(TraineeInfoCtrl traineeInfoCtrl) {
        ControllerManager.traineeInfoCtrl = traineeInfoCtrl;
    }
}
