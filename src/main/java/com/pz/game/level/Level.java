package com.pz.game.level;

import com.pz.game.character.Character;
import com.pz.game.dice.Dice;
import com.pz.game.maze.Maze;
import com.pz.game.test.CharacterTest;
import com.pz.game.test.Difficulty;
import com.pz.game.test.Result;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Level {

    public enum LevelResult {
        SUCCESS,
        FAILURE,
        PROGRESS
    }

    private final Dice dice;
    private final Maze maze;
    private final int startingVertex;
    private final int finalVertex;
    private final Map<UUID, Character> obstacles;
    private Integer currentVertex;

    Level(Dice dice,
          int startingVertex,
          int finalVertex,
          Maze maze,
          Map<UUID, Character> obstacles) {
        this.dice = dice;
        this.maze = maze;
        this.startingVertex = startingVertex;
        this.finalVertex = finalVertex;
        this.currentVertex = startingVertex;
        this.obstacles = obstacles;
    }

    public LevelResult travel(Character hero, int vertex){
        this.currentVertex = vertex;
        maze.getObstacle(vertex).ifPresent( obstacle -> {
            fight(hero, obstacles.get(obstacle));
        });

        if(!hero.isAlive()){
            return LevelResult.FAILURE;
        }

        if(hero.isAlive() && this.currentVertex == this.finalVertex){
            return LevelResult.SUCCESS;
        }

        return LevelResult.PROGRESS;
    }

    public List<Integer> getOptions() {
        return maze.getNeighbours(currentVertex);
    }

    private boolean fight(Character hero, Character obstacle) {
        while(hero.isAlive() && obstacle.isAlive()){
            System.out.println("Hero attacks foe!");
            //todo difficulty policy
            Result attackTest = hero.test(new CharacterTest(Character.Characteristics.ATTACK, Difficulty.NORMAL), dice.k100());
            if(attackTest.isSuccess() && obstacle.test(new CharacterTest(Character.Characteristics.DODGE, Difficulty.NORMAL), dice.k100()).isSuccess()){
                obstacle.receiveDamage(hero.dealDamage(attackTest));
                System.out.println("Foe hit!!!");
            } else {
                System.out.println("Foe missed!!!");
            }

            if(!obstacle.isAlive()){
                continue;
            }

            System.out.println("Foe attacks hero!");
            Result foeAttackTest = obstacle.test(new CharacterTest(Character.Characteristics.ATTACK, Difficulty.NORMAL), dice.k100());
            if(foeAttackTest.isSuccess() && hero.test(new CharacterTest(Character.Characteristics.DODGE, Difficulty.NORMAL), dice.k100()).isSuccess()){
                hero.receiveDamage(obstacle.dealDamage(attackTest));
                System.out.println("Hero hit!!!");
            } else {
                System.out.println("Hero missed!!!");
            }
        }

        if(hero.isAlive()){
            //todo experience policy
            System.out.println("Hero won!");
            hero.gainExperience(300);
        } else {
            System.out.println("Hero died");
        }

        return hero.isAlive();
    }
}
