package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// register-new-game
scenario('register new game') {
    http.post("/api/game", [
            id: "g1", // define payload as a map
            title: "Slay The Spire",
            type: "card rpg",
            priceUsd: 20])
    http.doc.capture("game-store-rest-post-game")

    http.get("/api/game/g1") { // request newly created game as GET
        id.should == "g1"
        title.should == "Slay The Spire" // direct access to response JSON title property
        type.should == "card rpg"
        priceUsd.should == 20
    }
    http.doc.capture("game-store-rest-get-game")
}
// scenario-end

scenario('clean up') {
    http.delete("/api/game/g1")
}