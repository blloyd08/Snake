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
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Width of the board.*/
    private final int myWidth;

    /**Depth of board.*/
    private final int myHeight;

    /**Speed of the snake. Timer tick duration*/
    private final int SPEED = 1000;

    /**Timer*/
    private Timer myTimer;

    /**The food object that will be moved around whenever the Snake object hits it.*/
    private Food myFood;

    /**The Snake that the user will play with.*/
    private Snake mySnake;
    
    /**Flag to mark that food has collided with the snake head.*/
    private boolean myWillGrow;
    
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
        myWillGrow = false;
    }
    
	public void setDirection(Direction theDirection){
		mySnake.setDirection(theDirection);
	}

    /**
     * Returns true if the given Snake head is within bounds. False otherwise.
     * Out of bounds means location < 0 or location > width or location > height.
     * @return True if snake head not out of bounds. False if out of bounds.
     */
    public boolean withinBounds(Point theHeadLocation)
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
    public boolean isThereCollision()
    {
        //TODO Test
        Boolean result = false;
        
        //Check if snake head is within the bounds of the game board
        if (mySnake.getHeadPosition().x > myWidth - 1 || mySnake.getHeadPosition().x < 0
        		|| mySnake.getHeadPosition().y > myHeight - 1 || mySnake.getHeadPosition().y < 0)
        	result = true;
        
        return result;
    }

    /**
     * Returns whether the game timer is running. This resembles the current game running status.
     * @return true if the game is running, false otherwise
     */
    public boolean isGameRunning(){
    	return myTimer.isRunning();
    }

    /**
     * Creates timer and sets delay and repeat.
     * Every time the timer ticks, the snake will move but not grow.
     */
    private void createTimer()
    {
        myTimer = new Timer(SPEED, theEvent ->
        {
            update();
        });
        myTimer.stop();
    }
    
    //Updates game state
    //Called at each timer tick
    private void update(){
    	boolean snakeBodyCollision = false;
    	
    	//Step the snake - Returns true is snake collides with self
        snakeBodyCollision = mySnake.move(myWillGrow);
        //System.out.println(snakeBodyCollision);
        
        //Handle snake collision with self
        if (snakeBodyCollision || isThereCollision())
        	endGame();
        
        //Set grow flag off if previously set
        myWillGrow = false;
        
        //If snake head collides with food. Grow snake and place new food
        myWillGrow = (mySnake.getHeadPosition().equals(myFood.getLocation()));
        //System.out.println("\nWill grow: " + myWillGrow);
        //System.out.println(mySnake.getHeadPosition() + " " + myFood.getLocation());
        if(myWillGrow){
        	//Place new food
        	generateFood();
        }
        	
    }

    /**
     * Opens JOptionPane that displays the message: "Game Over".
     * 
     *Possibly the front end should take care of this. Maybe a print to console?
     */
    public void displayEndOfGameMessage()
    {
    	System.err.println("Game Over");
//        //Display end of game message.
//        try
//        {
//            final ImageIcon icon = new ImageIcon(ImageIO.read(
//                    new File("images/gameOver.jpg")));
//            JOptionPane.showMessageDialog(this, "", "Game Over"
//                    , JOptionPane.INFORMATION_MESSAGE, icon);
//        } catch (final IOException exception)
//        {
//            exception.printStackTrace();
//        }

    }

    public void endGame() {
        //Stops timer
        myTimer.stop();

        //Stops all movement controls from working.
        //TODO

        //Displays end of game message.
        displayEndOfGameMessage();
    }

    // Should the timer be started on the start of the game?
    public void startGame() {
        //Starts timer
        createTimer();

        //Makes all snake movement workable
        //TODO

        //Creates Snake
        mySnake = new Snake();

        //Generates food
        generateFood();
        
        myTimer.start();
        
    }

    public void generateFood() {
    	
    	//Choose random position
    	int x;
        int y;
    	
    	//Keep placing food until it doesn't overlap with the snake.
    	do {
    		x = randomWidth();
            y = randomHeight();
    	} while(mySnake.hasCollision(new Point(x,y)));
    	System.out.println("X: " + x + " Y: " + y);
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
        int x = randomInt(myWidth);

        //Whenever an X location is chosen remove the location from the list.
        //myXLocations.remove(x);

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
        int y = randomInt(myHeight);

        //Whenever a Y location is chosen remove the location from the list
        //myYLocations.remove(y);
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

	public void printBoard(){
		ArrayList<Point> snakeBodyPoints = new ArrayList<Point>(mySnake.getPositions());
		char[][] board = new char[myWidth][myHeight];
		for (Point p : snakeBodyPoints)
			board[p.x][p.y] = 'X';
		board[myFood.getLocation().x][myFood.getLocation().y] = 'O';
		System.out.print("\n_");
		for (int i = 0; i < myWidth; i++)
			System.out.print(i);
		System.out.print('_');
		for (int i = 0; i < board.length; i++){
			System.out.print("\n" + i);
			for (int j = 0; j < board[0].length; j++){
				if (board[j][i] == 'X')
					System.out.print('X');
				else if (board[j][i] == 'O')
					System.out.print('O');
				else
					System.out.print(" ");
				
			}
			System.out.print("|");
		}
		System.out.print("\n--");
		for (int i = 0; i < myWidth; i++)
			System.out.print('-');
	}

}
