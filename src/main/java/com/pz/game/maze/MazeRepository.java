package com.pz.game.maze;

import com.pz.game.generic.repository.FileRepository;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MazeRepository extends FileRepository<Maze, String> {
    private static final String MAZE_FOLDER_NAME = "maze";
    private static final String STING_SEPARATOR = ";";
    private static final String TOPPLE_SEPARATOR = ",";


    public MazeRepository(String gameFolder) {
        super(Path.of(gameFolder, MAZE_FOLDER_NAME));
    }

    @Override
    protected String serialize(Maze entity) {
        StringBuilder builder = new StringBuilder();
        List<Integer> vertexes = entity.getVertexes();
        builder.append(entity.getId());
        builder.append(STING_SEPARATOR);
        builder.append(vertexes.size());
        builder.append(STING_SEPARATOR);
        vertexes.forEach(v -> {
            builder.append(String.format("%d,%s",v, entity.getObstacle(v)));
            builder.append(STING_SEPARATOR);
        });
        vertexes.forEach(v -> entity.getNeighbours(v)
                .forEach(e -> {
                    builder.append(String.format("%d,%d",v, e));
                    builder.append(STING_SEPARATOR);
                }));
        return builder.toString();
    }

    @Override
    protected Maze deserialize(String serialized) {
        var splitSerializedMaze = Arrays.asList(serialized.split(STING_SEPARATOR));
        var mazeName = splitSerializedMaze.get(0);
        var vertexCount = Integer.parseInt(splitSerializedMaze.get(1));
        var maze = new Maze(mazeName);

        fillObstacles(splitSerializedMaze, vertexCount, maze);
        filEdges(splitSerializedMaze, vertexCount, maze);

        return maze;
    }

    private void filEdges(List<String> splitSerializedMaze, int vertexCount, Maze maze) {
        for(int i = vertexCount +2; i < splitSerializedMaze.size(); i++){
            String[] edge = splitSerializedMaze.get(i).split(TOPPLE_SEPARATOR);
            var startingVertex = Integer.parseInt(edge[0]);
            var endingVertex = Integer.parseInt(edge[1]);
            maze.addEdge(startingVertex, endingVertex);
        }
    }

    private void fillObstacles(List<String> splitSerializedMaze, int vertexCount, Maze maze) {
        for(int i =2; i < vertexCount +2; i++ ){
            String[] obstacle = splitSerializedMaze.get(i).split(TOPPLE_SEPARATOR);
            var obstacleId = UUID.fromString(obstacle[1]);
            var vertexId = Integer.parseInt(obstacle[0]);
            maze.addObstacle(vertexId,obstacleId);
        }
    }
}
