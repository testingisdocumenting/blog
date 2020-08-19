package scenarios.gamestore

import utils.TestUtils

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.*

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
scenario('filter by text') {
    admin.reopen()
    admin.filterText.setValue("last")

    admin.titles.waitTo == ['Last Of Us 2']
}

scenario('filter by price') {
    admin.reopen()
    admin.filterBelow60.setValue(true)

    admin.titles.waitTo == ['Assassin Creed Odyssey', 'Division 2',
                            'Hearthstone', 'Inside', 'Slay The Spire']
    browser.doc.withAnnotations(
            browser.doc.badge(admin.filterText).above(),
            browser.doc.badge(admin.filterBelow60).above()).capture('admin-ui-filter')
}