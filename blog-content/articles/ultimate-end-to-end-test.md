---
date: 2020-06-07
summary: End to end of REST API, Command Line Interface, Web User Interface and Database
---

# WebTau

> WebTau stands for Web Tests Automation. An open source tool, API, and a framework designed to
> simplify end-to-end testing on multiple levels.

:include-meta: {presentationBulletListType: "RevealBoxes"}

* REST/GraphQL API
* Web UI
* Command Line 
* Database 

# Game Store

:include-image: game-store-main-page.png {fit: true}

:include-cli-command: gs-admin list {stickySlide: "top 20%"}

:include-cli-output: game-store-cli-output/out.txt {title: "admin tool cli output"}


:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "GraphQL query", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "left"
}

:include-json: game-store-graphql-game/response.json { 
   title: "response"
} 

# WebTau Introduction

:include-file: basic-http-invocation/request.fullurl.txt {
    title: "Target API",
    stickySlide: "top 20%"
}

:include-json: basic-http-invocation/response.json {
    title: "Response",
    pathsFile: "basic-http-invocation/paths.json",
    stickySlide: "left"
}

:include-file: basicScenarios/httpBasics.groovy {
    title: "httpBasics.groovy",
    startLine: "basic-http-invocation",
    endLine: "basic-http-invocation-end",
    excludeStartEnd: true,
    excludeRegexp: "http.doc",
    commentsType: "inline"
}

:include-cli-command: webtau httpBasics.groovy {stickySlide: "top 15%"}

:include-cli-output: webtau-cli-http-basics/out.txt {
    title: "Execution Output",
    startLine: "basic http invocation",
    endLine: "executed HTTP GET",
    highlight: ["equals \"delectus", "equals 200", "__\"delectus"]
} 

# Basic Configuration

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

