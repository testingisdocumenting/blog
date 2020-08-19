package org.testingisdocumenting.examples.gamestore.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Games {
    private EmbeddedGames _embedded = new EmbeddedGames();

    public EmbeddedGames get_embedded() {
        return _embedded;
    }

    public void set_embedded(EmbeddedGames _embedded) {
        this._embedded = _embedded;
    }
}
