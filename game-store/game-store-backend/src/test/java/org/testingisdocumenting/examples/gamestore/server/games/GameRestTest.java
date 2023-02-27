package org.testingisdocumenting.examples.gamestore.server.games;


import org.junit.jupiter.api.Test;
import org.testingisdocumenting.webtau.db.DatabaseTable;

import static org.testingisdocumenting.webtau.WebTauDsl.*;

public class GameRestTest {
    private final DatabaseTable GAME = db.table("GAME"); // declare GAME table

    @Test
    public void fetchListOfGames() {
        doc.console.captureNoStep("fetchListOfGamesOutput", () -> { // doc-exclude
        GAME.clear(); // remove all entries from the DB table
        GAME.insert(table("id",  "title",  "type", "priceUsd", // insert two rows with auto conversion from camelCase to SNAKE_CASE
                          __________________________________,
                         "id1", "Game One", "RPG", 20,
                         "id2", "Game Two", "FPS", 40));

        trace("games from DB", GAME.query().tableData()); // trace data from DB

        http.get("/api/game", (header, body) -> { // GET call
            body.get(0).get("type").should(equal("RPG")); // specific field validation
            body.should(equal(table("*id", "title",  // bulk validation
                                  _____________________,
                                     "id1", "Game One",
                                     "id2", "Game Two")));
        });
        }); // doc-exclude
        http.doc.capture("http-fetch-games-list"); // doc-exclude
    }
}