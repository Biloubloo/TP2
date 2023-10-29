package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;

public class StatementPrinter {
    public String print(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter(result);

        out.format("Statement for %s\n", invoice.customer);

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            int thisAmount = 0;

            // Utilisation des variables statiques pour les types de pièces
            switch (play.type) {
                case Play.TRAGEDY:
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30);
                    }
                    break;
                case Play.COMEDY:
                    thisAmount = 30000;
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown play type: " + play.type);
            }

            // Ajout de crédits de volume
            volumeCredits += Math.max(perf.audience - 30, 0);
            if ("comedy".equals(play.type)) {
                // Ajout de crédits supplémentaires pour chaque tranche de 5 spectateurs dans une comédie
                volumeCredits += Math.floor(perf.audience / 5);
            }

            // Génération de la ligne pour cette performance
            out.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount / 100), perf.audience);
            totalAmount += thisAmount;
        }

        // Ajout du montant total et des crédits de volume totaux
        out.format("Amount owed is %s\n", frmt.format(totalAmount / 100));
        out.format("You earned %d credits\n", volumeCredits);

        // Conversion du StringWriter en chaîne de caractères
        return result.toString();
    }
}
