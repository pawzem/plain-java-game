package com.pz.game.dice;

import java.util.Random;

public class Dice {

    private final Random random;

    public Dice() {
        this.random = new Random(System.currentTimeMillis());
    }

    public int k100(){
        return random.nextInt() % 100;
    }
}
