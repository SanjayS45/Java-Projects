import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Scanner;

public class Calculator extends JFrame implements ActionListener
{
	// Button initialization.
	private static JButton button0;
	private static JButton button1;
	private static JButton button2;
	private static JButton button3;
	private static JButton button4;
	private static JButton button5;
	private static JButton button6;
	private static JButton button7;
	private static JButton button8;	
	private static JButton button9;
	private static JButton buttonAdd;
	private static JButton buttonSubtract;
	private static JButton buttonMultiply;
	private static JButton buttonDivide;
	private static JButton buttonPeriod;
	private static JButton buttonClear;
	private static JButton buttonEquals;
	private static JButton buttonDel;
	private static JButton buttonNeg;
	private static JButton buttonFraction;
	
	// variable used in +/- button
	private static boolean isPositive = true;
	
	private static JTextField tf;
	
	// a is the 1st number, b is the 2nd number, operator is: +, -, *, /.
	private String a, b, operator;

	// Solution to the problem.
	double sol;
	
	public Calculator()
	{
		   a = "";
		   b = "";
		   operator = "";
		   sol = 0.0;
		   setTitle("Calculator");
		   
		   // Window will close when user presses the 'x' on the top right.
	       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       setSize(800, 800);
		   
	       // Creating 2 panels: p1 for the text box and p for the buttons.
		   JPanel p1 = new JPanel();
		   JPanel p = new JPanel();
		   
		   // Creates a grid of 1 row/1 column for the text box, and 5 rows/5 columns for the buttons.
		   p1.setLayout(new GridLayout(1,1));
		   p.setLayout(new GridLayout(5, 4, 5, 5));
		   p.setBackground(Color.CYAN);
		   
		   // Building and formatting 20 buttons.
		   buttonClear = new JButton("C");
		   buttonClear.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(buttonClear);
		   buttonClear.addActionListener(this);;
		   
		   buttonDel = new JButton("Del");
		   buttonDel.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(buttonDel);
		   buttonDel.addActionListener(this);
		   
		   buttonFraction = new JButton("1/x");
		   buttonFraction.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(buttonFraction);
		   buttonFraction.addActionListener(this);
		   
		   buttonAdd = new JButton("+");
		   buttonAdd.setFont(new Font("Arial", Font.BOLD, 30));
		   p.add(buttonAdd);
		   buttonAdd.addActionListener(this);
		   
		   button1 = new JButton("1");
		   button1.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button1);
		   button1.addActionListener(this);
		   
