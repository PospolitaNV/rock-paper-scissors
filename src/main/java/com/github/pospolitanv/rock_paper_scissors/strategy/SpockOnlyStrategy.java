package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import java.util.List;
import java.util.Map;

import static com.github.pospolitanv.rock_paper_scissors.moves.Move.SPOCK;

/**
 * Just plays {@link Move.SPOCK } every time
 */
public class SpockOnlyStrategy implements ComputerStrategy {

    @Override
    public Move nextMove(Player player, MovesGameMode gameMode, Map<Player, List<Move>> history) {
        return SPOCK;
    }

    @Override
    public boolean isApplicable(MovesGameMode gameMode) {
        return gameMode == MovesGameMode.ROCK_PAPER_SCISSORS_LIZARD_SPOCK;
    }
}
