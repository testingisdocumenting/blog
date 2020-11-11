package scenarios.gamestore

import utils.TestUtils // hide
// hide
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.* // import all the pages into test scope

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
scenario('filter by text') {
    landing.reopen()
    // set value using input defined in the page object
    landing.filterText.("civ")

    landing.titles.waitTo == ['Civilization 6']
}

scenario('filter by price') {
    landing.reopen()
    // set checkbox value using input defined in the page object
    landing.filterBelow60.setValue(true)

    landing.titles.waitTo == ['Doom', 'Slay The Spire']
    browser.doc.withAnnotations(
            browser.doc.badge(landing.filterText).toTheRight(),
            browser.doc.badge(landing.labelBelow60).toTheRight())
            .capture('admin-ui-filter')
}