		   button2 = new JButton("2");
		   button2.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button2);
		   button2.addActionListener(this);
		   
		   button3 = new JButton("3");
		   button3.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button3);
		   button3.addActionListener(this);
		   
		   buttonSubtract = new JButton("-");
		   buttonSubtract.setFont(new Font("Arial", Font.BOLD, 30));
		   p.add(buttonSubtract);
		   buttonSubtract.addActionListener(this);
		   
		   button4 = new JButton("4");
		   button4.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button4);
		   button4.addActionListener(this);
		   
		   button5 = new JButton("5");
		   button5.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button5);
		   button5.addActionListener(this);
		   
		   button6 = new JButton("6");
		   button6.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button6);
		   button6.addActionListener(this);
		   
		   buttonMultiply = new JButton("*");
		   buttonMultiply.setFont(new Font("Arial", Font.BOLD, 30));
		   p.add(buttonMultiply);
		   buttonMultiply.addActionListener(this);
		   
		   button7 = new JButton("7");
		   button7.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button7);
		   button7.addActionListener(this);
		   
		   button8 = new JButton("8");
		   button8.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button8);
		   button8.addActionListener(this);
		   
		   button9 = new JButton("9");
		   button9.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button9);
		   button9.addActionListener(this);
		   
		   buttonDivide = new JButton("/");
		   buttonDivide.setFont(new Font("Arial", Font.BOLD, 30));
		   p.add(buttonDivide);
		   buttonDivide.addActionListener(this);
		   
		   buttonNeg = new JButton("+/-");
		   buttonNeg.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(buttonNeg);
		   buttonNeg.addActionListener(this);
		   
		   button0 = new JButton("0");
		   button0.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(button0);
		   button0.addActionListener(this);
		   
		   buttonPeriod = new JButton(".");
		   buttonPeriod.setFont(new Font("Arial", Font.BOLD, 30));
		   p.add(buttonPeriod);
		   buttonPeriod.addActionListener(this);
		   
		   buttonEquals = new JButton("=");
		   buttonEquals.setFont(new Font("Arial", Font.BOLD, 24));
		   p.add(buttonEquals);
		   buttonEquals.addActionListener(this);
		   
		   // Making a text box for the output.
		   tf = new JTextField(4);
		   tf.setFont(new Font("Arial", Font.BOLD, 24));
		   tf.setEditable(false);
		   p1.add(tf);	
		   
	       // Formatting the panels to fit the frame.
	       add(p1, BorderLayout.NORTH);
	       add(p, BorderLayout.CENTER);
	       pack();
	       setVisible(true);

	}
	
	// The actionPerformed class captures any user clicks on buttons and assigns a function to each button.
	public void actionPerformed(ActionEvent e)
	{
		   double d;
		   String t = e.getActionCommand();
		   // Checks to see if the user inputs a number or a period.
		   if ((Character.isDigit(t.charAt(0)) && Character.isDigit(t.charAt(t.length()-1))) || t.charAt(0) == '.')
		   {
			   // sets 1st number equals to user input if operator is blank.
			   if (operator.equals(""))
			   {
				   a += t;
			   }
			   
			   // sets 2nd number equal to user input.
			   else
			   {
				   b += t;
			   }
			   tf.setText(a + operator + b);
			   
		   }
		   
		   // Checks to see if the user inputs the Clear button.
		   else if (t.charAt(0) == 'C')
		   {
			   a = "";
			   b = "";
			   operator = "";
			   isPositive = true;
			   // Sets format of text
			   tf.setText(a + operator + b);
		   }
		   
		   // Checks to see if the user inputs the delete button.
		   else if(t.equals("Del"))
		   {
			   if (!b.equals(""))
			   {
				   b = "";
			   }
			   
			   else if (!operator.equals(""))
			   {
				   operator = "";
			   }
			   
			   else
			   {
				   a = "";
			   }
			   tf.setText(a + operator + b);
			   
		   }
		   
		   // Checks to see if user inputs the +/- button.
		   else if (t.equals("+/-"))
		   {
			   // If the button is on positive mode, then set it to negative mode.
			   if (isPositive)
			   {
				   if (!b.equals(""))
				   {
					   b = "-" + b;
					   tf.setText(a + operator + b);
				   }
				   
				   else if ((!a.equals("")) && operator.equals(""))
				   {
					   a = "-" + a;
					   tf.setText(a + operator + b);
				   }
				   
				   isPositive = false;
			   }
			   
			   // If button is on negative mode, then set it to positive mode.
			   else if(!isPositive)
			   {
				   if (!b.equals(""))
				   {
					   b = b.substring(1);
					   tf.setText(a + operator + b);
				   }
				   
				   else if (!a.equals("") && operator.equals(""))
				   {
					   a = a.substring(1);
					   tf.setText(a + operator + b);
				   }
				   
				   isPositive = true;
			   }
		   }
		   
		   // Checks to see if user inputs the 1/x button.
		   else if (t.equals("1/x"))
		   {
			   if (operator.equals("") && !a.equals(""))
			   {
				    d = 1/Double.parseDouble(a);
				    a = Double.toString(d);
			   }
			   
			   else if (!b.equals(""))
			   {
				   d = 1/Double.parseDouble(b);
				   b = Double.toString(d);
			   }
			   tf.setText(a + operator + b);
		   }
		   
		// Checks to see if user inputs the = button.
		   else if (t.charAt(0) == '=')
		   {
			   // Sets the solution to the problem in sol, dependent on what the operator is.
			   if (operator.equals("+"))
			   {
				   sol = Double.parseDouble(a) + Double.parseDouble(b);
			   }
			   
			   else if (operator.equals("-"))
			   {
				   sol = Double.parseDouble(a) - Double.parseDouble(b);
			   }
			   
			   else if (operator.equals("*"))
			   {
				   sol = Double.parseDouble(a) * Double.parseDouble(b);
			   }
			   
			   else if (operator.equals("/"))
			   {
				   sol = Double.parseDouble(a) / Double.parseDouble(b);
			   }
			   
			   tf.setText(a + operator + b + '=' + sol);
			   a = Double.toString(sol);
			   operator = "";
			   b = "";
		   }
		   // sets operator equal to the user input.
		   else
		   {
			   if(operator.equals(""))
			   {
				   operator = t;
				   isPositive = true;
			   }
			   operator = t;
			   tf.setText(a + operator + b);
		   }
		   
	}
	
	public static void main(String[] args)
	{
		// Creating an object of this class
		new Calculator(); 
	}
}

