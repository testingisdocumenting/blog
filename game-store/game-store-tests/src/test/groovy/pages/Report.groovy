package pages

import java.nio.file.Paths

import static org.testingisdocumenting.webtau.WebTauDsl.*

class Report {
    def collapsedHeader = $(".collapsed-http-header")
    def groupNames = $(".group-of-tests .navigation-entry-group-label")
    def testNames = $(".navigation-entry .label")
    def testSummaryMetaKey = $(".test-summary-metadata th").get("METADATA KEY")

    def openReport() {
        openReportFile("file://" + Paths.get("webtau.report.html").toAbsolutePath())
    }

    def selectTest(String testName) {
        def navEntry = $('.navigation-entry .label').get(testName)
        navEntry.waitTo beVisible()
        navEntry.click()
    }

    def selectHttpCalls() {
        $(".tab-selection .tab-name").get('HTTP calls').click()
    }

    def selectSteps() {
        $(".tab-selection .tab-name").get('Steps').click()
    }

    def selectCliCalls() {
        $(".tab-selection .tab-name").get('CLI calls').click()
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
