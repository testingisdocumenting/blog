package org.testingisdocumenting.examples.gamestore.server.games;

public class GameReview {
    private final String id;
    private final String gameId;
    private final String userId;
    private final String review;

    public GameReview(String id, String gameId, String userId, String review) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public String getUserId() {
        return userId;
    }

    public String getReview() {
        return review;
    }
}
