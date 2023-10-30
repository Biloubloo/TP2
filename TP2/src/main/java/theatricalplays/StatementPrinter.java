package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatementPrinter {
    public String print(Invoice invoice, Map<String, Play> plays) {
        Billing billing = new Billing(invoice, plays);
        String stock = billing.toHTML();

        // Spécification du fichier de sortie
        String outputFile = "billing.html";
        
        // Écriture du contenu HTML dans un fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(stock); 
            System.out.println("HTML content has been written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Renvoie la représentation textuelle du relevé de facturation
        return billing.toText();
    }



}