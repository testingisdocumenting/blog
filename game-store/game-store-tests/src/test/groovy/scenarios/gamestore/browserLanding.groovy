package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('setup up') { // hide
    db.update("delete from GAME") // hide
} // hide
// hide
// hide
scenario('admin browser test') {
    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20])
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])
    http.post("/api/game", [id: "g3", title: "Doom", type: "fps", priceUsd: 40])

    browser.open("/")
    browser.doc.capture("landing-page")

    def titles = $('[class*="GamesList_title"]')

    titles.waitTo == ['Civilization 6', 'Doom', 'Slay The Spire']

    http.delete("/api/game/g3")

    titles.waitTo == ['Civilization 6', 'Slay The Spire']
    browser.doc.capture("landing-page-reduced")
}