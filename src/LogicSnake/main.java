package LogicSnake;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
	
	public static void main(String[] args) {
		SnakeBoard board = new SnakeBoard(10,10);
		board.startGame();
		board.printBoard();
		
		JFrame frame = new JFrame();
		Timer timer = new Timer(500, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(board.isGameRunning())
					board.printBoard();
			}
			
		});
		timer.setRepeats(true);
		frame.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()){
				case KeyEvent.VK_UP:
					board.setDirection(Direction.South);
					break;
				case KeyEvent.VK_DOWN:
					board.setDirection(Direction.North);
					break;
				case KeyEvent.VK_LEFT:
					board.setDirection(Direction.West);
					break;
				case KeyEvent.VK_RIGHT:
					board.setDirection(Direction.East);
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
	
}
