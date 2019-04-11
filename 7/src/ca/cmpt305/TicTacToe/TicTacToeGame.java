package ca.cmpt305.TicTacToe;

import java.util.LinkedList;
import java.util.Queue;

public class TicTacToeGame{
	Player x;
	Player o;
	Player temp; //dummy player
	int totRows = 3;
	int totCols = 3;
	Player currentPlayer;
	Player playerHolder[][] = new Player[totRows][totCols];
	int maxNumOfMoves = totRows * totCols;
	int currNumOfMoves;

	Queue <SavedMoves> movesList;
	
	public TicTacToeGame (){
		x = new Player("X");
		o = new Player("O");
		temp = new Player("_");
		movesList = new LinkedList<>();
		
		currentPlayer = o;
		
		for (int row = 0; row < totRows; row ++) {
		    for (int col = 0; col < totCols; col++) {
		        playerHolder[row][col] = temp;
		    }
		}
	}
	
	
	 /**
     * Check if the cell is occupied by a player
     * @param row and col in the game board
     * @return player 
     */
	Player getCurrentState(int row, int col) {
		return playerHolder[row][col];
	}
	
	
	 /**
     * Set player turn
     * @param player that played last
     * @return new player
     */
	private Player setCurrentPlayer(Player currentPlayer) {
		if (currentPlayer.equals(x)){
			return o;
		}
		return x;
	}
	
	
	/**
     * Get player turn
     * @param none
     * @return new player
     */
	Player getCurrentPlayer() {
		currentPlayer = setCurrentPlayer(currentPlayer);
		return currentPlayer;
	}
	
	
	/**
     * Check if the move is legal
     * @param row and column in the game board
     * @return true if legal, false otherwise
     */
	boolean validateMove(int row, int col) {
		if ((row >= 0 && row <= totRows-1) && (col >= 0 && col <= totCols-1)) {
			return playerHolder[row][col] == temp;
		}
		return false;
	}
	
	
	/**
     * Make a move -> place a player on the game board
     * @param current player, row and column
     * @return none
     */
    void move (Player player, int row, int col) {
		if (validateMove(row, col)) {
			playerHolder[row][col] = player;
			addToSavedMoves(player, row, col);
			currNumOfMoves++;
		}
	}
	
    
    /**
     * Check if there's a winner after the last move
     * @param player that played last and it's last move
     * @return true if there's a winner, false otherwise
     */
	boolean checkWinner(Player player, int row, int col) {

		if ((playerHolder[row][0].equals(player)  &&
			playerHolder[row][1].equals(player)  &&
		    playerHolder[row][2].equals(player))  ||
			
		    (playerHolder[0][col].equals(player)  &&
			 playerHolder[1][col].equals(player)  &&
			 playerHolder[2][col].equals(player))) {
					
			return true;
		} 

		if ((playerHolder[0][0].equals(player) && 
			 playerHolder[1][1].equals(player) &&
			 playerHolder[2][2].equals(player)) ||
				
		    (playerHolder[0][2].equals(player) && 
			 playerHolder[1][1].equals(player) &&
			 playerHolder[2][0].equals(player))) {
	
			return true;		
		}
		return false;
	}
	
	
	/**
     * Check if it's a draw --> no more moves left
     * @param None
     * @return true if it's a draw, false otherwise
     */
	boolean isDraw() {
		return currNumOfMoves == maxNumOfMoves ;
	}
	
	
	/**
     * Keep track of all the moves to replay later
     * @param player and their move
     * @return none
     */
	void addToSavedMoves(Player player, int row, int col) {
		SavedMoves savedMoves = new SavedMoves(player, row, col);
		movesList.add(savedMoves);
	}
	
	
	/**
     * Clear the board --> return to the initial state
     * @param none
     * @return none
     */
	void clearBoard() {
		for (int row = 0; row < totRows; row ++) {
		    for (int col = 0; col < totCols; col++) {
		        playerHolder[row][col] = temp;
		    }
		}
	}
	
	
	/**
     * Replay the game by extracting moves one at a time
     * @param none
     * @return extracted move
     */
	SavedMoves replayGame() {
		SavedMoves savedMoves = null;
		if (!movesList.isEmpty()) {
			savedMoves = movesList.remove();
			playerHolder[savedMoves.row][savedMoves.col] = savedMoves.player;		
		}
		return savedMoves;
	}
	
}
