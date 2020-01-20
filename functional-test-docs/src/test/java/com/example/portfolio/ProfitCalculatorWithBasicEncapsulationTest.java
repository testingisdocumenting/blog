package com.example.portfolio;

import org.junit.Test;

import java.util.Arrays;

import static com.example.portfolio.TestTransactions.createTransaction;
import static org.junit.Assert.assertEquals;

public class ProfitCalculatorWithBasicEncapsulationTest {
    private ProfitCalculator profitCalculator = new ProfitCalculator();

    @Test
    public void profitShouldBeZeroIfNoLotsSet() {
        Transaction t1 = createTransaction("t1", "SYM.B", 0, 8);
        Transaction t2 = createTransaction("t2", "SYM.C", 0, 19);

        assertEquals(0, profitCalculator.calculate(Arrays.asList(t1, t2)), 0);
    }
}
