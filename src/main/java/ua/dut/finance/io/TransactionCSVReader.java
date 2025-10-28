package ua.dut.finance.io;

import ua.dut.finance.model.Transaction;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {  }

    public static List<Transaction> readTransactions(DataSource source, boolean hasHeader) {
        List<Transaction> out = new ArrayList<>();
        try (InputStream is = source.openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            if (hasHeader) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;

                String date = parts[0].trim().replace("\"", "");
                double amount = parseAmountSafe(parts[1].trim());
                String description = parts[2].trim().replace("\"", "");

                out.add(Transaction.builder()
                        .date(date)
                        .amount(amount)
                        .description(description)
                        .build());
            }
        } catch (IOException e) {
            System.err.println("Помилка читання CSV з " + source.name() + ": " + e.getMessage());
        }
        return out;
    }

    private static double parseAmountSafe(String raw) {
        try {
            return Double.parseDouble(raw.replace(" ", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
