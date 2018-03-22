import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Scoreboard extends JFrame{
	JPanel board = new JPanel(); 
	private boolean submitted = false; //has user submitted score
	FileReader highscores; 
	BufferedReader br;
	
	String[] names = new String[5]; //names of highscore table
	String[] scores = new String[5];//scores of highscore table
	JLabel[] boardinfo = new JLabel[10]; //stores both names and scores
	
	int scorePosition = 0; //position on scoreboard if user's score is eligible
	private String username; //name to be submitted to scores
	private int userscore; //score to be submitted to scores
	
	public void readers(){ //reads from the scores file
		try{
		FileReader highscores = new FileReader("scores.txt"); 
		BufferedReader br = new BufferedReader(highscores);
		for (int i = 0; i < 5; i++){
		names[i] = br.readLine(); //name is read into names array
		scores[i] = br.readLine();//score is read into score array
		}
		br.close(); //close connection
		} catch (IOException e){
		System.out.println("Error reading data from file");
		}
	}
	
	public void UpdateFile(){ //writes to scores file
		try{
			FileWriter highscores = new FileWriter("scores.txt", false); //false allows rewriting of file
			BufferedWriter bw = new BufferedWriter(highscores);
			for (int i =0; i < 5; i++){
				bw.write(names[i] + "\n"); //name is copied from array to file
				bw.write(scores[i] + "\n"); //score copied from array to file
			}
			bw.close(); //close connection
		} catch (IOException e){
		System.out.println("Error copying data to file");
		}
	}
	
	private void createLabels(){ //adds labels with information from scoreboard
		for (int i = 0; i < 5; i++){
			boardinfo[i] = new JLabel(names[i]); //names are the first 5 items in the array
			boardinfo[i+5] = new JLabel(scores[i]); //scores are the last 5 items
			board.add(boardinfo[i]); //adds a name
			board.add(boardinfo[i+5]); //then adds its corresponding score
		}
	}
	
	private void refreshLabels(){ //rewrites labels in boardinfo to names and scores saved in the arrays
		for (int i = 0; i < 5; i++){
			boardinfo[i].setText(names[i]);
			boardinfo[i+5].setText(scores[i]);
		}
	}
	
	public void setHighscore(){
		if(scorePosition<4){ //if the user's position isn't last on the scoreboard
			for (int i = scorePosition;i<4;i++){
				scores[i+1] = scores[i]; //move scores down by 1 position
				names[i+1] = names[i]; //move names down by 1 position
			}
		}
		scores[scorePosition] = Integer.toString(userscore); //user's score is written to array of scores in position scorePosition
		names[scorePosition] = username; //user's name is also stored in names array at scorePosition
		UpdateFile(); //score file is updated
		refreshLabels(); //labels are rewritten
	}
	
	private void createBoard(){
		readers(); //calls readers to get info from file
		board.setLayout(new GridLayout(6,2,0,0)); //layout of scoreboard is 6 rows and 2 columns
		createLabels(); //calls createLabels 
		JTextArea name = new JTextArea();//creates area for user to input name
		JButton addScore = new JButton("Add score");//creates button for user to submit name and score
		addScore.addActionListener(new ActionListener(){ //adds event for when button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
					if (!submitted && name.getText()!=""){ //if user hasn't submitted yet and the text field isn't blank
						username = name.getText(); //user's typed name is stored into username
						for (int i=0; i<5;i++){ //for each score in the scoreboard
							if (userscore < Integer.parseInt(scores[i]) || (names[i] == "NONE")){ //if the users score is less than the score at i or the name is NONE i.e. undefined
								scorePosition = i; //user will take the position at i
								break;
							}
						}
						setHighscore(); //calls setHighscore
						submitted = true; //says the user can no longer submit a score
					}
					
				}
			});
		board.add(addScore);//adds submit button to panel
		board.add(name); //adds text box to panel
	}
	

	
	public boolean getSubmitted(){
		return submitted; //
	}

	public Scoreboard(int x){
		super("Highscores");
		userscore = x;
		setSize(400,400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); //if window is closed code stops running
		createBoard();
		add(board);
		setVisible(true);
	}
}
