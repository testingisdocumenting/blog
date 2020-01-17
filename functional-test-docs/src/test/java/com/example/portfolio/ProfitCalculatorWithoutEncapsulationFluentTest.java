package com.example.portfolio;

import static com.example.portfolio.TransactionFluent.transaction;

public class ProfitCalculatorWithoutEncapsulationFluentTest {
    private static TransactionFluent createTransaction() {
        return transaction("T1")
                .symbol("SYM.B")
                .lot(0)
                .price(0);
    }
}
