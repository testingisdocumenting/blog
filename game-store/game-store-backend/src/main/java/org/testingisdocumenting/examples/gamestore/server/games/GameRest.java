package org.testingisdocumenting.examples.gamestore.server.games;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.testingisdocumenting.examples.gamestore.server.ArtificialDelay.artificialDelay;

@RestController
@RequestMapping("/api")
public class GameRest {
    private final GameRepository repository;

    public GameRest(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> findGame(@PathVariable String id) {
        artificialDelay(50, 100);

        Optional<Game> game = repository.findById(id);
        return game
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        artificialDelay(100, 150);

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

    @DeleteMapping(value = "/game/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable String id) {
        artificialDelay(100, 200);
        repository.deleteById(id);
    }

    @GetMapping("/game")
    public List<Game> listAllGames(@RequestParam(value = "sortBy", required = false) String sortBy) {
        artificialDelay(200, 250);

        if (sortBy != null) {
            return repository.findAll(Sort.by(sortBy));
        } else {
            return repository.findAll();
        }
    }
}

