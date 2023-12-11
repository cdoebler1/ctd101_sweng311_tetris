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
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
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

    private final TetrisController CONTROLLER;
    private Tetromino tetromino;
    private Rectangle[][] playingField;

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
    private void buildBoard()
    {
        this.playingField = new Rectangle[ WIDTH ][ HEIGHT ];

        for ( int i = 0; i < TetrisBoard.WIDTH; i++ )
        {
            for ( int j = 0; j < TetrisBoard.HEIGHT; j++ )
            {
                this.playingField[ i ][ j ] = new Rectangle();
                this.playingField[ i ][ j ].setLocation( i * 20 + 40, j * 20 );
                this.playingField[ i ][ j ].setSize( Tetromino.SIZE, Tetromino.SIZE );
                this.playingField[ i ][ j ].setColor( Color.WHITE );
                this.playingField[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }
    }

    /**
     * Starts gameplay and is responsible for keeping the game going (INCOMPLETE)
     */
    public void run() {
        boolean gameover = false;
        while (!gameover)
        {
            this.tetromino = this.CONTROLLER.getNextTetronimo();
            this.tetromino.setLocation(this.tetromino.getXLocation(), this.tetromino.getYLocation());
            Utilities.sleep(500);

            while ((!this.CONTROLLER.tetrominoLanded(this.tetromino))&&(!this.CONTROLLER.collisionDetected(this.tetromino)))
            {
                this.tetromino.setLocation(this.tetromino.getXLocation(), this.tetromino.getYLocation() + Tetromino.SIZE);
                Utilities.sleep(500);
            }
            //updatePlayingField(this.tetromino);

            gameover = this.CONTROLLER.gameOver();

            this.tetromino = null;
        }
        this.CONTROLLER.handleGameOver();
    }

    public void updatePlayingField( Tetromino tetromino)
    {
        System.out.println("Updating PlayingField");
        for (Rectangle rect : this.tetromino.getRectangles()) {
            int x = (this.tetromino.getXLocation() + rect.getXLocation() - 40) / Tetromino.SIZE;
            int y = (this.tetromino.getYLocation() + rect.getYLocation()) / Tetromino.SIZE;

            // Update the color of the corresponding block in the playing field
            playingField[x][y].setColor(Color.black);
        }
    }

    /**
     * Getter method for the array representing the playing field, not used yet but will be needed by the controller later
     *
     * @return The playing field
     */
    public Rectangle[][] getPlayingField()
    {
        return playingField;
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