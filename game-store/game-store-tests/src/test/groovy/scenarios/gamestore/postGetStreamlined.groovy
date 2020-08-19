package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// register-new-game
scenario('register new game') {
    def payload = [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20]

    def id = http.post("/api/game", payload) {
        return id
    }

    http.get("/api/game/${id}") {
        body.should == payload
    }
}
// scenario-end

scenario('clean up') {
    http.delete("/api/game/g1")
}