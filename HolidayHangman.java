import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
@SuppressWarnings("serial")

public class HolidayHangman extends Applet implements ActionListener {
	
	//creates buttons for every letter
	public Panel alphabet(){
		Panel letter = new Panel();
		letter.setLayout(new GridLayout(10,5)); 
		letter.setForeground(Color.black);
		letter.setFont(new Font("TimesRoman", Font.BOLD, 30));
		letter.add(CButton("A"));
		letter.add(CButton("B"));
		letter.add(CButton("C"));
		letter.add(CButton("D"));
		letter.add(CButton("E"));
		letter.add(CButton("F"));
		letter.add(CButton("G"));
		letter.add(CButton("H"));
		letter.add(CButton("I"));
		letter.add(CButton("J"));
		letter.add(CButton("K"));
		letter.add(CButton("L"));
		letter.add(CButton("M"));
		letter.add(CButton("N"));
		letter.add(CButton("O"));
		letter.add(CButton("P"));
		letter.add(CButton("Q"));
		letter.add(CButton("R"));
		letter.add(CButton("S"));
		letter.add(CButton("T"));
		letter.add(CButton("U"));
		letter.add(CButton("V"));
		letter.add(CButton("W"));
		letter.add(CButton("X"));
		letter.add(CButton("Y"));
		letter.add(CButton("Z"));
		letter.add(CButton("New"));
	    return letter;
	}
	
	//create a button 
	protected Button CButton(String s) {
	        Button b = new Button(s);
	        b.setBackground(Color.white);
	        b.setForeground(Color.black);
	        b.addActionListener(this);
	        return b;
	    }

	public void init() {
		setFont(new Font("TimesRoman", Font.BOLD, 60));
		setLayout(new BorderLayout());
		setBackground(Color.white);
		//pick a random word from holiday words array
		Random random = new Random();
		actual = holidayWords[random.nextInt(holidayWords.length)];
		//replaces word chosen with equivalent length of blanks for guessing
		current = String.format("%0" + actual.length() + "d", 0).replace("0"," _");
		word.setText(current);
		title.setForeground(Color.blue);
		add("North", title);
		add("East", alphabet());
		add("South", word);	
    }

	//check if letters clicked are in the word
	public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof Button) {
				clicked[guessIncorrect.length() + guessCorrect.length()] = (Button)e.getSource();
	            String label = ((Button)e.getSource()).getLabel();
	            if (guessCorrect.contains(label)|| guessIncorrect.contains(label)
	            		||(gameOver && !label.equals("New")))
	            		;
	            else if (actual.contains(label)){	
	            		guessCorrect = guessCorrect+ label;
	            		((Button)e.getSource()).setForeground(Color.green);
	            		for(int i = 0; i< actual.length(); i++) {
	            			if (actual.charAt(i) == label.charAt(0)) {
	            				if (i> 0)
	            					current = current.substring(0,(2*i)+1) +
	            					label + current.substring((i*2)+2); 
	            				else 
	            					current = current.charAt(0) + label + current.substring((2*i)+2);
	            			}
	            			word.setText(current);
	            			if (current.replaceAll("\\s","").equals(actual)) {
	            				word.setForeground(Color.green);
	            				word.setText(actual + " -You Win!");
	            				gameOver = true;
	            			}
	            		}
	            }
	            else if (label.equals("New")) {
	            		gameOver= false;
	            		init();
	            		for(int i=0; i< (guessIncorrect.length() + guessCorrect.length()); i++) {
	            			clicked[i].setForeground(Color.black);
	            		}
	            		word.setForeground(Color.black);
	            		guessCorrect = guessIncorrect = "";
	            		
	            }
	            else {
	            		guessIncorrect = guessIncorrect + label;
	     			repaint();
	     			((Button)e.getSource()).setForeground(Color.red);
	            	}				
			}
		}
		
class HangmanCanvas extends Canvas implements MouseListener {

		public void mousePressed(MouseEvent e) {	}

		// need these also because we implement a MouseListener
	    public void mouseReleased(MouseEvent event) { }
	    public void mouseClicked(MouseEvent event) { }
	    public void mouseEntered(MouseEvent event) { }
	    public void mouseExited(MouseEvent event) { }
	}
	//instance variables
	protected String current;
	protected Label word= new Label("word");
	protected static String actual;
	protected String guessCorrect= new String("");
	protected String guessIncorrect= new String("");
	protected Label title = new Label("Holiday Hangman");
	protected String[] holidayWords = {"SANTA", "ORNAMENT", "ELVES", "REINDEER", 
			"COOKIE", "GINGERBREAD", "SNOWFLAKE", "ANGEL", "WREATH", "STAR", 
			"STOCKING", "PRESENT", "SNOWMAN", "CHIMNEY", "BELL", "MISTLETOE",
			"MERRY", "CHRISTMAS", "LIGHTS", "SLEIGH", "JOLLY", "HANUKKAH", 
			"DREIDEL", "MENORAH", "KWANZA", "CHESTNUTS", "NUTCRACKER", 
			"CAROLERS", "COAL", "HOLLY", "NOEL", "SCROOGE"};
	public Button[] clicked = new Button[53];
	protected boolean gameOver = false; 

	//adds corresponding image of snowman for incorrect guesses 
	//until game over
	public void paint(Graphics g) {
			Image snowball1 = getImage(getDocumentBase(), "snowball1.png");
			Image snowball2 = getImage(getDocumentBase(), "snowball2.png");
			Image snowball3 = getImage(getDocumentBase(), "snowball3.png");
			Image hat = getImage(getDocumentBase(), "hat.png");
			Image nose = getImage(getDocumentBase(), "nose.png");
			Image face = getImage(getDocumentBase(), "face.png");
			Image buttons = getImage(getDocumentBase(), "buttons.png");
			Image arms = getImage(getDocumentBase(), "arms.png");
			Image background = getImage(getDocumentBase(), "snow.jpg");
			g.drawImage(background, 0, 0, null);
			if (guessIncorrect.length() ==1) 
				g.drawImage(snowball1, 150, 100, this);
			else if (guessIncorrect.length() == 2) 
	    			g.drawImage(snowball2, 150, 100, this);
			else if(guessIncorrect.length() == 3) 
				g.drawImage(snowball3, 150, 100, this);
			else if(guessIncorrect.length() ==4) 
    				g.drawImage(hat, 150, 100, this);
			else if(guessIncorrect.length() ==5)
    				g.drawImage(nose, 150, 100, this);
			else if (guessIncorrect.length() == 6)
    				g.drawImage(face, 150, 100, this);
			else if(guessIncorrect.length() == 7) 
				g.drawImage(buttons, 150, 100, this);
			else if(guessIncorrect.length()>7) {
				g.drawImage(arms, 150, 100, this);
				word.setText(actual + " -Game Over");
				word.setForeground(Color.red);
				gameOver = true;
			}
		}	
}
