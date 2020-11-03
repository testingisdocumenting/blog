package scenarios.gamestore

import static clicommands.CliCommands.* // convenient access to all declared command lines
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('list games using CLI') {
    // cleaning up DB
    db.update("delete from GAME")

    // run our command line
    adminTool.run {
        output.should contain("List of games")
        // expect a title to be present, and nothing else
        output.numberOfLines.should == 1
    }

    http.post("/api/game", [title: "Slay The Spire", type: "card rpg", priceUsd: 20]) // pre-create data we need for the test
    http.post("/api/game", [title: "Civilization 6", type: "strategy", priceUsd: 60])

    adminTool.run {
        output.should contain("Slay The Spire")
        output.should contain("Civilization 6")
    }
    cli.doc.capture("list-games-cli")
}
