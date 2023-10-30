import theatricalplays.Tragedy;
import theatricalplays.Comedy;
import theatricalplays.History;
import theatricalplays.Pastoral;
import theatricalplays.Play;

import theatricalplays.Invoice;
import theatricalplays.Performance;
import theatricalplays.StatementPrinter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {
        // Création d'une carte de pièces de théâtre pour les performances
        Map<String, Play> plays = Map.of(
            "hamlet",  new Tragedy("Hamlet"),
            "as-like", new Comedy("As You Like It"),
            "othello", new Tragedy("Othello"));

        // Création d'une facture (invoice) pour l'entreprise "BigCo" avec des performances
        Invoice invoice = new Invoice("BigCo", List.of(
            new Performance("hamlet", 55),
            new Performance("as-like", 35),
            new Performance("othello", 40)));

        // Création d'une instance de StatementPrinter
        StatementPrinter statementPrinter = new StatementPrinter();

        // Impression du relevé de facturation et vérification avec l'approbation
        var result = statementPrinter.print(invoice, plays);
        verify(result);
    }

    @Test
    void statementWithNewPlayTypes() {
        // Création d'une carte de pièces de théâtre pour les performances, y compris des types de pièces inconnus
        Map<String, Play> plays = Map.of(
            "henry-v",  new History("Henry V"),
            "as-like", new Pastoral("As You Like It"));

        // Création d'une facture (invoice) pour l'entreprise "BigCo" avec des performances
        Invoice invoice = new Invoice("BigCo", List.of(
            new Performance("henry-v", 53),
            new Performance("as-like", 55)));

        // Création d'une instance de StatementPrinter
        StatementPrinter statementPrinter = new StatementPrinter();

        // Vérification qu'une erreur est levée lorsque des types de pièces inconnus sont utilisés
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }
}
