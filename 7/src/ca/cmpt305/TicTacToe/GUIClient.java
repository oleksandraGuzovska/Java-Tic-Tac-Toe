package ca.cmpt305.TicTacToe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIClient extends Application {
	private static GraphicsContext gc;
	static TicTacToeGame game = new TicTacToeGame();
 	Player player = game.getCurrentPlayer();
 	private SavedMoves savedMoves;
	int row = 0;
	int col = 0;
	int numMoves = 1;
	private final int stageWidth = 600;
	private final int stageHeight = 600;
	private final int squareWidth = 200;
	boolean endGame = false;	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
    public void start(Stage primaryStage) {
	
		  primaryStage.setTitle("TicTacToe");
	      FlowPane rootNode = new FlowPane(10, 10);
	      Scene scene = new Scene(rootNode, stageWidth, stageHeight);
	      primaryStage.setScene(scene);
	      
	      Canvas canvas = new Canvas(stageWidth, stageHeight);
	      gc = canvas.getGraphicsContext2D();
	      
	      String message = player + " make your move!";
	      drawGrid(gc, Color.GRAY, message);
	      	      
	      canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	
	                gc.clearRect(0, 0, 600, 40);
	                row = (int) event.getY() / squareWidth;
	                col = (int) event.getX() / squareWidth;
	                
	                if (!game.validateMove(row, col)) {
		            	gc.fillText(player + " you made an invalid move", 200, 30);
		            } else {
		            	game.move(player, row, col);
		            	drawPlayer(player, row, col);
		            	numMoves++;
               
		            	if (game.checkWinner(player, row, col)) {
		            		gc.fillText(player + " wins!", 200, 30);
		            		endGame = true;
		            	} 
		            	else if (game.isDraw()) {
		            		gc.fillText("It's a draw!", 200, 30);	
		            	    endGame = true;
		            	} 
		            	else if (!endGame) {
		            		player = game.getCurrentPlayer();
		            	    gc.fillText(player + " make your move!", 200, 30);
		            	}
		            }
	                
	            	if (endGame) {
	            		 canvas.setOnMouseClicked(null);
	            		 endGame();
	            	}	
	            }	            
	        });
	      
	      rootNode.getChildren().addAll(canvas);
	      primaryStage.show();
	            
	}

	private void endGame() {
		
		Timeline timeline1 = new Timeline(				
			    new KeyFrame(Duration.seconds(1), e -> {
			    	gc.clearRect(0, 0, stageHeight, stageWidth);
		     		drawGrid(gc, Color.GRAY, "Replay...");
			    })
		);
		timeline1.play();
		
	     Timeline timeline2 = new Timeline();
	     for (int time=1; time < numMoves; time++) {
	    	 KeyFrame kf = new KeyFrame(Duration.seconds(time), event -> replayGame());
	         timeline2.getKeyFrames().add(kf);
	     }
	     timeline2.play();
	}
	
	
	private void drawGrid(GraphicsContext gc, Color color, String message) {
	      gc.setFont(new Font(30));
	      gc.setLineWidth(1);
	      gc.fillText(message, 200, 30);
	      gc.strokeLine(200, 60, 200, 540);
	      gc.strokeLine(400, 60, 400, 540);
	      gc.strokeLine(60, 200, 540, 200);
	      gc.strokeLine(60, 400, 540, 400);
	 }
	 
	 private void drawPlayer(Player player, int row, int col) {
		 gc.setStroke(Color.BLACK);
	     gc.setLineWidth(20);
	     
	     int begX = col*squareWidth + squareWidth/2 - 40;
	     int begY = row*squareWidth + squareWidth/2 - 40;
	     int endX = col*squareWidth + squareWidth/2 + 40;
	     int endY = row*squareWidth + squareWidth/2 + 40;
	    
	     if (player.player.equals("X")) {		    		 
		     gc.strokeLine(begX, begY, endX, endY);
		     gc.strokeLine(begX, endY, endX, begY);
	     } else {
	    	 gc.strokeOval(begX, begY, 80, 80);
	     }
	 }
	 
	 private void replayGame() {
		 if (!game.movesList.isEmpty()) {
		 savedMoves = game.replayGame();
		 drawPlayer(savedMoves.player, savedMoves.row, savedMoves.col);	
		 }
	 }
}
