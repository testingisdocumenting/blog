package com.example.portfolio;

import org.testingisdocumenting.webtau.data.table.TableData;
import org.junit.Test;

import static com.example.portfolio.TestTransactions.createTransactions;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class ProfitCalculatorWithTableDataNoIdNoPriceTest {
    private final ProfitCalculator profitCalculator = new ProfitCalculator();

    @Test
    public void profitShouldBeZeroIfNoLotsSet() {
        TableData transactionsData = table("symbol", "lot",
                                           ________________,
                                           "SYM.B" , 0.0,
                                           "SYM.C" , 0.0);

        double margin = profitCalculator.calculate(createTransactions(transactionsData));
        actual(margin).should(equal(0));
    }
}