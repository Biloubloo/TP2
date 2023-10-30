package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;

public class StatementPrinter {
    public String print(Invoice invoice, Map<String, Play> plays) {
        Billing billing = new Billing(invoice, plays);
        return billing.toText();
    }
}