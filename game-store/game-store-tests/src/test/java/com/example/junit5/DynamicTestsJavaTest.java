package com.example.junit5;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.testingisdocumenting.webtau.data.table.TableData;
import org.testingisdocumenting.webtau.junit5.DynamicTests;

import java.util.stream.Stream;

import static org.testingisdocumenting.webtau.WebTauCore.*; // convenient single import for unit tests

class DynamicTestsJavaTest {
    TableData useCases = table("price", "quantity", "outcome", // java table definition
                               ______________________________,
                                   10 ,         30,       300,
                                  -10 ,         30,      -300);
    @TestFactory
    public Stream<DynamicTest> testFactoryExample() {
        return DynamicTests.fromTable(useCases, r -> { // generate DynamicTest per row
            long price = r.get("price");
            long quantity = r.get("quantity");
            long outcome = r.get("outcome");

            actual(PriceCalculator.calculate(price, quantity)).should(equal(outcome));
        });
    }
}
