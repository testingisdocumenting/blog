package scenarios.gamestore

import static clicommands.CliCommands.*
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('clean up') { // hide
    db.update("delete from GAME") // hide
} // hide
// hide
scenario('list games using CLI') {
    adminTool.run {
        output.should contain("List of my games")
    }

    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20])
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])
    http.post("/api/game", [id: "g3", title: "Doom", type: "fps", priceUsd: 40])

    adminTool.run {
        output.should contain("Slay The Spire")
        output.should contain("Civilization 6")
    }
    cli.doc.capture("list-games-cli")
}
