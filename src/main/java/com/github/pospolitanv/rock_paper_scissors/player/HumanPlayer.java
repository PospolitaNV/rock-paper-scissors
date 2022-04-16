package com.github.pospolitanv.rock_paper_scissors.player;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.strategy.HumanStrategy;

import java.util.List;
import java.util.Map;

public class HumanPlayer implements Player {

    private final String name;
    private final HumanStrategy humanStrategy;

    public HumanPlayer(String name, HumanStrategy humanStrategy) {
        this.name = name;
        this.humanStrategy = humanStrategy;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Move getMove(MovesGameMode gameMode, Map<Player, List<Move>> history) {
        return humanStrategy.nextMove(gameMode);
    }
}
