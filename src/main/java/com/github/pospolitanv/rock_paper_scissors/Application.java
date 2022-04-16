package com.github.pospolitanv.rock_paper_scissors;

import com.github.pospolitanv.rock_paper_scissors.game.Game;
import com.github.pospolitanv.rock_paper_scissors.game.command_line.CommandLineGame;
import org.beryx.textio.TextIoFactory;

public class Application {

    public static void main(String[] args) {
        Game game = new CommandLineGame(TextIoFactory.getTextTerminal());
        game.init();
        game.play();
    }
}
