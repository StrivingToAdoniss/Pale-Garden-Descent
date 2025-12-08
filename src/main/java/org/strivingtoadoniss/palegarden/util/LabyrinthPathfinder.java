package org.strivingtoadoniss.palegarden.util;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;

import java.util.*;

public class LabyrinthPathfinder {

    public static List<BlockPos> findPath(ServerWorld world, BlockPos start, BlockPos end) {
        BlockPos target = new BlockPos(end.getX(), start.getY(), end.getZ());

        PathfindingContext context = initializePathfinding(start, target);
        Node result = executePathfinding(context, target);

        return result != null ? reconstructPath(result, world) : Collections.emptyList();
    }

    private static PathfindingContext initializePathfinding(BlockPos start, BlockPos target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Map<BlockPos, Node> allNodes = new HashMap<>();
        Set<BlockPos> closedSet = new HashSet<>();

        Node startNode = new Node(start, null, 0, start.getManhattanDistance(target));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        return new PathfindingContext(openSet, allNodes, closedSet);
    }

    private static Node executePathfinding(PathfindingContext context, BlockPos target) {
        int maxIterations = 200000;
        int iterations = 0;

        while (!context.openSet.isEmpty() && iterations < maxIterations) {
            iterations++;
            Node current = context.openSet.poll();

            if (context.closedSet.contains(current.pos)) {
                continue;
            }
            context.closedSet.add(current.pos);

            if (current.pos.equals(target)) {
                return current;
            }

            processNeighbors(current, target, context.allNodes, context.openSet, context.closedSet);
        }

        return null;
    }

    private static void processNeighbors(Node current, BlockPos target,
        Map<BlockPos, Node> allNodes, PriorityQueue<Node> openSet, Set<BlockPos> closedSet) {
        for (BlockPos neighborPos : getNeighbors(current.pos)) {
            if (shouldSkipNeighbor(neighborPos, closedSet)) {
                continue;
            }

            double gCost = current.gCost + 1;
            Node neighbor = allNodes.getOrDefault(neighborPos,
                new Node(neighborPos, null, Double.MAX_VALUE, 0));

            if (gCost < neighbor.gCost) {
                updateNeighborNode(neighbor, current, gCost, target, neighborPos, allNodes,
                    openSet);
            }
        }
    }

    private static boolean shouldSkipNeighbor(BlockPos neighborPos, Set<BlockPos> closedSet) {
        return LabyrinthGenerator.shouldPlaceWallAt(neighborPos) || closedSet.contains(neighborPos);
    }

    private static void updateNeighborNode(Node neighbor, Node current, double gCost,
        BlockPos target, BlockPos neighborPos, Map<BlockPos, Node> allNodes,
        PriorityQueue<Node> openSet) {
        neighbor.parent = current;
        neighbor.gCost = gCost;
        neighbor.hCost = neighborPos.getManhattanDistance(target);
        neighbor.fCost = neighbor.gCost + neighbor.hCost;

        allNodes.putIfAbsent(neighborPos, neighbor);
        openSet.add(neighbor);
    }

    private static List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>();
        neighbors.add(pos.north());
        neighbors.add(pos.south());
        neighbors.add(pos.east());
        neighbors.add(pos.west());
        return neighbors;
    }

    private static List<BlockPos> reconstructPath(Node node, ServerWorld world) {
        List<BlockPos> path = new ArrayList<>();
        while (node != null) {
            path.add(attachCorridorY(world, node.pos));
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static BlockPos attachCorridorY(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable cursor = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).mutableCopy();

        while (cursor.getY() > world.getBottomY()) {
            if (isWalkableFloor(world, cursor)) {
                return cursor.toImmutable();
            }
            cursor.move(Direction.DOWN);
        }

        return pos;
    }

    private static boolean isWalkableFloor(ServerWorld world, BlockPos pos) {
        if (!world.getBlockState(pos).isSolidBlock(world, pos)) {
            return false;
        }

        return canWalkThrough(world, pos.up()) && canWalkThrough(world, pos.up(2));
    }

    private static boolean canWalkThrough(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        return state.getCollisionShape(world, pos).isEmpty();
    }

    private record PathfindingContext(PriorityQueue<Node> openSet, Map<BlockPos, Node> allNodes,
                                      Set<BlockPos> closedSet) {}

    private static class Node {
        BlockPos pos;
        Node parent;
        double gCost;
        double hCost;
        double fCost;

        public Node(BlockPos pos, Node parent, double gCost, double hCost) {
            this.pos = pos;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }
    }
}
