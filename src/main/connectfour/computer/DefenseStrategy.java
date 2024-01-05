package connectfour.computer;

import connectfour.components.ConnectFourGrid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefenseStrategy implements ComputerStrategy {
    @Override
    public int computerMoveColumn(ConnectFourGrid connectFourGrid) {
        char symbolToCount = connectFourGrid.TOKEN_PLAYER_SYMBOL;
        Map<String, Integer> playableColumnsWithMostConnectedTokens = new HashMap<>();
        // horizontal
        for(int i = 0; i < connectFourGrid.ROWS; i++) {
            for(int j = 0; j < connectFourGrid.COLUMNS - 3; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                int currentCount = 0;
                for(int k = 0; k <= 3; k++) {
                    if(connectFourGrid.getGridState()[i][j + k] == symbolToCount) {
                        currentCount++;
                    }
                }
                playableColumnsWithMostConnectedTokens.put("h"+j, currentCount);
            }
        }
        // vertical
        for (int i = 0; i < connectFourGrid.ROWS - 3; i++) {
            for (int j = 0; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                int currentCount = 0;
                for(int k = 0; k <= 3; k++) {
                    if(connectFourGrid.getGridState()[i + k][j] == symbolToCount) {
                        currentCount++;
                    }
                }
                playableColumnsWithMostConnectedTokens.put("v"+j, currentCount);
            }
        }
        // ascending diagonal
        for (int i = 3; i < connectFourGrid.ROWS; i++) {
            for (int j = 0; j < connectFourGrid.COLUMNS - 3; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                int currentCount = 0;
                for(int k = 0; k <= 3; k++) {
                    if(connectFourGrid.getGridState()[i - k][j + k] == symbolToCount) {
                        currentCount++;
                    }
                }
                playableColumnsWithMostConnectedTokens.put("ad"+j, currentCount);
            }
        }
        // descending diagonal
        for (int i = 3; i < connectFourGrid.ROWS; i++) {
            for (int j = 3; j < connectFourGrid.COLUMNS; j++) {
                if(connectFourGrid.isMoveInvalid(j)) {
                    continue;
                }
                int currentCount = 0;
                for(int k = 0; k <= 3; k++) {
                    if(connectFourGrid.getGridState()[i - k][j - k] == symbolToCount) {
                        currentCount++;
                    }
                }
                playableColumnsWithMostConnectedTokens.put("dd"+j, currentCount);
            }
        }
        String highestKey = Collections.max(playableColumnsWithMostConnectedTokens.entrySet(),
                                            Map.Entry.comparingByValue()).getKey();
        int value = playableColumnsWithMostConnectedTokens.get(highestKey);
        int column = Integer.parseInt(highestKey.replaceAll("[^\\d.]", ""));
        if(value == 0 || value == 1 || connectFourGrid.isMoveInvalid(column)) {
            return connectFourGrid.getRandomColumnWithLeastTokens();
        }
        return column;
    }
}
