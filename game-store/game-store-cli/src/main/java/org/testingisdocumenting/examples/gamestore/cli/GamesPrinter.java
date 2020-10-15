package org.testingisdocumenting.examples.gamestore.cli;

import org.apache.commons.lang3.StringUtils;
import org.testingisdocumenting.examples.gamestore.cli.ansi.Ansi;
import org.testingisdocumenting.examples.gamestore.cli.ansi.Color;

import java.util.List;

public class GamesPrinter {
    private final List<Game> games;

    private int idWidth;
    private int titleWidth;
    private int typeWidth;
    private int priceWidth;

    public GamesPrinter(List<Game> games) {
        this.games = games;
        this.games.forEach(this::calcWidth);
    }

    public void print() {
        Ansi.print(Color.CYAN, "List of games\n");
        games.forEach(this::print);
    }

    public void print(Game game) {
        Ansi.print(
                Color.PURPLE, StringUtils.leftPad(game.getId(), idWidth), ' ',
                Color.YELLOW, StringUtils.leftPad(game.getTitle(), titleWidth), ' ',
                Color.GREEN, StringUtils.leftPad(game.getType(), typeWidth), ' ',
                Color.YELLOW, StringUtils.leftPad(game.getPriceUsd().toString(), priceWidth));
    }

    private void calcWidth(Game game) {
        idWidth = Math.max(idWidth, game.getId().length());
        titleWidth = Math.max(titleWidth, game.getTitle().length());
        typeWidth = Math.max(typeWidth, game.getType().length());
        priceWidth = Math.max(priceWidth, game.getPriceUsd().toString().length());
    }
}
