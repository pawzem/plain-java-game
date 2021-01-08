package com.pz.game.maze;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void shouldReturnNeighbours() {
        //given
        var maze = new Maze(UUID.randomUUID().toString());
        maze.addEdge(1,2);
        maze.addEdge(1,3);
        maze.addEdge(1,4);
        maze.addEdge(4,5);

        //when
        List<Integer> neighbours = maze.getNeighbours(1);

        //then
        assertEquals(List.of(2,3,4),neighbours);
    }

    @Test
    void shouldAddObstacle() {
        //given
        var maze = new Maze(UUID.randomUUID().toString());
        maze.addEdge(1,2);
        var obstacleId = UUID.randomUUID();
        var vertexWithObstacle = 2;

        //when
        maze.addObstacle(vertexWithObstacle,obstacleId);

        //then
        assertTrue(maze.getObstacle(vertexWithObstacle).isPresent());
        assertEquals(obstacleId, maze.getObstacle(vertexWithObstacle).get());
    }

    @Test
    void shouldRemoveObstacle() {
        //given
        var maze = new Maze(UUID.randomUUID().toString());
        maze.addEdge(1,2);
        var obstacleId = UUID.randomUUID();
        var vertexWithObstacle = 2;
        maze.addObstacle(vertexWithObstacle,obstacleId);

        //when
        maze.removeObstacle(vertexWithObstacle);

        //then
        assertTrue(maze.getObstacle(vertexWithObstacle).isEmpty());
    }

    @Test
    void shouldNotContainObstacleInEmptyVertex() {
        //given
        var maze = new Maze(UUID.randomUUID().toString());

        //when
        maze.addEdge(1,2);


        //then
        assertTrue(maze.getObstacle(2).isEmpty());
    }
}