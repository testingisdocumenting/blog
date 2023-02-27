package org.testingisdocumenting.examples.gamestore.server.longversion;


import org.junit.jupiter.api.Test;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GamesLibrary;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testingisdocumenting.examples.gamestore.GamesData.createLibrary;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class GamesLibraryTest {
    @Test
    public void shouldFindTopTwoGames() {
        GamesLibrary library = createLibrary(table("id",        "type",      "title", "hoursPlayed", "price",
                                                   _________________________________________________________,
                                                   "g1",         "RPG",  "Divinity 2", 130,           30,
                                                   "g2",       "Sport",    "NBA 2023",   1,           40,
                                                   "g3",        "JRPG",   "Persona 5", 300,           20,
                                                   "g4",  "PainAction",  "Elden Ring", 200,           60));

        // games-validation
        List<Game> topTwoGames = library.findTopNGames(2);

        Game gameOne = topTwoGames.get(0);
        assertEquals("g3",  gameOne.getId());
        assertEquals("JRPG",  gameOne.getType());
        assertEquals("Persona 5",  gameOne.getTitle());
        assertEquals(BigDecimal.valueOf(20),  gameOne.getPriceUsd());
        assertEquals(300,  gameOne.getStats().getHoursPlayed());

        Game gameTwo = topTwoGames.get(1);
        assertEquals("g3",  gameOne.getId());
        assertEquals("PainAction",  gameOne.getType());
        assertEquals("Elden Ring",  gameOne.getTitle());
        assertEquals(BigDecimal.valueOf(60),  gameOne.getPriceUsd());
        assertEquals(200,  gameOne.getStats().getHoursPlayed());
        // games-validation
    }
}