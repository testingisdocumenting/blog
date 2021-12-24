package com.example.junit5;

import org.junit.jupiter.api.Test;
import org.testingisdocumenting.webtau.junit5.WebTau;

import java.util.Map;

import static org.testingisdocumenting.webtau.WebTauDsl.*; // convenient import for Java

@WebTau // optional annotation to include this test into web report
public class PostGetJavaTest {
    @Test
    public void registerNewGame() {
        Map<String, Object> payload = aMapOf( // define payload as a map
                "id", "g1",
                "title", "Slay The Spire",
                "type", "card rpg",
                "priceUsd", 20);

        http.post("/api/game", payload); // same post as in Groovy

        http.get("/api/game/g1", (header, body) -> {
            body.get("title").should(equal("Slay The Spire")); // validating title field in the response
        });
    }
}
