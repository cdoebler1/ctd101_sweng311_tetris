package models;

import java.awt.*;

/**
 * skew.java:
 * Creates a straight line tetronimo
 *
 * @author Professor Rossi
 * @version 1.0 July 24, 2020
 *
 * @see Point
 */
public class Square extends Tetromino
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public Square(Color color)
    {
        super(color);

        super.r1.setLocation( 0, 0 );
        super.r2.setLocation( Tetromino.SIZE, 0 );
        super.r3.setLocation( 0, Tetromino.SIZE );
        super.r4.setLocation( Tetromino.SIZE, Tetromino.SIZE );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );
    }

    /**
     * Rotates the tetromino
     */
    @Override
    public void rotate()
    {

    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        return Tetromino.SIZE * 2;
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        return Tetromino.SIZE * 2;
    }
}
