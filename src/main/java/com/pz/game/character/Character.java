package com.pz.game.character;

import com.pz.game.generic.repository.Entity;
import com.pz.game.test.CharacterTest;
import com.pz.game.test.Result;

import java.util.Map;
import java.util.UUID;

import static java.lang.Integer.max;
import static java.lang.Math.abs;

public class Character implements Entity<UUID> {

    public enum Characteristics {
        STRENGTH,
        TOUGHNESS,
        DODGE,
        ATTACK
        ;
    }

    private final UUID id;
    private final Map<Characteristics, Integer> stats;
    private int hitPoints;
    private Condition.State state;
    private int experience;
    private final AdvancementCalculationPolicy advancementCalculationPolicy;

    Character(Map<Characteristics, Integer> stats, int hitPoints, int experience) {
        this.stats = stats;
        this.hitPoints = hitPoints;
        this.state = Condition.State.ALIVE;
        this.experience = experience;
        this.id = UUID.randomUUID();
        this.advancementCalculationPolicy = new AdvancementCalculationPolicy();
    }

    Character(Map<Characteristics, Integer> stats, int hitPoints, int experience, UUID id, Condition.State state) {
        this.stats = stats;
        this.hitPoints = hitPoints;
        this.state = state;
        this.experience = experience;
        this.id = id;
        this.advancementCalculationPolicy = new AdvancementCalculationPolicy();
    }


    public Damage dealDamage(Result testResult){
        //TODO add area calculation policy
        //TODO add damage calculation policy
        Integer value = stats.getOrDefault(Characteristics.STRENGTH, 0) / 10;
        return new Damage(Damage.AREA.BODY, testResult.getLevels() + value);
    }

    public Condition receiveDamage(Damage damage){
        var toughnessBonus = stats.getOrDefault(Characteristics.TOUGHNESS, 0) / 10;
        var incomingDamage = max(0, damage.getHitPoints() - toughnessBonus);
        this.hitPoints = max(0, this.hitPoints - incomingDamage);
        this.state = this.hitPoints == 0 ? Condition.State.DEAD : Condition.State.ALIVE;

        return new Condition(this.state, this.hitPoints);
    }

    public Result test(CharacterTest test, int roll){
        var result =  stats.getOrDefault(test.getCharacteristic(), 0) - (roll + test.getDifficulty().getModifier());
        var successLevels = abs(result / 10);
        return result >= 0 ? Result.success(successLevels) : Result.failure(successLevels);
    }

    public int gainExperience(int receivedExperience) {
        this.experience += receivedExperience;
        return experience;
    }

    public int advance(Characteristics characteristics, int advancements) throws InvalidAdvancementException{
        var availablePoints = advancementCalculationPolicy.availableAdvancement(this);
        if(advancements > availablePoints){
            throw new InvalidAdvancementException(availablePoints, advancements);
        }
        stats.computeIfPresent(characteristics, (key, oldValue) -> oldValue + advancements);
        stats.putIfAbsent(characteristics, advancements);
        experience -= advancements * advancementCalculationPolicy.experienceCost(advancements);

        return stats.get(characteristics);
    }

    public boolean isAlive(){
        return this.state == Condition.State.ALIVE;
    }

    public Character heal(int i) {
        this.hitPoints += i;
        this.state = Condition.State.ALIVE;
        return this;
    }

    public int getAvailableAdvancementPoints(){
        return advancementCalculationPolicy.availableAdvancement(this);
    }

    public UUID getId() {
        return id;
    }

    Map<Characteristics, Integer> getStats() {
        return stats;
    }

    int getHitPoints() {
        return hitPoints;
    }

    Condition.State getState() {
        return state;
    }

    int getExperience() {
        return experience;
    }
}
