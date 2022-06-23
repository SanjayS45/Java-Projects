package FlappyBird_Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;




public class FlappyBird extends JFrame implements ActionListener, MouseListener
{	
	public static FlappyBird frame;
	public final int WIDTH = 800;
	public final int HEIGHT = 800;
	public int x = 100;
	public int y = 100;
	public static Render r;
	public Rectangle bird;
	public int birdWidth = 50;
	public int birdHeight = 50;
	
	public int ticks;
	public int yMotion;
	public int score;
	public boolean gameEnd = false;
	public boolean started = false;
	
	// Initalizing pipes in game.
	public ArrayList<Rectangle> columns;
	
	public FlappyBird()
	{
		
		r = new Render();
		Timer timer = new Timer(20, this);
		
		bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, birdWidth, birdHeight);
		columns = new ArrayList<Rectangle>();
		
		super.add(r);
		super.addMouseListener(this);
		super.setSize(WIDTH, HEIGHT);
		super.setResizable(false);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addColumn(true);
		addColumn(true);
			
		timer.start();
	}
	
	// If the user clicks on the screen.
	public void click()
	{
		int boost = 10;
		
		if (gameEnd)
		{
			bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, bird.width, bird.height);
			columns.clear();
			
			yMotion = 0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			
			gameEnd = false;
		}
		if (!started)
		{
			started = true;
		}
		
		// If the user is in the middle of the game.
		else if (!gameEnd)
		{
			if (yMotion  > 0)
			{
				yMotion = 0;
			}
			
			yMotion -= boost;
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int speed = 10;	
		ticks++;
		if (started)
		{
			// Moving the columns across the screen.
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			// With every other tick, gravity acts upon the bird(pulling it down).
			if (ticks % 2 == 0 && yMotion < 15)
			{
				yMotion += 2;
			}
			bird.y += yMotion;
			
			// If a pipe reaches end of screen, pipe gets deleted.
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);
				if (column.x + column.width <= 0)
				{
					columns.remove(column);
					addColumn(false);
				}
			}
			
			// If the bird hits a pipe, the ground, or the ceiling, it dies - Game Over.
			for (Rectangle column : columns)
			{
				if (bird.intersects(column)) 
				{
					gameEnd = true;
					bird.x = column.x - bird.width;
				}
				if (bird.y > HEIGHT - 120 || bird.y <= 0)
				{
					gameEnd = true;
				}
				
			}
			
			// If the bird passes a pipe, the score increases.
			for (Rectangle column : columns)
			{
				if (column.y == 0 && bird.x > column.x + column.width && bird.x < column.x + column.width + 15)
				{
					score ++;
				}
			}
			
			// If the bird dies, it drops to the ground.
			if (gameEnd)
			{
				bird.y = HEIGHT - bird.height - 120;
			}
		}
		
		r.repaint();
	}
	
	public void repaint(Graphics g)
	{
		// Creating Sky.
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Creating Grass.
		g.setColor(Color.GREEN);
		g.fillRect(0, HEIGHT-120 , WIDTH, 150);
		
		// Creating Bird.
		g.setColor(Color.MAGENTA);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		// Painting the pipes.
		for (Rectangle column : columns)
		{
	
			paintColumns(g, column);
		}
		
		// Displaying messages.
		if (!started)
		{
			bird.y = 300;
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Click to Start", WIDTH/2 - 150, HEIGHT/2);
		}
		
		if (gameEnd)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("GAME OVER", WIDTH/2 - 150, HEIGHT/2);
		}
		
		if (started)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Score: " + score, WIDTH/2 - 200, HEIGHT/2 - 300);
			
		}
		
	}
	 
	public void addColumn(boolean start)
	{
		int space = 300;
		int width = 100;
		int height = (int)(50 + Math.random()*300);
		
		// If game has started, pipes are added to screen, otherwise they are added beyond the screen.
		if (start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size()*300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1)*300, 0, width, HEIGHT - height - space));
		}
		
		else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		
	}
	public void paintColumns(Graphics g, Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	
	public static void main(String[] args)
	{
		frame = new FlappyBird();

	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		click();
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}
}

// Creates and renders flappy bird game.
public class Render extends JPanel
{
	
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		FlappyBird.frame.repaint(g);
	}
}
