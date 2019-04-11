package tictactoe;

public class SavedMoves {

	public Player player;
	public int row;
	public int col;

	public SavedMoves(Player player, int row, int col) {
		this.player = player;
		this.row = row;
		this.col = col;
	}

}