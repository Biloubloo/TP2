package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Billing {
    // Attributs pour stocker les données
    private Invoice invoice; // La facture à traiter
    private Map<String, Play> plays; // Les pièces de théâtre associées par ID
    private NumberFormat frmt; // Formatage des montants en monnaie
    private double totalAmount; // Montant total de la facture
    private int volumeCredits; // Crédits de volume gagnés

    public Billing(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        frmt = NumberFormat.getCurrencyInstance(Locale.US);
        totalAmount = 0.0;
        volumeCredits = 0;
    }

    // Génère un relevé de facturation au format texte brut
    public String toText() {
        StringBuilder result = new StringBuilder();

        // En-tête du relevé de facturation
        result.append("Statement for ").append(invoice.customer).append("\n");

        // Parcours des performances dans la facture
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = calculateAmount(play, perf);

            // Ajout de crédits de volume
            volumeCredits += Math.max(perf.audience - 30, 0);
            if (Play.COMEDY.equals(play.type)) {
                volumeCredits += (int) Math.floor(perf.audience / 5);
            }

            // Génération de la ligne pour cette performance
            result.append("  ").append(play.name).append(": ").append(frmt.format(thisAmount)).append(" (").append(perf.audience).append(" seats)\n");
            totalAmount += thisAmount;
        }

        // Ajout du montant total et des crédits de volume totaux
        result.append("Amount owed is ").append(frmt.format(totalAmount)).append("\n");
        result.append("You earned ").append(volumeCredits).append(" credits\n");

        return result.toString();
    }

    // Génère un relevé de facturation au format HTML
    public String toHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title></head><body>");
        html.append("<h1>Invoice for ").append(invoice.customer).append("</h1><ul>");

        // Parcours des performances dans la facture
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = calculateAmount(play, perf);

            // Ajout d'une ligne pour cette performance dans HTML
            html.append("<li>").append(play.name).append(": ").append(frmt.format(thisAmount)).append(" (").append(perf.audience).append(" seats)</li>");
        }

        html.append("</ul>");
        html.append("<p>Total amount: ").append(frmt.format(totalAmount)).append("</p>");
        html.append("<p>Total credits: ").append(volumeCredits).append("</p>");

        html.append("</body></html>");
        return html.toString();
    }

    // Calcule le montant pour une performance en fonction du type de pièce
    private double calculateAmount(Play play, Performance perf) {
        double thisAmount = 0.0;
        if (Play.TRAGEDY.equals(play.type)) {
            thisAmount = 400.0;
            if (perf.audience > 30) {
                thisAmount += 10.0 * (perf.audience - 30);
            }
        } else if (Play.COMEDY.equals(play.type)) {
            thisAmount = 300.0;
            if (perf.audience > 20) {
                thisAmount += 100.0 + 5.0 * (perf.audience - 20);
            }
            thisAmount += 3.0 * perf.audience;
        } else {
            throw new Error("Unknown play type: " + play.type);
        }
        return thisAmount;
    }
}
