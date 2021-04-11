package Pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
		double changeInX = Math.cos(angle) * BALL_SPEED;
		double changeInY = -(Math.sin(angle) * BALL_SPEED);
		
		setCurrentPositionX(currentPositionX + changeInX);
		setCurrentPositionY(currentPositionY + changeInY);
	}
	
	public double bounceBall(double angle, Collision_Elements element, Rectangle paddle) {
		double newAngle = 0;
		//Special cases first 0, 45, 90 + 45, 180, 180 + 45, 270 + 45  
		
		//Normal cases - Players (When colliding with the players we disregard the angle in which the ball is colliding, we just take into account
		//                        the place in which the ball touches the paddle at the moment of the collision)
		if(element == Collision_Elements.PLAYER_2) {
			double relativeIntersection = (paddle.getY() + (TennisPlayer.PLAYER_HEIGHT/2)) - this.currentPositionY;
			if(relativeIntersection > 45) {
				newAngle = Math.toRadians(115);
			}else if(relativeIntersection < -45) {
				newAngle = Math.toRadians(285);
			}
		}
		if(element == Collision_Elements.PLAYER_1) {
			double relativeIntersection = (paddle.getY() + (TennisPlayer.PLAYER_HEIGHT/2)) - this.currentPositionY;
		}
		
		//Normal cases - Walls
		if(element == Collision_Elements.UPPER_WALL || element == Collision_Elements.LOWER_WALL) {
			newAngle = 2*Math.PI - angle;
		} else if(element == Collision_Elements.RIGHT_WALL) {
			newAngle = -1;
		} else if(element == Collision_Elements.LEFT_WALL) {
			newAngle = -2;
		}
		
		
		/*if(element == Collision_Elements.UPPER_WALL) { // If ball is bouncing in upper wall
			// if angle is between 0 and 90 degrees
			if(angle > 0 && angle < Math.PI/2) {
				newAngle = (2*Math.PI) - angle;
			}else if(angle > Math.PI/2 && angle < Math.PI) { // if angle is between 90 and 180 degrees
				newAngle = (2*Math.PI) - angle;
			}
			
		}else if(element == Collision_Elements.LOWER_WALL) {
			// if angle is between 180 and 270 degrees
			if(angle > Math.PI && angle < (3*Math.PI)/2) {
				newAngle = (2*Math.PI) - angle;
			}else if(angle > (3*Math.PI)/2 && angle < 2*Math.PI ) { // if angle is between 270 and 360 degrees
				newAngle = (2*Math.PI) - angle;
			}
		}*/ 
		
		return newAngle;
	}
	
	public Circle getTennisBall() {
		return gameBall;
	}
	
}
