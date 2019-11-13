package GUI;

import javafx.scene.control.TextField;

/**Klasse zur Limitierung eines Textfeldes in der Anwendung */
public class TextFieldLimited extends TextField {

    /**Zahl der maximalen Länder eines Textfeldes */
    private int maxlength;

    /**Konstruktor */
    public TextFieldLimited() {
        this.maxlength = 50;
    }

    /**Methode setzt die maximale Länge*/
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    /**Ersetzt einen Zeichenbereich durch den angegebenen Text. */
    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }

    /**Ersetzt die Auswahl durch die angegebene Ersatzzeichenfolge.
     *Wenn keine Auswahl vorhanden ist, wird der Ersetzungstext einfach an der aktuellen Einfügeposition eingefügt.
     *Wenn eine Auswahl vorhanden war, wird die Auswahl gelöscht und der angegebene Ersetzungstext eingefügt.*/
    @Override
    public void replaceSelection(String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            // Add characters, but don't exceed maxlength.
            if (text.length() > maxlength - getText().length()) {
                text = text.substring(0, maxlength- getText().length());
            }
            super.replaceSelection(text);
        }
    }
}
