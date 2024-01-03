package src.main.computer;

import src.main.components.ConnectFourGrid;

public class AttackStrategy implements ComputerStrategy {
    @Override
    public int computerMoveColumn(ConnectFourGrid connectFourGrid) {
        int minimaxDepth = 7;
        int bestMoveColumn = 0;
        int bestMoveEvaluation = Integer.MIN_VALUE;
        ConnectFourGrid grid = connectFourGrid.cloneSelf();

        for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
            if(grid.isMoveInvalid(j)) {
                continue;
            }
            ConnectFourGrid fakeGrid = grid.cloneSelf();
            int moveEvaluation = minimax(fakeGrid, minimaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            if(moveEvaluation > bestMoveEvaluation) {
                bestMoveEvaluation = moveEvaluation;
                bestMoveColumn = j;
            }
        }
        return bestMoveColumn;
    }

    private int minimax(ConnectFourGrid connectFourGrid, int depth, int alpha, int beta, boolean isComputerTurn) {
        int currentGridEvaluation = connectFourGrid.getGridEvaluation(connectFourGrid.TOKEN_COMPUTER_SYMBOL);
        if(depth == 0 || currentGridEvaluation != 0) {
            if(isComputerTurn) {
                return currentGridEvaluation;
            } else {
                return - currentGridEvaluation;
            }
        }

        if(isComputerTurn) {
            int maxValue = Integer.MIN_VALUE;
            for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                ConnectFourGrid fakeGrid = connectFourGrid.cloneSelf();
                fakeGrid.insertToken(fakeGrid.TOKEN_COMPUTER_SYMBOL, j);
                maxValue = Math.max(maxValue, minimax(fakeGrid, depth - 1, alpha, beta, false));
                if(maxValue > beta) {
                    break;
                }
                alpha = Math.max(alpha, maxValue);

            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                ConnectFourGrid fakeGrid = connectFourGrid.cloneSelf();
                fakeGrid.insertToken(fakeGrid.TOKEN_PLAYER_SYMBOL, j);
                minValue = Math.min(minValue, minimax(fakeGrid, depth - 1, alpha, beta, true));
                if(minValue < alpha) {
                    break;
                }
                beta = Math.min(beta, minValue);
            }
            return minValue;
        }
    }
}
