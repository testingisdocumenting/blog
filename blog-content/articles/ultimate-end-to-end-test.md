---
date: 2020-06-07
summary: End to end of REST API, Command Line Interface, Web User Interface and Database
---

# WebTau

> WebTau stands for Web Tests Automation. An open source tool, and a framework designed to
> simplify end-to-end testing on multiple levels

:include-meta: {presentationBulletListType: "RevealBoxes"}

* REST/GraphQL API
* Web UI
* Command Line 
* Database 

# Game Store

:include-image: game-store-main-page.png {fit: true}

:include-cli-command: gs-admin list

:include-cli-output: game-store-cli-output/out.txt {title: "admin tool cli output"}


# REPL

REPL stands for read-eval-print-loop. It is an interactive computer programming environment that helps with 
prototyping.

You may have already used REPL if you used
* Jupyter Notebook
* iPython
* R
* MatLab  

:include-cli-output: game-store-repl-basic/out.txt {revealLineStop: [0, 1, 2, 3, 4], stickySlide: "top"}

Question: Why are we talking about REPL in the context of end to end testing?

We want to have a fast feedback loop. 
 * Browser, Servers, DB setup takes time
 * Preserving context
 * Interactive test development      

# HTTP GET

Let's start with `http.get` command to query out server health

:include-cli-output: repl-actuator-health/out.txt {revealLineStop: [0]}

Setting base url config `cfg.url`

:include-cli-output: repl-base-url-actuator-health/out.txt {revealLineStop: [0, 1, 2]}

We will list games now by accessing api end-point our game store has exposed.

:include-cli-output: game-store-repl-list-empty-games/out.txt {revealLineStop: [0, 6]}

We going to assert totalElements value by using `body.should`

:include-cli-output: game-store-repl-list-check-total/out.txt {revealLineStop: [0]}

Response tracks asserted values and shows them in the report

:include-cli-output: game-store-repl-list-body-after-check/out.txt {revealLineStop: [0], highlight: "totalElements"}

# HTTP POST

Let's add a new game to game store

:include-cli-output: game-store-repl-post-new-game/out.txt {revealLineStop: [0, 1, "}", "expected"]}

# Combined HTTP Scenario

Let's combine `http.get` and `http.post` together into a full test

:include-file: scenarios/gamestore/postGet.groovy {
    title: "putting together POST and GET",
    startLine: "register-new-game", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "title.should", 
    excludeRegexp: "http.doc",
    revealLineStop: ["priceUsd"],
    stickySlide: "left"}

:include-json: game-store-rest-new-game/response.json { 
    title: "Response",
    pathsFile: "game-store-rest-new-game/paths.json",
    highlight: "title"
}

:include-file: scenarios/gamestore/postGetStreamlined.groovy {
    title: "Refactor to re-use data",
    startLine: "register-new-game", endLine: "scenario-end", excludeStartEnd: true,
    highlight: ["return id", "should == payload"]
}

# Basic Configuration

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
    includeRegexp: ["base url for all http requests", "custom config value"], 
    commentsType: "inline"
}

:include-file: scenarios/configDemo.groovy { title: "config access" }


:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
   startLine: "environments config", endLine: "environments config-end", excludeStartEnd: true,
   highlight: ["dev", "cloud"],
   stickySlide: "top 70%"
}

