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
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//TODO: CREATE TENNIS PLAYER CLASS AND IMPLEMENT ITS USE IN THE MAIN TENNIS CLASS
//TODO: FIX ANGLE FUNCTIONS WHEN ANGLE IS BETWEEN 90 AND 180 DEGREES
//TODO: CHECK IF ALL ANGLES ARE CORRECT, CHECK 0, 30, 45, 90, 125, 180, 185, 270, 300, 330
//TODO: CHECK FOR ANGLES THAT ARE CL
//TODO: OR FIX THE WAY YOU MOVE THE BALL COMPLETELY BY TAKING INTO ACCOUNT
//      THE HIPOTENUSA AS THE BALL_SPEED AND USING COSINE AND SINE 

enum Collision_Elements{
	UPPER_WALL,
	LOWER_WALL,
	LEFT_WALL,
	RIGHT_WALL,
	PLAYER_1,
	PLAYER_2
}

public class Tennis extends Application{
	
	private static final int SCREEN_HEIGHT = 500;
	private static final int SCREEN_WIDTH = 500;
	
	private GraphicsContext gc;
	private Canvas canvas;
	private Rectangle player1Rec;
	private Rectangle player2Rec;
	private double movingAngle;
	
	TennisBall gameBall;
	TennisPlayer playerLeft;
	TennisPlayer playerRight;
	
	Group rootGamePlay;
	Group rootMenu;
	
	private GraphicsContext gcMenu;
	private Canvas canvasMenu;
	private Text nameOfGame;
	private Button playButton;
	private Button settingsButton;

	//Random class
	Random rand;
	
	private ArrayList<String> inputList;
	
	//Constructor of the class
	public Tennis() {
		rand = new Random();
		
		//Creating the ball
		gameBall = new TennisBall(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		
		//Creating the players
		playerLeft = new TennisPlayer(0, true);
		playerRight = new TennisPlayer(0, false);
		 
		inputList = new ArrayList<String>();
		movingAngle = rand.nextDouble() * (2*Math.PI); 
		 // Making sure the ball doesn't go completely in a vertical line or diagonal line because it would get stuck in that position if that occurs
		 if(movingAngle == Math.toRadians(270)) {
			 movingAngle = Math.toRadians(270) - Math.toRadians(30);
		 }else if(movingAngle == Math.toRadians(90)) {
			 movingAngle = Math.toRadians(90) + Math.toRadians(30);
		 }else if(movingAngle== Math.toRadians(45)) {
			 movingAngle = Math.toRadians(45) + Math.toRadians(5);
		 }else if(movingAngle== Math.toRadians(90+45)) {
			 movingAngle = Math.toRadians(90+45) + Math.toRadians(5);
		 }else if(movingAngle== Math.toRadians(180+45)) {
			 movingAngle = Math.toRadians(180+45) + Math.toRadians(5);
		 }else if(movingAngle== Math.toRadians(270+45)) {
			 movingAngle = Math.toRadians(270+45) + Math.toRadians(5);
		 }
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set title of the stage window
		primaryStage.setTitle("Pong");
		
		// Create group that will contain a lot of child nodes and include the 
		// gamePlayScene into this new group
		rootGamePlay = new Group();
		rootMenu = new Group();
		
		Scene menuScene = new Scene(rootMenu);
		Scene gamePlayScene = new Scene(rootGamePlay);
		
		//Creating elements that will be part of the menu scene
		createMenuGroup(primaryStage, gamePlayScene);
		
		//Creating elements that will be part of the game play scene
		createGamePlayGroup(primaryStage);
		
		// Add the scene to the stage
		primaryStage.setScene(menuScene);
		//primaryStage.setScene(gamePlayScene);
		
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
				if(primaryStage.getScene() == gamePlayScene) {
					// Start Game
					
					//Run animation of both players
					runAnimationPlayer1();
					runAnimationPlayer2();
					
					//Animating the ball
					gameBall.changePositionBall(movingAngle);
					
					//Checking for collisions
					checkCollisions();
				}
				
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
		
		// Checking collisions between any of the players and the walls
		// Checking collision with right player
		if(gameBall.getCurrentPositionX() > playerRight.getPlayerRectangle().getX() && gameBall.getCurrentPositionY() > playerRight.getPlayerRectangle().getY() && gameBall.getCurrentPositionY() < (playerRight.getPlayerRectangle().getY()) + TennisPlayer.PLAYER_HEIGHT) {
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.PLAYER_2, playerRight.getPlayerRectangle());
		}
		if(gameBall.getCurrentPositionX() < (playerLeft.getPlayerRectangle().getX() + TennisPlayer.PLAYER_WIDTH) && gameBall.getCurrentPositionY() > playerLeft.getPlayerRectangle().getY() && gameBall.getCurrentPositionY() < (playerLeft.getPlayerRectangle().getY()) + TennisPlayer.PLAYER_HEIGHT) {
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.PLAYER_1, playerLeft.getPlayerRectangle());
		}
		
