package theatricalplays;

public abstract class Play {
    public static final String TRAGEDY = "tragedy";
    public static final String COMEDY = "comedy";
    public static final String HISTORY = "history";
    public static final String PASTORAL = "pastoral";

    public String name; // Le nom de la pièce
    public String type; // Le type de la pièce

    public Play(String name, String type) {
        validateType(type); // Vérifie le type de pièce lors de la création de l'objet
        this.name = name;
        this.type = type;
    }

    private void validateType(String type) {
        if (!TRAGEDY.equals(type) && !COMEDY.equals(type) && !HISTORY.equals(type) && !PASTORAL.equals(type)) {
            throw new Error("Invalid play type: " + type);
        }
    }
}
