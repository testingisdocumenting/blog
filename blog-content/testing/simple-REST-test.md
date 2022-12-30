---
date: 2022-12-28
title: Simple and powerful HTTP API tests
summary: Introduction to WebTau HTTP module to interact with HTTP API for testing and setup
---

I want to show you how to write and run concise, readable and powerful HTTP API tests using `WebTau` tool.

# First Test

We are going to test a Todo API. [JSON Placeholder](https://jsonplaceholder.typicode.com) website is going to help us with this.

:include-json: doc-artifacts/todo-item-get/response.json {
  title: "GET https://jsonplaceholder.typicode.com/todos/1",
  paths: "root.title"
}

Our first test will send GET request to `:file: doc-artifacts/todo-item-get/request.fullurl.txt` and assertion of the `title` response field

:include-file: scenarios/apitest.groovy {
  autoTitle: true,
  highlight: "title.should",
  excludeRegexp: "http.doc",
  commentsType: "inline"
}

To run the test, we will use `WebTau` command line tool. One way to install it is to use [brew](https://brew.sh).
Other options available in documentation [Installation](https://testingisdocumenting.org/webtau/getting-started/installation#groovy-runner) section.

```cli
brew install testingisdocumenting/brew/webtau
webtau scenarios/apitest.groovy
```

Let's take a look at the output produced by the test run

:include-cli-output: scenarios.apitest.groovy-check-todo-item-title.out {
  title: "console output",
  excludeRegexp: "documentation",
  highlight: ["equals 200", '"title"']
}

Few things to note:
* WebTau produces detailed log of what happens
* Automatic assertion on statusCode when no explicit assertion provided
* Highlighting asserted fields in the response

# Base URL

In the example above we use the full URL to the TODO item resource. To be able to run the test against different environments (e.g. localhost)
we should extract base URL.

There are multiple ways to specify base URL, most commons are:
* Command line parameter
* Config file

:include-file: scenarios/apitestRelative.groovy {
  title: "apitest.groovy",
  highlight: "http.get"
}

To set base URL via command line we need to pass `--url` parameter to the CLI call

```cli
webtau scenarios/apitest.groovy --url https://jsonplaceholder.typicode.com
```

To set base URL using a config file use 

:include-file: webtau.resttesting.cfg.groovy {
  title: "webtau.cfg.groovy",
  includeRegexp: "url = "
}

# Extracting Data

Next, let me show you how to use data from one response to initiate the next one. 
We will create a TODO item and then extract its id for further usage.

:include-json: doc-artifacts/todo-item-get/response.json {
  title: "POST https://jsonplaceholder.typicode.com/posts",
  paths: "root.id"
}

:include-file: scenarios/apitestCreate.groovy {
  autoTitle: true,
  commentsType: "inline",
  excludeRegexp: "http.doc"
}

# Reporting

I already show you console output `WebTau` produces. Let me finish this post with a screenshot of an HTML report that `WebTau` generates.

:include-image: doc-artifacts/todo-item-report.png {annotate: true, border: true}

Generated report is a self-contained single HTML file that you can Slack around. Report captures a lot of details and provides permalink behavior. 
E.g. open a specific test and specific HTML call and then share the link. 

# Outro 

I demoed basic functionality of testing HTTP based API. Should be enough for you to start writing your tests.

There are so many other things you can do with WebTau and HTTP API, Browser, CLI, DataBase and Business Logic. 




