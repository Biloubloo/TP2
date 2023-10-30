package theatricalplays;

import java.util.List;

public class Invoice {
  public String customer; // Le nom du client associé à la facture
  public List<Performance> performances; // Une liste de performances dans la facture

  public Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }
}
