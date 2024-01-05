package connectfour.computer;

import connectfour.components.ConnectFourGrid;
import java.util.Random;

public class NeutralStrategy implements ComputerStrategy {
    @Override
    public int computerMoveColumn(ConnectFourGrid connectFourGrid) {
        Random r = new Random();
        boolean coin = r.nextBoolean();
        if(coin) {
            return (new AttackStrategy()).computerMoveColumn(connectFourGrid);
        } else {
            return (new DefenseStrategy()).computerMoveColumn(connectFourGrid);
        }
    }
}
