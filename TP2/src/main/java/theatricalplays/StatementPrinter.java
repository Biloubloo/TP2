package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;

public class StatementPrinter {
    public String print(Invoice invoice, Map<String, Play> plays) {
        // Total du montant de la facture
        double totalAmount = 0.0;
        // Crédits de volume
        int volumeCredits = 0;

        // Utilisation d'un StringWriter pour générer la sortie
        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter(result);

        // En-tête du relevé de facturation
        out.format("Statement for %s\n", invoice.customer);

        // Format pour afficher les montants en dollars
        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = 0.0;

            // Calcul du montant pour cette performance en fonction du type de pièce
            switch (play.type) {
                case Play.TRAGEDY:
                    thisAmount = 400.0;
                    if (perf.audience > 30) {
                        thisAmount += 10.0 * (perf.audience - 30);
                    }
                    break;
                case Play.COMEDY:
                    thisAmount = 300.0;
                    if (perf.audience > 20) {
                        thisAmount += 100.0 + 5.0 * (perf.audience - 20);
                    }
                    thisAmount += 3.0 * perf.audience;
                    break;
                default:
                    // Gestion d'une pièce de type inconnu
                    throw new Error("Unknown play type: " + play.type);
            }

            // Ajout de crédits de volume
            volumeCredits += Math.max(perf.audience - 30, 0);
            if (Play.COMEDY.equals(play.type)) {
                // Ajout de crédits supplémentaires pour chaque tranche de 5 spectateurs dans une comédie
                volumeCredits += Math.floor(perf.audience / 5);
            }

            // Génération de la ligne pour cette performance
            out.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience);
            totalAmount += thisAmount;
        }

        // Ajout du montant total et des crédits de volume totaux
        out.format("Amount owed is %s\n", frmt.format(totalAmount));
        out.format("You earned %d credits\n", volumeCredits);

        // Conversion du StringWriter en chaîne de caractères
        return result.toString();
    }
}