package org.testingisdocumenting.examples.gamestore.server.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GameRepository;

import java.util.List;

@Service
public class GraphQLQueriesResolver implements GraphQLQueryResolver {
    private final GameRepository gameRepository;

    public GraphQLQueriesResolver(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> games() {
        return gameRepository.findAll();
    }

    public Game game(String id) {
        return gameRepository.findById(id).orElse(null);
    }
}
