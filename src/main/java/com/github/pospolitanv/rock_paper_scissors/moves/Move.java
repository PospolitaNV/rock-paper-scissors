package com.github.pospolitanv.rock_paper_scissors.moves;

public enum Move {
    ROCK(1, "🪨"),
    PAPER(2, "📄"),
    SCISSORS(3, "✂️"),
    LIZARD(4, "🦎"),
    SPOCK(5, "🖖");

    private final Integer order;
    private final String emoji;

    Move(Integer order, String emoji) {
        this.order = order;
        this.emoji = emoji;
    }

    public Integer getOrder() {
        return this.order;
    }

    public String getEmoji() {
        return emoji;
    }
}
