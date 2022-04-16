package com.github.pospolitanv.rock_paper_scissors.player;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;

import java.util.List;
import java.util.Map;

public interface Player {

    String getName();

    Move getMove(MovesGameMode gameMode, Map<Player, List<Move>> history);
}
