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
	public final int x = 100;
	public final int y = 100;
	public static Render r;
	public Rectangle bird;
	public final int birdWidth = 50;
	public final int birdHeight = 50;
	public final int levelTwo = 10;
	public int ticks;
	public int yMotion;
	public int score;
	public int highScore;
	public boolean gameEnd = false;
	public boolean started = false;
	private boolean flap = false;
	
	// Initializing pipes in game.
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
			if (!flap)
			{
				flap = true;
			}
			else 
			{
				flap = false;
			}
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
				if (gameEnd)
				{
					column.x = column.x;
				}
				// Higher difficulty: if user passes a score of 10, level gets harder.
				else if (score <= levelTwo)
				{
					column.x -= speed;
				}
				
				else
				{
					speed = 15;
					column.x -= speed;
				}
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
					score++;
					// Sets highest total score to highScore variable.
					if (score > highScore)
					{
						highScore = score;
					}
				}
			}
		}
		// If the bird dies, it drops to the ground.
		if (gameEnd)
		{
			if(bird.intersects(0, HEIGHT-121, WIDTH, 150))
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
		
		g.setColor(Color.ORANGE.darker());
		g.fillRect(0, HEIGHT - 90, WIDTH, 120);
		
		// Creating Bird.
		g.setColor(Color.MAGENTA);
		g.fillRoundRect(bird.x, bird.y, bird.width, bird.height , bird.width, bird.height);
					
		// Creating eye.
		g.setColor(Color.WHITE);
		g.fillOval(bird.x + bird.width - 15, bird.y, bird.width/2, bird.height/2 + 5);
					
		// Creating pupil.
		g.setColor(Color.BLACK);
		g.fillOval(bird.x + bird.width - 3, bird.y + 8, bird.width/4, bird.height/4);
		
		// Creating beak.
		g.setColor(Color.ORANGE);
		g.fillOval(bird.x + bird.width - 15, bird.y + 30, bird.width, bird.height/3);
					
		// Creating and flapping wing.
		g.setColor(Color.YELLOW);
		if(!flap)
		{
			g.fillOval(bird.x - 15, bird.y + 20, bird.width - 10, bird.height/2);
		}
		else
		{
			g.fillOval(bird.x, bird.y + 20, bird.height/2, bird.width - 10);
			flap = false;
		}
		
		
		// Painting the pipes.
		for (Rectangle column : columns)
		{
	
			paintColumns(g, column);
		}
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Developer: Sanjay Sundaram", x - 90, y + 650);
		
		// Displaying messages.
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		if (!started)
		{
			bird.y = 300;
			g.setColor(Color.BLACK);
			g.drawString("Click to Start", WIDTH/2 - 150, HEIGHT/2);
			
		}
		
		if (gameEnd)
		{
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", WIDTH/2 - 150, HEIGHT/3);
			
			g.drawString("Click to Restart", WIDTH/2 - 160, HEIGHT/2);
			g.setColor(Color.RED);
			g.drawString("High Score: " + highScore, WIDTH/2 + 50, HEIGHT/2 - 300);
			
		}
		
		if (started)
		{
			// Creating new difficulty(Speed Boost).
			g.setColor(Color.BLACK);
			g.drawString("Score: " + score, WIDTH/2 - 300, HEIGHT/2 - 300);
			if (score > levelTwo)
			{
				g.drawString("Speed Boost!", WIDTH/2 - 100, HEIGHT/2 + 300);
			}

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
		
		else if (!start)
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		
		
		
	}
	public void paintColumns(Graphics g, Rectangle column)
	{
		if (score > levelTwo)
		{
			g.setColor(Color.orange);
			g.fillRect(column.x, column.y, column.width, column.height);
		}
		else
		{
			g.setColor(Color.green.darker());
			g.fillRect(column.x, column.y, column.width, column.height);
		}
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
