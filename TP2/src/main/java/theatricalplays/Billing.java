package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

public class Billing {
    private Invoice invoice;
    private Map<String, Play> plays;
    private NumberFormat frmt;
    private double totalAmount;
    private int volumeCredits;

    public Billing(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        frmt = NumberFormat.getCurrencyInstance(Locale.US);
        totalAmount = 0.0;
        volumeCredits = 0;
    }

    public String toText() {
        StringBuilder result = new StringBuilder();

        // En-tête du relevé de facturation
        result.append("Statement for ").append(invoice.customer).append("\n");

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

    public String toHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title></head><body>");
        html.append("<h1>Invoice for ").append(invoice.customer).append("</h1>");
        html.append("<ul>");

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = calculateAmount(play, perf);

            html.append("<li>").append(play.name).append(": ").append(frmt.format(thisAmount)).append(" (").append(perf.audience).append(" seats)</li>");
        }

        html.append("</ul>");
        html.append("<p>Total amount: ").append(frmt.format(totalAmount)).append("</p>");
        html.append("<p>Total credits: ").append(volumeCredits).append("</p>");

        html.append("</body></html>");
        return html.toString();
    }

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