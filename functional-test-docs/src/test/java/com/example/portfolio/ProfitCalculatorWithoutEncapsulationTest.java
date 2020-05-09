package com.example.portfolio;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ProfitCalculatorWithoutEncapsulationTest {
    private final ProfitCalculator profitCalculator = new ProfitCalculator();

    @Test
    public void profitShouldBeZeroIfNoLotsSet() {
        Transaction t1 = new Transaction();
        t1.setId("T1");
        t1.setSymbol("SYM.B");
        t1.setLot(0);
        t1.setPrice(8);

        Transaction t2 = new Transaction();
        t1.setId("T2");
        t1.setSymbol("SYM.C");
        t1.setLot(0);
        t1.setPrice(19);

        assertEquals(0, profitCalculator.calculate(Arrays.asList(t1, t2)), 0);
    }
}
