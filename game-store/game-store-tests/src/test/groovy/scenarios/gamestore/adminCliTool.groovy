package scenarios.gamestore

import static clicommands.CliCommands.* // convenient access to all declared command lines
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('clean up') { // hide
    db.update("delete from GAME") // hide
} // hide
// hide
scenario('list games using CLI') {
    adminTool.run { // run our command line
        output.should contain("List of games") // expect a title to be present
    }

    http.post("/api/game", [id: "g1", title: "Slay The Spire", type: "card rpg", priceUsd: 20]) // pre-create data we need for the test
    http.post("/api/game", [id: "g2", title: "Civilization 6", type: "strategy", priceUsd: 60])

    adminTool.run {
        output.should contain("Slay The Spire")
        output.should contain("Civilization 6")
    }
    cli.doc.capture("list-games-cli")
}
