package org.testingisdocumenting.examples.gamestore.cli;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStoreAdminTool {
    public static void main(String[] args) {
        GameStoreAdminTool adminTool = new GameStoreAdminTool();
        adminTool.start(args);
    }

    private void start(String[] args) {
        WebClient client = WebClient.create("http://localhost:8080/api/game");
        List gamesAsMapsList = client.get().retrieve().bodyToMono(List.class).block();
        List<Game> games = (List<Game>) gamesAsMapsList.stream()
                .map(m -> new Game((Map<String, Object>) m))
                .collect(Collectors.toList());

        printGames(games);
    }

    private void printGames(List<Game> games) {
        new GamesPrinter(games).print();
    }
}
