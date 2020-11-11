---
date: 2020-10-30
summary: Test across multiple layers like REST/GraphQL API, Web UI, CLI, Database. Use REPL to tighten feedback loop. 
Capture test artifacts to help with documentation.
---

# WebTau

> WebTau stands for Web Tests Automation. An open source tool, API, and a framework designed to
> simplify testing on multiple levels.

What do I mean by multiple levels? Apps I develop often have these layers:

:include-meta: {presentationBulletListType: "RevealBoxes"}

* REST/GraphQL API
* Web UI
* Command Line 
* Database 

When I write end-to-end tests I test on one layer, and validate on the other. 
I write a test for a command line tool and validate that CLI updates REST resource.
I write a test for Web UI and use GraphQL API to set up the initial data. 

# Testing Game Store 

We are going to test Game Store product. It has Web UI where you can see what games are available.

:include-image: game-store-main-page.png {fit: true}

It has CLI tool to help admins to manage the product. 

:include-cli-command: gs-admin list {stickySlide: "top 20%"}

:include-cli-output: game-store-cli-output/out.txt {title: "admin tool cli output"}

It has GraphQL and REST API to manage data.

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "GraphQL query", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "left"
}

:include-json: game-store-graphql-game/response.json { 
   title: "response"
}

Game Store product puts all the data into a database.
Below we are going to see how webtau lets you seamlessly test on different layers and use other layers to help with data validation.

# WebTau Introduction

