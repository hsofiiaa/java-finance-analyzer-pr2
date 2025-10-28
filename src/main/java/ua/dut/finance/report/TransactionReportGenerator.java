package ua.dut.finance.report;

import ua.dut.finance.model.Transaction;

import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.printf("Загальний баланс: %.2f%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.printf("Кількість транзакцій за %s: %d%n", monthYear, count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        topExpenses.forEach(tx ->
                System.out.printf("  %s | %s | %.2f%n", tx.getDate(), tx.getDescription(), tx.getAmount()));
    }

    // кожна * = stepAmount
    public static void printCategorySummary(Map<String, Double> byCategory, double stepAmountAbs) {
        System.out.println("Сумарні витрати по категоріях ( '*' ~ " + stepAmountAbs + " грн ):");
        byCategory.forEach((cat, sum) -> {
            double abs = Math.abs(sum); // витрати — від’ємні
            int stars = (int) Math.round(abs / stepAmountAbs);
            System.out.printf("  %-20s %10.2f | %s%n", cat, sum, "*".repeat(Math.max(0, stars)));
        });
    }

    public static void printMonthlySummary(Map<String, Double> byMonth, double stepAmountAbs) {
        System.out.println("Сумарні витрати по місяцях ( '*' ~ " + stepAmountAbs + " грн ):");
        byMonth.forEach((month, sum) -> {
            double abs = Math.abs(sum);
            int stars = (int) Math.round(abs / stepAmountAbs);
            System.out.printf("  %s  %10.2f | %s%n", month, sum, "*".repeat(Math.max(0, stars)));
        });
    }
}
