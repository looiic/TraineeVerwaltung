package logic;

public enum Standort {

    BASEL("Basel"),
    BERN("Bern"),
    BRUGG("Brugg"),
    BUKAREST("Bukarest"),
    DUSSELDORF("Düsseldorf"),
    FRANKFURT("Frankfurt am Main"),
    FREIBURG("Freiburg im Breisgau"),
    GENF("Genf"),
    HAMBURG("Hamburg"),
    KOPENHAGEN("Kopenhagen"),
    LAUSANNE("Lausanne"),
    MANNHEIM("Mannheim"),
    MUNCHEN("München"),
    STUTTGART("Stuttgart"),
    WIEN("Wien"),
    ZURICH("Zürich");

    private String value;

    Standort(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
/*  //Unnötiger Code da doch gegen Enum entschieden
    //ToDo: Exception erstellen für den Fall von einem ungültigen Standort-String.
    Standort getStandort(String standortInString){
        switch (standortInString){
            Standort standortInEnum;
            case "Basel":
                standortInEnum = BASEL;
                break;
            case "Bern":
                standortInEnum = BERN;
                break;
            case "Brugg":
                standortInEnum = BRUGG:
                break;
            case         "Bukarest":
                standortInEnum = BUKAREST;
            case    "Düsseldorf":
                standortInEnum = DUSSELDORF;
            case    "Frankfurt am Main":
                standortInEnum = FRANKFURT;
            case    "Freiburg im Breisgau":
                standortInEnum = FREIBURG;
            case    "Genf":

            case    "Hamburg":
            case    "Kopenhagen":
            case    "Lausanne":
            case    "Mannheim":
            case    "München":
            case    "Stuttgart":
            case    "Wien":
            case    "Zürich":
                */

    }

