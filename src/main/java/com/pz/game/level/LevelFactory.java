package com.pz.game.level;

import com.pz.game.character.Character;
import com.pz.game.character.CharacterFactory;
import com.pz.game.dice.Dice;
import com.pz.game.maze.Maze;

import java.util.Map;
import java.util.UUID;

public class LevelFactory {
    public static  Level defaultLevel(Dice dice){

        var weakEnemy = CharacterFactory.of(5,30,30, 30, 30);
        var challengingEnemy = CharacterFactory.of(12,50,50, 50, 50);
        var boss = CharacterFactory.of(16,50,20, 90, 60);

        var maze = new Maze("default");
        var startingVertex = 1;
        var finalVertex = 5;
        maze.addEdge(startingVertex,2);
        maze.addEdge(2,3);
        maze.addEdge(2,4);
        maze.addEdge(3,finalVertex);
        maze.addEdge(4,finalVertex);
        maze.addObstacle(2,weakEnemy.getId());
        maze.addObstacle(4,challengingEnemy.getId());
        maze.addObstacle(5,boss.getId());

        Map<UUID, Character> obstacles = Map.of(weakEnemy.getId(), weakEnemy,
                challengingEnemy.getId(), challengingEnemy,
                boss.getId(), boss);


        return new Level(dice, startingVertex,finalVertex, maze, obstacles);
    }
}
