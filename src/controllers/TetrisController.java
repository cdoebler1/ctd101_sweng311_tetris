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
    public TetrisController( TetrisBoard tetrisBoard )
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
        // selectTetromino = 4;
        switch (selectTetromino)
        {
            case 0: 
                tetromino = new StraightLine(Color.cyan);
                break;
            case 1:
                tetromino = new LTet(Color.orange);
                break;
            case 2:
                tetromino = new Square(Color.yellow);
                break;
            case 3:
                tetromino = new Skew(Color.green);
                break;
            default:
                tetromino = new TTet(Color.magenta);
                break;
        }
        
        tetromino.setLocation( 40 + (5 * Tetromino.SIZE), 0 );

        return tetromino;
    }

    /**
     * Method to determine if the tetronimo has landed (INCOMPLETE)
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public boolean tetrominoLanded(Tetromino tetronimo )
    {
        int nextY = tetronimo.getYLocation() + tetronimo.getHeight() + Tetromino.SIZE;
        return nextY > 480;
    }

    public boolean collisionDetected(Tetromino tetromino) {
        for (Rectangle rect : tetromino.getRectangles())
        {
            int x = (tetromino.getXLocation() + rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (tetromino.getYLocation() + rect.getYLocation()) / Tetromino.SIZE;

            if (x >= 0 && x < TetrisBoard.WIDTH && y >= 0 && y < TetrisBoard.HEIGHT)
            {
                // Check if the Tetromino collides with other landed Tetrominos
                if (this.TETRIS_BOARD.getPlayingField()[x][y].getColor() != Color.WHITE)
                {
                    System.out.println("True");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameOver()
    {
        return false;
    }
    public void handleGameOver()
    {

    }
}
