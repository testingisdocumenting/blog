package pages

import java.nio.file.Paths

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class ReportPage {
    def collapsedHeader = $(".collapsed-http-header")
    def groupNames = $(".group-of-tests .navigation-entry-group-label")
    def testNames = $(".navigation-entry .label")
    def testSummaryMetaKey = $(".test-summary-metadata th").get("METADATA KEY")
    def cardList = $(".card-list")
    def jsonHighlight = $(".json-value-highlight")
    def cliOutputHighlight = $(".cli-output-line.matched")
    def personaId = $(".persona-id")
    def steps = $(".card.step")
    def httpCallsSelection = $(".entry-type-http-calls")
    def testSummary = $(".test-summary")
    def navigationEntries = $('.navigation-entry .label')
    def skippedOperations = $("div").get("Skipped operations")
    def summaryTabs = $(".tab-name")
    def testTabs = $(".tab-selection .tab-name")
    def httpCallsTab = testTabs.get('HTTP calls')
    def stepsTab = testTabs.get('Steps')
    def cliCallsTab = testTabs.get('CLI calls')

    def testsSelection = $(".entry-type-tests")

    def openReport() {
        openReportFile("file://" + Paths.get("webtau.report.html").toAbsolutePath())
    }

    def selectSkippedOperations() {
        skippedOperations.click()
    }

    def selectTestsSelection() {
        testsSelection.click()
    }

    def selectSummaryTab(String name) {
        summaryTabs.get(name).click()
    }

    def selectTest(String testName) {
        def navEntry = navigationEntries.get(testName)
        navEntry.waitTo beVisible()
        navEntry.click()
    }

    def selectHttpCalls() {
        httpCallsTab.click()
    }

    def selectSteps() {
        stepsTab.click()
    }

    def selectCliCalls() {
        cliCallsTab.click()
    }

    def expandHttpCall(number) {
        def httpCalls = $(".test-http-call")
        httpCalls.waitTo beVisible()
        httpCalls.get(number).find(".collapse-toggle").click()
    }

    def expandCliCall(number) {
        def httpCalls = $(".test-cli-call")
        httpCalls.waitTo beVisible()
        httpCalls.get(number).find(".collapse-toggle").click()
    }

    private static def openReportFile(String fileName) {
        browser.open(fileName)
        $(".status-filter-area").waitTo beVisible()
    }
}
