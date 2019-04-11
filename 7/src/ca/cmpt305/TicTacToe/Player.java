package ca.cmpt305.TicTacToe;

import java.util.Objects;

public class Player {
	String player;

	public Player(String player) {
		this.player = player;
	}
	
	@Override
	public String toString() {
	       return player;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player playerCompare = (Player) o;
			return this.player.equals(playerCompare.player);
		}
		return false; 
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 73 * hash + Objects.hashCode(this.player);
		return hash;
	}
	
}
