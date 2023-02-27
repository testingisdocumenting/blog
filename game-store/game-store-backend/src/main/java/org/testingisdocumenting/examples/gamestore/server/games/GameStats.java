package org.testingisdocumenting.examples.gamestore.server.games;

public class GameStats {
    private final String gameId;
    private final int hoursPlayed;

    public GameStats(String gameId, int hoursPlayed) {
        this.gameId = gameId;
        this.hoursPlayed = hoursPlayed;
    }

    public String getGameId() {
        return gameId;
    }

    public int getHoursPlayed() {
        return hoursPlayed;
    }
}
