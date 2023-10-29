package theatricalplays;

public class HtmlStatementPrinter {
    public String generateHtmlStatement(Invoice invoice) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title></head><body>");
        html.append("<h1>Invoice for ").append(invoice.customer).append("</h1>");
        html.append("<ul>");

        for (Performance perf : invoice.performances) {
            // Générez le HTML pour chaque performance ici
        }

        html.append("</ul>");
        html.append("<p>Total Amount: ").append(invoice.totalAmount).append("</p>");
        html.append("<p>Volume Credits: ").append(invoice.volumeCredits).append("</p>");
        html.append("</body></html>");

        return html.toString();
    }
}
