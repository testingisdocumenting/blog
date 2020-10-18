package org.testingisdocumenting.examples.gamestore.server.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserPreferences {
    private String userId;
    private String favoriteGenre;

    public UserPreferences() {
    }

    public UserPreferences(String userId) {
        this.userId = userId;
    }

    @Id
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }
}
