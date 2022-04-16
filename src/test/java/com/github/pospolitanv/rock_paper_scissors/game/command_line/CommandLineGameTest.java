package com.github.pospolitanv.rock_paper_scissors.game.command_line;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.ComputerPlayer;
import com.github.pospolitanv.rock_paper_scissors.player.HumanPlayer;
import com.github.pospolitanv.rock_paper_scissors.player.Player;
import com.github.pospolitanv.rock_paper_scissors.strategy.CommandLineInputStrategy;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandLineGameTest {

    private MockTextTerminal textTerminal;
    private CommandLineGame game;

    @Test
    void initialScreen() {
        game.initialScreen();
        assertEquals(
                "Welcome to the \u001B[107;31mRock-\u001B[m\u001B[107;32mPaper-\u001B[m\u001B[107;34mScissors\u001B[m Game!",
                textTerminal.getOutput());
    }

    @BeforeEach
    public void setupTerminal() {
        textTerminal = new MockTextTerminal();
        game = spy(new CommandLineGame(textTerminal));
    }

    @Test
    void threeMovesGameHumanVsHumanFiveRoundsFirstPlayerWins() {
        textTerminal.getInputs().add("1");
        textTerminal.getInputs().add("1");
        textTerminal.getInputs().add("John");
        textTerminal.getInputs().add("Peter");
        textTerminal.getInputs().add("5");
        game.init();

        initStateCheck();

        //let's play five rounds
        for (int i = 0; i < 5; i++) {
            textTerminal.getInputs().add("1"); //rock
            textTerminal.getInputs().add("3"); //scissors
            textTerminal.getInputs().add(" "); //skip end of round
        }
        textTerminal.getInputs().add(" "); //skip end of game

        game.play();

        endGameStateCheck(true);
    }

    @Test
    void threeMovesGameHumanVsHumanFiveRoundsSecondPlayerWins() {
        textTerminal.getInputs().add("1");
        textTerminal.getInputs().add("1");
        textTerminal.getInputs().add("John");
        textTerminal.getInputs().add("Peter");
        textTerminal.getInputs().add("5");
        game.init();

        initStateCheck();

        //let's play five rounds
        for (int i = 0; i < 5; i++) {
            textTerminal.getInputs().add("1"); //rock
            textTerminal.getInputs().add("2"); //paper
            textTerminal.getInputs().add(" "); //skip end of round
        }
        textTerminal.getInputs().add(" "); //skip end of game

        game.play();

        endGameStateCheck(false);
    }



    @Test
    void fiveMovesGameHumanVsComputerFiveRoundsFirstPlayerWins() {
        textTerminal.getInputs().add("2");
        textTerminal.getInputs().add("5");
        ComputerPlayer computerPlayer = mock(ComputerPlayer.class);
        doReturn(Arrays.asList(
                new HumanPlayer("John",
                        new CommandLineInputStrategy(MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK, textTerminal)),
                computerPlayer))
                .when(game).initPlayers();
        when(computerPlayer.getMove(any(), anyMap())).thenReturn(Move.SPOCK);
        when(computerPlayer.getName()).thenReturn("Peter");
        game.init();

        initStateCheck();
        //let's play five rounds
        for (int i = 0; i < 5; i++) {
            textTerminal.getInputs().add("1"); //rock
            textTerminal.getInputs().add(" "); //skip end of round
        }
        textTerminal.getInputs().add(" "); //skip end of game

        game.play();

        endGameStateCheck(true);
    }

    @Test
    void fiveMovesGameComputerVsComputerFiveRoundsTie() {
        textTerminal.getInputs().add("2");
        textTerminal.getInputs().add("5");
        ComputerPlayer computerPlayerFirst = mock(ComputerPlayer.class);
        ComputerPlayer computerPlayerSecond = mock(ComputerPlayer.class);
        doReturn(Arrays.asList(computerPlayerFirst, computerPlayerSecond))
                .when(game).initPlayers();
        when(computerPlayerFirst.getMove(any(), anyMap())).thenReturn(Move.SPOCK);
        when(computerPlayerFirst.getName()).thenReturn("John");
        when(computerPlayerSecond.getMove(any(), anyMap())).thenReturn(Move.SPOCK);
        when(computerPlayerSecond.getName()).thenReturn("Peter");
        game.init();

        initStateCheck();
        //let's play five rounds
        for (int i = 0; i < 5; i++) {
            textTerminal.getInputs().add(" "); //skip end of round
        }
        textTerminal.getInputs().add(" "); //skip end of game

        game.play();

        endGameStateCheckTie();
    }

    @Test
    public void initPlayersHumanAndComputer() {
        textTerminal.getInputs().add("2");
        textTerminal.getInputs().add("2");
        textTerminal.getInputs().add("John");
        textTerminal.getInputs().add("5");
        game.init();
        assertTrue(game.getPlayers().get(0) instanceof HumanPlayer);
        assertTrue(game.getPlayers().get(1) instanceof ComputerPlayer);
    }

    @Test
    public void initPlayersComputerAndComputer() {
        textTerminal.getInputs().add("2");
        textTerminal.getInputs().add("3");
        textTerminal.getInputs().add("5");
        game.init();
        assertTrue(game.getPlayers().get(0) instanceof ComputerPlayer);
        assertTrue(game.getPlayers().get(1) instanceof ComputerPlayer);
    }

    private void initStateCheck() {
        verify(game, times(1)).initialScreen();

        Player firstPlayer = game.getPlayers().get(0);
        Player secondPlayer = game.getPlayers().get(1);

        assertEquals("John", firstPlayer.getName());
        assertEquals("Peter", secondPlayer.getName());

        //history is clear
        Map<Player, List<Move>> history = game.getHistory();
        assertEquals(2, history.size());
        assertTrue(history.get(firstPlayer).isEmpty());
        assertTrue(history.get(secondPlayer).isEmpty());

        //score is clear and set to zero
        Map<Player, Integer> score = game.getScore();
        assertEquals(2, score.size());
        assertEquals(0, score.get(firstPlayer));
        assertEquals(0, score.get(secondPlayer));

        assertEquals(5, game.getRoundsCount());
    }

    private void endGameStateCheck(boolean winnerIsFirst) {
        verify(game, times(5)).roundStart(anyInt());

        Player firstPlayer = game.getPlayers().get(0);
        Player secondPlayer = game.getPlayers().get(1);
        verify(game, times(5))
                .roundEnd(winnerIsFirst ? eq(firstPlayer) : eq(secondPlayer), anyMap());
        verify(game, times(1)).results();


        //history is clear
        Map<Player, List<Move>> history = game.getHistory();
        assertEquals(2, history.size());
        assertEquals(5, history.get(firstPlayer).size());
        assertEquals(5, history.get(secondPlayer).size());

        if (winnerIsFirst)
            checkWinnerState(firstPlayer, secondPlayer);
        else
            checkWinnerState(secondPlayer, firstPlayer);

    }

    private void checkWinnerState(Player firstPlayer, Player secondPlayer) {
        //score is clear and set to zero
        Map<Player, Integer> score = game.getScore();
        assertEquals(2, score.size());
        assertEquals(5, score.get(firstPlayer));
        assertEquals(0, score.get(secondPlayer));

        Map.Entry<Player, Integer> gameWinner = game.getGameWinner();
        assertEquals(firstPlayer, gameWinner.getKey());
        assertEquals(5, gameWinner.getValue());
    }

    private void endGameStateCheckTie() {
        verify(game, times(5)).roundStart(anyInt());

        Player firstPlayer = game.getPlayers().get(0);
        Player secondPlayer = game.getPlayers().get(1);
        verify(game, times(5)).roundEnd(isNull(), anyMap());
        verify(game, times(1)).results();


        //history is clear
        Map<Player, List<Move>> history = game.getHistory();
        assertEquals(2, history.size());
        assertEquals(5, history.get(firstPlayer).size());
        assertEquals(5, history.get(secondPlayer).size());

        //score is clear and set to zero
        Map<Player, Integer> score = game.getScore();
        assertEquals(2, score.size());
        assertEquals(0, score.get(firstPlayer));
        assertEquals(0, score.get(secondPlayer));

        Map.Entry<Player, Integer> gameWinner = game.getGameWinner();
        assertNull(gameWinner.getKey());
        assertEquals(2, gameWinner.getValue());
    }


}
