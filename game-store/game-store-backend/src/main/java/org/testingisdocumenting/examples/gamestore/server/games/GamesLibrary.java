package org.testingisdocumenting.examples.gamestore.server.games;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamesLibrary {
    private final List<Game> games;
    private final Map<String, Game> gameById;

    public GamesLibrary() {
        games = new ArrayList<>();
        gameById = new HashMap<>();
    }

    public List<Game> findTopNGames(int n) {
        Stream<GameStats> topNStats = games.stream().map(Game::getStats)
                .sorted((a, b) -> b.getHoursPlayed() - a.getHoursPlayed())
                .limit(n);

        return topNStats.map(stat -> gameById.get(stat.getGameId())).collect(Collectors.toList());
    }

    public void addGame(Game game) {
        Game copy = game.makeCopy();
        this.games.add(copy);
        this.gameById.put(game.getId(), copy);
    }

    public void addStat(String gameId, int hoursPlayed) {
        Game game = gameById.get(gameId);
        game.setStats(new GameStats(gameId, hoursPlayed));
    }

    public List<Game> getGames() {
        return games;
    }
}
