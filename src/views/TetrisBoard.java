package views;

import controllers.TetrisController;
import models.Tetromino;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Charles Doebler
 * @version 1.0 December 15, 2023
 *
 * @see Color
 * @see KeyListener
 * @see KeyEvent
 */
public class TetrisBoard implements KeyListener
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represent the height of the board
     */
    public static final int HEIGHT = 24;

    /**
     * Constant for the preview board size
     */
    public static final int previewSize = 4;

    private final TetrisController CONTROLLER;
    private Tetromino tetromino;
    private Rectangle[][] playingField;
    private Rectangle[][] previewField;
    private int[][] clonedBoard;
    private Tetromino nextTetromino;

    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame (so we can add this class as a key listener for the frame)
     */
    public TetrisBoard( Frame frame )
    {
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        this.buildBoard();

        this.run();
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard() {
        this.playingField = new Rectangle[WIDTH][HEIGHT];
        this.previewField = new Rectangle[previewSize][previewSize];

        for (int i = 0; i < TetrisBoard.WIDTH; i++)
        {
            for (int j = 0; j < TetrisBoard.HEIGHT; j++)
            {
                this.playingField[i][j] = new Rectangle();
                this.playingField[i][j].setLocation(i * 20 + 40, j * 20);
                this.playingField[i][j].setSize(Tetromino.SIZE, Tetromino.SIZE);
                this.playingField[i][j].setColor(Color.WHITE);
                this.playingField[i][j].setFrameColor(Color.BLACK);
                this.clonedBoard = new int[WIDTH][HEIGHT];
            }
        }
        for (int i = 0; i < previewSize; i++)
        {
            for (int j = 0; j < previewSize; j++)
            {
                this.previewField[i][j] = new Rectangle();
                // set the size, location, and color of each preview block
                // you may want to use different coordinates for setLocation to position the preview where you want
                this.previewField[i][j].setLocation(i * 20 + 40, j * 20);
                this.previewField[i][j].setSize(Tetromino.SIZE, Tetromino.SIZE);
                this.previewField[i][j].setColor(Color.WHITE);
                this.previewField[i][j].setFrameColor(Color.BLACK);
            }
        }
    }
    /**
     * Starts gameplay and is responsible for keeping the game going
     */
    public void run() {
        // Create first (next) tetromino
        this.nextTetromino = this.CONTROLLER.getNextTetronimo();
        while (!this.CONTROLLER.gameOver())
        {
            // nextTetromino becomes active tetromino
            this.tetromino = this.nextTetromino;
            this.tetromino.setLocation(this.tetromino.getXLocation(), this.tetromino.getYLocation());
            Utilities.sleep(500);

            // Create next tetromino
            this.nextTetromino = this.CONTROLLER.getNextTetronimo();

            // Update preview
            this.updatePreview(this.nextTetromino);

            // Check for collision. If no collision, drop tetromino one row.
            while (!this.CONTROLLER.collisionDetected(this.tetromino))
            {
                this.tetromino.setLocation(this.tetromino.getXLocation(), this.tetromino.getYLocation() + Tetromino.SIZE);
                Utilities.sleep(500);
            }

            // Update array board using for tracking and scoring
            updateClonedBoard();

            // Destroy active tetromino
            this.tetromino = null;
        }
        this.CONTROLLER.handleGameOver();
    }

    public void updateClonedBoard()
    {
        for (Rectangle rect : this.tetromino.getRectangles()) {
            int x = (rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (rect.getYLocation()) / Tetromino.SIZE;

            // Update the color of the corresponding block in the playing field
            System.out.println("Update: " + x + ", " + y);
            clonedBoard[x][y] = 1;
        }
    }

    public void updatePreview(Tetromino nextTetromino)
    {
        // Clear the old preview
        for (int i = 0; i < previewField.length; i++) {
            for (int j = 0; j < previewField[i].length; j++) {
                previewField[i][j].setColor(Color.WHITE);
            }
        }

        // Draw the new preview
        for (Rectangle rect : nextTetromino.getRectangles()) {
            int x = (rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (rect.getYLocation()) / Tetromino.SIZE;

            if (x >= 0 && x < previewField.length && y >= 0 && y < previewField[0].length) {
                previewField[x][y].setColor(nextTetromino.getColor());
            }
        }
    }

    /**
     * Getter method for the array representing the playing field, not used yet but will be needed by the controller later
     *
     * @return The playing field
     */
    public int[][] getClonedBoard()
    {
        return clonedBoard;
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user (INCOMPLETE)
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( this.tetromino == null )
        {
            return;
        }

        switch( key )
        {
            case 38:
                this.tetromino.rotate();
                while ((this.tetromino.getXLocation() + this.tetromino.getWidth()) >
                        (TetrisBoard.WIDTH * Tetromino.SIZE + 40))
                {
                    this.tetromino.shiftLeft();
                }
                break;
            case 37:
                if( this.tetromino.getXLocation() - Tetromino.SIZE >= 40 )
                {
                    this.tetromino.shiftLeft();
                }
                break;
            case 39:
                // System.out.println(this.tetromino.getHeight() + ", " + this.tetromino.getWidth());
                if((this.tetromino.getXLocation() + this.tetromino.getWidth()) <
                        ((TetrisBoard.WIDTH * Tetromino.SIZE) + 40))
                {
                    this.tetromino.shiftRight();
                }
                break;
        }

    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //not in use
    }
}