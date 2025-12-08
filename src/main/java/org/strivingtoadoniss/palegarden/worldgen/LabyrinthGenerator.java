package org.strivingtoadoniss.palegarden.worldgen;

import lombok.Setter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public class LabyrinthGenerator {

    private LabyrinthGenerator() {}

    private static final int CELL_SIZE = 4;
    private static final int GRID_SIZE = 4;
    private static final int WALL_HEIGHT = 6;

    @Setter
    private static ServerWorld world;
    @Setter
    private static BlockPos origin;

    private static final Map<ChunkPos, boolean[][][]> generatedChunks = new HashMap<>();
    private static final List<BlockPos> generatedWalls = new ArrayList<>();

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

        return isWallAtPosition(grid, localX, localZ);
    }

    private static boolean isWallAtPosition(boolean[][][] grid, int localX, int localZ) {
        int cellX = localX / CELL_SIZE;
        int cellZ = localZ / CELL_SIZE;

        int inCellX = localX % CELL_SIZE;
        int inCellZ = localZ % CELL_SIZE;

        return isInnerCellWall(grid, cellX, cellZ, inCellX, inCellZ)
            || isChunkBoundaryWall(grid, cellX, cellZ, localX, localZ);
    }

    private static boolean isInnerCellWall(boolean[][][] grid, int cellX, int cellZ, int inCellX, int inCellZ) {
        return (inCellZ == CELL_SIZE - 1 && !grid[cellX][cellZ][0])
            || (inCellX == CELL_SIZE - 1 && !grid[cellX][cellZ][1]);
    }

    private static boolean isChunkBoundaryWall(boolean[][][] grid, int cellX, int cellZ, int localX, int localZ) {
        return (localX == 15 && !grid[cellX][cellZ][1])
            || (localZ == 15 && !grid[cellX][cellZ][0]);
    }

    public static int getWallHeight() {
        return WALL_HEIGHT;
    }

    private static boolean[][][] generateMazeGridForChunk(ChunkPos chunkPos) {
        int expandedSize = GRID_SIZE * 3;
        boolean[][][] expandedGrid = new boolean[expandedSize][expandedSize][2];
        boolean[][] visited = new boolean[expandedSize][expandedSize];

        Random globalRandom = new Random(world.getSeed() ^ ((chunkPos.x - 1) * 341873128712L
            + (chunkPos.z - 1) * 132897987541L));

        performRecursiveBacktracking(expandedGrid, visited, globalRandom, expandedSize, chunkPos);

        return extractChunkGrid(expandedGrid);
    }

    private static void performRecursiveBacktracking(boolean[][][] grid, boolean[][] visited,
        Random random, int gridSize, ChunkPos currentChunkPos) {
        Deque<int[]> stack = new ArrayDeque<>();

        initializeBacktracking(stack, visited, gridSize, currentChunkPos);

        while (!stack.isEmpty()) {
            processCurrentCell(stack, grid, visited, random, gridSize);
        }
    }

    private static void initializeBacktracking(Deque<int[]> stack, boolean[][] visited, int gridSize, ChunkPos currentChunkPos) {
        int startX, startZ;

        if (origin != null) {
            int gridWorldStartX = (currentChunkPos.x - 1) * 16;
            int gridWorldStartZ = (currentChunkPos.z - 1) * 16;

            int targetCellX = (origin.getX() - gridWorldStartX) / CELL_SIZE;
            int targetCellZ = (origin.getZ() - gridWorldStartZ) / CELL_SIZE;

            startX = MathHelper.clamp(targetCellX, 0, gridSize - 1);
            startZ = MathHelper.clamp(targetCellZ, 0, gridSize - 1);
        } else {
            startX = gridSize / 2;
            startZ = gridSize / 2;
        }

        stack.push(new int[]{startX, startZ});
        visited[startX][startZ] = true;
    }

    private static void processCurrentCell(Deque<int[]> stack, boolean[][][] grid, boolean[][] visited, Random random, int gridSize) {
        int[] current = stack.peek();
        int x = current[0];
        int z = current[1];

        List<int[]> neighbors = getUnvisitedNeighbors(x, z, visited, gridSize);

        if (!neighbors.isEmpty()) {
            carvePathToNeighbor(stack, grid, visited, random, neighbors, x, z);
        } else {
            stack.pop();
        }
    }

    private static void carvePathToNeighbor(Deque<int[]> stack, boolean[][][] grid, boolean[][] visited, Random random, List<int[]> neighbors, int x, int z) {
        int[] next = neighbors.get(random.nextInt(neighbors.size()));
        int nx = next[0];
        int nz = next[1];

        removeWall(grid, x, z, nx, nz);

        visited[nx][nz] = true;
        stack.push(next);
    }

    private static boolean[][][] extractChunkGrid(boolean[][][] expandedGrid) {
        boolean[][][] chunkGrid = new boolean[GRID_SIZE][GRID_SIZE][2];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int z = 0; z < GRID_SIZE; z++) {
                chunkGrid[x][z][0] = expandedGrid[x + GRID_SIZE][z + GRID_SIZE][0];
                chunkGrid[x][z][1] = expandedGrid[x + GRID_SIZE][z + GRID_SIZE][1];
            }
        }
        return chunkGrid;
    }

    private static List<int[]> getUnvisitedNeighbors(int x, int z, boolean[][] visited,
        int gridSize) {
        List<int[]> neighbors = new ArrayList<>();

        addNorthNeighbor(neighbors, x, z, visited);
        addSouthNeighbor(neighbors, x, z, visited, gridSize);
        addWestNeighbor(neighbors, x, z, visited);
        addEastNeighbor(neighbors, x, z, visited, gridSize);

        return neighbors;
    }

    private static void addNorthNeighbor(List<int[]> neighbors, int x, int z, boolean[][] visited) {
        if (z > 0 && !visited[x][z - 1]) {
            neighbors.add(new int[]{x, z - 1});
        }
    }

    private static void addSouthNeighbor(List<int[]> neighbors, int x, int z, boolean[][] visited,
        int gridSize) {
        if (z < gridSize - 1 && !visited[x][z + 1]) {
            neighbors.add(new int[]{x, z + 1});
        }
    }

    private static void addWestNeighbor(List<int[]> neighbors, int x, int z, boolean[][] visited) {
        if (x > 0 && !visited[x - 1][z]) {
            neighbors.add(new int[]{x - 1, z});
        }
    }

    private static void addEastNeighbor(List<int[]> neighbors, int x, int z, boolean[][] visited,
        int gridSize) {
        if (x < gridSize - 1 && !visited[x + 1][z]) {
            neighbors.add(new int[]{x + 1, z});
        }
    }

    private static void removeWall(boolean[][][] grid, int x1, int z1, int x2, int z2) {
        if (x1 == x2) {
            removeVerticalWall(grid, x1, z1, x2, z2);
        } else {
            removeHorizontalWall(grid, x1, z1, x2, z2);
        }
    }

    private static void removeVerticalWall(boolean[][][] grid, int x1, int z1, int x2, int z2) {
        if (z1 < z2) {
            grid[x1][z1][0] = true;
        } else {
            grid[x2][z2][0] = true;
        }
    }

    private static void removeHorizontalWall(boolean[][][] grid, int x1, int z1, int x2, int z2) {
        if (x1 < x2) {
            grid[x1][z1][1] = true;
        } else {
            grid[x2][z2][1] = true;
        }
    }

    public static boolean isChunkInfected(ChunkPos chunkPos) {
        return generatedChunks.containsKey(chunkPos);
    }

    public static void addWallToGeneratedList(BlockPos pos) {
        generatedWalls.add(new BlockPos(pos.getX(), 0, pos.getZ()));
    }

    public static boolean isWallGenerated(BlockPos pos) {
        return generatedWalls.contains(new BlockPos(pos.getX(), 0, pos.getZ()));
    }
}