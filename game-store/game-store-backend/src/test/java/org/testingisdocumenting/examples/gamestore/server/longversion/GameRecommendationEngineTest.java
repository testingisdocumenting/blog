package org.testingisdocumenting.examples.gamestore.server.longversion;

import org.junit.jupiter.api.Test;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GameRecommendationEngine;
import org.testingisdocumenting.examples.gamestore.server.games.GamesLibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testingisdocumenting.examples.gamestore.GamesData.createLibrary;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class GameRecommendationEngineTest {
    @Test
    public void shouldRecommendNextGameWhereAccumulatedPerTypeIsLowest() {
        GamesLibrary library = createLibrary(table("id",  "type",      "title", "hoursPlayed", // create game library with play stats
                                                   _________________________________________,
                                                   "g1",   "RPG", "Divinity 2", 130,
                                                   "g2", "Sport",   "NBA 2023",   1,
                                                   "g3",  "JRPG",  "Persona 5", 300));

        GameRecommendationEngine engine = new GameRecommendationEngine(library);
        Game nextGame = engine.recommendNextGame();

        assertEquals("g2", nextGame.getId());
        assertEquals("Sport", nextGame.getType());
        assertEquals("NBA 2023", nextGame.getTitle());
        assertEquals(1, nextGame.getStats().getHoursPlayed());
    }
}