import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Scoreboard extends JFrame{
	JPanel board = new JPanel();
	private boolean submitted = false;
	FileReader highscores;
	BufferedReader br;
	String[] names = new String[5];
	String[] scores = new String[5];
	JLabel[] boardinfo = new JLabel[10];	
	int currentscore = 0;
	private String username;
	private int userscore;
	
	public void readers(){
		try{
		FileReader highscores = new FileReader("scores.txt");
		BufferedReader br = new BufferedReader(highscores);
		for (int i = 0; i < 5; i++){
		names[i] = br.readLine();
		scores[i] = br.readLine();
		}
		br.close();
		} catch (IOException e){
		}
	}
	
	public void UpdateFile(){
		try{
			FileWriter highscores = new FileWriter("scores.txt", false);
			BufferedWriter bw = new BufferedWriter(highscores);
			for (int i =0; i < 5; i++){
				bw.write(names[i] + "\n");
				bw.write(scores[i] + "\n");
			}
			bw.close();
		} catch (IOException e){
		
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
								currentscore = i;
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
		if(currentscore<4){
			for (int i = currentscore;i<4;i++){
				scores[i+1] = scores[i];
			}
		}
		scores[currentscore] = Integer.toString(userscore);
		names[currentscore] = username;
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