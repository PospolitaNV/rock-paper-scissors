package com.github.pospolitanv.rock_paper_scissors.player;

public enum PlayersGameMode {
    HUMAN_VS_HUMAN("Human vs Human"),
    HUMAN_VS_COMPUTER("Human vs Computer"),
    COMPUTER_VS_COMPUTER("Computer vs Computer");

    private final String modeName;

    PlayersGameMode(String modeName) {
        this.modeName = modeName;
    }

    public String prettyPrint() {
        return this.modeName;
    }
}
