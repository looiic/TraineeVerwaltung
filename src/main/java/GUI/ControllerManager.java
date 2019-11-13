package GUI;

import GUI.kursInfo.KursInfoCtrl;
import GUI.kursListe.KursListeCtrl;
import GUI.main.MainCtrl;
import GUI.traineeInfo.TraineeInfoCtrl;
import GUI.traineeListe.TraineeListeCtrl;

public class ControllerManager {

    static private MainCtrl mainCtrl;
    static private KursListeCtrl kursListeCtrl;
    static private KursInfoCtrl kursInfoCtrl;
    static private TraineeListeCtrl traineeListeCtrl;
    static private TraineeInfoCtrl traineeInfoCtrl;


    public static MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public static void setMainCtrl(MainCtrl mainCtrl) {
        ControllerManager.mainCtrl = mainCtrl;
    }

    public static KursListeCtrl getKursListeCtrl() {
        return kursListeCtrl;
    }

    public static void setKursListeCtrl(KursListeCtrl kursListeCtrl) {
        ControllerManager.kursListeCtrl = kursListeCtrl;
    }

    public static KursInfoCtrl getKursInfoCtrl() {
        return kursInfoCtrl;
    }

    public static void setKursInfoCtrl(KursInfoCtrl kursInfoCtrl) {
        ControllerManager.kursInfoCtrl = kursInfoCtrl;
    }

    public static TraineeListeCtrl getTraineeListeCtrl() {
        return traineeListeCtrl;
    }

    public static void setTraineeListeCtrl(TraineeListeCtrl traineeListeCtrl) {
        ControllerManager.traineeListeCtrl = traineeListeCtrl;
    }

    public static TraineeInfoCtrl getTraineeInfoCtrl() {
        return traineeInfoCtrl;
    }

    public static void setTraineeInfoCtrl(TraineeInfoCtrl traineeInfoCtrl) {
        ControllerManager.traineeInfoCtrl = traineeInfoCtrl;
    }
}
