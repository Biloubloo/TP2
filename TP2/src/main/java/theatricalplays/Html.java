package theatricalplays;

public class Html {
   public String toHTML(Invoice invoice) {
    StringBuilder html = new StringBuilder();
    html.append("<html><head><title>Invoice</title></head><body>");
    html.append("<h1>Invoice for ").append(invoice.customer).append("</h1>");
    html.append("<table border='1'><tr><th>Performance</th><th>Audience</th><th>Amount</th></tr>");

    for (Performance perf : invoice.performances) {
        Play play = plays.get(perf.playID);
        double thisAmount = calculateAmount(play.type, perf.audience);

        html.append("<tr><td>").append(play.name).append("</td><td>").append(perf.audience).append("</td><td>").append(thisAmount).append("</td></tr>");
    }

    html.append("</table>");
    html.append("<p>Total Amount: ").append(invoice.totalAmount).append("</p>");
    html.append("<p>Volume Credits: ").append(invoice.volumeCredits).append("</p>");
    html.append("</body></html>");

    return html.toString();
}
}
