package com.example.portfolio;

import com.twosigma.webtau.data.table.TableData;
import org.junit.Test;

import static com.example.portfolio.TestTransactions.createTransactions;
import static com.twosigma.webtau.WebTauCore.*;

public class ProfitCalculatorWithTableDataNoIdNoLotTest {
    private ProfitCalculator profitCalculator = new ProfitCalculator();

    @Test
    public void profitShouldBeZeroIfNoLotsSet() {
        TableData transactionsData = table("symbol", "price",
                                           ________________,
                                           "SYM.B" , 8.0,
                                           "SYM.C" , 19.0);

        double margin = profitCalculator.calculate(createTransactions(transactionsData));
        actual(margin).should(equal(0));
    }
}