package basicScenarios

url = "http://localhost:8080" // base url for all http and browser requests
browserUrl = "http://localhost:3000" // base url for browser open commands. Overrides url above if both are present
browserId = "firefox" // specifying browser to use for browser based operations

environments {
    dev { // optional overrides for the configs for dev environment
        url = "http://dev-server:8080"
        browserUrl = "http://dev-server:8080"
    }
}

docPath = "../../../../target/doc-artifacts/" // hide
