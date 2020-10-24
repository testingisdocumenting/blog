package org.testingisdocumenting.examples.gamestore.server.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;
import org.testingisdocumenting.examples.gamestore.server.auth.AuthorizationService;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GameRepository;
import org.testingisdocumenting.examples.gamestore.server.user.UserPreferences;
import org.testingisdocumenting.examples.gamestore.server.user.UserPreferencesRepository;

import java.math.BigDecimal;

@Service
public class GraphQLMutationsResolver implements GraphQLMutationResolver {
    private final GameRepository gameRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final AuthorizationService authorizationService;

    public GraphQLMutationsResolver(GameRepository gameRepository, UserPreferencesRepository userPreferencesRepository, AuthorizationService authorizationService) {
        this.gameRepository = gameRepository;
        this.userPreferencesRepository = userPreferencesRepository;
        this.authorizationService = authorizationService;
    }

    public Game createGame(String id, String title, String type, BigDecimal priceUsd) {
        Game game = new Game();
        game.setId(id);
        game.setTitle(title);
        game.setType(type);
        game.setPriceUsd(priceUsd);

        return gameRepository.save(game);
    }

    public UserPreferences updateUserPreferences(String favoriteGenre, DataFetchingEnvironment env) {
        String userId = authorizationService.userId(env);
        if (userId.isEmpty()) {
            throw new RuntimeException("forbidden");
        }

        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUserId(userId);
        userPreferences.setFavoriteGenre(favoriteGenre);

        return userPreferencesRepository.save(userPreferences);
    }
}
