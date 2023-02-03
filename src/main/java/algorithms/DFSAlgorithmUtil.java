package algorithms;

import cards.interfaces.AbstractCardAI;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

import java.util.ArrayList;
import java.util.HashSet;

import static algorithms.CardPriorityUtil.getMoveSet;

public class DFSAlgorithmUtil {
    private static final int MAX_DEPTH = 10;

    public static ArrayList<DungeonState> dungeonStateDFS(DungeonState state) {
        ArrayList<DungeonState> bestPath = new ArrayList<>();
        ArrayList<DungeonState> currentPath = new ArrayList<>();
        currentPath.add(state);
        stateSearchRecursive(bestPath, currentPath, 0);
        return bestPath;
    }

    private static int getPathHeuristic(ArrayList<DungeonState> path) {
        if (path.size() == 0) {
            return 0;
        }
        return path.get(path.size() - 1).getHeuristic();
    }
    
    private static void stateSearchRecursive(ArrayList<DungeonState> bestPath, ArrayList<DungeonState> currentPath, int depth) {
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
        for (DungeonState futureState : getPossibleStates(currentState)) {
            currentPath.add(futureState);
            stateSearchRecursive(bestPath, currentPath, depth + 1);
            currentPath.remove(currentPath.size() - 1);
        }
    }
    
    public static ArrayList<DungeonState> getPossibleStates(DungeonState state) {
        ArrayList<DungeonState> result = new ArrayList<>();
        HashSet<ArrayList<AbstractCardAI>> moveSet = getMoveSet(state.getHand(), 3);
        for (ArrayList<AbstractCardAI> move : moveSet) {
            result.addAll(getPossibleStatesForMove(state, move));
        }
        for (int i = result.size() - 1; i >= 0; i--) {
            DungeonState stateInQuestion = result.get(i);
            for (int j = 0; j < result.size(); j++) {
                if (i == j) {
                    continue;
                }
                DungeonState stateComparedTo = result.get(j);
                if (!stateInQuestion.isBetterInAnyWay(stateComparedTo)) {
                    result.remove(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public static ArrayList<DungeonState> getPossibleStatesForMove(DungeonState startState, ArrayList<AbstractCardAI> move) {
        ArrayList<DungeonState> tmp;
        ArrayList<DungeonState> result = new ArrayList<>();
        ArrayList<DungeonState> input = new ArrayList<>();
        ArrayList<DungeonState> output = new ArrayList<>();
        DungeonState newState;

        input.add(startState.clone());
        for (AbstractCardAI card : move) {
            for (DungeonState state : input) {
                if (card instanceof AttackCardAI) {
                    for (int i = 0; i < state.getMonsters().size(); i++) {
                        AbstractMonsterAI monster = state.getMonsters().get(i);
                        if (monster.isDead()) {
                            continue;
                        }
                        newState = state.clone();
                        newState.playCard(card, newState.getMonsters().get(i));
                        if (card.isDraw()) {
                            result.addAll(getPossibleStates(newState));
                        } else {
                            output.add(newState);
                        }
                    }
                } else {
                    newState = state.clone();
                    newState.playCard(card);
                    if (card.isDraw()) {
                        result.addAll(getPossibleStates(newState));
                    } else {
                        output.add(newState);
                    }
                }
            }
            tmp = input;
            input = output;
            tmp.clear();
            output = tmp;
        }
        for (DungeonState state : input) {
            state.getCardsPlayed().addAll(move);
            state.endTurn();
        }
        result.addAll(input);
        return result;
    }
    
}
