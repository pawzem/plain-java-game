package com.pz.game.maze;

import com.pz.game.generic.repository.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Maze implements Entity<String> {

    private final String name;
    private final Map<Integer, Vertex> maze;

    public Maze(String name) {
        this.name = name;
        this.maze = new HashMap<>();
    }

    public void addEdge(int startingVertex, int endingVertex){
        maze.putIfAbsent(startingVertex, Vertex.emptyVertex());

        maze.computeIfPresent(startingVertex, (key, vertex) -> {
            vertex.addEdge(endingVertex);
            return vertex;
        });
    }

    public void addObstacle(int vertexId, UUID obstacleId){
        maze.putIfAbsent(vertexId, Vertex.emptyVertex());
        maze.get(vertexId).addObstacle(obstacleId);
    }

    public void removeObstacle(int vertexId){
        maze.putIfAbsent(vertexId, Vertex.emptyVertex());
        maze.get(vertexId).removeObstacle();
    }

    public List<Integer> getNeighbours(int vertexId) {
        Vertex vertex = this.maze.getOrDefault(vertexId, Vertex.emptyVertex());

        return vertex.getEdges();
    }

    public Optional<UUID> getObstacle(int vertexId) {
        UUID obstacle = maze.getOrDefault(vertexId, Vertex.emptyVertex()).getObstacle();
        return Optional.ofNullable(obstacle);
    }

    @Override
    public String getId() {
        return this.name;
    }

    List<Integer> getVertexes(){
        return List.copyOf(this.maze.keySet());
    }
}
