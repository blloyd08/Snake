/* Class: 	Snake
 * Author:	Brian Lloyd
 * Date:	4/15/2016
 * Desc:	This class represents the snake who has a body made up of nodes that all follow each other
 * 			when the snake moves. The snake can grow in size and moves in four Directions.
 */

package LogicSnake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents the snake who has a body made up of nodes that all follow each other
 * when the snake moves. The snake can grow in size and moves in four Directions.
 * @author Brian Lloyd
 */
public class Snake implements Collidable{
	
	/** Beginning node of the linked list that represents the snake */
	private final SnakeNode myHead;
	
	/** The direction the snake will travel when moved */
	private Direction myDirection;
	
	/**
	 * Constructs a snake that has a head and a direction of travel
	 */
	public Snake(){
		myHead = new SnakeNode(0,0);
		myDirection = Direction.East;
	}
	
	/**
	 * Sets the current direction that the snake will move
	 * 
	 * @param theDirection the direction the snake will travel to when moved.
	 */
	public void setDirection(Direction theDirection){
		myDirection = theDirection;
	}
	
	/**
	 * Moves the snake in the current direction. If the snake will grow, a new node will be added
	 * to the back of the snake after the snake has moved.
	 * 
	 * @param willGrow flag determining if the snake should grow when move is complete
	 */
	public boolean move(boolean willGrow){
		Point tempPosition = myHead.position;
		
		//Change HeadPosition
		Point directionPoint = decodeDirection(myDirection);
		myHead.position = new Point( myHead.position.x + directionPoint.x,
									 myHead.position.y + directionPoint.y);
		return moveSnakeBody(myHead, tempPosition, myHead.position, willGrow);
		
	}
	
	/**
	 * Returns all the positions of the snakes body
	 * 
	 * @return all the positions of the snakes body
	 */
	public Collection<Point> getPositions(){
		//Create list of points
		ArrayList<Point> allPositions = new ArrayList<Point>();
		
		//Iterate through snake and get all positions
		getSnakePositions(allPositions, myHead);
		
		return allPositions;
	}
	
	/**
	 * Returns the position of the head of the snake.
	 * 
	 * @return position of the head node of the snake
	 */
	public Point getHeadPosition(){
		return new Point(myHead.position.x, myHead.position.y);
	}
	
	public String toString(){
		Collection<Point> positions = getPositions();
		StringBuilder sb = new StringBuilder();
		sb.append("[" + myDirection.toString() + ", {");
		for (Point p : positions){
			sb.append("" + p.x + "-" + p.y);
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("}]");
		return sb.toString();
	}
	
	/**
	 * Checks to see if some point matches a point on the snake
	 * 
	 * @param newPosition the point that will be matched against other snake body points
	 */
	@Override
	public boolean hasCollision(Point newPosition) {
		boolean collisionFound = bodyCollision(myHead,newPosition);
		System.out.println(collisionFound);
		return collisionFound;
	}
	
	/**
	 * Iterates through the body of the snake looking for points that match the parameter point.
	 * @param body linked list node representing the body of the snake to check point
	 * @param newPosition position that must much the snake body position
	 * @return true if the parameter point matches a nodes position, false otherwise
	 */
	private boolean bodyCollision(SnakeNode body, Point newPosition){
		if (body == null) return false;
		System.out.println("\nBody: " + body.position + " Food Position: " + newPosition);
		if (newPosition.equals(body.position)){
			return true;
		} else {
			return bodyCollision(body.next, newPosition);
		}
	}
	
	/**
	 * Iterates through the linked list of snake nodes and adds each nodes position to a list
	 * 
	 * @param thePositions list of all node positions
	 * @param node the node who's position should be added to the list
	 */
	private void getSnakePositions(ArrayList<Point> thePositions, SnakeNode node){
		if (node != null){
			thePositions.add(node.position);
			getSnakePositions(thePositions, node.next);
		}
	}
	
	/**
	 * Iterates through the linked list of snake nodes and updates there position.
	 * If the snake will grow, a new node will be added to the end of the list.
	 * 
	 * @param head snake node that is already in position, all nodes following will be moved.
	 * @param position the position that the next node should be moved to.
	 * @param grow flag determining if a new node should be added to the back of the snake
	 * 
	 * @return true if the snake collides with itself, false otherwise
	 */
	private boolean moveSnakeBody(SnakeNode head, Point position, Point collisionPoint, boolean grow){
		//Check if passed node is null, base case
		if (head != null){
			//System.out.println("\nHead: " + head.position + "\nCollision: " + collisionPoint);
			//Reached end of snake, add new node if needing to grow
			if (head.next == null){
				if (grow){
					head.next = new SnakeNode(position.x, position.y);
				}
			}
			//Check next node in the snake, check for collision with self
			else {
				Point tmpPosition  = head.next.position;
				head.next.position = position;
				return collisionPoint.equals(head.next.position) 
						| moveSnakeBody(head.next, tmpPosition, collisionPoint, grow);
			}
		}
		return false;

	}
	
	/**
	 * Returns the relative point correlating to the direction.
	 * North with return a point (0,1), south (0,-1) and so on.
	 * 
	 * @param theDirection direction that will be decoded
	 * @return returns the relative point correlating to the direction.
	 */
	private Point decodeDirection (Direction theDirection){
		Point returnPoint;
		switch (theDirection){
		case North:
			returnPoint = new Point(0,1);
			break;
		case South:
			returnPoint = new Point(0, -1);
			break;
		case East:
			returnPoint = new Point(1,0);
			break;
		case West:
			returnPoint = new Point(-1,0);
			break;
		default:
			returnPoint = null;
		}
		
		return returnPoint;
	}

	/**
	 * Snake node class represents a block on the snake.
	 * @author Brian Lloyd
	 */
	class SnakeNode{
		private Point position;
		private SnakeNode next;
		
		public SnakeNode(int x, int y){
			position = new Point(x, y);
			next = null;
		}
		
		public SnakeNode(int x, int y, SnakeNode child){
			position = new Point(x, y);
			next = child;
		}
		
		public void setPosition(int x, int y){
			position = new Point(x,y);
		}
		
		public void setChild(SnakeNode child){
			next = child;
		}
		
		@Override
		public String toString(){
			return (position.toString());
		}
		
	}
}
