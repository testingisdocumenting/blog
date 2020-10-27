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
    landing.filterText.setValue("last") // set value using input defined in the page object

    landing.titles.waitTo == ['Last Of Us 2']
}

scenario('filter by price') {
    landing.reopen()
    landing.filterBelow60.setValue(true) // set checkbox value using input defined in the page object

    landing.titles.waitTo == ['Assassin Creed Odyssey', 'Division 2',
                              'Hearthstone', 'Inside', 'Slay The Spire']
    browser.doc.withAnnotations(
            browser.doc.badge(landing.filterText).toTheRight(),
            browser.doc.badge(landing.labelBelow60).toTheRight()).capture('admin-ui-filter')
}