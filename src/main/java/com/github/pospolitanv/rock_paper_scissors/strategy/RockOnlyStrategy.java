package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import java.util.List;
import java.util.Map;

import static com.github.pospolitanv.rock_paper_scissors.moves.Move.ROCK;

/**
 * Just plays {@link Move.ROCK } every time
 */
public class RockOnlyStrategy implements ComputerStrategy {

    @Override
    public Move nextMove(Player player, MovesGameMode gameMode, Map<Player, List<Move>> history) {
        return ROCK;
    }

    @Override
    public boolean isApplicable(MovesGameMode gameMode) {
        return true;
    }

}