Before writing a test for Game Store, we are going to use [JSON Placeholder](https://jsonplaceholder.typicode.com/)
website to demo basic `webtau` parts.  

Our goal is to get and validate a response from this end point:
:include-file: basic-http-invocation/request.fullurl.txt {
    title: "Target API",
    stickySlide: "top 20%"
}

:include-json: basic-http-invocation/response.json {
    title: "Response",
    pathsFile: "basic-http-invocation/paths.json",
    stickySlide: "left"
}

In webtau use `scenario` to define a test. Let's create a file and define our first scenario: 

:include-file: basicScenarios/httpBasics.groovy {
    title: "httpBasics.groovy",
    startLine: "basic-http-invocation",
    endLine: "basic-http-invocation-end",
    excludeStartEnd: true,
    excludeRegexp: "http.doc",
    commentsType: "inline"
}

Note: Most of the time we are going to use webtau as both API and command line runner. You can use `webtau` with 
`JUnit5` and `JUnit4` and pure Java if you prefer. 

To run a test, assuming `webtau` is in `PATH`:

:include-cli-command: webtau httpBasics.groovy {stickySlide: "top 15%"}

:include-cli-output: webtau-cli-http-basics/out.txt {
    title: "Execution Output",
    startLine: "basic http invocation",
    endLine: "executed HTTP GET",
    highlight: ["equals \"delectus", "equals 200", "__\"delectus"]
} 

Webtau captures everything that happens in a test:
* commands there were ran
* assertions that were made (both passed and failed)
* values that were checked (`__` in the console output)

All the captured data is available after test run inside a rich html report that we are going to look at later. 

# Basic Configuration

To avoid writing full url in our tests let's define a base url for our service, UI and define an additional environment:

:include-file: basicScenarios/webtau.cfg.groovy {
  title: "webtau.cfg.groovy", 
  commentsType: "inline",
  excludeRegexp: "package",
  stickySlide: "left"
}

:include-file: basicScenarios/configAccess.groovy { 
  title: "config access",
  commentsType: "inline",
  stickySlide: "top 70"
}

You can override config values using CLI params. Use `--env=<value>` to select an environment, i.e. a different set of config values:  

:include-cli-command: webtau scenarios/* --env=dev --url=http://override-value --browserId=chrome {
  paramsToHighlight: "dev"
}

# Game Store REST API

Let's test Game Store API to register and check a game by id.

:include-file: game-store-rest-post-game/request.fullurl.txt {
    title: "End-point to create a game",
    stickySlide: "top 20%"
}

:include-json: game-store-rest-get-game/response.json { 
  title: "Register a new game and check it after",
  stickySlide: "left 30%"
}

:include-file: scenarios/gamestore/postGet.groovy {
    title: "POST and GET",
    startLine: "register-new-game", endLine: "scenario-end", excludeStartEnd: true,
    excludeRegexp: "http.doc",
    revealLineStop: ["priceUsd"],
    commentsType: "inline",
    stickySlide: "left"
}

:include-json: game-store-rest-get-game/response.json { 
  title: "Game POST Response",
  pathsFile: "game-store-rest-get-game/paths.json"
}

Did you notice that request to POST and response from GET looks the same? Let's extract payload into a variable and 
re-use it for both request payload and response validation. 
This time we also not going to pass `id` to the request and instead rely on the server to generate a new ID.

:include-file: scenarios/gamestore/postGetStreamlined.groovy {
    title: "Re-use payload data",
    excludeRegexp: "http.doc",
    commentsType: "inline"
}

# HTTP Explicit Auth

End-point `/api/game` was not requiring authentication. It was done for example’s sake to make it simpler to approach.
Now let's test an end-point that requires auth. 

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "auth required end-point", 
  startLine: "without-auth", endLine: "without-auth-end", excludeStartEnd: true,
  commentsType: "inline"
}

To authenticate a user our system relies on `Bearer` token. Let's generate a token and explicitly pass it
via `header` parameter.

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "explicit auth", 
  startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true,
  excludeRegexp: "http.doc",
  commentsType: "inline",
  stickySlide: "left"
}

:include-json: game-store-put-user-preferences/response.json {
  title: "User preferences response",
  pathsFile: "game-store-put-user-preferences/paths.json"
}

# Personas

Webtau provides a way to implement implicit authentication for your requests. 
Before we get there, we need to cover `Persona` concept. 

`Persona` sets the context for config values and code execution.

:include-file: basicScenarios/webtau.persona.cfg.groovy {
  title: "webtau.cfg.groovy",
  excludeRegexp: "package",
  commentsType: "inline",
  stickySlide: "left 33%"
}

:include-file: basicScenarios/personaDemo.groovy {
    title: "personaDemo.groovy",
    startLine: "persona-demo", endLine: "persona-demo-end", excludeStartEnd: true, 
    commentsType: "inline",
    stickySlide: "left" 
}

:include-cli-output: webtau-persona-basics/out.txt { 
  title: "output",
  startLine: "persona demo", endLine: "uid-bob",
  revealLineStop: [0, 1, 2]    
}

# HTTP Implicit Auth

Instead of explicitly passing `header` to each `http` call we will execute calls in the context of a persona.

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth PUT", 
  startLine: "with-personas-put", endLine: "with-personas-put-end", excludeStartEnd: true,
  commentsType: "inline"
}

Console output (and report that we are going to look at later) captures what steps were executed in what context.
 
:include-cli-output: http-persona-auth-ran-with-repl/out.txt {
  title: "webtau output",
  startLine: "running:", endLine: "Bob",
  highlight: ["Alice", "__\"uid", "__\"RPG", "Bob"]
}

To make sure our `PUT` worked as intended we are going to `GET` user preferences in different Persona contexts.

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth GET", 
  startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true,
  excludeRegexp: "http.doc",
  commentsType: "inline"
}

How does it work behind the covers? `Webtau` allows you to define an implicit `HTTP Header Provider` that
can inject header values into each request.  

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
  startLine: "personas-auth-config", endLine: "personas-auth-config-end", excludeStartEnd: true,
  excludeRegexp: "browserPageNavigationHandler",
  commentsType: "inline",
  stickySlide: "left 40%"
}

Our custom implementation of `HTTP Header Provider` checks `cfg.userId` value and when it is present, 
it will be used to generate `Bearer` token and inject into ongoing request.

Note: `cfg.userId` is only set in the context of `Bob` or `Alice`. Outside persona context `userId` is an empty value.  
 
:include-file: auth/HttpHeaderProvider.groovy {
  title: "HTTP Header Provider",
  commentsType: "inline"
}

# GraphQL

We covered how to use `http.` namespace to test REST based API layer. Let's move to `graphql.` namespace to 
test GraphQL based API.

Game Store provided a few queries and mutations:

:include-file: resources/schema.graphqls {title: "GraphQL schema"} 

To start, we define a query to fetch a game by hardcoding id in the query.

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "query definition", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "left 33%"
}

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "GraphQL query", 
   startLine: "query-game-start", endLine: "query-game-end", excludeStartEnd: true,
   excludeRegexp: "http.doc",
   commentsType: "inline",
   stickySlide: "left"
}

:include-json: game-store-graphql-game/response.json { 
   title: "response",
   pathsFile: "game-store-graphql-game/paths.json"
} 

GraphQL allows to define a query with parameters: 

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "params query definition", 
   startLine: "query-params-definition-start", endLine: "query-params-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

Webtau provides a way to pass parameter values as a `Map`:

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "query parameters", 
   startLine: "query-game-param-start", endLine: "query-game-param-end", excludeStartEnd: true,
   highlight: "id:",
   excludeRegexp: "http.doc"
}

# GraphQL Explicit Auth

As in REST based user preference example above, `updateUserPreferences` mutation requires authentication. 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "mutation to change preferences", 
   startLine: "mutation-definition-start", endLine: "mutation-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

Unlike REST based API, the auth error appears in `errors` field on a response and not via `statusCode`: 

:include-json: graphql-auth-error/response.json { 
  title: "GraphQL error response", 
  pathsFile: "graphql-auth-error/paths.json",
  stickySlide: "left"
} 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "not authenticated error", 
   startLine: "without-auth", endLine: "without-auth-end", excludeStartEnd: true,
   excludeRegexp: ".doc",
   commentsType: "inline"
}

To do auth explicitly, similar to REST API, we can pass `header` as a parameter to `graphql.execute`: 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "explicit authentication", 
   startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true
}

# GraphQL Persona Auth

Persona based authentication for GraphQL works exactly like REST API based one. 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "Persona authentication", 
   startLine: "with-personas-put", endLine: "with-personas-put-end", excludeStartEnd: true,
   commentsType: "inline"
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
  startLine: "personas-auth-config", endLine: "personas-auth-config-end", excludeStartEnd: true,
  excludeRegexp: "browserPageNavigationHandler",
  commentsType: "inline",
  stickySlide: "left 40%"
}

:include-file: auth/HttpHeaderProvider.groovy {
  title: "previously defined HTTP Header Provider",
  commentsType: "inline"  
}

Let's make system reflects the changes performed with mutations 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "query to fetch preferences", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "left"
}

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "Persona authentication", 
   startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true,
   commentsType: "inline"  
}

# Personas Re-use

We started with defining personas in-place within each test file like this:

:include-file: basicScenarios/personaDemo.groovy {
  title: "in place defined personas",
  excludeRegexp: ["// persona-demo", "package"],
  commentsType: "inline"
}

As we start using Personas for multiple tests and multiple files it makes sense to define Personas once and
reference them in tests as needed. 

:include-file: personas/Personas.groovy {
  title: "personas/Personas.groovy",
  commentsType: "inline",
  stickySlide: "top 30%"
}

To use defined personas we leverage Java/Groovy static import:

:include-file: basicScenarios/personaReUseDemo.groovy {
  title: "Persona re-use demo",
  excludeRegexp: "// persona-demo",
  commentsType: "inline"
}

# Browser

We looked at how to test REST and GraphQL based APIs. Now let's move on to testing Web UI.
Before we jump into testing Game Store UI, we will do a classic test of Google Page.

:include-image: browser-basic.png

Our test will enter "test" value into the search box and, wait for the results to show up and pick a result based on 
a regular expression:

:include-file: basicScenarios/browserBasics.groovy {
  title: "basic browser interactions",
  startLine: "browser-basics", endLine: "browser-basics-end", excludeStartEnd: true,
  excludeRegexp: "browser.doc",
  commentsType: "inline",
  stickySlide: "left 40%"
}

As with REST and GraphQL, webtau console output captures all the actions that happen, and how much time each
action took:

:include-cli-output: webtau-browser-basics/out.txt {
  title: "webtau output",
  startLine: "basic browser interaction",
  endLine: "expected pattern: speedtest.net",
  highlight: ["setting value", "to be greater than or equal", "clicking", "expecting"]
}

Note: Both passed and failed assertions are captured. 

# Game Store UI

The first Game Store page we are going to test is a landing page. It shows available games and let you filter 
based on text or price.

:include-image: landing-page.png { fit: true }

We covered how to use `http.` layer to create new games. We can use that to prepare a setup for our UI test:

:include-file: scenarios/gamestore/browserLanding.groovy {
  title: "Browser Landing test", 
  excludeRegexp: ["browser.doc", "hide", ".capture"],
  commentsType: "inline"
}

:include-image: landing-page-reduced.png { fit: true }

Let's test the filters:

:include-file: scenarios/gamestore/browserLandingFilterNoPage.groovy { 
  title: "filtering games",
  startLine: "filter-start", endLine: "filter-end", excludeStartEnd: true,
  excludeRegexp: ["hide", "browser.doc"],
  commentsType: "inline"
}

Note: `setValue` is used for both text input box and check box. Webtau will figure out what actions to perform based on the
underlying form element.

# UI Page Object

If you have experience in writing UI tests, you may have heard about [Page Object pattern](https://martinfowler.com/bliki/PageObject.html).
Basic idea is to separate what user can see or do from technical details of how to simulate the actions or query the values.

If we take a look at the code in our UI test we will see that details of how to locate elements on the UI are exposed.
Any time we change id element or class names, we risk breaking our tests. 

Warning: Tests should not be broken if only implementation details has changed.

:include-file: basicScenarios/browserBasics.groovy {
  title: "exposed page details",
  startLine: "impl-details", endLine: "impl-details-end", excludeStartEnd: true,
  commentsType: "inline"
}

To implement page object in Webtau we will use a regular Java/Groovy/Kotlin class:

:include-file: pages/LandingPage.groovy {
  title: "page object",
  commentsType: "inline",
  stickySlide: "left 40%"
}

Since our page object class is stateless and elements declarations are lazy, we can precreate instances of all 
the page objects in one place:

:include-file: pages/Pages.groovy {
  title: "all pages (similar to Personas)",
  stickySlide: "top 30%",
  commentsType: "inline",
  excludeRegexp: "report"
}

:include-file: scenarios/gamestore/browserLandingFilter.groovy { 
  title: "filtering games",
  commentsType: "inline",
  excludeRegexp: ["browser.doc", "hide", ".capture"]
}

# WaitTo

You saw `waitTo` in a couple of places already. Let's pause on the topic.

:include-meta: {stickySlide: "top 20%"}

Question: Why do we need `waitTo` and can we instead make `should` do the waiting?

:include-meta: {presentationParagraph: "default"}
:include-meta: {stickySlide: "top 20%"}

We could. However, semantics are important. I think it is important to distinct between an immediate result, 
and a result that becomes available over time. 

:include-meta: {stickySlide: "top 30%"}

Are our users going to see the result right away? Is database going to have the result right away? Is command line going 
to print that line right away? 

:include-meta: {stickySlide: "left"}

:include-meta: {presentationParagraph: ""}

Additionally `waitTo` is not specific for `browser.` layer. It can be used to wait on files content, console output, database data, etc. 

:include-file: basicScenarios/waitToDemo.groovy {
  title: "waitTo is NOT browser specific",
  startLine: "wait-to-demo", endLine: "wait-to-demo-end", excludeStartEnd: true, 
  commentsType: "inline"
}

> Testing Is Documenting

Testing is Documenting is my moto. WaitTo semantics allow you to look at the test and get a better understanding of how system behaves.
This is an example of a more subtle, more common definition of how tests can help you to understand the system. 

Later I will show you how to make tests help you with the actual documentation in a more explicit manner.  

# Browser Explicit Auth

Game Store have a page where users can update their user preferences.  

:include-image: user-preferences-screen.png { caption: "user preferences", fit: true }

If we go to that page directly, we will be redirected to the login page instead.

:include-image: login-screen.png { caption: "login is required", fit: true }

In order to test user preferences page, we have to choices:
* handle login explicitly 
* handle login implicitly 

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Explicit Login",
    startLine: "explicit-login-start", endLine: "explicit-login-end", excludeStartEnd: true,
    commentsType: "inline",
    excludeRegexp: "browser.doc",
    stickySlide: "left"
}

:include-file: pages/UserPreferencesPage.groovy {
    title: "user preferences page object",
    commentsType: "inline",
    stickySlide: "temp"
}

:include-file: pages/LoginPage.groovy {
    title: "login page object",
    commentsType: "inline"
}

Explicit login approach works well if we go the page once, and we know that we haven't performed login yet.
If on the other hand we need to get to that page as part of other scenario, we may face the case where we have already
logged-in, and the code that assumed logic redirection will fail.  

To fix that we will need to add additional if-else logic that may complicate things a bit.

Alternatively we can use an implicit login with Personas.

# Browser Implicit Auth

Browser Implicit Auth in also Persona based. 

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Persona Login",
    startLine: "persona-login-start", endLine: "persona-login-end", excludeStartEnd: true,
    excludeRegexp: "browser.doc",
    commentsType: "inline"
}

Let's now update preferences through UI and validate them using REST API:

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Update Through UI",
    startLine: "change-through-ui-start", endLine: "change-through-ui-end", excludeStartEnd: true,
    commentsType: "inline"
}

To make implicit auth work we need to implement `browserPageNavigationHandler`. 
A handler that will be called for each page open event.

:include-file: auth/BrowserOpenHandler.groovy {
  title: "Browser Navigation Handler", 
  commentsType: "inline",
  stickySlide: "left 50%"
}

Note: Game Store UI code uses local storage to manage auth token. In your app it could be session storage or cookies.  

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - personas setup", 
   startLine: "personas-auth-config", endLine: "personas-auth-config-end", excludeStartEnd: true,
   excludeRegexp: "httpHeaderProvider",
   commentsType: "inline"
}

Question: How do we maintain different localStorage for different Personas?

:include-meta: {presentationParagraph: "default", presentationBulletListType: "RevealBoxes"}

`Webtau` maintains a browser per `persona`. In the examples above we have a total of two browsers during test run:

:include-meta: {presentationParagraph: "clear"}
 
* Default browser  
* Alice's browser

:include-meta: {presentationParagraph: "", stickySlide: "clear"}

# Browser WebSocket

Now that we know how to use Persona to manage multiple browsers, 
we can write a test for the last part of Game Store UI - admin page to send messages to visitors.

Admin page allows to send a message to all visitors via WebSocket. 

:include-image: admin-send-message.png { fit: true, stickySlide: "left" }

:include-image: landing-received-message.png { fit: true }

In order to test this we will need two browsers: one to send a message and another to receive a websocket event. 

Let's start with defining a page object for the new page:

:include-file: pages/MaintenancePage.groovy {
    title: "page object"
}

:include-file: scenarios/gamestore/browserAdminMessage.groovy {
  title: "Leveraging  multiple browsers", 
  excludeRegexp: ["browser.doc", "hide"],
  commentsType: "inline"
}

# Database

Another layer to explore is Database. Database layer can be used to prepare test data. It can also be used to validate 
data persistence after REST/GraphQL API calls.

Warning: Database layer is often considered to be implementation details, and most of your tests should not use it directly.
However, it can be useful to validate that REST POST method did modify data in the database and didn't persist data in cache only.    

:include-file: scenarios/gamestore/database.groovy {
    title: "Database query",
    startLine: "db-list", endLine: "scenario-end", excludeStartEnd: true,
    commentsType: "inline"
}

:include-file: scenarios/gamestore/database.groovy {
    title: "Database insert",
    startLine: "db-insert", endLine: "scenario-end", excludeStartEnd: true,
    excludeRegexp: ["doc.capture"],
    commentsType: "inline",
    stickySlide: "left"
}

:include-json: game-store-list-after-db/response.json { 
    title: "Games list JSON response", 
    pathsFile: "game-store-list-after-db/paths.json",
    collapsedPaths: ["root._links", "root.page"]
} 

# CLI Command

Command line interface is our final layer to test.
Before we test admin app, let's see how `cli.` works on a simple command: 

:include-file: scenarios/cliBasics.groovy {
    title: "example of command line run",
    startLine: "cli-basics", endLine: "cli-basics-end", excludeStartEnd: true,
    highlight: "should",
    stickySlide: "left"
}

:include-cli-output: cli-basics-ls/out.txt {highlightPath: "cli-basics-ls/out.matched.txt"}

Our admin CLI tool works similar `ls` and list games available in the system:

:include-cli-output: list-games-cli/out.txt {title: "Admin tool output"} 

We still develop the tool and it is not available in `PATH` yet, so we will need to use a relative path to run it.
Exposing the path to tests will make our tests brittle in the similar way how exposing UI elements definition does: 
any time we change the tool location we will have to change tests using the tool. 

Similar to UI Page Object idea, we declare CLI tools our tests need access to in a separate file/class:
 
:include-file: clicommands/CliCommands.groovy {
    title: "Extracted CLI command definition", 
    excludeRegexp: ["Server", "backend"],
    commentsType: "inline"
}

:include-file: scenarios/gamestore/adminCliTool.groovy {
    title: "Admin CLI tool test",
    commentsType: "inline",
    excludeRegexp: ["cli.doc", "hide", "stop line"]
}

# TableData

We have seen `TableData` usage when we worked with Database and when we validated complex REST API output.
Let's take a closer look at it.

`TableData` is a core webtau data type. Think of it as a list of maps with extra functionality.
You can assign `TableData` to a variable, pass it around, return from functions and use for assertions. 
Data is available at runtime, and it is not a compile-time construct.  

Here is an example of usage of extra `TableData` features to generate test data:

:include-file: basicScenarios/tableDataDemo.groovy {
  title: "table data quick overview",
  startLine: "table-data-permute", endLine: "table-data-permute-end", excludeStartEnd: true,
  commentsType: "inline"
}

:include-cli-output: webtau-table-basics/out.txt {
  title: "Webtau Output",
  startLine: "table data demo", endLine: "100, 100"
}

# Can I Use TableData With JUnit?

It is worth noting that `TableData` is not specific to Groovy command line runner. 
In fact it can be used for example as JUnit5 test factory:

:include-file: com/example/junit5/DynamicTestsGroovyTest.groovy {
  title: "Table Data as Test Factory (Groovy)",
  commentsType: "inline"
}

Moreover, `TableData` is not specific to Groovy and you can use Java syntax to define an instance:

:include-file: com/example/junit5/DynamicTestsJavaTest.java {
  title: "Table Data as Test Factory (Java)",
  commentsType: "inline"
}

# Can I Use WebTau With JUnit? 

Since we are talking about JUnit and Java, let me show you an example of REST API test using JUnit5 and Java syntax:

:include-file: com/example/junit5/PostGetJavaTest.java {
  title: "HTTP Post Get (Java)",
  commentsType: "inline",
  stickySlide: "left 70%"
}

In case of JUnit runner, webtau reads config values from `webtau.properties` resource file:
 
:include-file: src/test/resources/webtau.properties {
  title: "src/test/resources/webtau.properties"
}

# Reporting 

As I mentioned at the beginning, webtau captures a lot of information. Information is printed to the console, but 
also stored in a rich HTML report.

Generated report is a self-contained HTML file that can be emailed, slacked or copied to a network drive. You don't need
to have a server to look at the report. Open it in a browser, and you will get an interactive mini app. 

:include-image: game-store-report-summary.png {annotationsPath: "game-store-report-summary.json"}

Report Summary page consist of 
1. **Tests Runtime** - shows overall time spend
2. **Tests success ratio** - how many failed vs passed
3. **HTTP Coverage** - how many REST API operations are covered (webtau uses optional Open API spec url to discover all the operations)
4. **Tests run Ratio** - how many skipped vs ran
5. **HTTP calls time** - high level stats on HTTP based APIs

Let's click on (`6`) to get more details about skipped operations:  

:include-image: game-store-skipped-operations.png {annotationsPath: "game-store-skipped-operations.json"}

Report switched from **Tests** view to **HTTP Calls** view (`1`). 
You can see the list of operations we yet to test on the left (`2`). 
Number of items on the left matches the number in the summary card (`3`).
We can use panel at the bottom (`4`) to switch between skipped, total, passed operations or tests. 

Let's navigate from **Summary** tab to **Overal HTTP Performance** tab. The screen shows high level picture of 
how performant our server is under test load. While it is not a true performance test, it may give you an initial glance into
things that may need a closer look.

:include-image: game-store-overall-http-performance.png {annotationsPath: "game-store-overall-http-performance.json"}

**HTTP Operations Performance** tab provides a performance information based on Open API defined operations. 

:include-image: game-store-operations-http-performance.png {annotationsPath: "game-store-operations-http-performance.json"}

Let's get back to **tests** view (`1`) and select a test (`2`).
First screen we are going to see is a test **Summary** (`3`) with high level information on time taken. 

:include-image: game-store-report-test-summary.png {annotationsPath: "game-store-report-test-summary.json"} 

If a test performed any HTTP calls, you going to see the **HTTP Calls** tab (`1`). It contains every HTTP call performed
with captured request, response and assertions (`2` and `3`) information. 

:include-image: game-store-report-http-call.png {annotationsPath: "game-store-report-http-call.json"} 

If a test performed any CLI calls, you going to see the **CLI Calls** tab (`1`). It contains every CLI call performed
with captured command, output and assertions (`2`) information. 

:include-image: game-store-report-cli-call.png {annotationsPath: "game-store-report-cli-call.json"}

Every test also has **Steps** tab (`1`) that contains every step test performed, time it took, and what Persona if any
performed the step (`2` and `3`)

:include-image: game-store-report-steps.png {annotationsPath: "game-store-report-steps.json"}

# REPL

> REPL stands for read-eval-print-loop. It is an interactive computer programming environment that helps with 
prototyping.

You may have already used REPL if you used
* Jupyter Notebook
* iPython
* R
* MatLab  

:include-cli-output: game-store-repl-basic/out.txt {revealLineStop: [0, 1, 2, 3, 4], stickySlide: "left 30%"}

:include-meta: {stickySlide: "top 30%"}

Question: Why are we talking about REPL in the context of end to end testing?

We want to have a fast feedback loop. 
* Browser, Servers, DB setup takes time
* Preserving context
* End to end tests - slow feedback
* REPL - speedup feedback loop
* The faster feedback the happier you are      

# REPL Browser

Writing UI tests can be time-consuming. One of the reason is it takes time to open a browser, 
run your test, add an extra line to the test, repeat. 

Instead we can run webtau in repl mode like this

:include-cli-command: webtau repl

after that we can execute one command at a time, preserving context
 
:include-cli-output: browser-basic-repl/out.txt {
  revealLineStop: [0, 3, 4, 7, 8, 14, 15, 18, 19, 23, 24]
}

# REPL Database

Database layer can be used to semi-automatically validate state of your system during experimentation.
Or it can be used to quickly wipe or setup the data. I personally use it during active development to iterate faster.

:include-cli-output: game-store-db-query/out.txt {title: "DB REPL", revealLineStop: [0]}

# REPL Test Selection  

The true power of webtau REPL mode comes from combining Test runs and experimentation in one go.
We can run webtau in repl mode and at the same time pass the test files we want to work with: 

:include-cli-command: {
    commandFile: "test-files-list-repl/command.txt", 
    stickySlide: "top 30%"
}

:include-cli-output: test-files-list-repl/out.txt {
    title: "Test files listing", revealLineStop: [0],
    stickySlide: "left"
}

Once you list all test files, you can select one by [either index or text](https://testingisdocumenting.org/webtau/REPL/test-runs#test-file-selection)

:include-cli-output: scenarios-list-repl/out.txt {
    title: "Test file selection", 
    revealLineStop: [0]
}

After test file selection, you can run one or more scenarios on demand.

:include-cli-output: http-persona-auth-ran-with-repl/out.txt {
  title: "Test ran, context is preserved",
  revealLineStop: [0],
  endLine: "Bob",
  excludeRegexp: ["before first test"]
}

When I write tests, I keep re-running (`r`) a current test under development, then experiment with a few lines in REPL mode,
do some checks, update the test file and re-run it (`r`). Webtau will reload test file and pickup your changes.

After each test run, context is preserved, browser is open in the last location, DB is up to date, and REST/GraphQL APIs are primed.  

Here is an example of running a `db.` query after a test run:

:include-cli-output: db-query-after-http-repl/out.txt {
  title: "Experiments after a test run",
  revealLineStop: [0]
}

# Testing is Documenting

When we document things, we try them out and make sure they work as intended. 
When we test things, we follow happy paths and edge cases.

Happy path tests often cover what our users will do. Happy path tests also often match the things we document.  

:include-meta: {presentationBulletListType: "HorizontalStripes", presentationParagraph: "default", stickySlide: "top 50%"}

Documentation is hard:

:include-meta: {presentationParagraph: ""}

* `:icon: pen-tool` Mostly manual labor
* `:icon: archive` Often becomes outdated  

:include-meta: {presentationBulletListType: "HorizontalStripes", stickySlide: "clear top 30%"}

Question: How do we make documentation easier to write and maintain?

:include-meta: {presentationParagraph: "", stickySlide: "top 40%"}
  
> Artifacts capture

We already have things in our codebase that we can use to help with our product documentation. Example
code snippets, GraphQL schema files, basic config files.

By writing happy path tests we can add a few more to the list: 
  
:include-meta: {presentationBulletListType: "RevealBoxes"}

* CLI Outputs
* HTTP responses
* Screenshots 

:include-meta: {stickySlide: "clear"}

> The major part of this content was generated by running tests

Webtau console outputs your saw, the REST/GraphQL API response, Game Store and Webtau Report screenshots, all of it was
automatially generated by running tests.  

# Capturing Test Artifacts

When I was showing your snippets of code before I was hidding some code from your.
Let's take a look.

:include-file: scenarios/gamestore/database.groovy {
    title: "HTTP doc capture",
    startLine: "db-insert", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "doc.capture",
    excludeRegexp: ["//"]
}

`http.doc.capture` generates a directory with captured response, request, url, assertions, etc

:include-image: img/ue2e-ide-doc-artifacts.png {
  scaleRatio: 0.5,     
  stickySlide: "left 40%"
}

:include-file: game-store-list-after-db/request.url.txt {
  title: "game-store-list-after-db/request.url.txt",
  stickySlide: "top 20%"
}

:include-file: game-store-list-after-db/response.json {
  title: "game-store-list-after-db/response.json",
  stickySlide: "top 70%"
}

:include-file: game-store-list-after-db/paths.json {
  title: "game-store-list-after-db/paths.json"
}

To capture screenshots webtau has `browser.doc.capture`

:include-file: scenarios/gamestore/browserLandingFilter.groovy { 
  title: "UI doc capture",
  startLine: "filter by price",
  highlight: ["browser.doc"], 
  excludeRegexp: ["hide", "//"],
  stickySlide: "left 50%"
}

:include-image: admin-ui-filter.png {
  annotationsPath: "admin-ui-filter.json",
  title: "admin-ui-filter.json",
  caption: "admin-ui-filter.png",
  stickySlide: "top 40%"
}

In addition to the screenshot `.png` file webtau also captures annotations placement and type:

:include-file: admin-ui-filter.json {
  title: "admin-ui-filter.json"
}

In case of `CLI` `cli.doc.capture` captures the command that was run, its output and assertions performed

:include-file: scenarios/gamestore/adminCliTool.groovy {
  title: "CLI doc capture",
  excludeRegexp: ["//"],
  highlight: ["cli.doc"], 
  stickySlide: "left 40%"
}

:include-file: list-games-cli/command.txt {
  title: "list-games-cli/command.txt",
  stickySlide: "top"
}

:include-cli-output: list-games-cli/out.txt {
  title: "list-games-cli/out.txt"
}

# Example of Generated Documentation

I used the captured information to generate the content of this blog/presentation. At my work I use captured artifacts 
to produce user guides. This approach makes guides to be always up-to date and validated. 

Here is an example of Game Store manual created with captured artifacts and [Znai](https://testingisdocumenting.org/znai/) documentation system.

:include-image: game-store-docs-web-ui.png {scaleRatio: 0.5, border: true}

:include-image: game-store-docs-rest-api.png {scaleRatio: 0.5, border: true}

:include-image: game-store-docs-cli.png {scaleRatio: 0.5, border: true}

# Summary

* We tested Game Store on multiple layers, using one layer to set-up and re-inforce tests on the other layers.
* We used consistent matchers and concepts like `should`, `waitTo`, `Persona` across layers.  
* We saw how each step and assertions is captured by webtau and written to console and rich HTML report.
* We saw how REPL can improve feedback loop and make you write tests faster.
* We saw how writing tests can generate artifacts to help you with writing User facing documentation.

# To Get Started

:include-meta: {presentationParagraph: "default", stickySlide: "top"}

WebTau - GitHub: [https://github.com/testingisdocumenting/webtau](https://github.com/testingisdocumenting/webtau)
\
WebTau - User Guide: [https://testingisdocumenting.org/webtau](https://testingisdocumenting.org/webtau) 

Znai - Github: [https://github.com/testingisdocumenting/znai](https://github.com/testingisdocumenting/znai)
\
Znai - User Guide: [https://testingisdocumenting.org/znai](https://testingisdocumenting.org/znai)
