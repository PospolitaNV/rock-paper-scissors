package com.github.pospolitanv.rock_paper_scissors.strategy;



import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import java.util.List;
import java.util.Map;

/**
 * Provides ability to implement different player's strategies
 */
public interface ComputerStrategy {

    Move nextMove(Player player, MovesGameMode gameMode, Map<Player, List<Move>> history);

    boolean isApplicable(MovesGameMode gameMode);

    default List<Move> enemyMoves(Player player, Map<Player, List<Move>> history) {
        return history.entrySet().stream()
                .filter(playerEntry -> playerEntry.getKey() != player)
                .map(Map.Entry::getValue)
                .findFirst()
                .get();
    }
}
