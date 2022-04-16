package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.HumanPlayer;
import com.github.pospolitanv.rock_paper_scissors.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ComputerStrategiesTest {

    @Test
    public void rockOnlyStrategyTest() {
        RockOnlyStrategy rockOnlyStrategy = new RockOnlyStrategy();
        Assertions.assertTrue(rockOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK));
        Assertions.assertTrue(rockOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS));
        Assertions.assertEquals(Move.ROCK, rockOnlyStrategy.nextMove(null, null, null));
    }

    @Test
    public void paperOnlyStrategyTest() {
        PaperOnlyStrategy paperOnlyStrategy = new PaperOnlyStrategy();
        Assertions.assertTrue(paperOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK));
        Assertions.assertTrue(paperOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS));
        Assertions.assertEquals(Move.PAPER, paperOnlyStrategy.nextMove(null, null, null));
    }

    @Test
    public void spockOnlyStrategyTest() {
        SpockOnlyStrategy spockOnlyStrategy = new SpockOnlyStrategy();
        Assertions.assertTrue(spockOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK));
        Assertions.assertFalse(spockOnlyStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS));
        Assertions.assertEquals(Move.SPOCK, spockOnlyStrategy.nextMove(null, null, null));
    }

    @Test
    public void randomStrategyFairness() {
        RandomStrategy randomStrategy = new RandomStrategy();
        Assertions.assertTrue(randomStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK));
        Assertions.assertTrue(randomStrategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS));

        HashSet<Move> threeMoves = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            threeMoves.add(randomStrategy.nextMove(null, MovesGameMode.ROCK_PAPER_SCISSORS, null));
        }
        Assertions.assertEquals(3, threeMoves.size());

        HashSet<Move> fiveMoves = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            fiveMoves.add(randomStrategy.nextMove(null, MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, null));
        }
        Assertions.assertEquals(5, fiveMoves.size());
    }

    @Test
    public void lastDecisionBasedComputerStrategy() {
        LastDecisionBasedComputerStrategy strategy = new LastDecisionBasedComputerStrategy();

        Assertions.assertTrue(strategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK));
        Assertions.assertTrue(strategy.isApplicable(MovesGameMode.ROCK_PAPER_SCISSORS));

        Player firstPlayer = new HumanPlayer("first", null);
        Player secondPlayer = new HumanPlayer("second", null);
        HashMap<Player, List<Move>> history = new HashMap<>();
        history.put(firstPlayer, new ArrayList<>());
        history.put(secondPlayer, new ArrayList<>());

        Move move = strategy.nextMove(firstPlayer, MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, history);
        //history is empty, so it's random
        HashSet<Move> fiveMoves = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            fiveMoves.add(strategy.nextMove(firstPlayer, MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, history));
        }
        Assertions.assertEquals(5, fiveMoves.size());

        history.get(firstPlayer).add(Move.PAPER);
        history.get(secondPlayer).add(Move.ROCK);

        //last move
        Assertions.assertSame(Move.ROCK,
                strategy.nextMove(firstPlayer, MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, history));

        history.get(secondPlayer).add(Move.SPOCK);
        Assertions.assertSame(Move.SPOCK,
                strategy.nextMove(firstPlayer, MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, history));

    }

}
