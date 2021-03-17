package Pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TennisBall {
	
	public static final int BALL_R = 8;
	private static final int  BALL_SPEED = 4;
	private double currentPositionX;
	private double currentPositionY;
	private Circle gameBall;
	private boolean angleChanged;
	
	public TennisBall(int positionX, int positionY) {
		gameBall = new Circle(positionX-BALL_R, positionY+BALL_R, BALL_R, Color.WHITE);
		currentPositionX = positionX-BALL_R;
		currentPositionY = positionY+BALL_R;
		angleChanged = false;
	}

	public double getCurrentPositionX() {
		return currentPositionX;
	}

	public double getCurrentPositionY() {
		return currentPositionY;
	}
	
	public void setCurrentPositionX(double currentPositionX) {
		gameBall.setCenterX(currentPositionX);
		this.currentPositionX = currentPositionX;
	}

	public void setCurrentPositionY(double currentPositionY) {
		gameBall.setCenterY(currentPositionY);
		this.currentPositionY = currentPositionY;
	}
	
	public void changePositionBall(double angle) {
		if(angle < 0) {
			return;
		} 
		
		double check = Math.tan(angle);
		
		// Determine the change in X and Y based on the given angle (angle is in degrees)
		double changeInX = (1 * BALL_SPEED)/Math.tan(angle);
		double changeInY = (Math.tan(angle) * BALL_SPEED)/Math.tan(angle);
		
		if(Math.tan(angle) < 1) {
			changeInX = BALL_SPEED;
			changeInY = Math.tan(angle) * BALL_SPEED;
		}
		
		//Special angle cases
		if(angle == 0) {
			changeInX = BALL_SPEED;
			changeInY = 0;
		}
		if(angle == Math.PI) {
			changeInX = -BALL_SPEED;
			changeInY = 0;
		}
		if(angle > Math.PI/2 && angle < Math.PI) {
			changeInX *= -BALL_SPEED;
		}
		if(angle >= 0 && angle <= Math.PI/2) {
			changeInY *= -BALL_SPEED;
		}
		
		setCurrentPositionX(currentPositionX + changeInX);
		setCurrentPositionY(currentPositionY + changeInY);
	}
	
	public Circle getTennisBall() {
		return gameBall;
	}
	
}
