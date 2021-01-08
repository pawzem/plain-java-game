package com.pz.game.character;

import java.util.EnumMap;
import java.util.UUID;

public class CharacterFactory {
    private CharacterFactory() {
    }

    public static Character of(int hitPoints, int experience, int attack, int dodge, int strength, int toughness){
        var stats = buildStats(attack, dodge, strength, toughness);

        return new Character(stats, hitPoints, experience);
    }

    public static Character of(int hitPoints, int attack, int dodge, int strength, int toughness){
        return of(hitPoints, 0, attack, dodge, strength, toughness);
    }

    static Character of(UUID id, int hitPoints, int experience, int attack, int dodge, int strength, int toughness, Condition.State state){
        var stats = buildStats(attack, dodge, strength, toughness);

        return new Character(stats,hitPoints,experience,id, state);
    }

    private static EnumMap<Character.Characteristics, Integer> buildStats(int attack, int dodge, int strength, int toughness) {
        var stats = new EnumMap<Character.Characteristics, Integer>(Character.Characteristics.class);
        stats.put(Character.Characteristics.ATTACK, attack);
        stats.put(Character.Characteristics.DODGE, dodge);
        stats.put(Character.Characteristics.STRENGTH, strength);
        stats.put(Character.Characteristics.TOUGHNESS, toughness);

        return stats;
    }

}
