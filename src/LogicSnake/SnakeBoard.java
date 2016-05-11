/*
Class: SnakeBoard
Author: Ameet Toor
Date: 4/15/16
Description: The SnakeBoard class starts and ends the game. It checks to make sure there are no collisions with the
snake hitting itself. Or the snake hitting the walls. This class will also create the food at random locations. When
a food is eaten, a new food will be created at a random location.
 */

package LogicSnake;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SnakeBoard extends Component {

    //TODO gets keyboard inputs to dictate direction of the move.
    
    /**Width of the board.*/
    private final int myWidth;

    /**Depth of board.*/
    private final int myHeight;

    /**Speed of the snake.*/
    private final int SPEED = 1000;

    /**Timer*/
    private Timer myTimer;

    /**The food object that will be moved around whenever the Snake object hits it.*/
    private Food myFood;

    /**The Snake that the user will play with.*/
    private Snake mySnake;

    /**Arraylist of all X locations that are available.*/
    private ArrayList<Boolean> myXLocations;

    /**Arraylist of all Y locations that are available.*/
    private ArrayList<Boolean> myYLocations;

    /**
     * Starts and ends game, as well as creates the board.
     * @param theWidth The width of the game board.
     * @param theHeight The height of the game board.
     */
    public SnakeBoard(final int theWidth, final int theHeight)
    {
        myWidth = theWidth;
        myHeight = theHeight;
        myXLocations = new ArrayList<>();
        myYLocations = new ArrayList<>();
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
        return !(theHeadLocation.getX() < 0
                || theHeadLocation.getY() < 0
                || theHeadLocation.getY() > myHeight
                || theHeadLocation.getX() > myWidth);
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


    /**
     * Creates timer and sets delay and repeat.
     * Every time the timer ticks, the snake will move but not grow.
     */
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
        //TODO

        //Displays end of game message.
        displayEndOfGameMessage();
    }

    public void startGame() {
        //Starts timer
        createTimer();

        //Makes all snake movement workable
        //TODO

        //Creates Snake
        mySnake = new Snake();

        //Generates food
        generateFood();
    }

    public void generateFood() {
        //Choose random position
        final int x = randomWidth();
        final int y = randomHeight();

        //Generate food
        myFood = new Food(x,y);
    }

    /**
     * Randomly chooses an int that is within the bounds of the x axis.
     * Will be a different int each time, no repeats.
     * @return An int that is within the x axis.
     */
    private int randomWidth()
    {
        //TODO Test

        //Randomly select an X
        int x = randomInt(myXLocations.size());

        //Whenever an X location is chosen remove the location from the list.
        myXLocations.remove(x);

        return x;
    }

    /**
     * Randomly chooses an int that is within the bounds of the y axis.
     * Will be a different int each time, no repeats.
     * @return An int that is within the y axis.
     */
    private int randomHeight()
    {
        //TODO Test
        // Randomly select an that is not true
        int y = randomInt(myYLocations.size());

        //Whenever a Y location is chosen remove the location from the list
        myYLocations.remove(y);
        return y;
    }

    /**
     * Returns a random integer between 0 and theRange - 1.
     * @param theRange The range to choose a number between.
     * @return Random int between 0 and theRange - 1.
     */
    private int randomInt(int theRange)
    {
        //Todo Test
        Random random = new Random();
        return random.nextInt(theRange);
    }


}
