import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class UI extends JFrame{
	JPanel p = new JPanel();
	JButton b = new JButton();	
	private int score = 0; //user's score
	JButton[][] OrderedB = new JButton[3][4]; //how the game appears to the user
	JButton[][] OriginalB = new JButton[3][4]; //what the game should look like
	JLabel currentScore = new JLabel("Score: " + score); //displays current score
	GridBagConstraints gbc = new GridBagConstraints();//layout of game
	private int bx = 0; //used to determine where buttons will be placed
	private int by = 0; // same as bx
	private boolean hasclicked = false;
	private boolean clicked = false;
	private int bartNum = 0;//used to create name of bart image file
	ImageIcon blank = new ImageIcon("bart0.jpg");

	private boolean gameOver = false; //false = game is still going, true = game has been won
	
	private void createButtons(){
		gbc.gridwidth = 112; 
		gbc.gridheight = 121; //setting width and height of the cells in grid
		
		StringBuilder iname = new StringBuilder(10);//used to store name of bart image file
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				iname.append("bart");
				iname.append(bartNum);
				iname.append(".jpg");//this creates the name of the images from bart0 to bart11 in sequence
				ImageIcon bartImage = new ImageIcon(iname.toString());//image to be used stored in bartimage
				iname = new StringBuilder(10);//last name of image is wiped so it can be rewritten
				
				OrderedB[i][j] = new JButton(bartImage); //button created with image stored in bartImage
				OrderedB[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));//button border set to black
				OriginalB[i][j] = OrderedB[i][j]; //how the game should look when complete is stored into OriginalB to be compared
				p.remove(OriginalB[i][j]);

				OrderedB[i][j].addActionListener(new ActionListener(){ //what the button should do when clicked
					@Override
					public void actionPerformed(ActionEvent e) {
						int Bui = 0;
						int Buj = 0;
						int blanki = 0;
						int blankj = 0;
						for (int a = 0; a< 3; a++)
						{
							for (int b = 0; b< 4; b++)
							{ 
								if (OrderedB[a][b] == e.getSource()){ //finds which button was clicked by comparing to buttons in the array
									Bui = a;//stores their index into Bui and Buj
									Buj = b;
								}
								else if (OrderedB[a][b].getIcon() == blank){ //finds which button has the blank icon (bart0)
									blanki = a; //stores the index into blanki and blankj
									blankj = b;
								}
							}
						}
						if (OrderedB[Bui][Buj].getIcon() != blank){ //if the user didn't click the blank
							if (((Bui-1 == blanki || Bui+1 == blanki )&& Buj == blankj)||((Buj-1 == blankj || Buj+1 == blankj )&& Bui == blanki)){ //checks if button clicked is adjacent to the blank square
								OrderedB[blanki][blankj].setIcon(OrderedB[Bui][Buj].getIcon()); //sets blank square's icon to the one from the button clicked
								OrderedB[Bui][Buj].setIcon(blank);//sets icon of button clicked to blank								
								score++;//score increments
								currentScore.setText("Score: " + score); //update score
								hasclicked = true;
								checkClicked();
							}
						}
					}
				});
				
				bartNum++; //next bart image will be used.
			}
		}
	}
	
	public void checkClicked(){
		if (hasclicked == true){
			clicked = true;
		}
	}

	public boolean getClicked(){
		return clicked;
	}

	private void addButtons(){
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				gbc.gridx = bx; //sets coordinates of grid cells
				gbc.gridy = by; 
				
				gbc.fill = GridBagConstraints.HORIZONTAL; //button will fill to size of grid cell
				gbc.fill = GridBagConstraints.VERTICAL;
				
				p.add(OrderedB[i][j], gbc); //adds button to empty grid cell
				bx += 112; //x coordinate of grid cell increases
			}
			bx = 0; //x coordinate of cells resets
			by += 121; //y coordinate of cells increases
		}
	}
	
	private void randomize(){
		Random generator = new Random(); //to create random number
		
		JButton temp = new JButton(); //placeholder for buttons
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				int randx = generator.nextInt(3); //random index in array
				int randy = generator.nextInt(4); //
				temp = OrderedB[i][j]; //button is stored into temp
				OrderedB[i][j] = OrderedB[randx][randy]; //button at first position in array is copied to that at random index found
				OrderedB[randx][randy] = temp; //button at random index is copied to first position in array effectively swapping the two
			}

		}
	}
	
	public void checkWin(){
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (OriginalB[i][j] != OrderedB[i][j]){ //if current state of game is different from what it should look like
					
				}
				else{
					gameOver= true; //if tiles are in correct order gameOver set to true i.e. user has beaten the game
				}
			}
		}
	}
	
	public boolean getOver(){ //returns state of game
		return gameOver;
	}
	
	public int getScore(){ //returns user's score
		return score; 
	}
	
	public UI(){
		super("Swingin' Simpsons");
		p.setLayout(new GridBagLayout());
		currentScore.setBounds(0,100,100,100);
		add(currentScore);
		setSize(700,700);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		createButtons();
		addButtons();
		add(p);
		setVisible(true);
	}
	
}
