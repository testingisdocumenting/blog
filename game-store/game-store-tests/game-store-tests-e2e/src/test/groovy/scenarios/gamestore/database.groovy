package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// db-list
scenario('http post and db list games') {
    db.update("delete from Game") // wipe out table

    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20])
    // create two games using HTTP
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])

    def GAME = db.table("GAME")
    GAME.should == ["*ID" | "TITLE"] { // make sure DB reflects changes
                   __________________________
                     "g1" | "Slay The Spire"
                     "g2" | "Civilization 6"}
}
// scenario-end

// db-insert
scenario('db write and http list games') {
    db.update("delete from Game")

    def GAME = db.table("GAME")
    // populate GAME table with two rows
    GAME << ["ID" | "TITLE"           | "TYPE"     | "PRICE_USD"] {
             ____________________________________________________
             "g1" | "Slay The Spire"  | "card rpg" | 20
             "g2" | "Civilization 6"  | "strategy" | 60  }

    http.get("/api/game") {
        // expect body to contain a list of two games
        body.should == [ "*id" | "title" ] {
                       ___________________________
                          "g2" | "Civilization 6"
                          "g1" | "Slay The Spire" }
    }
    http.doc.capture("game-store-list-after-db")
}
// scenario-end
