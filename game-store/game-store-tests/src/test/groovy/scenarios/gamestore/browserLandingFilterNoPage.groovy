package scenarios.gamestore

import utils.TestUtils

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
scenario('filter by text') {
    browser.reopen("/")

    $("#filter").setValue("last")
    $('[class*="GamesList_title"]').waitTo == ['Last Of Us 2']
}

scenario('filter by price') {
    browser.reopen("/")

    $("#below60").setValue(true)
    $('[class*="GamesList_title"]').waitTo == ['Assassin Creed Odyssey', 'Division 2',
                                               'Hearthstone', 'Inside', 'Slay The Spire']
}