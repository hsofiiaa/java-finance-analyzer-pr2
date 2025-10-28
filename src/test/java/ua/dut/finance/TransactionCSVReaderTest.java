package ua.dut.finance.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dut.finance.model.Transaction;

import java.util.List;

public class TransactionCSVReaderTest {

    @Test
    void readTransactions_fromUrl_notEmpty() {
        DataSource ds = new UrlDataSource("https://informer.com.ua/dut/java/pr2.csv");
        List<Transaction> txs = TransactionCSVReader.readTransactions(ds, true);
        Assertions.assertFalse(txs.isEmpty(), "Список транзакцій не має бути порожнім");
        // базова перевірка формату
        Transaction t = txs.get(0);
        Assertions.assertNotNull(t.getDate());
        Assertions.assertNotNull(t.getDescription());
    }
}
