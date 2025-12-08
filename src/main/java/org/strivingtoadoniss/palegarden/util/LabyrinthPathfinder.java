package org.strivingtoadoniss.palegarden.util;

import static org.strivingtoadoniss.palegarden.spread.InfectionStarter.isInImpactZone;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;

import java.util.*;

public class LabyrinthPathfinder {

    private LabyrinthPathfinder() {}

    public static List<BlockPos> findPath(ServerWorld world, BlockPos start, BlockPos end) {
        BlockPos target = new BlockPos(end.getX(), start.getY(), end.getZ());

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Map<BlockPos, Node> allNodes = new HashMap<>();
        Set<BlockPos> closedSet = new HashSet<>();

        Node startNode = new Node(start, null, 0, start.getManhattanDistance(target));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        int maxIterations = 200000;
        int iterations = 0;

        while (!openSet.isEmpty() && iterations < maxIterations) {
            iterations++;
            Node current = openSet.poll();

            if (closedSet.contains(current.pos)) {
                continue;
            }
            closedSet.add(current.pos);

            if (isInImpactZone(current.pos)) {
                return reconstructPath(current, world);
            }

            processNeighbors(current, target, openSet, allNodes, closedSet);
        }

        return Collections.emptyList();
    }

    private static void processNeighbors(Node current, BlockPos target, PriorityQueue<Node> openSet,
                                          Map<BlockPos, Node> allNodes, Set<BlockPos> closedSet) {
        for (BlockPos neighborPos : getNeighbors(current.pos)) {
            if (!isInImpactZone(neighborPos) && shouldSkipNeighbor(neighborPos, closedSet)) {
                continue;
            }

            updateNeighborNode(current, neighborPos, target, openSet, allNodes);
        }
    }

    private static boolean shouldSkipNeighbor(BlockPos neighborPos, Set<BlockPos> closedSet) {
        return LabyrinthGenerator.isWallGenerated(neighborPos) || closedSet.contains(neighborPos);
    }

    private static void updateNeighborNode(Node current, BlockPos neighborPos, BlockPos target,
        PriorityQueue<Node> openSet, Map<BlockPos, Node> allNodes) {
        double gCost = current.gCost + 1;
        Node neighbor = allNodes.getOrDefault(neighborPos,
            new Node(neighborPos, null, Double.MAX_VALUE, 0));

        if (gCost < neighbor.gCost) {
            neighbor.parent = current;
            neighbor.gCost = gCost;
            neighbor.hCost = neighborPos.getManhattanDistance(target);
            neighbor.fCost = neighbor.gCost + neighbor.hCost;

            allNodes.putIfAbsent(neighborPos, neighbor);
            openSet.add(neighbor);
        }
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
