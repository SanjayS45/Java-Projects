import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;



public class FlappyBird_Game extends JFrame implements ActionListener
{
	private CardLayout layout;
	private static FlappyBird_Game frame;
	private static JButton bStart;
	private static JPanel p;
	private static JPanel p1;
	private static JPanel main;
	private static JButton bClick;
	private static Container cPane;
	private static Bird b;
	public FlappyBird_Game()
	{
		b = new Bird();
		layout = new CardLayout();
		p = new JPanel();
		bStart = new JButton("Start");
		bStart.addActionListener(this);
		p.add(bStart);
		p.setVisible(true);

		p1 = new JPanel();
		bClick = new JButton("Click");
		p1.add(bClick);
		p1.add(b);
		p1.setVisible(true);
		
		cPane = getContentPane();
		cPane.setLayout(layout);
		cPane.add(p,"Menu");
		cPane.add(p1, "Game");
	}
	
	
	//@Override
	public void actionPerformed(ActionEvent e)
	{
		String t = e.getActionCommand();
		
		if (t.equals("Start"))
		{
			layout.next(cPane);
			
		}		
		
		else if (t.equals("bClick"))
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		frame = new FlappyBird_Game();
		frame.setSize(600, 600);
		frame.setVisible(true);

	}
}
