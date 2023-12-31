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
    private int score = 0;
    private TextBox scoreboard;

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
        this.scoreboard = new TextBox("Score: " + score);
        scoreboard.setLocation(300, 100);

        for (int i = 0; i < TetrisBoard.WIDTH; i++)
        {
            for (int j = 0; j < TetrisBoard.HEIGHT; j++)
            {
                this.playingField[i][j] = new Rectangle();
                this.playingField[i][j].setLocation(i * 20 + 40, j * 20);
                this.playingField[i][j].setSize(Tetromino.SIZE, Tetromino.SIZE);
                this.playingField[i][j].setColor(Color.WHITE);
                this.playingField[i][j].setFrameColor(Color.BLACK);
            }
        }
        for (int i = 0; i < previewSize; i++)
        {
            for (int j = 0; j < previewSize; j++)
            {
                this.previewField[i][j] = new Rectangle();
                this.previewField[i][j].setLocation(i * 20 + 300, j * 20);
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
        boolean runGame = true;
        // Create first (next) tetromino
        Tetromino nextTetromino = this.CONTROLLER.getNextTetronimo();
        nextTetromino.setLocation(nextTetromino.getXLocation() + 180, nextTetromino.getYLocation());

        while (runGame)
        {
            // nextTetromino becomes active tetromino
            this.tetromino = nextTetromino;
            this.tetromino.setLocation(this.tetromino.getXLocation() - 200, this.tetromino.getYLocation());
            Utilities.sleep(500);

            if (this.CONTROLLER.collisionDetected(this.tetromino))
            {
                runGame = false;
            }

            // Create next tetromino
            nextTetromino = this.CONTROLLER.getNextTetronimo();
            nextTetromino.setLocation(nextTetromino.getXLocation() + 180, nextTetromino.getYLocation());

            // Update preview
            this.updatePreview(nextTetromino);

            // Check for collision. If no collision, drop tetromino one row.
            while (!this.CONTROLLER.collisionDetected(this.tetromino))
            {
                this.tetromino.setLocation(this.tetromino.getXLocation(), this.tetromino.getYLocation() + Tetromino.SIZE);
                Utilities.sleep(500);
            }

            // Update array board used for tracking and scoring
            boards();

            // Destroy active tetromino
            for (Rectangle rect: this.tetromino.getRectangles())
            {
                rect.hide();
            }

            // Check for full rows
            clearRows();
        }
        gameOver();
    }

    public void boards()
    {
        for (Rectangle rect : this.tetromino.getRectangles()) {
            int x = (rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (rect.getYLocation()) / Tetromino.SIZE;

            // Update the color of the corresponding block in the playing field
            this.playingField[x][y].setFillColor(this.tetromino.getColor());
            this.playingField[x][y].setFrameColor(Color.BLACK);
        }
    }

    public void updatePreview(Tetromino nextTetromino)
    {
        // Clear the old preview
        for (Rectangle[] rectangles : previewField)
        {
            for (Rectangle rectangle : rectangles)
            {
                rectangle.setFillColor(Color.WHITE);
            }
        }

        // Draw the new preview
        for (Rectangle rect : nextTetromino.getRectangles()) {
            int x = (rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (rect.getYLocation()) / Tetromino.SIZE;

            if (x >= 0 && x < previewField.length && y >= 0 && y < previewField[0].length) {
                previewField[x][y].setFillColor(nextTetromino.getColor());
            }
        }
    }

    /**
     * Getter method for the array representing the playing field
     *
     * @return The playing field
     */
     public Rectangle[][] getPlayingField()
    {
        return playingField;
    }

    public void gameOver()
    {
        TextBox TextBox = new TextBox("Game Over");
        TextBox.setLocation(300, 200);
    }

    /**
     * This method will loop over each row in the board. If a row is filled (all elements are not white),
     * that row is removed, and a new row is added at the top of the board.
     */
    private void clearRows()
    {
        int rowsCleared = 0;
        for (int i = HEIGHT - 1; i > 0; i--)
        {
            boolean rowFilled = false;
            for (int j = 0; j < WIDTH; j++)
            {
                rowFilled = true;
                if (this.playingField[j][i].getFillColor() == Color.WHITE)
                {
                    rowFilled = false;
                    break;
                }
            }
            if (rowFilled) {
                // Shift every row above down by one.
                rowsCleared ++;
                for (int k = i; k > 0; k--)
                {
                    for (int l = 0; l < WIDTH; l++)
                    {
                        //clonedBoard[l][k] = clonedBoard[l][k - 1];
                        Color temp = this.playingField[l][k - 1].getFillColor();
                        this.playingField[l][k].setFillColor(temp);
                    }
                }
                // Clear the topmost row
                for (int l = 0; l < WIDTH; l++)
                {
                    //clonedBoard[l][0] = 0;
                    this.playingField[l][0].setFillColor(Color.WHITE);
                }
                score += 100;
                if (rowsCleared == 4)
                {
                    score += 400;
                }
                scoreboard.setText("Score: " + score);
                i++; // Rerun the same row after shifting all rows above
            }
        }
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
                if((this.tetromino.getXLocation() + this.tetromino.getWidth()) <
                        ((TetrisBoard.WIDTH * Tetromino.SIZE) + 40))
                {
                    this.tetromino.shiftRight();
                }
                break;
            case 40:
                if(!this.CONTROLLER.collisionDetected(this.tetromino))
                {
                    this.tetromino.shiftDown();
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