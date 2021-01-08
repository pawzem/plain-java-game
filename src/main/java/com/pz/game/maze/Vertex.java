package com.pz.game.maze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class Vertex {
    private final Set<Integer> edges;
    private UUID obstacleId;

    static Vertex emptyVertex(){
        return new Vertex();
    }

    private Vertex() {
        this.edges = new HashSet<>();
        this.obstacleId = null;
    }

    List<Integer> addEdge(int vertexId){
        edges.add(vertexId);
        return List.copyOf(edges);
    }

    void addObstacle(UUID obstacleId){
        this.obstacleId = obstacleId;
    }

    void removeObstacle(){
        this.obstacleId = null;
    }

    UUID getObstacle(){
        return this.obstacleId;
    }

    List<Integer> getEdges() {
        return List.copyOf(edges);
    }

}
