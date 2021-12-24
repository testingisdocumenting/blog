package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('setup up') {
    db.update("delete from GAME")
    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20])
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])
    http.post("/api/game", [id: "g3", title: "Doom", type: "fps", priceUsd: 40])
}

// filter-start
scenario('filter by text') {
    browser.reopen("/")

    $("#filter").setValue("civ") // use setValue abstraction to set value inside input box
    $('[class*="GamesList_title"]').waitTo == ['Civilization 6'] // wait for changes to be reflected
}

scenario('filter by price') {
    browser.reopen("/")

    $("#below60").setValue(true) // use setValue abstraction to set value for a checkbox
    $('[class*="GamesList_title"]').waitTo == ['Doom', 'Slay The Spire'] // wait for changes to be reflected
}
// filter-end

scenario('implementation details') {
    browser.reopen("/")

    // impl-details
    $("#filter").setValue("civ") // exposing css selector to the test is going to haunt you later
    $('[class*="GamesList_title"]').waitTo == ['Civilization 6'] // class based selection is more likely to become out of sync
    // impl-details-end
}
