package com.pz.game.character;

import com.pz.game.test.CharacterTest;
import com.pz.game.test.Difficulty;
import com.pz.game.test.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class characterAggregateTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldPassTest() {
        //given
        var stat = 50;
        var character = CharacterFactory.of(12,stat,stat, stat,stat);
        var test = new CharacterTest(Character.Characteristics.DODGE, Difficulty.NORMAL);

        //when
        var result = character.test(test, 50);

        //then
        assertEquals(new Result(true, 0), result);
    }

    @Test
    void shouldFailHardTest() {
        //given
        var stat = 50;
        var character = CharacterFactory.of(12,stat,stat, stat,stat);
        var test = new CharacterTest(Character.Characteristics.DODGE, Difficulty.HARD);

        //when
        var result = character.test(test, 50);

        //then
        assertEquals(new Result(false, 1), result);
    }

    @Test
    void shouldPassEasyTest() {
        //given
        var stat = 40;
        var character = CharacterFactory.of(12,stat,stat, stat,stat);
        var test = new CharacterTest(Character.Characteristics.DODGE, Difficulty.EASY);

        //when
        var result = character.test(test, 50);

        //then
        assertEquals(new Result(true, 0), result);
    }

    @Test
    void shouldDealDamageOnSuccessfulTest() {
        //given
        var strength = 75;
        var character = CharacterFactory.of(12,50,50, strength,50);
        int successLevels = 3;
        var testResult = Result.success(successLevels);

        //when
        Damage damage = character.dealDamage(testResult);

        //then
        assertEquals(strength /10 + successLevels, damage.getHitPoints());
    }

    @Test
    void shouldSurviveSmallAttack() {
        //given
        var attackerStat = 90;
        var attacker = CharacterFactory.of(12,attackerStat,attackerStat, attackerStat,attackerStat);
        Damage attackerDamage = attacker.dealDamage(Result.success(6));

        var toughness = 60;
        var character = CharacterFactory.of(12,50,50, 50,toughness);

        //when
        Condition condition = character.receiveDamage(attackerDamage);

        //then
        assertEquals(new Condition(Condition.State.ALIVE, 3), condition);
    }

    @Test
    void shouldDieFromWounds() {
        //given
        var attackerStat = 90;
        var attacker = CharacterFactory.of(12,attackerStat,attackerStat, attackerStat,attackerStat);
        Damage attackerDamage = attacker.dealDamage(Result.success(6));

        var toughness = 60;
        var character = CharacterFactory.of(12,50,50, 50,toughness);
        character.receiveDamage(attackerDamage);

        //when
        Condition condition = character.receiveDamage(attackerDamage);

        //then
        assertEquals(new Condition(Condition.State.DEAD, 0), condition);
    }


    @Test
    void shouldGainExperience(){
        //given
        var stat = 40;
        int initialExperience = 100;
        var character = CharacterFactory.of(12, initialExperience,stat,stat, stat,stat);
        var receivedExperience = 300;

        //when
        int result = character.gainExperience(receivedExperience);

        //then
        assertEquals(initialExperience + receivedExperience, result);
    }

    @Test
    void shouldAdvanceCharacteristics() throws Exception{
        //given
        var stat = 40;
        var character = CharacterFactory.of(12, 0,stat,stat, stat,stat);
        character.gainExperience(400);
        var advancements = 2;

        //when
        var result =character.advance(Character.Characteristics.ATTACK, advancements);

        //then
        assertEquals(stat + advancements, result);
    }

    @Test
    void shouldForbidAdvancementForToManyPoints() throws Exception{
        //given
        var stat = 40;
        var character = CharacterFactory.of(12, 0,stat,stat, stat,stat);
        character.gainExperience(400);
        var advancements = 8;

        //when
        //then
        assertThrows(InvalidAdvancementException.class, () ->character.advance(Character.Characteristics.ATTACK, advancements));

    }

    @Test
    void shouldResurrectHero() throws Exception{
        //given
        var stat = 40;
        var character = CharacterFactory.of(12, 0,stat,stat, stat,stat);
        character.receiveDamage(new Damage(Damage.AREA.HEAD, 1000));

        //when
        character.heal(10);

        //then
        assertEquals(Condition.State.ALIVE, character.getState());

    }

}