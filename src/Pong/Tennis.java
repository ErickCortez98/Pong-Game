package Pong;


import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//TODO: CREATE TENNIS PLAYER CLASS AND IMPLEMENT ITS USE IN THE MAIN TENNIS CLASS
//TODO: FIX ANGLE FUNCTIONS WHEN ANGLE IS BETWEEN 90 AND 180 DEGREES
//TODO: CHECK IF ALL ANGLES ARE CORRECT, CHECK 0, 30, 45, 90, 125, 180, 185, 270, 300, 330
//TODO: CHECK FOR ANGLES THAT ARE CL
//TODO: OR FIX THE WAY YOU MOVE THE BALL COMPLETELY BY TAKING INTO ACCOUNT
//      THE HIPOTENUSA AS THE BALL_SPEED AND USING COSINE AND SINE 

public class Tennis extends Application{
	
	private static final int SCREEN_HEIGHT = 500;
	private static final int SCREEN_WIDTH = 500;
	
	//TODO: THIS WILL GO INTO THE PLAYER CLASS
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH  = 20;
	
	private static final double INITIAL_POS_X2 = 500 - PLAYER_WIDTH;
	private static final double INITIAL_POS_Y2 = 250-PLAYER_HEIGHT/2;
	//////////////////////////////////////////

	private static final double INITIAL_POS_X1 = 0;
	private static final double INITIAL_POS_Y1 = 250-PLAYER_HEIGHT/2;

	
	private GraphicsContext gc;
	private Canvas canvas;
	private Rectangle player1Rec;
	private Rectangle player2Rec;
	private Circle gameBall;
	private double movingAngle;
	
	//Random class
	Random rand;
	
	private ArrayList<String> inputList;
	
	//Constructor of the class
	public Tennis() {
		 rand = new Random();
		 inputList = new ArrayList<String>();
		 movingAngle = rand.nextDouble() * (2*Math.PI);
		 movingAngle = Math.toRadians(1);
		 
		 if(movingAngle == Math.toRadians(270)) {
			 movingAngle = Math.toRadians(270) - Math.toRadians(30);
		 }else if(movingAngle == Math.toRadians(90)) {
			 movingAngle = Math.toRadians(90) + Math.toRadians(30);
		 }
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set title of the stage window
		primaryStage.setTitle("Pong");
		
		// Create group that will contain a lot of child nodes and include the 
		// gamePlayScene into this new group
		Group root = new Group();
		Scene gamePlayScene = new Scene(root);
		
		// Add the scene to the stage
		primaryStage.setScene(gamePlayScene);
		
		//Canvas is created to be able to handle animations
		canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);
	
		// Get graphics context object from the canvas
		gc = canvas.getGraphicsContext2D();
		
		// Setting background color
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				
				
		//Drawing rectangles at the initial positions
		player1Rec = new Rectangle(INITIAL_POS_X1, INITIAL_POS_Y1, PLAYER_WIDTH,PLAYER_HEIGHT);
		player2Rec = new Rectangle(INITIAL_POS_X2, INITIAL_POS_Y2, PLAYER_WIDTH,PLAYER_HEIGHT);
		player1Rec.setFill(Color.WHITE);
		player2Rec.setFill(Color.WHITE);
		
		//Creating the ball
		TennisBall  gameBall = new TennisBall(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		
		// Adding circle to the root group
		root.getChildren().add(gameBall.getTennisBall());
		
		//Adding rectangles to root group
		root.getChildren().add(player1Rec);
		root.getChildren().add(player2Rec);
		
		// Key Handlers
		// When a key is pressed we add that input to the inputList
		gamePlayScene.setOnKeyPressed(
				new EventHandler<KeyEvent>() {
					public void handle(KeyEvent e) {
						String input = e.getCode().toString();
						
						//Adding to the array of inputs only new inputs
						if(!inputList.contains(input)) {
							inputList.add(input);
						}
					}
				}
			);
		
		// When a key is released we remove that input from the inputList
		gamePlayScene.setOnKeyReleased(
				new EventHandler<KeyEvent>() {
					public void handle(KeyEvent e) {
						String input = e.getCode().toString();
						
						
						inputList.remove(input);
					}
					
				}
			);
	
		
		
		
		// Animation timer that will handle the animation of the sprites
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Method that actually handles animation
				
				//Run animation of both players
				runAnimationPlayer1();
				runAnimationPlayer2();
				
				//Animating the ball
				gameBall.changePositionBall(movingAngle);
				
				//Checking for collisions
				checkCollisions();
			}
			
		}.start();
		
		primaryStage.show();
	}
	
	public void runAnimationPlayer1() {
		// Checking which keys have been pressed
		if(inputList.contains("W")) {
			moveLeftUp();
		}else if(inputList.contains("S")) {
			moveLeftDown();
		}
	}
	
	public void runAnimationPlayer2() {
		// Checking which keys have been pressed
		if(inputList.contains("UP")) {
			moveRightUp();
		}else if(inputList.contains("DOWN")) {
			moveRightDown();
		}
	}
	
	public void checkCollisions() {
		
	}
	
	private void moveLeftUp() {
		if(!(player1Rec.getY() < 0)) {
			player1Rec.setY(player1Rec.getY()-5);
		}
	}
	
	private void moveLeftDown() {
		if(!(player1Rec.getY() > 500 - PLAYER_HEIGHT)) {
			player1Rec.setY(player1Rec.getY()+5);
		}
	}
	
	private void moveRightUp() {
		if(!(player2Rec.getY() < 0)) {
			player2Rec.setY(player2Rec.getY()-5);
		}
	}
	
	private void moveRightDown() {
		if(!(player2Rec.getY() > 500 - PLAYER_HEIGHT)) {
			player2Rec.setY(player2Rec.getY()+5);
		}
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
