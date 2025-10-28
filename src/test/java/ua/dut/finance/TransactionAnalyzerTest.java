package ua.dut.finance.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.model.Transaction;

import java.util.Arrays;
import java.util.List;

public class TransactionAnalyzerTest {

    private static List<Transaction> demo() {
        return Arrays.asList(
                new Transaction("01-02-2023", 50.0, "Дохід"),
                new Transaction("15-02-2023", -20.0, "Витрата"),
                new Transaction("05-03-2023", 100.0, "Дохід"),
                new Transaction("10-03-2023", -200.0, "Оренда"),
                new Transaction("25-03-2023", -80.0, "Продукти")
        );
    }

    @Test
    void calculateTotalBalance_ok() {
        double total = TransactionAnalyzer.calculateTotalBalance(demo());
        Assertions.assertEquals(-150.0, total, 1e-6);
    }

    @Test
    void countTransactionsByMonth_ok() {
        int feb = TransactionAnalyzer.countTransactionsByMonth(demo(), "02-2023");
        int mar = TransactionAnalyzer.countTransactionsByMonth(demo(), "03-2023");
        Assertions.assertEquals(2, feb);
        Assertions.assertEquals(3, mar);
    }

    @Test
    void findTopExpenses_top2() {
        var top = TransactionAnalyzer.findTopExpenses(demo(), 2);
        Assertions.assertEquals(2, top.size());
        // Найбільші витрати — -200, потім -80
        Assertions.assertEquals(-200.0, top.get(0).getAmount(), 1e-6);
        Assertions.assertEquals(-80.0, top.get(1).getAmount(), 1e-6);
    }
}
