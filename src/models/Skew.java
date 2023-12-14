package models;

import java.awt.*;

/**
 * skew.java:
 * Creates a straight line tetronimo
 *
 * @author Charles Doebler
 * @version 1.0 December 15, 2023
 *
 * @see Point
 */
public class Skew extends Tetromino
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public Skew(Color color)
    {
        super(color);

        super.r1.setLocation( 0, Tetromino.SIZE );
        super.r2.setLocation( Tetromino.SIZE, Tetromino.SIZE );
        super.r3.setLocation( Tetromino.SIZE, 0 );
        super.r4.setLocation( Tetromino.SIZE * 2, 0 );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );
    }

    /**
     * Rotates the tetronimo
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();

        super.setLocation( 0, 0 );

        // Rotate the rectangles around a central point
        if (curRotation % 2 == 1) {
            r1.setLocation(0, Tetromino.SIZE);
            r2.setLocation(Tetromino.SIZE, Tetromino.SIZE);
            r3.setLocation(Tetromino.SIZE, 0);
            r4.setLocation(Tetromino.SIZE * 2, 0);
        } else {
            r1.setLocation(0, 0);
            r2.setLocation(0, Tetromino.SIZE);
            r3.setLocation(Tetromino.SIZE, Tetromino.SIZE);
            r4.setLocation(Tetromino.SIZE, Tetromino.SIZE * 2);
        }

        super.setLocation( curLoc );
    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetromino.SIZE * 3;
        }
        else
        {
            return Tetromino.SIZE * 2;
        }
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetromino.SIZE * 2;
        }
        else
        {
            return Tetromino.SIZE * 3;
        }
    }
}
