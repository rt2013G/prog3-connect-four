package connectfour.computer;

import connectfour.components.ConnectFourGrid;
import java.util.*;

/**
 * The attack strategy or gamemode is the most difficult gamemode as it uses the minimax algorithm with alpha-beta
 * pruning to find the best possible move for the computer
 *
 * @author Raffaele Talente
 * @see "Alpha-beta pruning, Wikipedia"
 */
public class AttackStrategy implements ComputerStrategy {
    /**
     * Iterates all possible moves creating a list of moves where the index in the list represents the column of the
     * move and corresponding value represents its evaluation. Invalid moves are evaluated as minus infinity so
     * they cannot be picked.
     * The evaluation is done with the minimax algorithm using alpha-beta pruning.
     *
     * @param connectFourGrid A ConnectFourGrid object to evaluate in order to get a column
     * @return The column to insert the token into
     */
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
            connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_COMPUTER_SYMBOL, j);
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
            }
        }
        Random r = new Random();
        return bestMoves.get(r.nextInt(bestMoves.size()));
    }

    /**
     * Implements the minimax algorithm with alpha-beta pruning via recursion
     *
     * @param connectFourGrid The grid to evaluate
     * @param depth The depth of the current move
     * @param isComputerTurn True if it's the computer's turn to insert a token, False otherwise
     * @return The evaluation based on the winner (or no winner) from the perspective of the computer
     * @see ConnectFourGrid#getGridEvaluation(char)
     */
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
                connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_COMPUTER_SYMBOL, j);
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
                connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_PLAYER_SYMBOL, j);
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
