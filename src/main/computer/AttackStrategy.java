package src.main.computer;

import src.main.components.ConnectFourGrid;

public class AttackStrategy implements ComputerStrategy {
    @Override
    public int bestMoveColumn(ConnectFourGrid connectFourGrid) {
        int bestMoveColumn = 0;
        int bestMoveEvaluation = Integer.MIN_VALUE;
        ConnectFourGrid grid = connectFourGrid.cloneSelf();

        for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
            if(!grid.isMoveValid(j)) {
                continue;
            }
            ConnectFourGrid fakeGrid = grid.cloneSelf();
            int moveEvaluation = minimax(fakeGrid, 5, true);
            if(moveEvaluation > bestMoveEvaluation) {
                bestMoveEvaluation = moveEvaluation;
                bestMoveColumn = j;
            }
        }
        return bestMoveColumn;
    }

    private int minimax(ConnectFourGrid connectFourGrid, int depth, boolean isComputerTurn) {
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
                if(!connectFourGrid.isMoveValid(j)) {
                    continue;
                }
                ConnectFourGrid fakeGrid = connectFourGrid.cloneSelf();
                fakeGrid.insertToken(fakeGrid.TOKEN_COMPUTER_SYMBOL, j);
                maxValue = Math.max(maxValue, minimax(fakeGrid, depth - 1, false));

            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for(int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(!connectFourGrid.isMoveValid(j)) {
                    continue;
                }
                ConnectFourGrid fakeGrid = connectFourGrid.cloneSelf();
                fakeGrid.insertToken(fakeGrid.TOKEN_PLAYER_SYMBOL, j);
                minValue = Math.min(minValue, minimax(fakeGrid, depth - 1, true));
            }
            return minValue;
        }
    }
}
