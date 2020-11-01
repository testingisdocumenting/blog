package org.testingisdocumenting.examples.gamestore.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private String id;
    private String title;
    private String type;
    private BigDecimal priceUsd;

    public Game(Map<String, Object> payload) {
        id = payload.get("id").toString();
        title = payload.get("title").toString();
        type = payload.get("type").toString();
        priceUsd = BigDecimal.valueOf((double)payload.get("priceUsd"));
    }

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
}
