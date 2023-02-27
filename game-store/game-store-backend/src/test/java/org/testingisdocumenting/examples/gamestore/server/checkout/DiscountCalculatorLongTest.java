package org.testingisdocumenting.examples.gamestore.server.checkout;

import org.junit.Test;
import org.testingisdocumenting.examples.gamestore.server.games.Game;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DiscountCalculatorLongTest {
    @Test
    public void longObjectsCreation() {
        Game game1 = new Game();
        game1.setId("id1");
        game1.setType("RPG");
        game1.setPriceUsd(BigDecimal.valueOf(12));

        Game game2 = new Game();
        game1.setId("id2");
        game1.setType("RPG");
        game1.setPriceUsd(BigDecimal.valueOf(28));

        Game game3 = new Game();
        game1.setId("id3");
        game1.setType("RPG");
        game1.setPriceUsd(BigDecimal.valueOf(70));

        // ....

        List<Game> checkoutGames = Arrays.asList(game1, game2, game3);
    }
}