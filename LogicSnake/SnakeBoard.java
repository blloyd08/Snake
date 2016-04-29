/*
 * Class: SnakeBoard
 * Author: Ameet Toor ameet2r@uw.edu
 * Date: 4/15/2016
 * Description: This class makes up the board on which the game will be played. It starts and ends the game, as well as
 * checks for collisions and makes new food.
 */

package LogicSnake;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SnakeBoard extends Component {

    /**Width of the board.*/
    private final int myWidth;

    /**Depth of board.*/
    private final int myHeight;

    /**Speed of the snake.*/
    private final int SPEED = 1000;

    /**Timer*/
    private Timer myTimer;

    /**
     * The snake that the user will play the game with.
     */
    private Snake mySnake;

    /**
     * Food that will be moved around everytime the old location was gone over by the snake.
     */
    private Food myFood;

    /**
     * Starts and ends game, as well as creates the board.
     * @param theWidth The width of the game board.
     * @param theHeight The height of the game board.
     */
    public SnakeBoard(final int theWidth, final int theHeight)
    {
        myWidth = theWidth;
        myHeight = theHeight;
    }

    /**
     * Returns true if the given Snake head is within bounds. False otherwise.
     * Out of bounds means location < 0 or location > width or location > height.
     * @return True if snake head not out of bounds. False if out of bounds.
     */
    public Boolean withinBounds(Point theHeadLocation)
    {
        //TODO: test
        //If snake head location is out of bounds return false.
        if (theHeadLocation.getX() < 0
                || theHeadLocation.getY() < 0
                || theHeadLocation.getY() > myHeight
                || theHeadLocation.getX() > myWidth) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retuns true if any part of the snake body has collided with itself. False if no part of the body has collided
     * with itself.
     * @return True if snake collided with itself, false otherwise.
     */
    public Boolean isThereCollision(Point theBodyLocations[])
    {
        //TODO Test
        Boolean result = false;
        //Check to make sure the array is not empty
        if ( theBodyLocations.length < 1 )
        {
            System.out.println("Array is empty!");
            result = false;
        } else
        {
            //If snake head is equal to any other point in the array there has been a collision

            //The head Point
            Point myHeadLocation = theBodyLocations[0];

            //TODO check if faster way to compare points. Sort array?
            //Go through array and check if any of the locations match.
            for (Point point: theBodyLocations)
            {
                if (point.getX() == myHeadLocation.getX() && point.getY() == myHeadLocation.getY())
                {
                    result = true;
                }
            }
        }
        return result;
    }


    /**Creates timer and sets delay and repeat. During each tick of the timer,
     * the snake will move, but will not grow.*/
    private void createTimer()
    {
        myTimer = new Timer(SPEED, theEvent ->
        {
            //Step the snake
            mySnake.move(false);
        });
        myTimer.stop();
    }

    /**
     * Opens JOptionPane that displays the message: "Game Over".
     */
    public void displayEndOfGameMessage()
    {
        //Display end of game message.
        try
        {
            final ImageIcon icon = new ImageIcon(ImageIO.read(
                    new File("images/gameOver.jpg")));
            JOptionPane.showMessageDialog(this, "", "Game Over"
                    , JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (final IOException exception)
        {
            exception.printStackTrace();
        }

    }

    public void endGame() {
        //Stops timer
        myTimer.stop();

        //Stops all movement controls from working.

        //Displays end of game message.
        displayEndOfGameMessage();
    }

    public void startGame() {
        //Starts timer
        createTimer();

        //Makes all snake movement workable

        //Creates Snake
        mySnake = new Snake();

        //Generates food

    }

    public int randomWidth()
    {
        return 0;
    }

    public int randomHeight()
    {
        return 0;
    }




}
