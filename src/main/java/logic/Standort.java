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

}
