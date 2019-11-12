package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;


import javax.tools.Tool;

/**
 * Dieser Handler schriebt Infos über die Funktion eines Bedienelements(Tooltip) in ein Label wenn der Mauszeiger daraufzeigt
 */
public class ShowToolTipHandler implements EventHandler {
    private final Label label;


    public ShowToolTipHandler(Label label){
         this.label = label;

    }

    @Override
    public void handle(Event event) {
        String test = showTooltip(event);
        System.out.println(test);
        label.setText(test);
    }

    public String showTooltip(Event mouseEvent) {
//        final Tooltip customTooltip = new Tooltip();
        Button thisButton = (Button) mouseEvent.getSource();
        Tooltip tip = new Tooltip("I am a ToolTip");
        thisButton.setTooltip(tip);
        return thisButton.getTooltip().getText();

    }
}
