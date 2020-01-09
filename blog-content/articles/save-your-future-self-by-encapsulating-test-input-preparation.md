---
date: 2020-01-07
summary: Examples of how to reduce boilerplate and brittleness of your test input preparation 
---

# Tests Brittleness and Verbosity slows you down

In [Testing Makes You Faster Day one](entry/testing-makes-you-faster-day-one) article I claimed that writing tests makes you faster.
However there are test patterns that most likely will slow you down and you need to be ready to handle them. 

Writing tests for a simple logic with a simple input is a breathe. Take for example a test for a simple calculator class. 
Two numeric inputs and we are done: 

:include-file: com/example/portfolio/SimpleCalculatorTest.java {title: "SimpleCalculatorTest.java"}

Unfortunately real apps require real domain, and real domain is rarely represented by a couple of numbers.

Let's take an example of a fake `ProfitCalculator`. 

Business explained to us how to calculate a potential profit based on a list of `Transactions`. Let's implement
and test this crucial piece of our software. 

`Transaction` is a simple data class with a few fields (number of fields is reduced for simplicity).

:include-file: com/example/portfolio/Transaction.java {title: "Transaction.java", readMore: true, readMoreVisibleLines: 20}

Here is the first `ProfitCalculator` implementation in all its glory, ready to be tested.

:include-file: com/example/portfolio/ProfitCalculator.java {title: "ProfitCalculator.java"}

And here is the first, boilerplate version of our test.

:include-file: com/example/portfolio/ProfitCalculatorWithoutEncapsulationTest.java {title: "ProfitCalculatorTest.java"}

You may think "test is not that bad", only a few setters to call, not a big deal. 
The number of setters will only grow as you dig more into requirements. 
The number of transactions will grow as we deal with more complex scenarios. 
But that's not all the reasons to be worried.

Another reason to worry is the way `Transaction` instance is being constructed. Right now it is a bunch of setters.
Tomorrow it becomes a builder pattern, or straight constructor initialization.  

From `ProfitCalculator` perspective the way `Transaction` instances are created is irrelevant as it doesn't affect the calculation logic.

`Transaction` is going to be used in other places and not just `ProfitCalculator`. There will be other tests creating `Transaction` instances.
The more tests you have, the more reluctant you will be to refactor anything about `Transaction` definition.

# Test Input Initial Encapsulation

Let's extract `Transaction` creation code into `TestTransactions`

:include-file: com/example/portfolio/ProfitCalculatorWithBasicEncapsulationTest.java {title: "ProfitCalculatorTest.java with initial encapsulation"}

:include-java: com/example/portfolio/TestTransactions.java {title: "TestTransactions.createTransaction", entry: "createTransaction"}

Less boilerplate and a good start.

Question: Can you still spot the problem?

--TODO-- add reveal plugin to hide the answer and show it only on a click

As we add more properties to `Transaction` class, some of the tests may need to set extra transaction properties. 
Additionally some of the properties will be completely irrelevant to the business logic under test, yet you will be forced to set them anyway.

# Test Input Encapsulation With Webtau Table Data   

I was dealing with test problems like above for awhile and eventually came up with a solution that fits my needs.
I hope it will fit your needs as well. The solution is to use flexible data structure like [Webtau TableData](https://twosigma.github.io/webtau/guide/reference/table-data)

:include-file: com/example/portfolio/ProfitCalculatorWithTableDataTest.java {title: "ProfitCalculatorTest.java with TableData encapsulation", commentsType: "inline"}

Note: [Webtau](https://github.com/twosigma/webtau) open source project started as my answer to common testing problems I encountered. 
While I am not working at [Two Sigma](https://www.twosigma.com/) anymore I am still contributing and using it on a regular basis.     

:include-java: com/example/portfolio/TestTransactions.java {title: "TestTransactions.createTransactions", commentsType: "inline", entries: ["createTransactions", "genTransactionId"]}

Notice how `createTransactions` defaults values when they are not present?
As a result of this, tests that don't need say `id` or `lot` are free to ignore them.

:include-java: com/example/portfolio/ProfitCalculatorWithTableDataNoIdNoLotTest.java {title: "ignored id,lot Transaction properties", commentsType: "inline", entry: "profitShouldBeZeroIfNoLotsSet"}

If later a new required property will be added to `Transaction`, you won't have to change all your existing tests. 
Instead you will update `createTransaction` with a new default value. 
The only time you will have to change the tests to use the new property is if the new property is affecting the logic under test.

# Try it out

:include-file: webtau-core-java-dep.xml {title: "Maven dependency"}

* [Webtau user guide](https://twosigma.github.io/webtau/guide/)
* [Webtau github](https://github.com/twosigma/webtau)

# Summary

* Tests input preparation can be verbose and brittle.
* Identify core domain objects and provide a convenient way to create them.
* Use as little data as necessary to prove logic correctness. Default the rest. 