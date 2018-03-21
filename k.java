

class k{
	public static void main(String[] args){
		UI game = new UI();
		while (!game.getOver()){
			game.checkWin();
		}
		Scoreboard x = new Scoreboard(game.getScore());
		boolean checksubmit = x.getSubmitted();
		game.setVisible(false);
		game.dispose();
		while (!x.getSubmitted()){
			checksubmit=x.getSubmitted();
		}
		x.setVisible(false);
		x.dispose();
	}
}