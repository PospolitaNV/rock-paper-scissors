package com.github.pospolitanv.rock_paper_scissors.game;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import javax.annotation.CheckForNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main game class providing game engine via Template Method pattern
 */
public abstract class Game {

    protected List<Player> players = new ArrayList<>();
    protected Integer roundsCount;
    protected MovesGameMode gameMode;
    protected Map<Player, List<Move>> history; // history for analytics or for AI moves based on previous decisions
    protected Map<Player, Integer> score;

    public void init() {
        initialScreen();
        gameMode = initGameMode();
        players = initPlayers();
        initScore();
        initHistory();

        roundsCount = initRounds();
        clear();
    }

    abstract protected void initialScreen();

    abstract protected List<Player> initPlayers();

    private void initScore() {
        this.score = players.stream().collect(Collectors.toMap(
                player -> player,
                player -> 0
        ));
    }

    private void initHistory() {
        this.history = players.stream().collect(Collectors.toMap(
                player -> player,
                player -> new ArrayList<>()
        ));
    }

    abstract protected MovesGameMode initGameMode();

    abstract protected int initRounds();

    abstract protected void clear();

    public void play() {
        for (int round = 0; round < roundsCount; round++) {
            playRound(round);
        }

        results();

        exit();
    }

    private void playRound(int round) {
        roundStart(round);

        Map<Player, Move> playersMoves = getPlayersMoves();

        Player winner = getWinner(playersMoves);

        changeScore(winner);
        updateHistory(playersMoves);

        roundEnd(winner, playersMoves);
    }

    private void updateHistory(Map<Player, Move> playersMoves) {
        this.history.forEach((player, moves) -> moves.add(playersMoves.get(player)));
    }

    private void changeScore(Player winner) {
        if (winner == null)
            return;
        score.put(winner, score.get(winner) + 1);
    }

    @CheckForNull
    private Player getWinner(Map<Player, Move> playersMoves) {
        ArrayList<Map.Entry<Player, Move>> playersMovesList = new ArrayList<>(playersMoves.entrySet());
        Map.Entry<Player, Move> firstPlayerMove = playersMovesList.get(0);
        Map.Entry<Player, Move> secondPlayerMove = playersMovesList.get(1);
        //there is actually a general solution to find a winner for odd moves count
        int result = (gameMode.getMovesCount()
                      + firstPlayerMove.getValue().getOrder() - secondPlayerMove.getValue().getOrder()
                     ) % gameMode.getMovesCount();
        if (result == 0)
            return null;
        else if (result % 2 == 1)
            return firstPlayerMove.getKey();
        else // result % 2 == 0
            return secondPlayerMove.getKey();
    }

    private Map<Player, Move> getPlayersMoves() {
        return players.stream().collect(Collectors.toMap(player -> player, player -> player.getMove(gameMode, history)));
    }

    protected abstract void roundStart(int roundNum);

    protected abstract void roundEnd(Player winner, Map<Player, Move> playersMoves);

    abstract protected void results();

    abstract protected void exit();

    public Map.Entry<Player, Integer> getGameWinner() {
        if (score.values().stream().distinct().count() == 1) // if it's a tie
            return new AbstractMap.SimpleEntry<>(null, roundsCount / 2);

        return score.entrySet().stream().max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(null, roundsCount / 2));
    }

    //bunch of getters to interact with game and for testing. Uses defensive copying for data safety
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    public Integer getRoundsCount() {
        return this.roundsCount;
    }

    public Map<Player, List<Move>> getHistory() {
        return new HashMap<>(this.history);
    }

    public Map<Player, Integer> getScore() {
        return new HashMap<>(this.score);
    }
}
