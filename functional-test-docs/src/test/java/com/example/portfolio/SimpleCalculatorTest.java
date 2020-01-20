package com.example.portfolio;

import org.junit.Assert;
import org.junit.Test;

public class SimpleCalculatorTest {
    @Test
    public void zeroMultiply() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        Assert.assertEquals(0, simpleCalculator.multiply(100.0, 0.0), 0.0);
        Assert.assertEquals(0, simpleCalculator.multiply(100.0, 0.0), 0.0);
    }
}