		// The second parameter of the bounceBall function is the element the ball is crashing against 
		/*
		 * enum Element_In_Game{
			UPPER_WALL,
			LOWER_WALL,
			LEFT_WALL,
			RIGHT_WALL,
			PLAYER_1,
			PLAYER_2}
		 * */
		
		
		// Checking collisions between the ball and the walls 
		if(gameBall.getCurrentPositionX() < 0) {
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.LEFT_WALL, null);
		}else if(gameBall.getCurrentPositionX() > 500){
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.RIGHT_WALL, null);
		}else if(gameBall.getCurrentPositionY() > 500) {
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.LOWER_WALL, null);
		}else if(gameBall.getCurrentPositionY() < 0) {
			movingAngle = gameBall.bounceBall(movingAngle, Collision_Elements.UPPER_WALL, null);
		}
		

		// If moving angle is -1, then left player scored a point
		if(movingAngle == -1) {
			movingAngle = 0;
			scoredPoint(players.LEFT_PLAYER);
		}
		// If moving angle is -2, then right player scored a point
		if(movingAngle == -2) {
			movingAngle = Math.PI;
			scoredPoint(players.RIGHT_PLAYER);
		}
		
	}
	
	private void scoredPoint(players playerWhoScored) {
		Text playerScored;
		if(playerWhoScored == players.RIGHT_PLAYER) {
			playerScored = new Text(150, 250, "Player 2 Scored!");
		}else {
			playerScored = new Text(150, 250, "Player 1 Scored!");
		}
		playerScored.setFont(Font.font("Verdana", 25));
		playerScored.setFill(Color.WHITE);
		rootGamePlay.getChildren().add(playerScored);
		
		restartGame();
	}
	
	private void restartGame() {
		
	}
	
	private void moveLeftUp() {
		if(!(playerLeft.getPlayerRectangle().getY() < 0)) {
			playerLeft.getPlayerRectangle().setY(playerLeft.getPlayerRectangle().getY()-5);
		}
	}
	
	private void moveLeftDown() {
		if(!(playerLeft.getPlayerRectangle().getY() > 500 - TennisPlayer.PLAYER_HEIGHT)) {
			playerLeft.getPlayerRectangle().setY(playerLeft.getPlayerRectangle().getY()+5);
		}
	}
	
	private void moveRightUp() {
		if(!(playerRight.getPlayerRectangle().getY() < 0)) {
			playerRight.getPlayerRectangle().setY(playerRight.getPlayerRectangle().getY()-5);
		}
	}
	
	private void moveRightDown() {
		if(!(playerRight.getPlayerRectangle().getY() > 500 - TennisPlayer.PLAYER_HEIGHT)) {
			playerRight.getPlayerRectangle().setY(playerRight.getPlayerRectangle().getY()+5);
		}
	}
	
	private void createMenuGroup(Stage primaryStage, Scene gamePlayScene) {
		// Canvas is created to be able to handle animations
		canvasMenu = new Canvas(500, 500);
		rootMenu.getChildren().add(canvasMenu);
		
		// Get graphics context object from the canvas
		gcMenu = canvasMenu.getGraphicsContext2D();
						
		// Setting background color
		gcMenu.setFill(Color.BLACK);
		gcMenu.fillRect(0, 0, canvasMenu.getWidth(), canvasMenu.getHeight());
		
		//Label with name of the game
		nameOfGame = new Text(120,115,"Pong Game");
		nameOfGame.setFont(Font.font("Verdana", 45));
		nameOfGame.setFill(Color.WHITE);
		
		playButton = new Button("Play Game");
		playButton.setLayoutX(200);
		playButton.setLayoutY(200);
		
		settingsButton = new Button("Settings");
		settingsButton.setLayoutX(208);
		settingsButton.setLayoutY(250);
		
		playButton.setOnAction(e -> primaryStage.setScene(gamePlayScene));
				
		// Add text and buttons to the root
		rootMenu.getChildren().addAll(nameOfGame, playButton, settingsButton);
		//playButton.setOnAction(e -> );
	}
	
	private void createGamePlayGroup(Stage primaryStage) {
		//Canvas is created to be able to handle animations
		canvas = new Canvas(500, 500);
		rootGamePlay.getChildren().add(canvas);
			
		// Get graphics context object from the canvas
		gc = canvas.getGraphicsContext2D();
				
		// Setting background color
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
						
						
		//Drawing rectangles at the initial positions
		playerLeft.getPlayerRectangle().setFill(Color.WHITE);
		playerRight.getPlayerRectangle().setFill(Color.WHITE);
				
		// Adding circle to the root group
		rootGamePlay.getChildren().add(gameBall.getTennisBall());
				
		//Adding rectangles to root group 
		rootGamePlay.getChildren().add(playerLeft.getPlayerRectangle());
		rootGamePlay.getChildren().add(playerRight.getPlayerRectangle());
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
