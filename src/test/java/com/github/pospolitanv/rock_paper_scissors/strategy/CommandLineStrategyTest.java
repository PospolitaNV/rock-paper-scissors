package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandLineStrategyTest {

    private final MockTextTerminal terminal = new MockTextTerminal();


    @Test
    public void commandLineInputStrategyWithThreeMoves() {
        MovesGameMode gameMode = MovesGameMode.ROCK_PAPER_SCISSORS;
        CommandLineInputStrategy strategy =
                new CommandLineInputStrategy(gameMode, terminal);

        //valid moves
        terminal.getInputs().add("1");
        Assertions.assertEquals(Move.ROCK, strategy.nextMove(gameMode));
        terminal.getInputs().add("2");
        Assertions.assertEquals(Move.PAPER, strategy.nextMove(gameMode));
        terminal.getInputs().add("3");
        Assertions.assertEquals(Move.SCISSORS, strategy.nextMove(gameMode));

        //invalid moves are causing exception because wait for input again
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            terminal.getInputs().add("4");
            strategy.nextMove(gameMode);
        });

        //invalid moves are causing exception because wait for input again
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            terminal.getInputs().add("asdf");
            strategy.nextMove(gameMode);
        });

    }

    @Test
    public void commandLineInputStrategyWithFiveMoves() {
        MovesGameMode gameMode = MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK;
        CommandLineInputStrategy strategy =
                new CommandLineInputStrategy(gameMode, terminal);

        //valid moves
        terminal.getInputs().add("1");
        Assertions.assertEquals(Move.ROCK, strategy.nextMove(gameMode));
        terminal.getInputs().add("2");
        Assertions.assertEquals(Move.PAPER, strategy.nextMove(gameMode));
        terminal.getInputs().add("3");
        Assertions.assertEquals(Move.SCISSORS, strategy.nextMove(gameMode));
        terminal.getInputs().add("4");
        Assertions.assertEquals(Move.LIZARD, strategy.nextMove(gameMode));
        terminal.getInputs().add("5");
        Assertions.assertEquals(Move.SPOCK, strategy.nextMove(gameMode));

        //invalid moves are causing exception because wait for input again
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            terminal.getInputs().add("6");
            strategy.nextMove(gameMode);
        });

        //invalid moves are causing exception because wait for input again
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            terminal.getInputs().add("asdf");
            strategy.nextMove(gameMode);
        });

    }

}
