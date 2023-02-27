package org.testingisdocumenting.examples.gamestore.server.games;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
public class Game {
    private String id;
    private String title;
    private String type;
    private BigDecimal priceUsd;

    private GameStats stats;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    @Transient
    public GameStats getStats() {
        return stats;
    }

    public void setStats(GameStats stats) {
        this.stats = stats;
    }

    public Game makeCopy() {
        Game copy = new Game();
        copy.setId(getId());
        copy.setType(getType());
        copy.setTitle(getTitle());
        copy.setStats(getStats());

        return copy;
    }
}
