package org.testingisdocumenting.examples.gamestore.server.checkout;

import org.testingisdocumenting.examples.gamestore.server.games.Game;

import java.math.BigDecimal;
import java.util.List;

public class DiscountCalculator {
    public int calculateDiscountPercentage(List<Game> games) {
        long numberOfDistinctTypes = games.stream()
                .filter(game -> game.getPriceUsd().compareTo(BigDecimal.valueOf(15)) > 0) // price filter
                .map(Game::getType)
                .distinct() // only distinct game types
                .count();

        long limitedDistinctTypes = Math.min(3, numberOfDistinctTypes); // no more than three
        return (int) (limitedDistinctTypes * 10);
    }
}
