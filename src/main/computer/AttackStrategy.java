package src.main.computer;

import src.main.components.ConnectFourGrid;

import java.util.*;

public class AttackStrategy implements ComputerStrategy {
    @Override
    public int computerMoveColumn(ConnectFourGrid connectFourGrid) {
        int minimaxDepth = 10;
        int bestMoveEvaluation = Integer.MIN_VALUE;
        int[] moveEvals = new int[connectFourGrid.COLUMNS];
        List<Integer> bestMoves = new ArrayList<>();

        for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
            if(connectFourGrid.isMoveInvalid(j)) {
                moveEvals[j] = Integer.MIN_VALUE;
                continue;
            }
            connectFourGrid.insertToken(connectFourGrid.TOKEN_COMPUTER_SYMBOL, j);
            int moveEvaluation = minimax(connectFourGrid, minimaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            connectFourGrid.removeTopToken(j);
            moveEvals[j] = moveEvaluation;
            if(moveEvaluation > bestMoveEvaluation) {
                bestMoveEvaluation = moveEvaluation;
            }
        }
        for(int i = 0; i < connectFourGrid.COLUMNS; i++) {
            if(moveEvals[i] == bestMoveEvaluation) {
                bestMoves.add(i);
                System.out.println(i);
            }
        }
        Random r = new Random();
        return bestMoves.get(r.nextInt(bestMoves.size()));
    }

    private int minimax(ConnectFourGrid connectFourGrid, int depth, int alpha, int beta, boolean isComputerTurn) {
        if(depth <= 0) {
            return 0;
        }
        int currentGridEvaluation = connectFourGrid.getGridEvaluation(connectFourGrid.TOKEN_COMPUTER_SYMBOL);
        if(currentGridEvaluation == 1) {
            return depth;
        } else if(currentGridEvaluation == -1) {
            return -depth;
        } else if(connectFourGrid.isGridFull()) {
            return 0;
        }

        if(isComputerTurn) {
            int maxValue = Integer.MIN_VALUE;
            for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                connectFourGrid.insertToken(connectFourGrid.TOKEN_COMPUTER_SYMBOL, j);
                maxValue = Math.max(maxValue, minimax(connectFourGrid, depth - 1, alpha, beta, false));
                connectFourGrid.removeTopToken(j);
                alpha = Math.max(alpha, maxValue);
                if(alpha >= beta) {
                   break;
                }
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                connectFourGrid.insertToken(connectFourGrid.TOKEN_PLAYER_SYMBOL, j);
                minValue = Math.min(minValue, minimax(connectFourGrid, depth - 1, alpha, beta, true));
                connectFourGrid.removeTopToken(j);
                beta = Math.min(beta, minValue);
                if(alpha >= beta) {
                    break;
                }
            }
            return minValue;
        }
    }
}
