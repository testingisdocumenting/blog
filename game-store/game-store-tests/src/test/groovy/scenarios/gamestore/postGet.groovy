package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// register-new-game
scenario('register new game') {
    def payload = [id: "g1",
                   title: "Slay The Spire",
                   type: "card rpg",
                   priceUsd: 20]
    http.post("/api/game", payload)

    http.get("/api/game/g1") {
        id.should == "g1"
        title.should == "Slay The Spire"
        type.should == "card rpg"
        priceUsd.should == 20
    }
    http.doc.capture("game-store-rest-new-game")
}
// scenario-end

scenario('clean up') {
    http.delete("/api/game/g1")
}