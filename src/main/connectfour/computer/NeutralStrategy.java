package connectfour.computer;

import connectfour.components.ConnectFourGrid;
import java.util.Random;

/**
 * The neutral strategy tosses an imaginary coin every time it has to make a move, and it either returns
 * a move using the attack strategy or the defense strategy.
 *
 * @author Raffaele Talente
 */
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
