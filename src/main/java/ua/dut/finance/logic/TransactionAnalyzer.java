package ua.dut.finance.logic;

import ua.dut.finance.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private TransactionAnalyzer() {}

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> txs) {
        return txs.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public static int countTransactionsByMonth(List<Transaction> txs, String monthYear) {
        return (int) txs.stream()
                .filter(t -> MONTH_FMT.format(parse(t.getDate())).equals(monthYear))
                .count();
    }

    // від’ємні суми, найменші значення
    public static List<Transaction> findTopExpenses(List<Transaction> txs, int limit) {
        return txs.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount)) // зростання (найбільш від’ємні першими)
                .limit(limit)
                .collect(Collectors.toList());
    }

    //найбільш від’ємна та найбільш додатна транзакції включно
    public static Optional<Transaction> minExpenseInPeriod(List<Transaction> txs, String from, String to) {
        LocalDate f = parse(from), tt = parse(to);
        return txs.stream()
                .filter(t -> inRange(parse(t.getDate()), f, tt))
                .min(Comparator.comparingDouble(Transaction::getAmount));
    }

    public static Optional<Transaction> maxExpenseInPeriod(List<Transaction> txs, String from, String to) {
        LocalDate f = parse(from), tt = parse(to);
        return txs.stream()
                .filter(t -> inRange(parse(t.getDate()), f, tt))
                .max(Comparator.comparingDouble(Transaction::getAmount));
    }

    // Сума по категоріях
    public static Map<String, Double> sumByCategory(List<Transaction> txs) {
        return txs.stream().collect(Collectors.groupingBy(
                t -> t.getDescription() == null ? "Unknown" : t.getDescription().trim(),
                Collectors.summingDouble(Transaction::getAmount)
        ));
    }

    // Витрати сума тільки від’ємних
    public static Map<String, Double> expensesByCategory(List<Transaction> txs) {
        return txs.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> t.getDescription() == null ? "Unknown" : t.getDescription().trim(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    // Сума витрат по місяцях
    public static Map<String, Double> expensesByMonth(List<Transaction> txs) {
        return txs.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> MONTH_FMT.format(parse(t.getDate())),
                        TreeMap::new, // впорядковано по ключу
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    private static LocalDate parse(String ddMMyyyy) {
        return LocalDate.parse(ddMMyyyy, DF);
    }

    private static boolean inRange(LocalDate d, LocalDate from, LocalDate to) {
        return (d.isEqual(from) || d.isAfter(from)) && (d.isEqual(to) || d.isBefore(to));
    }
}
