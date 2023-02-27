package org.testingisdocumenting.examples.gamestore.server.games;

import org.junit.jupiter.api.Test;

import static org.testingisdocumenting.examples.gamestore.GamesData.createLibrary;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class GameRecommendationEngineTest {
    @Test
    public void shouldRecommendNextGameWhereAccumulatedPerTypeIsLowest() {
        doc.console.captureNoStep("recommendNextGameOutput", () -> { // doc-exclude
        GamesLibrary library = createLibrary(table("id",  "type",      "title", "hoursPlayed", // create game library with play stats
                                                   _________________________________________,
                                                   "g1",   "RPG", "Divinity 2", 130,
                                                   "g2", "Sport",   "NBA 2023",   1,
                                                   "g3",  "JRPG",  "Persona 5", 300));

        GameRecommendationEngine engine = new GameRecommendationEngine(library);

        code(() -> { // doc-exclude
        actual(engine.recommendNextGame(), "nextGame").should(equal(map( // assert on Game bean using map
                "id", "g2",
                "type", "Sport",
                "title", contain("2023"), // using a matcher for a field
                "stats", map("hoursPlayed", 1))));
        }).should(throwException(AssertionError.class)); // doc-exclude
        }); //doc-exclude
    }
}