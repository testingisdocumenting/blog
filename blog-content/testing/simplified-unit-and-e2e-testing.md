---
date: 2023-02-22
title: Simplified Unit And E2E testing with WebTau
summary: Learn how to simplify your data input and output preparation for your business and infrastructure logic.
---

# Business Logic Data Preparation

Business logic testing often involves creating data input and validating logic output. I want to show you how you can simplify complex domain data creation using `WebTau` API.

Throughout this article we will be building a Game Store server and its various business rules. 

:include-meta: {presentationParagraph: "default"}

Let's start with implementation and testing of games checkout discount logic.

:include-meta: {presentationParagraph: ""}

Business department came up with the idea that we should encourage people to buy different game genres. So discount depends on:

* how many distinct type of games you buy
* each distinct game type will yield 10% discount, as long as games cost more than $15
* no more than three distinct games types will be counted

As an example, given the following checkout cart, a user will get a 20% discount:
```table {title: "games discount example&nbsp;&nbsp;", highlightRow: [1, 2]}
id, type, price
g1, RPG, 10
g2, RPG, 50
g3, Action, 70
g4, Puzzle, 10
```

Let's create a discount calculator to implement the business rule:

:include-java: org/testingisdocumenting/examples/gamestore/server/checkout/DiscountCalculator.java {
  title: "discount calculator",
  entry: "DiscountCalculator",
  commentsType: "inline"
}

To test that code we will need to create a sufficient number of `Game` instances to make sure we cover:

:include-meta: {presentationBulletListType: "RevealBoxes"}

* Count only one game per type
* Count only games with *high enough price*
* Should count no more than *three distinct types*

Creating many `Game` instances is a lot of boilerplate:

:include-java: org/testingisdocumenting/examples/gamestore/server/checkout/DiscountCalculatorLongTest.java {
  title: "boilerplate",
  entry: "longObjectsCreation",
  bodyOnly: true
}

When we deal with boilerplate multiple things can happen:
* copy pasting and not updating values
* not creating enough instances

To streamline the process of data creation, `WebTau` provides `table` API:

:include-java: org/testingisdocumenting/examples/gamestore/server/checkout/DiscountCalculatorTest.java {
  title: "discount calculator test",
  entry: "DiscountCalculatorTest",
  commentsType: "inline",
  exclude: "doc-exclude"
}

Let's take a look at implementation of `createGames`:

:include-java: org/testingisdocumenting/examples/gamestore/GamesData.java {
  title: "create games",
  entry: ["createGames", "createGame"],
  entrySeparator: "",
  commentsType: "inline"
}

By using `table` and a domain specific methods like `createGames` you make your tests easier to write, read and maintain.

# Business Logic Data Validation

We just simplified our data creation process. Now let's see how we can simplify data validation one.

:include-meta: {presentationParagraph: "default"}

This time we will test a *recommendation engine*. One of its role is to provide a new game to play based on the games you already played.
For now the engine will recommend the least played game in the least played type of games.

Here is the test:

:include-meta: {presentationParagraph: ""}

:include-java: org/testingisdocumenting/examples/gamestore/server/games/GameRecommendationEngineTest.java {
  title: "recommendation logic test",
  entry: "GameRecommendationEngineTest",
  commentsType: "inline",
  exclude: "doc-exclude"
}

Our current implementation has a bug, and uses incorrect `type` for the returned game.
When mismatch occurs, WebTau output provides all the information you need to understand the issue and highlights important parts of the output:

:include-cli-output: doc-artifacts/recommendNextGameOutput.txt {
  title: "console output", 
  revealLineStop: [0, 3],
  highlight: '**"RPG"**'
}

`WebTau` did good with an object validation with a nested object. Let's take a look at how it fares with a list of objects.

:include-meta: {presentationParagraph: "default"}

Games Library class has a method to find top N games. It returns a list of games. Let's test it.

:include-meta: {presentationParagraph: ""}

:include-java: org/testingisdocumenting/examples/gamestore/server/games/GamesLibraryTest.java {
  title: "top two games test",
  entry: "GamesLibraryTest",
  commentsType: "inline",
  exclude: "doc-exclude"
}

:include-cli-output: doc-artifacts/findTopTwoGamesOutput.txt {
  title: "console output",
  revealLineStop: [0, 5, 8, 12],
  highlight: '**null**'
}

:include-meta: {presentationParagraph: "default"}

Using `trace` method is optional and can be helpful when dealing with complex data to see what's going on.
When failure happens, `WebTau` displays actual complex data and highlights values within that you need to take a look at.

We did validation using `TableData`.\
Let's take a look at how a property by property validation would look like.

:include-meta: {presentationParagraph: ""}

:include-file: org/testingisdocumenting/examples/gamestore/server/longversion/GamesLibraryTest.java {
  title: "property by property validation",
  surroundedBy: "games-validation",
  stickySlide: "top"
}

To contrast it with `table` approach:

:include-file: org/testingisdocumenting/examples/gamestore/server/games/GamesLibraryTest.java {
  title: "table data validation",
  startLine: "TableData expected", 
  endLine: "));",
  commentsType: "remove"
}

When dealing with validation boilerplate code you can face even worse problems than with data preparation:
* ignore certain fields validation
* copy and paste wrong values and they match incorrect logic


# REST API And Database

We tested some business logic and now lets move on to REST API testing. Game server exposes `/api/game` end-point to list available games. 
To test it, we will manually insert records into `GAME` database table and then use `http.get` request to validate a piece of data.

:include-json: doc-artifacts/http-fetch-games-list/response.json {title: "REST API response"}

:include-java: org/testingisdocumenting/examples/gamestore/server/games/GameRestTest.java {
  title: "create DB entries and query with REST API",
  entry: "GameRestTest",
  commentsType: "inline",
  exclude: "doc-exclude",
  stickySlide: "left 35%"
}

:include-cli-output: doc-artifacts/fetchListOfGamesOutput.txt {
  title: "console output",
  highlight: ["created", "inserted", "tracing", "executing", "equals 200", "response", "executed"]
}

# Reporting 

As you may have noticed, WebTau produces a lot of useful output to the console.
In addition, it also generates HTML rich self-contained report. A single HTML file anyone can slack or email to your colleagues. 
Continuous Integration can send it to a chat or put under shared file system, and it will just work.
On top of it, the report supports permalink navigation, so you can have a handy link to a specific portion of your test suite.

:include-image: img/game-report-failed-summary.png {pixelRatio: 2, border: true}

Even when your test passes, it still has plenty of information you can find useful. Here is an example of HTTP response fields your test validated.

1. JSON fields your test touched are marked
2. It marks both bulk and precise field assertion

```image img/game-report-http-details.png {pixelRatio: 2, border: true}
942,294
922,350
```

# There Is More

There are so many things WebTau can do and I want to share them with you very much. But I want to keep this article short.
Head over to [WebTau GitHub](https://github.com/testingisdocumenting/webtau) to learn more about

:include-meta: {presentationBulletListType: "Grid"}

* REPL mode
* HTTP Response Coverage
* *Open API* Integration
* *Performance* Metrics
* Browser UI testing
* GraphQL 
* CLI testing
* Fake and Proxy Servers
* Personas
* Test Containers Integration

Don't be afraid by the sheer amount of things the tool can do. It is all modular, and you can use only what you need. 

# GitHub And Star WebTau

If you find it interesting, go to [WebTau GitHub](https://github.com/testingisdocumenting/webtau) page and star the project!

There is also a link to [Discord Server](https://discord.gg/aEHbzXTX6N) where you can ask any questions or report a bug.



