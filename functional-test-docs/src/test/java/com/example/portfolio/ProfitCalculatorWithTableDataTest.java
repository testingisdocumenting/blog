package com.example.portfolio;

import com.twosigma.webtau.data.table.TableData;
import org.junit.Test;

import static com.example.portfolio.TestTransactions.createTransactions;
import static com.twosigma.webtau.WebTauCore.*; // table and underscores are defined in WebTauCore

public class ProfitCalculatorWithTableDataTest {
    private ProfitCalculator profitCalculator = new ProfitCalculator();

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