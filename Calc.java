// Postfix Calculator Applet
//
// CS 201 HW 8 
//Lily Kuntz

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

public class Calc extends Applet implements ActionListener {

    private static final long serialVersionUID = 1L; // to avoid Eclipse warning
    
    // instance variables
   
    protected Label result = new Label ("result", Label.RIGHT);//label used to show result
    protected Stack<Integer> stack = new Stack<Integer>();// stack used for calculations									
    protected int current;   // current number being entered
    protected boolean entered; // keep track if current has been entered
                                

    // local color constants
    static final Color black = Color.black;
    static final Color white = Color.white;
    static final Color red = Color.red;
    static final Color blue = Color.blue;
    static final Color yellow = Color.yellow;
    static final Color orange = Color.orange;
    static final Color lblue = new Color(200, 220, 255);
    static final Color dred = new Color(160, 0, 100);
    static final Color dgreen = new Color(0, 120, 90);
    static final Color dblue = Color.blue.darker();
    
    ////sets up number panel
    public Panel numberPanel(){
    		Panel num = new Panel();
	    num.setLayout(new GridLayout(4,4, 2, 2)); 
	    num.setFont(new Font("TimesRoman", Font.BOLD, 30));
	    num.add(CButton("7", dgreen, yellow));
	    num.add(CButton("8", dgreen, yellow));
	    num.add(CButton("9", dgreen, yellow));
	    num.add(CButton("+", dblue, orange));
	    num.add(CButton("4", dgreen, yellow));
	    num.add(CButton("5", dgreen, yellow));
	    num.add(CButton("6", dgreen, yellow));
	    num.add(CButton("-", dblue, orange));
	    num.add(CButton("1", dgreen, yellow));
	    num.add(CButton("2", dgreen, yellow));
	    num.add(CButton("3", dgreen, yellow));
	    num.add(CButton("*", dblue, orange));
	    num.add(CButton("0", dgreen, yellow));
	    num.add(CButton("(-)", dred, yellow));
	    num.add(CButton("Pop", dred, yellow));
	    num.add(CButton("/", dblue, orange)); 
	    return num;
    }
    
    //sets up top part of calculator result bar.
    public Panel top(){
    		Panel top = new Panel();
    		top.setLayout(new BorderLayout());
    		
    		//blank labels for blue border
    		Label b1 = new Label(" ");
    		b1.setBackground(blue);
    		Label b2 = new Label(" ");
    		b2.setBackground(blue);
    		Label b3 = new Label(" ");
    		b3.setBackground(blue);
    		Label b4 = new Label(" ");
    		b4.setBackground(blue);
    		b1.setFont(new Font("TimesRoman", Font.BOLD, 7));
    		b3.setFont(new Font("TimesRoman", Font.BOLD, 18));
    		b4.setFont(new Font("TimesRoman", Font.BOLD, 18));
    		b2.setFont(new Font("TimesRoman", Font.BOLD, 7));
    		top.add("North",  b1);
    		top.add("South",  b2);
    		top.add("East",  b3);
    		top.add("West",  b4);
    		
    		//result bar
    		result.setBackground(white);
    		result.setForeground(dgreen);
    		result.setFont(new Font("TimesRoman", Font.BOLD, 34));
    		top.add("Center",  result);
    		return top;
    }
    
    //sets up bottom two buttons of calculator
    public Panel bottom() {
    		Panel bottom = new Panel();
    		bottom.setLayout(new GridLayout(1,2, 3, 3));
    		bottom.setFont(new Font("TimesRoman", Font.BOLD, 30));
    		bottom.add(CButton("Enter", dblue, lblue));
        bottom.add(CButton("Clear", dblue, lblue));
        return bottom;
    }
    
    //uses helper functions to set up calculator
    public void init() {
        setLayout(new BorderLayout(2,2));
        add("North", top());
        add("Center", numberPanel());
        add("South", bottom());
        this.setBackground(blue);
        setSize(290, 400);
        setVisible(true);
        show(current);
    }

    // create a colored button
    protected Button CButton(String s, Color fg, Color bg) {
        Button b = new Button(s);
        b.setBackground(bg);
        b.setForeground(fg);
        b.addActionListener(this);
        return b;
    }

    //safely uses the pop method by checking if stack is empty
    public int safePop(){
    		if (stack.isEmpty())
    			return 0;
    		else
    			return stack.pop();
    }
    
    //helper function for actions-- if current is entered/not
    public int perhapsNotEntered(){
    		if (entered)
    			return safePop();
    		else
    			return current;
    	}
    		
   //pushes current and shows current in red when entered
   public void enter(){
	  entered = true;
	  result.setForeground(red);
	  stack.push(current);
	  show(current);
   }
    
    // handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            String label = ((Button)e.getSource()).getLabel();
            
            	if (label.equals("+")) {
            	//adds the last two numbers in the stack together
            	//pops old numbers and pushes new result
            		int first = stack.pop();
            		int second = perhapsNotEntered();
        			current = first + second;
        			enter();
            }
            else if (label.equals("-")) {
            	//subtracts the last two numbers in the stack
            	//pops old numbers and pushes new result
	            
            		int second = perhapsNotEntered();
	            	int first = safePop(); 
		    		current = first - second;
		    		enter();
            }
            else if (label.equals("*")) {
            	//multiplies the last two numbers in the stack together
            	//pops old numbers and pushes new result
	            	int first = safePop();
	            	int second = perhapsNotEntered();
	        		current = first * second;
	        		enter();
            }
            else if (label.equals("/")) {
            	//divides the last two numbers in the stack 
            	//pops old numbers and pushes new result
            		int second = perhapsNotEntered();
            		int first = safePop();
	        		current = first/ second;
	        		enter();
            }
            else if (label.equals("Pop")) {
            	//pops the current number
            	//current becomes the next number in the stack
            		if (entered == false)
            			stack.push(current);
            		safePop();
            		if(stack.isEmpty())
            			stack.push(0);
            		current = stack.peek();
            		enter();
            		stack.pop();
	        }
            else if (label.equals("(-)")) {
            	//multiplies the current number by -1
            		perhapsNotEntered();
       	     	current = (current * -1);
       	     	enter();
 	        }
            else if (label.equals("Enter")) {
            	//pushed the current number to the stack
            		if (entered == true) {
            			current = 0;
            			show(current);
            		}
            		else {
            			enter();
            		}
            }
            	else if (label.equals("Clear")) {
            	//clears the stack and the current number (0)
            		stack.clear();
            		current = 0;
            		show(current);
            	}

            else {     // number button
            	//allows multiple digit numbers to be entered 
            		int n = Integer.parseInt(label);
            		if(entered==false)
            			current = (current*10) + n;
            		else {
            			entered = false;
            			result.setForeground(dgreen);
            			current = n;
            		}
            		number(current);
            }
        }
    }

    // display number n in result label
    protected void show(int n) {
        result.setText(Integer.toString(n));

    }
    // handle number buttons
    protected void number(int n) {
    		show(n);
    }
}
