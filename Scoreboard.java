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
	
	private void createLabels(){
		for (int i = 0; i < 5; i++){
			boardinfo[i] = new JLabel(names[i]);
			boardinfo[i+5] = new JLabel(scores[i]);
			board.add(boardinfo[i]);
			board.add(boardinfo[i+5]);
		}

	}
	
	private void refreshLabels(){
		for (int i = 0; i < 5; i++){
			boardinfo[i].setText(names[i]);
			boardinfo[i+5].setText(scores[i]);
		}
	}
	
	private void createBoard(){
		readers();
		board.setLayout(new GridLayout(6,2,0,0));
		createLabels();
		JTextArea name = new JTextArea();
		JButton addScore = new JButton("Add score");
		addScore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					if (!submitted && name.getText()!=""){
						username = name.getText();
						for (int i=0; i<5;i++){
							if (userscore < Integer.parseInt(scores[i]) || (names[i] == "NONE")){
								scorePosition = i;
								break;
							}
						}
						setHighscore();
						submitted = true;
					}
					
				}
			});
		board.add(addScore);
		board.add(name);
	}
	
	public void setHighscore(){
		if(scorePosition<4){
			for (int i = scorePosition;i<4;i++){
				scores[i+1] = scores[i];
				names[i+1] = names[i];
			}
		}
		scores[scorePosition] = Integer.toString(userscore);
		names[scorePosition] = username;
		UpdateFile();
		refreshLabels();
		submitted=true;
	}
	
	public boolean getSubmitted(){
		return submitted;
	}

	public Scoreboard(int x){
		super("Highscores");
		userscore = x;
		setSize(400,400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		createBoard();
		add(board);
		setVisible(true);
	}
}