package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;

/**
 * Provides ability to implement different computer strategies (mostly by input source)
 */
public interface HumanStrategy {

    Move nextMove(MovesGameMode gameMode);

}
