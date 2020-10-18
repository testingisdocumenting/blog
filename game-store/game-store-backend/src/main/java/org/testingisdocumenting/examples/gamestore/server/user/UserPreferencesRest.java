package org.testingisdocumenting.examples.gamestore.server.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testingisdocumenting.examples.gamestore.server.auth.AuthorizationService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserPreferencesRest {
    private final UserPreferencesRepository repository;
    private final AuthorizationService authorizationService;

    public UserPreferencesRest(UserPreferencesRepository repository, AuthorizationService authorizationService) {
        this.repository = repository;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/user-preferences")
    public ResponseEntity<UserPreferences> myPreferences(@RequestHeader HttpHeaders header) {
        String userId = authorizationService.userId(header);
        return userPreferencesById(userId);
    }

    @PutMapping("/user-preferences")
    public ResponseEntity<UserPreferences> createPreferences(@RequestHeader HttpHeaders header,
                                                             @RequestBody UserPreferences userPreferences) {
        String userId = authorizationService.userId(header);
        if (userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userPreferences.setUserId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.save(userPreferences));
    }

    @GetMapping("/user-preferences/{id}")
    public ResponseEntity<UserPreferences> findPreferences(@RequestHeader HttpHeaders header,
                                                           @PathVariable String id) {
        String userId = authorizationService.userId(header);
        return userId.equals(id) || authorizationService.isAdmin(userId) ?
                userPreferencesById(id) :
                emptyUserPreferences(userId);
    }

    @PutMapping("/user-preferences/{id}")
    public ResponseEntity<UserPreferences> updatePreferences(@RequestHeader HttpHeaders header,
                                                             @RequestBody UserPreferences userPreferences, @PathVariable String id) {
        String userId = authorizationService.userId(header);
        if (!authorizationService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        repository.save(userPreferences);

        return ResponseEntity.status(HttpStatus.OK).body(userPreferences);
    }

    @DeleteMapping("/user-preferences/{id}")
    public ResponseEntity<Object> deletePreferences(@RequestHeader HttpHeaders header,
                                                    @PathVariable String id) {
        String userId = authorizationService.userId(header);
        if (!authorizationService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<UserPreferences> userPreferencesById(String id) {
        Optional<UserPreferences> game = repository.findById(id);
        return game
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> emptyUserPreferences(id));
    }

    private ResponseEntity<UserPreferences> emptyUserPreferences(String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserPreferences(userId));
    }
}

