package com.pz.game.test;

import com.pz.game.character.Character;

import java.util.Objects;

public class CharacterTest {

    private final Character.Characteristics characteristic;
    private final Difficulty difficulty;

    public CharacterTest(Character.Characteristics characteristic, Difficulty difficulty) {
        this.characteristic = characteristic;
        this.difficulty = difficulty;
    }

    public Character.Characteristics getCharacteristic() {
        return characteristic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterTest that = (CharacterTest) o;
        return characteristic == that.characteristic &&
                difficulty == that.difficulty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(characteristic, difficulty);
    }
}
