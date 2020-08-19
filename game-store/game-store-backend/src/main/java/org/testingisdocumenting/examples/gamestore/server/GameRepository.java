package org.testingisdocumenting.examples.gamestore.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "games", path = "game")
public interface GameRepository extends JpaRepository<Game, String> {
}
