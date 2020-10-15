package org.testingisdocumenting.examples.gamestore.cli;

import java.util.ArrayList;
import java.util.List;

public class EmbeddedGames {
    private List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
