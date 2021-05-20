package Pong;

import javafx.scene.shape.Rectangle;

enum players{
	LEFT_PLAYER,
	RIGHT_PLAYER
}

public class TennisPlayer {
	public static final int PLAYER_HEIGHT = 100;
	public static final int PLAYER_WIDTH  = 20;
	
	private static final double INITIAL_POS_X2 = 500 - TennisPlayer.PLAYER_WIDTH;
	private static final double INITIAL_POS_Y2 = 250-TennisPlayer.PLAYER_HEIGHT/2;

	private static final double INITIAL_POS_X1 = 0;
	private static final double INITIAL_POS_Y1 = 250-TennisPlayer.PLAYER_HEIGHT/2;
	
	private int currentScore;
	private Rectangle playerRectangle;
	
	public TennisPlayer(int initialScore, boolean leftPlayer) {
		this.setCurrentScore(initialScore);
		
		if(leftPlayer) {
			playerRectangle = new Rectangle(INITIAL_POS_X1, INITIAL_POS_Y1, PLAYER_WIDTH, PLAYER_HEIGHT);
		}else {
			playerRectangle = new Rectangle(INITIAL_POS_X2, INITIAL_POS_Y2, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}

	public void setPlayerRectangle(Rectangle playerRectangle) {
		this.playerRectangle = playerRectangle;
	}
}
