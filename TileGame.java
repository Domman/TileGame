import java.util.concurrent.TimeUnit;

class TileGame{
	public static void main(String[] args){
		UI game = new UI(); //creates new instance of the game
		while (!game.getOver()){
			game.checkWin();
			try{
				TimeUnit.SECONDS.sleep(5);
			}
			catch(InterruptedException e){
			}
		}
		Scoreboard x = new Scoreboard(game.getScore());//creates scoreboard
		while (!x.getSubmitted()){//while user has not submitted yet
		}

	}
}
