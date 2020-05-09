package com.example.portfolio;

import org.junit.Test;
import org.testingisdocumenting.webtau.data.table.TableData;

import static com.example.portfolio.TestTransactions.createTransactions;
import static org.testingisdocumenting.webtau.WebTauCore.*; // table and underscores are defined in WebTauCore

public class ProfitCalculatorWithTableDataTest {
    private final ProfitCalculator profitCalculator = new ProfitCalculator();

    @Test
    public void profitShouldBeZeroIfNoLotsSet() {
        TableData transactionsData = table( "id", "symbol", "lot", "price",  // webtau TableData definition
                                           ________________________________, // header and values separator
                                            "t1", "SYM.B" ,  0.0 ,    8.0,
                                            "t2", "SYM.C" ,  0.0 ,    19.0);

        double margin = profitCalculator.calculate(createTransactions(transactionsData));
        actual(margin).should(equal(0));
    }
}