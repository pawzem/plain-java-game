package com.pz.game.character;

public class Damage {
    public enum AREA{
        HEAD,
        BODY,
    }

    private final AREA area;
    private final int hitPoints;

    public Damage(AREA area, int hitPoints) {
        this.area = area;
        this.hitPoints = hitPoints;
    }

    public AREA getArea() {
        return area;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
