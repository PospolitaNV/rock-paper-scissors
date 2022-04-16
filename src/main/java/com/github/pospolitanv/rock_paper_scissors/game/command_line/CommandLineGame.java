package com.github.pospolitanv.rock_paper_scissors.game.command_line;

import com.github.pospolitanv.rock_paper_scissors.game.Game;
import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.ComputerPlayer;
import com.github.pospolitanv.rock_paper_scissors.player.HumanPlayer;
import com.github.pospolitanv.rock_paper_scissors.player.Player;
import com.github.pospolitanv.rock_paper_scissors.player.PlayersGameMode;
import com.github.pospolitanv.rock_paper_scissors.strategy.CommandLineInputStrategy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Attribute.INTENSITY_BOLD;
import static org.fusesource.jansi.Ansi.Attribute.UNDERLINE;
import static org.fusesource.jansi.Ansi.Color.*;

public class CommandLineGame extends Game {

    private final TextIO textIO;

    public CommandLineGame(TextTerminal textTerminal) {
        textIO = new TextIO(textTerminal);
    }

    @Override
    protected void initialScreen() {
        textIO.getTextTerminal().println(
                "Welcome to the "
                + CommandLineUtils.bgAndFgColor(WHITE, RED, "Rock-")
                + CommandLineUtils.bgAndFgColor(WHITE, GREEN, "Paper-")
                + CommandLineUtils.bgAndFgColor(WHITE, BLUE, "Scissors")
                + " Game!");
    }

    @Override
    protected List<Player> initPlayers() {
        PlayersGameMode players =
                textIO.newEnumInputReader(PlayersGameMode.class).withDefaultValue(PlayersGameMode.HUMAN_VS_COMPUTER)
                        .withAllValuesNumbered().withValueFormatter(PlayersGameMode::prettyPrint)
                        .read("What are the "
                              + CommandLineUtils.fgColorAndTextStyle(RED, UNDERLINE, "players")
                              + "?");
        return switch (players) {
            case HUMAN_VS_COMPUTER -> Arrays.asList(
                    new HumanPlayer(promptForHumanPlayerName(1),
                            new CommandLineInputStrategy(gameMode, textIO.getTextTerminal())),
                    new ComputerPlayer(gameMode)
            );
            case COMPUTER_VS_COMPUTER -> Arrays.asList(new ComputerPlayer(gameMode), new ComputerPlayer(gameMode));
            case HUMAN_VS_HUMAN -> Arrays.asList(
                    new HumanPlayer(promptForHumanPlayerName(1), new CommandLineInputStrategy(gameMode,
                            textIO.getTextTerminal())),
                    new HumanPlayer(promptForHumanPlayerName(2), new CommandLineInputStrategy(gameMode,
                            textIO.getTextTerminal()))
            );

        };
    }

    private String promptForHumanPlayerName(int i) {
        return textIO.newStringInputReader()
                .withMinLength(2)
                .withMaxLength(30)
                .withDefaultValue("defaultName")
                .read("Player "
                      + CommandLineUtils.fgColor(RED, "№" + i)
                      + " What is your name?");
    }

    @Override
    protected int initRounds() {
        return textIO.newIntInputReader().withMinVal(1).withMaxVal(100)
                .read("How many "
                      + CommandLineUtils.fgColorAndTextStyle(GREEN, UNDERLINE, "rounds")
                      + " would you play? (1-100)");
    }

    @Override
    protected void clear() {
        for (int i = 0; i < 50; i++) {
            textIO.getTextTerminal().println();
        }
    }

    @Override
    protected MovesGameMode initGameMode() {
        return textIO.newEnumInputReader(MovesGameMode.class).withAllValuesNumbered()
                .withDefaultValue(MovesGameMode.ROCK_PAPER_SCISSORS).withValueFormatter(MovesGameMode::prettyPrint)
                .read("What game "
                      + CommandLineUtils.fgColorAndTextStyle(BLUE, UNDERLINE, "mode")
                      + " would you play?");
    }

    @Override
    protected void roundStart(int roundNum) {
        textIO.getTextTerminal().println(
                "Let's start ROUND "
                + CommandLineUtils.fgColorAndTextStyle(RED, INTENSITY_BOLD, "№" + roundNum)
                + " !");
    }

    @Override
    protected void roundEnd(Player winner, Map<Player, Move> playersMoves) {
        if (winner == null) {
            textIO.getTextTerminal().println(
                    "It is a "
                    + CommandLineUtils.fgColor(RED, "TIE")
                    + ". Both players chose "
                    + playersMoves.values().stream().findFirst().get().getEmoji()
                    + " this round! Let's try once more...");
        } else {
            textIO.getTextTerminal().println(
                    "Winner of this round is "
                    + CommandLineUtils.fgColor(GREEN, winner.getName())
                    + " !");
            textIO.getTextTerminal().println(
                    "He chose "
                    + playersMoves.get(winner).getEmoji()
                    + " over "
                    + playersMoves.values().stream()
                            .filter(move -> move != playersMoves.get(winner))
                            .findFirst().get().getEmoji());
        }
        textIO.newStringInputReader()
                .withMinLength(-1)
                .read("Continue? Press enter to continue...");
        textIO.getTextTerminal().println();
    }

    @Override
    protected void results() {
        Map.Entry<Player, Integer> gameWinner = getGameWinner();
        if (gameWinner.getKey() == null) { //tie case
            textIO.getTextTerminal().println("Nobody won this time...");
        } else {
            textIO.getTextTerminal().println(
                    "Winner of the Game is "
                    + CommandLineUtils.fgColor(GREEN, gameWinner.getKey().getName())
                    + " with score of "
                    + CommandLineUtils.fgColorAndTextStyle(RED, UNDERLINE, gameWinner.getValue().toString())
                    + " out of "
                    + CommandLineUtils.fgColor(BLUE, roundsCount.toString())
                    + "! Congratulations!");
        }
    }

    @Override
    protected void exit() {
        textIO.newStringInputReader()
                .withMinLength(-1)
                .read("Press enter to exit...");
        TextIoFactory.getTextIO().getTextTerminal().abort();
    }
}
