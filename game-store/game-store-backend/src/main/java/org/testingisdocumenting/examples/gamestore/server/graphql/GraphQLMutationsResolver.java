package org.testingisdocumenting.examples.gamestore.server.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Service;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GameRepository;

import java.math.BigDecimal;

@Service
public class GraphQLMutationsResolver implements GraphQLMutationResolver {
    private final GameRepository gameRepository;

    public GraphQLMutationsResolver(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String id, String title, String type, BigDecimal priceUsd) {
        Game game = new Game();
        game.setId(id);
        game.setTitle(title);
        game.setType(type);
        game.setPriceUsd(priceUsd);

        return gameRepository.save(game);
    }
}
