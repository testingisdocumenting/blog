package scenarios.gamestore

import utils.TestUtils

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
scenario('filter by text') {
    browser.reopen("/")

    $("#filter").setValue("last") // use setValue abstraction to set value inside input box
    $('[class*="GamesList_title"]').waitTo == ['Last Of Us 2'] // wait for changes to be reflected
}

scenario('filter by price') {
    browser.reopen("/")

    $("#below60").setValue(true) // use setValue abstraction to set value for a checkbox
    $('[class*="GamesList_title"]').waitTo == ['Assassin Creed Odyssey', 'Division 2', // wait for changes to be reflected
                                               'Hearthstone', 'Inside', 'Slay The Spire']
}