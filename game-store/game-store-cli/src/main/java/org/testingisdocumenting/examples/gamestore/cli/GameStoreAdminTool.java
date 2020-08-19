package org.testingisdocumenting.examples.gamestore.cli;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class GameStoreAdminTool {
    public static void main(String[] args) {
        GameStoreAdminTool adminTool = new GameStoreAdminTool();
        adminTool.start(args);
    }

    private void start(String[] args) {
        WebClient client = WebClient.create("http://localhost:8080/api/game");
        Games games = client.get().retrieve().bodyToMono(Games.class).block();

        printGames(games.get_embedded().getGames());
    }

    private void printGames(List<Game> games) {
        new GamesPrinter(games).print();
    }
}
