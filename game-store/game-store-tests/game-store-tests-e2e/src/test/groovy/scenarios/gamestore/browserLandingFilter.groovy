package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.* // import all the pages into test scope

scenario('setup up') {  // hide
    db.update("delete from GAME")  // hide
    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20])  // hide
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60]) // hide
    http.post("/api/game", [id: "g3", title: "Doom", type: "fps", priceUsd: 40]) // hide
} // hide
// hide
scenario('filter by text') {
    landing.reopen()
    // set value using input defined in the page object
    landing.filterText.setValue("civ")

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