import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class UI extends JFrame{
	JPanel p = new JPanel();
	JButton b = new JButton();
	JButton[][] OrderedB = new JButton[3][4];
	JButton[][] OriginalB = new JButton[3][4];
	JLabel currentScore = new JLabel("Score: 0");
	GridBagConstraints gbc = new GridBagConstraints();
	private int bx = 0;
	private int by = 0;
	private int count = 0;
	ImageIcon blank = new ImageIcon("bart0.jpg");
	private int score = 2;
	private boolean gameOver = false;
	
	private void createButtons(){
		gbc.gridwidth = 112; 
		gbc.gridheight = 121;
		StringBuilder iname = new StringBuilder(10);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				iname.append("bart");
				iname.append(count);
				iname.append(".jpg");//this creates the name of the images from bart0 to bart11 in sequence
				ImageIcon bartimage = new ImageIcon(iname.toString());//image to be used stored in bartimage
				iname = new StringBuilder(10);//last name of image is wiped so it can be rewritten
				OrderedB[i][j] = new JButton(bartimage);
				OrderedB[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
				OriginalB[i][j] = OrderedB[i][j];
				OrderedB[i][j].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						int Bui = 0;
						int Buj = 0;
						int blanki = 0;
						int blankj = 0;
						ImageIcon temp;
						for (int a = 0; a< 3; a++)
						{
							for (int b = 0; b< 4; b++)
							{ 
								if (OrderedB[a][b] == e.getSource()){
									Bui = a;
									Buj = b;
								}
								else if (OrderedB[a][b].getIcon() == blank){
									blanki = a;
									blankj = b;
								}
							}
						}
						if (OrderedB[Bui][Buj].getIcon() != blank){
							if (((Bui-1 == blanki || Bui+1 == blanki )&& Buj == blankj)||((Buj-1 == blankj || Buj+1 == blankj )&& Bui == blanki)){
								OrderedB[blanki][blankj].setIcon(OrderedB[Bui][Buj].getIcon());
								OrderedB[Bui][Buj].setIcon(blank);								
								score++;						
								currentScore.setText("Score: " + score);
							}
						}
					}
				});
				
				count++;
			}
		}
	}
	
	private void addButtons(){
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				gbc.gridx = bx;
				gbc.gridy = by;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.fill = GridBagConstraints.VERTICAL;
				p.add(OrderedB[i][j], gbc);
				bx += 112;
			}
			bx = 0;
			by += 121;
		}
	}
	
	private void randomize(){
		Random generator = new Random();
		int randx = generator.nextInt(3);
		int randy = generator.nextInt(4);
		JButton temp = new JButton();
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				temp = OrderedB[i][j];
				OrderedB[i][j] = OrderedB[randx][randy];
				OrderedB[randx][randy] = temp;
			}

		}
	}
	
	public void checkWin(){
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (OriginalB[i][j] != OrderedB[i][j]){
					
				}
				else{
					gameOver= true;
				}
			}
		}
	}
	
	public boolean getOver(){
		return gameOver;
	}
	
	public int getScore(){
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
