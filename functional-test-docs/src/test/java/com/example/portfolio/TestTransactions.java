package com.example.portfolio;

import com.twosigma.webtau.data.table.TableData;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class TestTransactions {
    private static AtomicInteger idCount = new AtomicInteger(0);

    public static Transaction createTransaction(String id, String symbol, double lot, double price) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setSymbol(symbol);
        transaction.setLot(lot);
        transaction.setPrice(price);

        return transaction;
    }

    public static List<Transaction> createTransactions(TableData tableData) {
        return tableData.rowsStream()
                .map(row -> {
                    Transaction transaction = new Transaction();
                    transaction.setId(row.get("id", genTransactionId())); // default value is auto generated
                    transaction.setSymbol(row.get("symbol"));
                    transaction.setLot(row.get("lot", 0.0)); // default value is hardcoded
                    transaction.setPrice(row.get("price", 0.0));

                    return transaction;
                })
                .collect(toList());
    }

    private static String genTransactionId() {
        return "t-id-" + idCount.incrementAndGet();
    }
}