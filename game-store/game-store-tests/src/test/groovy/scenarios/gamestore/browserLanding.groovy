package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('setup up') { // hide
    db.update("delete from GAME") // hide
} // hide
// hide
// hide
scenario('admin browser test') {
    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20]) // pre-create test data using HTTP
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])
    http.post("/api/game", [id: "g3", title: "Doom", type: "fps", priceUsd: 40])

    browser.open("/") // open landing page, relying on url and/or browserUrl
    browser.doc.capture("landing-page")

    def titles = $('[class*="GamesList_title"]')

    titles.waitTo == ['Civilization 6', 'Doom', 'Slay The Spire'] // wait for games titles to show up and match our expectations

    http.delete("/api/game/g3") // delete a single game using HTTP

    titles.waitTo == ['Civilization 6', 'Slay The Spire'] // wait for UI to reflect changes
    browser.doc.capture("landing-page-reduced")
}