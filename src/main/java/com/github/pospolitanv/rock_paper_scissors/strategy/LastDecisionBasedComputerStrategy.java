package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LastDecisionBasedComputerStrategy implements ComputerStrategy {

    @Override
    public Move nextMove(Player player, MovesGameMode gameMode, Map<Player, List<Move>> history) {
        List<Move> enemyMoves = enemyMoves(player, history);
        if (enemyMoves.isEmpty())
            return new RandomStrategy().nextMove(player, gameMode, history);
        return enemyMoves.get(enemyMoves.size() - 1);
    }

    @Override
    public boolean isApplicable(MovesGameMode gameMode) {
        return true;
    }
}
