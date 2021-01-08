package com.pz.game.character;

import java.util.Objects;

public class Condition {

    public enum State{
        ALIVE,
        DEAD
    }
    private int hitPoints;
    private State state;

    public Condition(State state, int hitPoints) {
        this.state =state;
        this.hitPoints = hitPoints;
    }

    int getHitPoints() {
        return hitPoints;
    }

    State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return hitPoints == condition.hitPoints &&
                state == condition.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hitPoints, state);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "hitPoints=" + hitPoints +
                ", state=" + state +
                '}';
    }
}
