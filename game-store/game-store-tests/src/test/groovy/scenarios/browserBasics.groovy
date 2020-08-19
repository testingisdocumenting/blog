package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('basic browser interaction') {
    // browser-basics
    browser.open("https://google.com")
    $('input[title="Search"]').setValue("test\n")
    $('h3').count.waitTo >= 0
    // browser-basics-end

    browser.doc.capture("browser-basic")
}