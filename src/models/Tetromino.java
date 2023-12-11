package models;

import wheelsunh.users.Rectangle;
import wheelsunh.users.ShapeGroup;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Tetromino.java:
 * An abstract class to model the base capabilities of a tetronimo
 *
 * @author Charles Doebler
 * @version 1.0 December 15, 2023
 *
 * @see java.awt.Color
 */
public abstract class Tetromino extends ShapeGroup
{
    /**
     * Constant to represent the size of the tetronimo
     */
    public static final int SIZE= 20;

    protected Rectangle r1;
    protected Rectangle r2;
    protected Rectangle r3;
    protected Rectangle r4;
    private final List<Rectangle> rectangles;

    protected Color tetrominoColor;

    protected int curRotation = 1;

    /**
     * Generates the four rectangles for the tetronimo and puts them on the screen, they are at the default coordinates
     * to start
     */
    public Tetromino(Color color)
    {
        super();
        this.tetrominoColor = color;
        rectangles = new ArrayList<>();

        this.r1 = new Rectangle();
        this.r1.setSize( Tetromino.SIZE, Tetromino.SIZE );
        this.r1.setColor(tetrominoColor);
        this.r1.setFrameColor( Color.BLACK );
        rectangles.add(r1);

        this.r2 = new Rectangle();
        this.r2.setSize( Tetromino.SIZE, Tetromino.SIZE );
        this.r2.setColor(tetrominoColor);
        this.r2.setFrameColor( Color.BLACK );
        rectangles.add(r2);

        this.r3 = new Rectangle();
        this.r3.setSize( Tetromino.SIZE, Tetromino.SIZE );
        this.r3.setColor(tetrominoColor);
        this.r3.setFrameColor( Color.BLACK );
        rectangles.add(r3);

        this.r4 = new Rectangle();
        this.r4.setSize( Tetromino.SIZE, Tetromino.SIZE );
        this.r4.setColor(tetrominoColor);
        this.r4.setFrameColor( Color.BLACK );
        rectangles.add(r4);
    }

    /**
     * Increments the rotation of the tetronimo, other classes need to override this to provide the full functionality
     */
    public void rotate() { this.curRotation++; }

    /**
     * Shifts the tetronimo left one row
     */
    public void shiftLeft()
    {
        super.setLocation( super.getXLocation() - Tetromino.SIZE, super.getYLocation() );
    }

    /**
     * Shifts the tetronimo right one row
     */
    public void shiftRight()
    {
        super.setLocation( super.getXLocation() + Tetromino.SIZE, super.getYLocation() );
    }

    public List<Rectangle> getRectangles() {

        return rectangles;
    }
}