:include-cli-command: webtau scenarios/* --env=dev --url=http://override-value --browserId=chrome {
  paramsToHighlight: "dev"
}


# Game Store REST API

Let's combine `http.get` and `http.post` together into a full test

:include-file: game-store-rest-post-game/request.fullurl.txt {
    title: "Target API",
    stickySlide: "top 20%"
}

:include-json: game-store-rest-get-game/response.json { 
  title: "Game POST Request",
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

:include-file: scenarios/gamestore/postGetStreamlined.groovy {
    title: "Re-use payload data",
    startLine: "register-new-game", endLine: "scenario-end", excludeStartEnd: true,
    excludeRegexp: "http.doc",
    commentsType: "inline"
}

# HTTP Explicit Authentication

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "auth required end-point", 
  startLine: "without-auth", endLine: "without-auth-end", excludeStartEnd: true,
  commentsType: "inline"
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "explicit auth", 
  startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true,
  commentsType: "inline"
}

# Personas

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

# HTTP Implicit Authentication

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth PUT", 
  startLine: "with-personas-put", endLine: "with-personas-put-end", excludeStartEnd: true,
  commentsType: "inline"
}

:include-cli-output: http-persona-auth-ran-with-repl/out.txt {
  title: "webtau output",
  startLine: "running:", endLine: "Bob",
  highlight: ["Alice", "__\"uid", "__\"RPG", "Bob"]
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth GET", 
  startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true,
  commentsType: "inline"
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
  startLine: "personas-auth-config", endLine: "personas-auth-config-end", excludeStartEnd: true,
  excludeRegexp: "browserPageNavigationHandler",
  commentsType: "inline",
  stickySlide: "left 40%"
}

:include-file: auth/HttpHeaderProvider.groovy {
  title: "HTTP Header Provider",
  commentsType: "inline"
}

# GraphQL

:include-file: resources/schema.graphqls {title: "GraphQL schema"} 

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

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "params query definition", 
   startLine: "query-params-definition-start", endLine: "query-params-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "query parameters", 
   startLine: "query-game-param-start", endLine: "query-game-param-end", excludeStartEnd: true,
   highlight: "id:",
   excludeRegexp: "http.doc"
}

# GraphQL Persona Auth

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "mutation to change preferences", 
   startLine: "mutation-definition-start", endLine: "mutation-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

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

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "explicit authentication", 
   startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true
}

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

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "query to fetch preferences", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "left"
}

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "Persona authentication", 
   startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true,
   commentsType: "inline"  
}

# Personas Re-use

:include-file: basicScenarios/personaDemo.groovy {
  title: "in place defined personas",
  excludeRegexp: ["// persona-demo", "package"],
  commentsType: "inline"
}

:include-file: personas/Personas.groovy {
  title: "personas/Personas.groovy",
  commentsType: "inline",
  stickySlide: "top 30%"
}

:include-file: basicScenarios/personaReUseDemo.groovy {
  title: "in place defined personas",
  excludeRegexp: "// persona-demo",
  commentsType: "inline"
}

# Browser

Let's start with a simple example of openning google site and searching for a word `test`.

:include-image: browser-basic.png

:include-file: basicScenarios/browserBasics.groovy {
  title: "basic browser interactions",
  startLine: "browser-basics", endLine: "browser-basics-end", excludeStartEnd: true,
  excludeRegexp: "browser.doc",
  commentsType: "inline",
  stickySlide: "left 40%"
}

:include-cli-output: webtau-browser-basics/out.txt {
  title: "webtau output",
  startLine: "basic browser interaction",
  endLine: "expected pattern: speedtest.net",
  highlight: ["setting value", "to be greater than or equal", "clicking", "expecting"]
}

# Game Store UI

Let's test landing web page

:include-image: landing-page.png { fit: true }

:include-file: scenarios/gamestore/browserLanding.groovy {
  title: "Browser Landing test", 
  excludeRegexp: ["browser.doc", "hide", ".capture"],
  commentsType: "inline"
}

:include-image: landing-page-reduced.png { fit: true }

let's test the filters

:include-file: scenarios/gamestore/browserLandingFilterNoPage.groovy { 
  title: "filtering games",
  excludeRegexp: ["hide", "browser.doc"],
  commentsType: "inline"
}

# UI Page Object

:include-file: basicScenarios/browserBasics.groovy {
  title: "exposed page details",
  startLine: "impl-details", endLine: "impl-details-end", excludeStartEnd: true,
  commentsType: "inline"
}

let's move page elements definitions to page objects

:include-file: pages/LandingPage.groovy {
  title: "page object",
  commentsType: "inline",
  stickySlide: "left 40%"
}

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

:include-file: basicScenarios/waitToDemo.groovy {
  title: "waitTo is NOT browser specific",
  startLine: "wait-to-demo", endLine: "wait-to-demo-end", excludeStartEnd: true, 
  commentsType: "inline"
}

:include-meta: {presentationParagraph: ""}

> Testing Is Documenting

# Browser Auth

:include-image: user-preferences-screen.png { caption: "user preferences", fit: true }

:include-image: login-screen.png { caption: "login is required", fit: true }


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

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Persona Login",
    startLine: "persona-login-start", endLine: "persona-login-end", excludeStartEnd: true,
    excludeRegexp: "browser.doc",
    commentsType: "inline"
}

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Update Through UI",
    startLine: "change-through-ui-start", endLine: "change-through-ui-end", excludeStartEnd: true,
    commentsType: "inline"
}

:include-file: auth/BrowserOpenHandler.groovy {
  title: "Browser Navigation Handler", 
  commentsType: "inline",
  stickySlide: "left 50%"
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - personas setup", 
   startLine: "personas-auth-config", endLine: "personas-auth-config-end", excludeStartEnd: true,
   excludeRegexp: "httpHeaderProvider",
   commentsType: "inline"
}

Question: How do we maintain different localStorage for different Personas?

:include-meta: {presentationParagraph: "default", presentationBulletListType: "RevealBoxes"}

`Webtau` maintains a browser per `persona`. In the examples above we have a total of two browsers:

:include-meta: {presentationParagraph: "clear"}
 
* Default browser  
* Alice's browser

:include-meta: {presentationParagraph: "", stickySlide: "clear"}


# Browser WebSocket

double browsers persona demo

:include-image: admin-send-message.png { fit: true, stickySlide: "left" }

:include-image: landing-received-message.png { fit: true }

:include-file: pages/MaintenancePage.groovy {
    title: "page object"
}

:include-file: scenarios/gamestore/browserAdminMessage.groovy {
  title: "Leveraging  multiple browsers", 
  excludeRegexp: ["browser.doc", "hide"],
  commentsType: "inline"
}

# Database

A final piece to explore is DB. 
Most of the time you should avoid checking or manipulating DB directly from the test and rely on higher 
order interfaces. The interfaces your users will use.

Not all the operations can be done through REST or UI. For those cases using DB is the answer. 

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

Let's start with a run of `ls -l` command 

:include-file: scenarios/cliBasics.groovy {
    title: "example of command line run",
    startLine: "cli-basics", endLine: "cli-basics-end", excludeStartEnd: true,
    highlight: "should",
    stickySlide: "left"
}

:include-cli-output: cli-basics-ls/out.txt {highlightPath: "cli-basics-ls/out.matched.txt"}

If you have a command line tool you plan on running multiple times, we can extract its definition.

:include-cli-output: list-games-cli/out.txt {title: "Admin tool output"} 

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

a bit about table data,

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

:include-file: com/example/junit5/DynamicTestsGroovyTest.groovy {
  title: "Table Data as Test Factory (Groovy)",
  commentsType: "inline"
}

:include-file: com/example/junit5/DynamicTestsJavaTest.java {
  title: "Table Data as Test Factory (Java)",
  commentsType: "inline"
}

# Can I Use The Rest With JUnit? 

:include-file: com/example/junit5/PostGetJavaTest.java {
  title: "HTTP Post Get (Java)",
  commentsType: "inline",
  stickySlide: "left 70%"
}

:include-file: src/test/resources/webtau.properties {
  title: "src/test/resources/webtau.properties"
}

# Reporting 

:include-image: game-store-report-summary.png {annotationsPath: "game-store-report-summary.json"}

:include-image: game-store-skipped-operations.png {annotationsPath: "game-store-skipped-operations.json"}

:include-image: game-store-overall-http-performance.png {annotationsPath: "game-store-overall-http-performance.json"}

:include-image: game-store-operations-http-performance.png {annotationsPath: "game-store-operations-http-performance.json"}

:include-image: game-store-report-test-summary.png {annotationsPath: "game-store-report-test-summary.json"} 

:include-image: game-store-report-http-call.png {annotationsPath: "game-store-report-http-call.json"} 

:include-image: game-store-report-cli-call.png {annotationsPath: "game-store-report-cli-call.json"}

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

:include-cli-output: browser-basic-repl/out.txt {
  revealLineStop: [0, 3, 4, 7, 8, 14, 15, 18, 19, 23, 24]
}

# REPL DB

:include-cli-output: game-store-db-query/out.txt {title: "DB REPL", revealLineStop: [0]}

# REPL Test Selection  

:include-cli-command: {
    commandFile: "test-files-list-repl/command.txt", 
    stickySlide: "top 30%"
}

:include-cli-output: test-files-list-repl/out.txt {
    title: "Test files listing", revealLineStop: [0],
    stickySlide: "left"
}

:include-cli-output: scenarios-list-repl/out.txt {
    title: "Test file selection", 
    revealLineStop: [0]
}

:include-cli-output: http-persona-auth-ran-with-repl/out.txt {
  title: "Test ran, context is preserved",
  revealLineStop: [0],
  endLine: "Bob",
  excludeRegexp: ["before first test"]
}

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

:include-meta: {presentationBulletListType: "RevealBoxes"}

* Example snippets
* OpenAPI / GraphQL definitions
* CLI Outputs
* HTTP responses
* Screenshots 

:include-meta: {stickySlide: "clear"}

> The major part of the presented content was generated by running tests 

# Capturing Test Artifacts

:include-file: scenarios/gamestore/database.groovy {
    title: "HTTP doc capture",
    startLine: "db-insert", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "doc.capture",
    excludeRegexp: ["//"]
}

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

:include-file: admin-ui-filter.json {
  title: "admin-ui-filter.json"
}

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