:include-cli-command: webtau scenarios/* --env=dev

# Personas and Configuration

:include-file: scenarios/personaDemo.groovy {
    title: "persona demo", 
    highlight: ["John {", "Bob {"],
    stickySlide: "left",
    excludeRegexp: "// do-something" 
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy", 
   startLine: "personas config", endLine: "personas config-end", excludeStartEnd: true,
   highlight: ["John", "Bob"]
}

```columns
left:
:include-file: scenarios/personaDemo.groovy {title: "persona context", 
   startLine: "do-something-start", endLine: "do-something-end", excludeStartEnd: true
}

right:
:include-file: ue2e/persona-output.txt {title: "example output"}
```

:include-file: personas/Personas.groovy {
    title: "define all personas in one place",
    stickySlide: "top"
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy {
    title: "single include",
    includeRegexp: "import static personas"
}

# HTTP Authentication

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "auth required end-point", 
  startLine: "without-auth", endLine: "without-auth-end", excludeStartEnd: true,
  highlight: ["403"]
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "explicit auth", 
  startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true,
  highlight: ["generateToken", "http.header"]
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth PUT", 
  startLine: "with-personas-put", endLine: "with-personas-put-end", excludeStartEnd: true,
  highlight: ["John", "Bob"]
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth GET", 
  startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true
}

:include-file: scenarios/gamestore/userPreferencesRest.groovy { 
  title: "Persona auth Admin", 
  startLine: "with-personas-admin-get", endLine: "with-personas-admin-get-end", excludeStartEnd: true,
  highlight: ["uid-john", "uid-bob"]
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - header provider", 
  startLine: "header-provider-start", endLine: "header-provider-end", excludeStartEnd: true,
  stickySlide: "top 20%"
}

:include-file: auth/HttpHeaderProvider.groovy {
  title: "HTTP Header Provider",
  commentsType: "inline", 
  stickySlide: "left 60%"
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - personas setup", 
   startLine: "personas config", endLine: "personas config-end", excludeStartEnd: true,
   highlight: ["uid-john", "uid-bob", "uid-admin"]
}

# GraphQL

:include-file: resources/schema.graphqls {title: "GraphQL schema"} 

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "query definition", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

:include-file: scenarios/gamestore/graphqlQueries.groovy {title: "GraphQL query", 
   startLine: "query-game-start", endLine: "query-game-end", excludeStartEnd: true,
   highlight: "title",
   stickySlide: "left",
   excludeRegexp: "http.doc"
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

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "not authenticated error", 
   startLine: "without-auth", endLine: "without-auth-end", excludeStartEnd: true,
   excludeRegexp: ".doc",
   commentsType: "inline",
   stickySlide: "left"
}

:include-json: graphql-auth-error/response.json { 
    title: "GraphQL error response", 
    pathsFile: "graphql-auth-error/paths.json"
} 

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "explicit authentication", 
   startLine: "with-explicit-auth", endLine: "with-explicit-auth-end", excludeStartEnd: true
}

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "Persona authentication", 
   startLine: "with-personas-put", endLine: "with-personas-put-end", excludeStartEnd: true,
   commentsType: "inline",
   stickySlide: "left"
}

:include-file: auth/HttpHeaderProvider.groovy {
  title: "same HTTP Header Provider",
  commentsType: "inline"  
}

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "query to fetch preferences", 
   startLine: "query-definition-start", endLine: "query-definition-end", excludeStartEnd: true,
   stickySlide: "top"
}

:include-file: scenarios/gamestore/userPreferencesGraphQL.groovy {title: "Persona authentication", 
   startLine: "with-personas-get", endLine: "with-personas-get-end", excludeStartEnd: true
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
    highlight: "cli.command"
}

:include-file: scenarios/gamestore/adminCliTool.groovy {
    title: "Admin CLI tool test",
    revealLineStop: ["}", "Doom"], 
    excludeRegexp: ["cli.doc", "hide", "stop line"]
}

# Browser

Let's start with a simple example of openning google site and searching for a word `test`.

:include-image: browser-basic.png { 
    stickySlide: "left",
    fit: true 
}

:include-cli-output: browser-basic-repl/out.txt {
    revealLineStop: [0, "input", "setValue", 15, 19, 24]
}

Let's test landing web page

:include-image: landing-page.png { fit: true }

:include-file: scenarios/gamestore/browserLanding.groovy {
    title: "Browser Landing test", 
    excludeRegexp: ["browser.doc", "hide"],
    highlight: "waitTo",
    revealLineStop: ['waitTo']
}

:include-image: landing-page-reduced.png { fit: true }

let's test the filters

:include-file: scenarios/gamestore/browserLandingFilterNoPage.groovy { 
    title: "filtering games",
    highlight: ["#filter", "#below60"], 
    excludeRegexp: ["hide", "browser.doc"]
}

let's move page elements definitions to page objects

:include-file: pages/LandingPage.groovy {title: "page object", stickySlide: "left"}

:include-file: pages/Pages.groovy {
    title: "pages",
    stickySlide: "top 20%",
    highlight: "landing",
    excludeRegexp: "report"
}

:include-file: scenarios/gamestore/browserLandingFilter.groovy { 
    title: "filtering games",
    highlight: ["pages.Pages.*", "filterText", "filterBelow60"], 
    excludeRegexp: ["hide", "browser.doc"]
}

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
    stickySlide: "top"
}

:include-file: pages/LoginPage.groovy {
    title: "login page object"
}

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Persona Login",
    startLine: "persona-login-start", endLine: "persona-login-end", excludeStartEnd: true,
    highlight: "put",
    excludeRegexp: "browser.doc"
}

:include-file: scenarios/gamestore/userPreferencesUi.groovy {
    title: "Update Through UI",
    startLine: "change-through-ui-start", endLine: "change-through-ui-end", excludeStartEnd: true,
    highlight: ["Saved", "Bob"]
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - browser navigation handler", 
  startLine: "browser-navigation-handler-start", endLine: "browser-navigation-handler-end", excludeStartEnd: true,
  stickySlide: "top 10%"
}

:include-file: auth/BrowserNavigationHandler.groovy {
  title: "Browser Navigation Handler", 
  commentsType: "inline",
  stickySlide: "left 60%"
}

:include-file: webtau.cfg.groovy {title: "webtau.cfg.groovy - personas setup", 
   startLine: "personas config", endLine: "personas config-end", excludeStartEnd: true,
   highlight: ["uid-john", "uid-bob", "uid-admin"]
}

# Browser WebSocket

double browsers persona demo

:include-image: admin-send-message.png { fit: true, stickySlide: "left" }

:include-image: landing-received-message.png { fit: true }

:include-file: pages/AdminPage.groovy {
    title: "page object"
}

:include-file: scenarios/gamestore/browserAdminMessage.groovy {
    title: "Persona for multiple browsers", 
    excludeRegexp: ["browser.doc", "hide"],
    highlight: ["Admin = ", "Admin {", "waitTo"]
}

# Database

A final piece to explore is DB. 
Most of the time you should avoid checking or manipulating DB directly from the test and rely on higher 
order interfaces. The interfaces your users will use.

Not all the operations can be done through REST or UI. For those cases using DB is the answer. 

:include-cli-output: game-store-db-query/out.txt {title: "DB REPL", revealLineStop: [0]} 

:include-file: scenarios/gamestore/database.groovy {
    title: "Database query",
    startLine: "db-list", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "*ID"
}

:include-file: scenarios/gamestore/database.groovy {
    title: "Database insert",
    startLine: "db-insert", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "_embedded.games",
    excludeRegexp: ["//", "doc.capture"]
}

:include-file: scenarios/gamestore/database.groovy {
    title: "HTTP response Table comparsion",
    startLine: "http-table-comparison", endLine: "marker-end", excludeStartEnd: true,
    stickySlide: "left"
}

:include-json: game-store-list-after-db/response.json { 
    title: "Games list JSON response", 
    pathsFile: "game-store-list-after-db/paths.json",
    collapsedPaths: ["root._links", "root.page"]
} 

# Reporting 

:include-image: game-store-report-summary.png {fit: true}

:include-image: game-store-report-http-call.png {fit: true}

:include-image: game-store-report-cli-call.png {fit: true}

:include-image: game-store-report-steps.png {fit: true}

# Testing is Documenting

When we document things, we try them out and make sure they work as intended. 
When we test things, we follow happy paths and edge cases.

Happy path tests often cover what our users will do. Happy path tests also often match the things we document.  

:include-meta: {presentationBulletListType: "RevealBoxes"}

* Example snippets
* CLI Outputs
* REST responses
* Screenshots 

> In fact tests generated the majority of this content

# Capturing Test Artifacts

:include-file: scenarios/gamestore/database.groovy {
    title: "HTTP doc capture",
    startLine: "db-insert", endLine: "scenario-end", excludeStartEnd: true,
    highlight: "doc.capture",
    excludeRegexp: ["//"]
}

:include-file: scenarios/gamestore/browserLandingFilter.groovy { 
    title: "UI doc capture",
    startLine: "filter by price",
    highlight: ["browser.doc"], 
    excludeRegexp: ["hide"]
}

# Example of Generated Documentation
