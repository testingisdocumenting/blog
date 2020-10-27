package org.testingisdocumenting.examples.gamestore.server.games;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GameRest {
    private final GameRepository repository;

    public GameRest(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> findGame(@PathVariable String id) {
        Optional<Game> game = repository.findById(id);
        return game
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/game")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        setIdIfRequired(game);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(game));
    }

    private void setIdIfRequired(Game game) {
        if (game.getId() == null) {
            game.setId(java.util.UUID.randomUUID().toString());
        }
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<Game> updateGame(@RequestBody Game game, @PathVariable String id) {
        Optional<Game> existing = repository.findById(id);

        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        repository.save(game);

        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @DeleteMapping("/game/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/game")
    public Iterable<Game> listAllGames(@RequestParam(value = "sortBy", required = false) String sortBy) {
        if (sortBy != null) {
            return repository.findAll(Sort.by(sortBy));
        } else {
            return repository.findAll();
        }
    }
}

