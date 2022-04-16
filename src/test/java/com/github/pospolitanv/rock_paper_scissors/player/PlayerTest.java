package com.github.pospolitanv.rock_paper_scissors.player;

import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.strategy.ComputerStrategy;
import com.github.pospolitanv.rock_paper_scissors.strategy.HumanStrategy;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerTest {

    @Test
    public void humanPlayerSimpleTest() {
        HumanStrategy humanStrategy = mock(HumanStrategy.class);
        HumanPlayer player = new HumanPlayer("Test", humanStrategy);
        player.getMove(MovesGameMode.ROCK_PAPER_SCISSORS, new HashMap<>());
        verify(humanStrategy, times(1)).nextMove(any());
        assertEquals("Test", player.getName());
    }

    @Test
    public void computerPlayersStrategiesAreFairlyRandom() {
        HashSet<Class<? extends ComputerStrategy>> computerStrategies = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            computerStrategies.add(new ComputerPlayer(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK).getStrategy().getClass());
        }
        assertEquals(5, computerStrategies.size());
    }

    @Test
    public void computerPlayersStrategiesAreFairlyRandomAndAreLimitedToGameMode() {
        HashSet<Class<? extends ComputerStrategy>> computerStrategies = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            computerStrategies.add(new ComputerPlayer(MovesGameMode.ROCK_PAPER_SCISSORS).getStrategy().getClass());
        }
        //spock strategy is excluded
        assertEquals(4, computerStrategies.size());
    }
}
