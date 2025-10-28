package ua.dut.finance;

import ua.dut.finance.io.DataSource;
import ua.dut.finance.io.TransactionCSVReader;
import ua.dut.finance.io.UrlDataSource;
import ua.dut.finance.logic.TransactionAnalyzer;
import ua.dut.finance.model.Transaction;
import ua.dut.finance.report.TransactionReportGenerator;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String fileUrl = "https://informer.com.ua/dut/java/pr2.csv";
        DataSource ds = new UrlDataSource(fileUrl);

        //  Читання
        List<Transaction> txs = TransactionCSVReader.readTransactions(ds, true);

        //  Аналіз
        double total = TransactionAnalyzer.calculateTotalBalance(txs);
        String monthYear = "01-2024";
        int countJan = TransactionAnalyzer.countTransactionsByMonth(txs, monthYear);
        var top10 = TransactionAnalyzer.findTopExpenses(txs, 10);
        var minOpt = TransactionAnalyzer.minExpenseInPeriod(txs, "01-01-2024", "31-12-2024");
        var maxOpt = TransactionAnalyzer.maxExpenseInPeriod(txs, "01-01-2024", "31-12-2024");
        Map<String, Double> byCat = TransactionAnalyzer.expensesByCategory(txs);
        Map<String, Double> byMonth = TransactionAnalyzer.expensesByMonth(txs);

        //  Звіти
        TransactionReportGenerator.printBalanceReport(total);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, countJan);
        TransactionReportGenerator.printTopExpensesReport(top10);

        minOpt.ifPresent(min -> System.out.printf("Найбільш від’ємна транзакція у періоді: %s | %s | %.2f%n",
                min.getDate(), min.getDescription(), min.getAmount()));
        maxOpt.ifPresent(max -> System.out.printf("Найбільш додатна транзакція у періоді: %s | %s | %.2f%n",
                max.getDate(), max.getDescription(), max.getAmount()));

        System.out.println();
        TransactionReportGenerator.printCategorySummary(byCat, 1000); // 1 * = 1000 грн
        System.out.println();
        TransactionReportGenerator.printMonthlySummary(byMonth, 1000);
    }
}
