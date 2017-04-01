package client;
import java.awt.Canvas;

import javax.swing.JFrame;

public class Window extends Canvas
{
	// Constructor:
	public Window(int width, int height, String title, Game game)
	{
		JFrame frame = new JFrame(title);
		
		frame.setSize(width + 7, height - 2);
		frame.setResizable(false);
		frame.setBounds(0, 0, width + 7, height - 2);  //setSize considers the borders as parts of its bounds, so to account for that, I'm adding/subtracting
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
	}
}