package org.testingisdocumenting.examples.gamestore;

import org.testingisdocumenting.examples.gamestore.server.games.Game;
import org.testingisdocumenting.examples.gamestore.server.games.GamesLibrary;
import org.testingisdocumenting.webtau.data.table.Record;
import org.testingisdocumenting.webtau.data.table.TableData;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class GamesData {
    public static List<Game> createGames(TableData table) { // table data is like list of map
        return table.rowsStream() // you can stream rows
                .map(GamesData::createGame)
                .collect(Collectors.toList());
    }

    public static Game createGame(Record row) {
        Game game = new Game();
        game.setId(row.get("id"));
        game.setType(row.get("type"));
        game.setTitle(row.get("title", "")); // default values when not provided
        game.setPriceUsd(BigDecimal.valueOf((int) row.get("price", 0)));

        return game; // and create domain data you need
    }

    public static GamesLibrary createLibrary(TableData table) {
        GamesLibrary library = new GamesLibrary();

        table.rowsStream().forEach(row -> {
            Game game = createGame(row);
            library.addGame(game);
            library.addStat(game.getId(), row.get("hoursPlayed"));
        });

        return library;
    }
}
