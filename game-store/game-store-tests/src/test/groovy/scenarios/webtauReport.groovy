package scenarios


import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.report

scenario('report summary') {
    report.openReport()
    browser.doc.capture('game-store-report-summary')
}

scenario('http call') {
    report.selectTest('db write and http list games')
    report.selectHttpCalls()
    report.expandHttpCall(1)
    browser.doc.capture('game-store-report-http-call')
}

scenario('cli') {
    report.selectTest('list games using CLI')
    report.selectCliCalls()
    report.expandCliCall(1)
    browser.doc.capture('game-store-report-cli-call')
}

scenario('steps') {
    report.selectTest('admin browser test')
    report.selectSteps()
    browser.doc.capture('game-store-report-steps')
}

