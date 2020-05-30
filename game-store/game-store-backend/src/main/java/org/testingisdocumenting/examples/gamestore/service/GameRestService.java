package org.testingisdocumenting.examples.gamestore.service;

import org.springframework.web.bind.annotation.RestController;
import org.testingisdocumenting.examples.gamestore.storage.GameRepository;

@RestController
public class GameRestService {
    private final GameRepository repository;

    public GameRestService(GameRepository repository) {
        this.repository = repository;
    }
}
