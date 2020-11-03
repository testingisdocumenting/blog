package com.example.junit5

import org.junit.jupiter.api.TestFactory

class DynamicTestsGroovyTest {
    @TestFactory // Junit5 test factory that expects Stream<DynamicTest>
    def "test factory example"() {
        ["price" | "quantity" | "outcome"] {
        ___________________________________
              10 |  30        |  300
             -10 |  30        | -300
        }.test { // test is a what generates DynamicTest from each row
            PriceCalculator.calculate(price, quantity).should == outcome
        }
    }
}