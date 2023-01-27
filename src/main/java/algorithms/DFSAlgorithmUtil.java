package algorithms;

import dungeon.DungeonState;

import java.util.ArrayList;

public class DFSAlgorithmUtil {
    private static final int MAX_DEPTH = 10;

    public static ArrayList<DungeonState> dungeonStateDFS(DungeonState state) {
        ArrayList<DungeonState> bestPath = new ArrayList<>();
        ArrayList<DungeonState> currentPath = new ArrayList<>();
        currentPath.add(state);
        dungeonStateDFS(bestPath, currentPath, 0);
        return bestPath;
    }

    private static int getPathHeuristic(ArrayList<DungeonState> path) {
        if (path.size() == 0) {
            return 0;
        }
        return path.get(path.size() - 1).getHeuristic();
    }

    private static void dungeonStateDFS(ArrayList<DungeonState> bestPath, ArrayList<DungeonState> currentPath, int depth) {
        DungeonState currentState = currentPath.get(currentPath.size() - 1);
        int bestPathHeuristic = getPathHeuristic(bestPath);
        int currentPathHeuristic = getPathHeuristic(currentPath);

        // Process end states
        if (currentState.isEndState()) {
            // If there is no current best path, make this path the best path
            if (bestPath.size() == 0) {
                bestPath.addAll(currentPath);
                return;
            }

            // If this path is worse than the best path, ignore
            if (bestPathHeuristic > currentPathHeuristic) {
                return;
            }

            // If this path is better than the best path, make this path the best path
            if (bestPathHeuristic < currentPathHeuristic || bestPath.size() > currentPath.size()) {
                bestPath.clear();
                bestPath.addAll(currentPath);
            }
            return;
        }

        /*
         * Stop transversing this tree if
         *  1. This path is already worse than the best path
         *  2. MAX_DEPTH has been reached
         *
         * NOTE: This assumes you can't gain HP from cards. This can be modified in the future / exceptions made
         */
        if (bestPathHeuristic > currentPathHeuristic || MAX_DEPTH == depth) {
            return;
        }

        // Recursive DFS over possible states
        for (DungeonState futureState : currentState.getPossibleStates()) {
            currentPath.add(futureState);
            dungeonStateDFS(bestPath, currentPath, depth + 1);
            currentPath.remove(currentPath.size() - 1);
        }
    }
}
