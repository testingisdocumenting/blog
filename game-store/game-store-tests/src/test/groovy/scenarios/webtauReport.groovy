package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.report

scenario('report summary') {
    report.openReport()
    browser.doc.withAnnotations(
            browser.doc.badge(report.cardList.get(1)).toTheLeft(),
            browser.doc.badge(report.cardList.get(2)).toTheLeft(),
            browser.doc.badge(report.cardList.get(3)).toTheLeft(),
            browser.doc.badge(report.cardList.get(4)).toTheLeft(),
            browser.doc.badge(report.cardList.get(5)).toTheLeft(),
            browser.doc.badge(report.skippedOperations).toTheRight())
            .capture('game-store-report-summary')
}

scenario('skipped operations') {
    report.selectSkippedOperations()

    browser.doc.withAnnotations(
            browser.doc.badge(report.httpCallsSelection).toTheRight(),
            browser.doc.badge(report.navigationEntries.get(2)).toTheLeft(),
            browser.doc.badge(report.skippedOperations),
            browser.doc.badge(report.testSummary.get(~/Total/)).toTheRight())
            .capture('game-store-skipped-operations')
}

scenario('overall http performance') {
    def tabName = 'Overall HTTP Performance'
    report.selectSummaryTab(tabName)

    browser.doc.withAnnotations(
            browser.doc.badge(report.summaryTabs.get(tabName)).below())
            .capture('game-store-overall-http-performance')
}

scenario('operations http performance') {
    def tabName = 'HTTP Operations Performance'
    report.selectSummaryTab(tabName)

    browser.doc.withAnnotations(
            browser.doc.badge(report.summaryTabs.get(tabName)).below())
            .capture('game-store-operations-http-performance')
}

scenario('http call') {
    report.selectTestsSelection()
    def testName = 'db write and http list games'
    report.selectTest(testName)
    report.selectHttpCalls()
    report.expandHttpCall(1)

    browser.doc.withAnnotations(
            browser.doc.badge(report.testsSelection).toTheLeft(),
            browser.doc.badge(report.navigationEntries.get(testName)).toTheRight(),
            browser.doc.badge(report.httpCallsTab).below(),
            browser.doc.badge(report.jsonHighlight.get(2)).toTheRight(),
            browser.doc.badge(report.jsonHighlight.get(4)).toTheRight())
            .capture('game-store-report-http-call')
}

scenario('cli') {
    report.selectTest('list games using CLI')
    report.selectCliCalls()
    report.expandCliCall(1)

    browser.doc.withAnnotations(
            browser.doc.badge(report.cliCallsTab).below(),
            browser.doc.badge(report.cliOutputHighlight.get(1)).toTheLeft())
            .capture('game-store-report-cli-call')
}

scenario('steps') {
    report.selectTest('change preferences through UI')
    report.selectSteps()

    browser.doc.withAnnotations(
            browser.doc.badge(report.stepsTab).below(),
            browser.doc.badge(report.steps.get(1)).toTheLeft(),
            browser.doc.badge(report.steps.get(6)).toTheLeft())
            .capture('game-store-report-steps')
}

