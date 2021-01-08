package com.pz.game.character;

class AdvancementCalculationPolicy {
    private static final int EXPERIENCE_MODIFIER = 100;

    public int availableAdvancement(Character character){
        return character.getExperience() / EXPERIENCE_MODIFIER;
    }

    public int  experienceCost(int points){
        return points * EXPERIENCE_MODIFIER;
    }

}
