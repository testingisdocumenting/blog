package org.testingisdocumenting.examples.gamestore.server.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testingisdocumenting.examples.gamestore.server.games.Game;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, String> {
}
