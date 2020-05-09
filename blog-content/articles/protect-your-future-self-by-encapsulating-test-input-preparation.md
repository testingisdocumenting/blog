---
date: 2020-01-07
summary: Examples of how to reduce verbosity and brittleness of your tests by encapsulating input preparation 
---

# Tests Brittleness and Verbosity slows you down

In [Testing Makes You Faster Day one](entry/testing-makes-you-faster-day-one) article I claimed that writing tests makes you faster.
However, there are test patterns that most likely will slow you down and you need to be ready to handle them. 

Writing tests for a simple logic with a simple input is a breeze. Take for example a test for a simple calculator class. 
Two numeric inputs and we are done: 

:include-file: com/example/portfolio/SimpleCalculatorTest.java {title: "SimpleCalculatorTest.java"}

However real apps require real domain, and real domain is rarely represented by a couple of numbers.
Let's take Finance as an example. It is full of types with multiple fields such as `Transaction`, and it has a lot of business logic.

One example of such business logic is `ProfitCalculator`. Its job is to calculate profit based on the executed `Transactions`.

After consulting with the business people, we are ready to give our implementation a shot.

`Transaction` is a data class with a few fields (number of fields is reduced for simplicity).

:include-file: com/example/portfolio/Transaction.java {title: "Transaction.java", readMore: true, readMoreVisibleLines: 20}

Here is our first `ProfitCalculator` implementation, ready to be tested.

:include-file: com/example/portfolio/ProfitCalculator.java {title: "ProfitCalculator.java"}

And here is the first version of our test.

:include-file: com/example/portfolio/ProfitCalculatorWithoutEncapsulationTest.java {title: "ProfitCalculatorTest.java"}

This test is already on the **verbose** side. As requirements for the `ProfitCalculator` evolve, the number of setters 
we need to validate new business logic will grow, as will grow the number of instances that we may need to create. 

Question: But why **verbose** test can be bad?  

```spoiler {title: "tap me to see why a verbose test can be a problem"}
* A verbose test can be bad, because the boilerplate code used to setup the test makes it harder to read intent behind the test.
* Additionally it may discourage you to write separate test scenario and instead you may want to clump test scenarios together and re-use the test data setup.
* Test data re-use can lead to a random test being broken as you setup data for an unrelated scenario.    
```

**Verbosity** is not the only potential problem here. The way `Transaction` instance is being constructed for testing 
purposes can lead to a maintenance burden. 

The First iteration of `Transaction` class uses `setters` to set the data. 
A future iteration may switch to using fluent API instead of `setters`.

:include-java: com/example/portfolio/ProfitCalculatorWithoutEncapsulationFluentTest.java {title: "fluent API", entry: "createTransaction", bodyOnly: true, removeReturn: true}

From the `ProfitCalculator`'s perspective the way `Transaction` instances are created is irrelevant as it doesn't affect the calculation logic.
But refactoring `Transaction` will break `ProfitCalculatorTest`. 

`Transaction` is going to be used in other tests. As the number of tests that use `Transaction` instances increases you will be more and more reluctant
to do refactoring. All your tests that create `Transaction` instances are **brittle** tests now. 
They are **brittle** because they won't survive `Transaction` refactoring. 

If you ever want to refactor `Transaction` class you will have two choices:
* Refactor and waste time fixing tests.
* Convince yourself that `Transaction` is good as it is. 

Neither is a good choice. 

> I want you to have a way to protect your future self by encapsulating test input preparation

# Test Input Initial Encapsulation

Let's extract `Transaction` creation code into `TestTransactions`

:include-file: com/example/portfolio/ProfitCalculatorWithBasicEncapsulationTest.java {title: "ProfitCalculatorTest.java with initial encapsulation"}

:include-java: com/example/portfolio/TestTransactions.java {title: "TestTransactions.createTransaction", entry: "createTransaction"}

Less verbosity. A good start.

Question: Can you still spot the problem?

```spoiler {title: "tap me to see remaining problems"}
* As we add more properties to `Transaction` class, some of the tests may need to set *extra transaction properties*. 
* Additionally some of the properties will be completely *irrelevant to the business logic* under a test, yet you will be forced to set them anyway.
```

# Test Input Encapsulation With Webtau Table Data   

I was dealing with test problems like above for years and eventually came up with a solution that fits my needs.
I hope it will fit your needs as well. The solution is to use flexible data structure like [Webtau TableData](https://testingisdocumenting.org/webtau/reference/table-data)

:include-file: com/example/portfolio/ProfitCalculatorWithTableDataTest.java {title: "ProfitCalculatorTest.java with TableData encapsulation", commentsType: "inline"}

Note: [Webtau](https://github.com/testingisdocumenting/webtau) is open source project that I started as my answer to common testing problems I encountered. 

:include-java: com/example/portfolio/TestTransactions.java {title: "TestTransactions.createTransactions", commentsType: "inline", entries: ["createTransactions", "genTransactionId"]}

Notice how `createTransactions` defaults values when they are not present?
As a result of this, tests that don't need, say, `id` or `lot` are free to ignore them.

:include-java: com/example/portfolio/ProfitCalculatorWithTableDataNoIdNoPriceTest.java {title: "ignored id,lot Transaction properties", commentsType: "inline", entry: "profitShouldBeZeroIfNoLotsSet"}

If later a new required property will be added to `Transaction`, you won't have to change all your existing tests. 
Instead you will update `createTransaction` with a new default value. 
The only time you will have to change the tests to use the new property is if the new property is affecting the logic under test.

Essentially your test will use as little data to prove logic works as possible.  

Question: Why it is important to minimize the data in your test?

```spoiler {title: "tap me to see why minimizing data is important"}
* Test is crucial to understanding *complex business logic*
* The more data the harder it to comprehend the logic
* We want business people to look at our data sets (stay tuned to see a good way to expose test data to business)  
```

# Try it out

:include-file: webtau-core-java-dep.xml {title: "Maven dependency"}

* [Webtau user guide](https://testingisdocumenting.org/webtau)
* [Webtau github](https://github.com/testingisdocumenting/webtau)

# Summary

* Tests input preparation can be verbose and brittle.
* Identify core domain objects and provide a convenient way to create them.
* Use as little data as necessary to prove logic correctness. Default the rest. 