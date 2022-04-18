package com.github.pospolitanv.rock_paper_scissors.moves;

/**
 * Available game modes by moves
 */
public enum MovesGameMode {
    ROCK_PAPER_SCISSORS(3, "Original (Rock, Paper, Scissors)"),
    ROCK_PAPER_SCISSORS_LIZARD_SPOCK(5, "Extended (Rock, Paper, Scissors, Lizard, Spock)");

    private final Integer movesCount;
    private final String modeName;

    MovesGameMode(Integer movesCount, String modeName) {
        this.movesCount = movesCount;
        this.modeName = modeName;
    }

    public String prettyPrint() {
        return this.modeName;
    }

    public Integer getMovesCount() {
        return this.movesCount;
    }
}
