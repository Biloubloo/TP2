package theatricalplays;

public class Performance {
  public String playID; // L'identifiant de la pièce de théâtre associée à la performance
  public int audience; // Le nombre de spectateurs présents lors de la performance

  public Performance(String playID, int audience) {
    this.playID = playID;
    this.audience = audience;
  }
}
