package com.github.pospolitanv.rock_paper_scissors.strategy;


import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Choose the next {@link Move} at random.
 */
public class RandomStrategy implements ComputerStrategy {

    @Override
    public Move nextMove(Player player, MovesGameMode gameMode, Map<Player, List<Move>> history) {
        List<Move> moves = Arrays.stream(Move.values()).filter(move -> move.getOrder() <= gameMode.getMovesCount())
                .collect(Collectors.toList());
        return moves.get(ThreadLocalRandom.current().nextInt(0, moves.size()));
    }

    @Override
    public boolean isApplicable(MovesGameMode gameMode) {
        return true;
    }

}
