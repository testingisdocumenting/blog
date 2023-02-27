package org.testingisdocumenting.examples.gamestore.server.games;

public class GameRecommendationEngine {
    private final GamesLibrary library;

    public GameRecommendationEngine(GamesLibrary library) {
        this.library = library;
    }

    public Game recommendNextGame() {
        // dummy implementation with a bug
        Game game = library.getGames().get(1);
        game.setType("RPG");

        return game;
    }
}
