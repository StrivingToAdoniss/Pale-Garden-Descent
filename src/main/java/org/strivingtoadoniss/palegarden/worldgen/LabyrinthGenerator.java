package org.strivingtoadoniss.palegarden.worldgen;

import lombok.Setter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class LabyrinthGenerator {

    private static final int CELL_SIZE = 4;
    private static final int GRID_SIZE = 4;
    private static final int WALL_HEIGHT = 5;
    @Setter
    private static ServerWorld world;

    private static final Map<ChunkPos, boolean[][][]> generatedChunks = new HashMap<>();

    public static void generateLabyrinthChunk(ChunkPos chunkPos) {
        boolean[][][] grid = generateMazeGridForChunk(chunkPos);
        generatedChunks.put(chunkPos, grid);
    }

    public static void generateLabyrinthChunkIfNotInfected(ChunkPos chunkPos) {
        if (!isChunkInfected(chunkPos)) {
            generateLabyrinthChunk(chunkPos);
        }
    }

    public static boolean shouldPlaceWallAt(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        boolean[][][] grid = generatedChunks.get(chunkPos);
        if (grid == null) {
            return false;
        }

        int localX = Math.floorMod(pos.getX(), 16);
        int localZ = Math.floorMod(pos.getZ(), 16);

        int cellX = localX / CELL_SIZE;
        int cellZ = localZ / CELL_SIZE;

        int inCellX = localX % CELL_SIZE;
        int inCellZ = localZ % CELL_SIZE;

        if (inCellZ == CELL_SIZE - 1 && !grid[cellX][cellZ][0] || inCellX == CELL_SIZE - 1 && !grid[cellX][cellZ][1]) {
            return true;
        }

        return localX == 15 && !grid[cellX][cellZ][1] || localZ == 15 && !grid[cellX][cellZ][0];
    }

    public static int getWallHeight() {
        return WALL_HEIGHT;
    }

    private static boolean[][][] generateMazeGridForChunk(ChunkPos chunkPos) {
        int expandedSize = GRID_SIZE * 3;
        boolean[][][] expandedGrid = new boolean[expandedSize][expandedSize][2];
        boolean[][] visited = new boolean[expandedSize][expandedSize];

        Random globalRandom = new Random(world.getSeed() ^ ((chunkPos.x - 1) * 341873128712L + (chunkPos.z - 1) * 132897987541L));

        Deque<int[]> stack = new ArrayDeque<>();

        int startX = globalRandom.nextInt(expandedSize);
        int startZ = globalRandom.nextInt(expandedSize);

        stack.push(new int[] { startX, startZ });
        visited[startX][startZ] = true;

        // Recursive Backtracker (DFS)
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int z = current[1];

            List<int[]> neighbors = getUnvisitedNeighbors(x, z, visited, expandedSize);

            if (!neighbors.isEmpty()) {
                int[] next = neighbors.get(globalRandom.nextInt(neighbors.size()));
                int nx = next[0];
                int nz = next[1];

                removeWall(expandedGrid, x, z, nx, nz);

                visited[nx][nz] = true;
                stack.push(next);
            } else {
                stack.pop();
            }
        }

        boolean[][][] chunkGrid = new boolean[GRID_SIZE][GRID_SIZE][2];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int z = 0; z < GRID_SIZE; z++) {
                chunkGrid[x][z][0] = expandedGrid[x + GRID_SIZE][z + GRID_SIZE][0];
                chunkGrid[x][z][1] = expandedGrid[x + GRID_SIZE][z + GRID_SIZE][1];
            }
        }

        return chunkGrid;
    }

    private static List<int[]> getUnvisitedNeighbors(int x, int z, boolean[][] visited, int gridSize) {
        List<int[]> neighbors = new ArrayList<>();

        if (z > 0 && !visited[x][z - 1]) {
            neighbors.add(new int[] { x, z - 1 });
        }
        if (z < gridSize - 1 && !visited[x][z + 1]) {
            neighbors.add(new int[] { x, z + 1 });
        }
        if (x > 0 && !visited[x - 1][z]) {
            neighbors.add(new int[] { x - 1, z });
        }
        if (x < gridSize - 1 && !visited[x + 1][z]) {
            neighbors.add(new int[] { x + 1, z });
        }

        return neighbors;
    }

    private static void removeWall(boolean[][][] grid, int x1, int z1, int x2, int z2) {
        if (x1 == x2) {
            if (z1 < z2) {
                grid[x1][z1][0] = true;
            } else {
                grid[x2][z2][0] = true;
            }
        } else {
            if (x1 < x2) {
                grid[x1][z1][1] = true;
            } else {
                grid[x2][z2][1] = true;
            }
        }
    }

    public static boolean isChunkInfected(ChunkPos chunkPos) {
        return generatedChunks.containsKey(chunkPos);
    }
}
