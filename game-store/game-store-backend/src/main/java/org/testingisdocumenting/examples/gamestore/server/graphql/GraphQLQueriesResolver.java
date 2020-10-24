package org.testingisdocumenting.examples.gamestore.server.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;
import org.testingisdocumenting.examples.gamestore.server.auth.AuthorizationService;
import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GameRepository;
import org.testingisdocumenting.examples.gamestore.server.user.UserPreferences;
import org.testingisdocumenting.examples.gamestore.server.user.UserPreferencesRepository;

import java.util.List;

@Service
public class GraphQLQueriesResolver implements GraphQLQueryResolver {
    private final GameRepository gameRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final AuthorizationService authorizationService;

    public GraphQLQueriesResolver(GameRepository gameRepository,
                                  UserPreferencesRepository userPreferencesRepository,
                                  AuthorizationService authorizationService) {
        this.gameRepository = gameRepository;
        this.userPreferencesRepository = userPreferencesRepository;
        this.authorizationService = authorizationService;
    }

    public List<Game> games() {
        return gameRepository.findAll();
    }

    public Game game(String id) {
        return gameRepository.findById(id).orElse(null);
    }

    public UserPreferences userPreferences(DataFetchingEnvironment env) {
        String userId = authorizationService.userId(env);
        return userPreferencesRepository.findById(userId).orElse(new UserPreferences(userId));
    }
}
