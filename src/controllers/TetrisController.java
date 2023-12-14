package controllers;

import models.StraightLine;
import models.Tetromino;
import models.LTet;
import models.Skew;
import models.Square;
import models.TTet;
import views.TetrisBoard;

import java.awt.*;
import java.util.Random;
import wheelsunh.users.Rectangle;

/**
 * TetrisController.java:
 * Class to hold all the game logic for tetris
 *
 * @author Charles Doebler
 * @version 1.0 December 15, 2023
 */
public class TetrisController
{
    private final TetrisBoard TETRIS_BOARD;

    /**
     * Constructor to take in a tetris board so the controller and the board can communicate
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController(TetrisBoard tetrisBoard)
    {
        this.TETRIS_BOARD = tetrisBoard;
    }

    /**
     * Randomly chooses the next tetronimo and returns it (INCOMPLETE)
     *
     * @return The next tetronimo to be played
     */
    public Tetromino getNextTetronimo()
    {
        Tetromino tetromino;

        Random random = new Random();
        int selectTetromino = random.nextInt(5);

        tetromino = switch (selectTetromino) {
            case 0 -> new StraightLine(Color.cyan);
            case 1 -> new LTet(Color.orange);
            case 2 -> new Square(Color.yellow);
            case 3 -> new Skew(Color.green);
            default -> new TTet(Color.magenta);
        };

        tetromino.setLocation(40 + (5 * Tetromino.SIZE), 0);

        return tetromino;
    }

    /**
     * Method to determine if the tetronimo has landed or collided
     *
     * @param tetromino The tetronimo to evaluate
     * @return True if the tetronimo has landed on the bottom of the board or another tetronimo.
     */

    public boolean collisionDetected(Tetromino tetromino)
    {

        for (Rectangle rect : tetromino.getRectangles())
        {

            int x = ((rect.getXLocation() - 40) / Tetromino.SIZE);
            int y = (rect.getYLocation() / Tetromino.SIZE);

            if (y >= TetrisBoard.HEIGHT - 1)
            {
                return true;
            }

            // Check if the Tetromino collides with other landed Tetrominos
            if (TETRIS_BOARD.getPlayingField()[x][y + 1].getFillColor() != Color.WHITE)
            {
                return true;
            }
        }
        return false;
    }
}
