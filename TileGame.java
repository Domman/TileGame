

class TileGame{
	public static void main(String[] args){
		UI game = new UI(); //creates new instance of the game
		while (!game.getOver()){ //if the game hasn't been won wait 
			game.checkWin(); //check if game has been won
		}
		Scoreboard x = new Scoreboard(game.getScore());//creates scoreboard
		game.setVisible(false);//makes game window invisible
		game.dispose();//closes game window
		while (!x.getSubmitted()){//while user has not submitted yet
		}
		x.setVisible(false); //makes scoreboard invisible
		x.dispose(); //closes scoreboard
	}
}