package org.testingisdocumenting.examples.gamestore.server.checkout;

import org.junit.Test;
import org.testingisdocumenting.examples.gamestore.server.games.Game;

import java.util.List;

import static org.testingisdocumenting.examples.gamestore.GamesData.createGames;
import static org.testingisdocumenting.webtau.WebTauCore.*;

public class DiscountCalculatorTest {
    @Test
    public void discountBasedUniqueGenresAbove15DollarsLimitedBy3() {
        doc.console.capture("gamesDiscountOutput", () -> { // doc-exclude
        List<Game> checkoutGames = createGames(table("id"     , "type", "price", // WebTau table API to create domain data
                                                     ___________________________,
                                                     cell.guid, "RPG",   12, // cell.guid generated unique id for each row
                                                     cell.guid, "RPG",   28,
                                                     cell.guid, "RPG",   70, // three games with the same Type to make sure we pick only one distinct even though 2 match price criteria
                                                     cell.guid, "FPS",   30,
                                                     cell.guid, "FPS",   10,
                                                     cell.guid, "RTS",   60,
                                                     cell.guid, "Sport", 30)); // extra game type to make sure we stop at 3

        int discountPercentage = new DiscountCalculator().calculateDiscountPercentage(checkoutGames);
        actual(discountPercentage, "discount").should(equal(30)); // validate the result
        }); // doc-exclude
    }
}