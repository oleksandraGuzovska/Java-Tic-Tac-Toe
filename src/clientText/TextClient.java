package clientText;

import tictactoe.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TextClient {
	static TicTacToeGame game;

	public static void main(String[] args) throws InterruptedException {
		 	game = new TicTacToeGame();
		 	Player player = game.getCurrentPlayer();
	        Scanner console = new Scanner(System.in);

	        while (true) {

	            System.out.print("Player " + player + " make your move <row col>: ");
	            String userInput = console.nextLine();
	            String[] token = parseUserInput(userInput);

	            if (token.length > 0 && token[0].equals("Exit")) {
	                System.out.println("Thanks for playing... bye!");
	                System.exit(0);
	            }

	            int row = Integer.parseInt(token[0]);
	            int col = Integer.parseInt(token[1]);
	            if (!game.validateMove(row, col)) {
	            	System.out.println(player + " you made an invalid move");
	            	continue;
	            }

	            game.move(player, row, col);	            
	            drawBoard();

	            if (game.checkWinner(player, row, col)) {
	            	System.out.println(player + " wins!");
	            	break;
	            } else if (game.isDraw()) {
	            	System.out.println("It's a draw!");
	            	break;
	            }

	            player = game.getCurrentPlayer();
	        }

	        TimeUnit.SECONDS.sleep(2);
	        System.out.println("\nReplaying the last game...");
	        replay();

	        console.close();
	}

	    private static String[] parseUserInput(String userInput) {
	        Scanner tokenizer = new Scanner(userInput);
	        String[] token = new String[2];
	        for (int i=0; i < token.length && tokenizer.hasNext(); i++) {
	            token[i] = tokenizer.next();
	        }
	        tokenizer.close();
	        return token;
	    }

	    static void drawBoard() {
	    	for (int row = 0; row < 3; row ++) {
			    for (int col = 0; col < 3; col++) {
			        System.out.print(" " + game.getCurrentState(row, col) + " ");			        
			    }
			    System.out.println();
	    	}
	    	System.out.println();

	    }

	    static void replay() throws InterruptedException {
	    	game.clearBoard();
	    	drawBoard();
	    	TimeUnit.SECONDS.sleep(1);

	    	while (!game.movesList.isEmpty()) {
	    		game.replayGame();
	    		TimeUnit.SECONDS.sleep(1);
	    		drawBoard();
	    	}
	    }
} 