package LogicSnake;

import java.awt.Point;
import java.util.Random;

public class Food implements Collidable {
	
//	/** The maximum x position */
//	private final int myWidthMax;
//	
//	/** The maximum y position */
//	private final int myHeightMax;
	
	/** The Location of the one food position */
	private Point myLocation;
	
	public Food(final int theXPosition, final int theYPosition){
//		myWidthMax = theBoardWidth;
//		myHeightMax = theBoardHeight;
		myLocation = new Point(theXPosition, theYPosition);
	}
	
	/**
	 * Gets the current location of the food
	 * 
	 * @return the x and y location of the food
	 */
	public Point getLocation(){
		return new Point(myLocation.x, myLocation.y);
	}
	
	/**
	 * Randomly sets the food location by randomly selecting an x position
	 * between 0 and the width and a random y position between 0 and the height.
	 */
//	public void newLocation(){
//		Random ran = new Random();
//		int x, y;
//		x = ran.nextInt(myWidthMax + 1);
//		y = ran.nextInt(myHeightMax + 1);
//		myLocation = new Point(x,y);
//	}

	/**
	 * Checks if the parameter point matches the foods point
	 * 
	 * @param newPoint point that must match the food point
	 * @return true if parameter point matches foods point.
	 */
	@Override
	public boolean hasCollision(Point newPoint) {
		return myLocation.equals(newPoint);
	}
}
