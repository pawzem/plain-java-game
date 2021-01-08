package com.pz.game.test;

public enum Difficulty {
    EASY(-10),
    NORMAL(0),
    HARD(+10);

    private final int modifier;

    Difficulty(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }
}
