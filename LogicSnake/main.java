package LogicSnake;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class main {

	public static void main(String[] args) {
		Snake s = new Snake();
		s.move(true);
		s.move(true);
		s.move(true);
		System.out.println(s);
		s.setDirection(Direction.North);
		System.out.println(s);
	 	boolean eatFood = s.hasCollision(food.getPosition);
	 	s.move(eatFood);
	 	food.newPostion();
		s.move(false);
		s.move(false);
		System.out.println(s);
		s.move(false);
		s.move(false);
		s.move(false);
		s.move(false);
		s.move(true);
		s.move(true);
		s.move(true);
		s.setDirection(Direction.East);
		s.move(true);
		s.move(true);
		s.setDirection(Direction.South);
		s.move(true);
		s.move(false);
		s.move(false);
		s.move(false);
		s.move(false);
		System.out.println(s);
		System.out.println(s.getPositions().toString());
		printBoard(s.getPositions());
		JFrame frame = new JFrame();
		Timer timer = new Timer(500, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				s.move(false);
				printBoard(s.getPositions());
			}
			
		});
		timer.setRepeats(true);
		frame.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()){
				case KeyEvent.VK_UP:
					s.setDirection(Direction.South);
					break;
				case KeyEvent.VK_DOWN:
					s.setDirection(Direction.North);
					break;
				case KeyEvent.VK_LEFT:
					s.setDirection(Direction.West);
					break;
				case KeyEvent.VK_RIGHT:
					s.setDirection(Direction.East);
					break;
				case KeyEvent.VK_SPACE:
					if (timer.isRunning())
						timer.stop();
					else
						timer.start();
				} 
			}
		});
		
		frame.setVisible(true);
	}
	
	private static void printBoard(Collection<Point> thePoints){
		char[][] board = new char[10][10];
		for (Point p : thePoints)
			board[p.x][p.y] = 'X';
		System.out.print("____________");
		for (int i = 0; i < board.length; i++){
			System.out.print("\n|");
			for (int j = 0; j < board[0].length; j++){
				if (board[j][i] == 'X')
					System.out.print('X');
				else
					System.out.print(" ");
			}
			System.out.print("|");
		}
		System.out.println("\n------------");
	}
}
