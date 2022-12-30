package scenarios

import java.nio.file.Paths

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario("capture report") {
    def reportUrl = "file://" + cfg.fullPath("webtau.report.html").toAbsolutePath()
    browser.open(reportUrl)


    def test = $('.navigation-entry .label').get("create and update todo item")
    test.waitToBe visible
    test.click()

    def tabNames = $(".tab-selection .tab-name")
    def httpCallsTab = tabNames.get("HTTP calls")

    httpCallsTab.click()

    def httpCalls = $(".test-http-call")

    httpCalls.waitTo visible
    httpCalls.get(2).find(".collapse-toggle").click()

    browser.doc.capture("todo-item-report")
}