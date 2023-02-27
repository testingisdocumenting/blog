package org.testingisdocumenting.examples.gamestore.server.games;


import org.junit.jupiter.api.Test;
import org.testingisdocumenting.webtau.data.table.TableData;

import java.util.List;

import static org.testingisdocumenting.examples.gamestore.GamesData.createLibrary;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class GamesLibraryTest {
  @Test
  public void shouldFindTopTwoGames() {
    doc.console.captureNoStep("findTopTwoGamesOutput", () -> { // doc-exclude
    GamesLibrary library = createLibrary(table("id",        "type",      "title", "hoursPlayed", "price",
                                               _________________________________________________________,
                                               "g1",         "RPG",  "Divinity 2", 130,           30,
                                               "g2",       "Sport",    "NBA 2023",   1,           40,
                                               "g3",        "JRPG",   "Persona 5", 300,           20,
                                               "g4",  "PainAction",  "Elden Ring", 200,           60));
    trace("all games", propertiesTable(library.getGames())); // tracing all games when need to debug

    List<Game> topTwoGames = library.findTopNGames(2); // find top two games as list of game

    TableData expected = table( "*id",        "type",      "title", "priceUsd"   ,  "stats",  // expectation as table with key column <id>
                               _____________________________________________________________________________,
                                 "g3",        "JRPG",   "Persona 5", lessThan(60), map("hoursPlayed", 300), // using a matcher inside a table cell
                                 "g4",  "PainAction",  "Elden Ring",          60 , map("hoursPlayed", 200));

    code(() -> { // doc-exclude
    actual(topTwoGames, "top2").should(equal(expected)); // compare with list of java beans order agnostic
    }).should(throwException(AssertionError.class)); // doc-exclude
    }); // doc-exclude
  }
}