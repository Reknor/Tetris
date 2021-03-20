package project;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Tetris extends Application {
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static final int XMAX = SIZE * 12;
	public static final int YMAX = SIZE * 24;
	public static final int[][] GRID = new int[XMAX / SIZE][YMAX / SIZE];;
	private Pane groupe;
	private Form object;
	private Form nextObj;
	private Scene scene;
	private int top;
	private int score;
	private int linesCounter;
	private int gameSpeed;
	private Timer fall;
	private TimerTask task;
	private boolean inGame;
	private MusicController musicController;
	private Controller controller;
	private boolean soundOn;
	private boolean musicOn;
	private static final String BACKGROUND_MUSIC = "./data/Waterflame - Glorious morning.wav";
	private static final String SCORING_SOUND = "./data/Wow.wav";
	private static final String END_SOUND = "./data/Ah_Come_On.wav";
	
	
	// Create default settings
	public Tetris() {
		groupe = new Pane();
		scene = new Scene(groupe, XMAX + 150, YMAX);
		score = 0;
		top = 0;
		gameSpeed = 1000;
		inGame = true;
		controller = new Controller(MOVE, SIZE, XMAX, YMAX, GRID, this);
		nextObj = controller.makeRect();
		linesCounter = 0;
		soundOn = true;
		musicOn = true;
	}
	
	
	public static void main (String []args) {
		new Tetris().run(args);
	}
	
	
	// Create scene and start the game
	public void run(String []args) {
		launch(args);
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		for (int[] a : GRID) {
			Arrays.fill(a, 0);
		}

		// Create score and level text
		Line line = new Line(XMAX, 0, XMAX, YMAX);
		Text scoreText = new Text("Score: ");
		scoreText.setStyle("-fx-font: 20 arial;");
		scoreText.setY(50);
		scoreText.setX(XMAX + 5);
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arial;");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.GREEN);
		groupe.getChildren().addAll(scoreText, line, level);
		
		// Create audio buttons and playing sounds
		createSoundButtons();
		
		
		// Create first block and the stage
		Form a = nextObj;
		groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
		onKeyPress(a);
		object = a;
		nextObj = controller.makeRect();
		stage.setScene(scene);
		stage.setTitle("T E T R I S");
		stage.show();
		
		
		fall = new Timer();
		task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
								|| object.d.getY() == 0) {
							top++;
						} else {
							top = 0;
						}
						
						if (top == 3) {
							// GAME OVER
							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							groupe.getChildren().add(over);
							inGame = false;
							musicController.stopBackgroundMusic();
							if(soundOn) {
								musicController.playSound(END_SOUND);
							}
						}

						// Exit
						if (top == 7) {
							stop();
						}
						
						if (inGame) {
							controller.MoveDown(object);
							scoreText.setText("Score: " + Integer.toString(score));
							level.setText("Lines: " + Integer.toString(linesCounter));
						}
					}
				});
			}
		};
		// Start game
		fall.schedule(task, 0, gameSpeed);
	}

	
	private void onKeyPress(Form form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT: case D: case NUMPAD6:
					controller.MoveRight(form);
					break;
				case DOWN: case S: case NUMPAD2:
					controller.MoveDown(form);
					score++;
					break;
				case LEFT: case A: case NUMPAD4:
					controller.MoveLeft(form);
					break;
				case UP: case W: case NUMPAD8:
					controller.rotateFigure(form);
					break;
				case M:
					if(soundOn)
					{
						if (musicOn) {
							musicOn = false;
							musicController.stopBackgroundMusic();
						} else {
							musicOn = true;
							musicController.resumeBackgroundMusic();
						}
					}
					break;
				default:
					break;
				}
			}
		});
	}

	
	// Create a new block and add to the scene
	public void addNextBlock() {
		Form a = nextObj;
		nextObj = controller.makeRect();
		object = a;
		groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
		onKeyPress(a);
	}
	
	
	public void RemoveRows() {
		Pane pane = groupe;
		ArrayList<Node> rect = new ArrayList<>();
		ArrayList<Integer> lines = new ArrayList<>();
		ArrayList<Node> newRects = new ArrayList<>();
		int full = 0;
		
		// Check which line is full
		for (int i = 0; i < GRID[0].length; i++) {
			for (int j = 0; j < GRID.length; j++) {
				if (GRID[j][i] == 1) {
					full++;
				}
			}

			if (full == GRID.length) {
				lines.add(i);
			}
			full = 0;
		}

		// Delete the row
		if (lines.size() > 0) {
			do {
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle) {
						rect.add(node);
					}
				}
				score += 50;
				linesCounter++;
				boolean removed = false;
				
				
				// Delete block
				for (Node node : rect) {
					Rectangle a = (Rectangle) node;
					if (a.getY() == lines.get(0) * SIZE) {
						GRID[((int) a.getX() / SIZE)][((int) a.getY() / SIZE)] = 0;
						pane.getChildren().remove(node);
						removed = true;
					} else {
						newRects.add(node);
					}
				}

				for (Node node : newRects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() < lines.get(0) * SIZE) {
						GRID[((int) a.getX() / SIZE)][((int) a.getY() / SIZE)] = 0;
						a.setY(a.getY() + SIZE);
					}
				}
					lines.remove(0);
					rect.clear();
					newRects.clear();
					if (removed && soundOn) {
						new MusicController().playSound(SCORING_SOUND);
					}

					for (Node node : pane.getChildren()) {
						if (node instanceof Rectangle)
							rect.add(node);
					}

					for (Node node : rect) {
						Rectangle a = (Rectangle) node;
						try {
							GRID[((int) a.getX() / SIZE)][((int) a.getY() / SIZE)] = 1;
						} catch (ArrayIndexOutOfBoundsException e) {

						}
					}
					rect.clear();
			} 
			while (lines.size() > 0);
		}
	}
	
	
	@Override
	public void stop() {
		// Ends GUI thread
		Platform.exit();
		// End JVM
		System.exit(0);
	}
	
	
	private void createSoundButtons() {
		// Audio
		musicController = new MusicController();
		musicController.playBackgroundMusic(BACKGROUND_MUSIC);
		// Audio button
		Button audioButton = new Button();
		try {
			Image imageMute = new Image(new FileInputStream("./data/Unmute.png"));
			audioButton.setGraphic(new ImageView(imageMute));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Pane audioButtonPane = new Pane();
		audioButtonPane.getChildren().add(audioButton);
		audioButtonPane.setLayoutX(XMAX + 1);
		audioButtonPane.setLayoutY(YMAX - 3 * SIZE);
		audioButton.setFocusTraversable(false);
		audioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (soundOn) {
					soundOn = false;
					if(musicOn) {
						musicController.stopBackgroundMusic();
					}
					try {
						audioButton.setGraphic(new ImageView(new Image(new FileInputStream("./data/Mute.png"))));
					} catch (IOException ioe) {
					}
				} else {
					soundOn = true;
					if(musicOn) {
						musicController.resumeBackgroundMusic();
					}
					try {
						audioButton.setGraphic(new ImageView(new Image(new FileInputStream("./data/Unmute.png"))));
					} catch (IOException ioe) {
					}
				}
			}
		});
		DropShadow shadow = new DropShadow();
		audioButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				audioButton.setEffect(shadow);
			}
		});
		audioButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				audioButton.setEffect(null);
			}
		});
		groupe.getChildren().add(groupe.getChildren().size(), audioButtonPane);
		// Music button
		Button musicButton = new Button();
		try {
			Image imageMute = new Image(new FileInputStream("./data/Music.png"));
			musicButton.setGraphic(new ImageView(imageMute));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Pane musicButtonPane = new Pane();
		musicButtonPane.getChildren().add(musicButton);
		musicButtonPane.setLayoutX(XMAX + 3 * SIZE);
		musicButtonPane.setLayoutY(YMAX - 3 * SIZE);
		musicButton.setFocusTraversable(false);
		musicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(soundOn)
				{
					if (musicOn) {
						musicOn = false;
						musicController.stopBackgroundMusic();
					} else {
						musicOn = true;
						musicController.resumeBackgroundMusic();
					}
				}
			}
		});
		musicButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				musicButton.setEffect(new DropShadow());
			}
		});
		musicButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				musicButton.setEffect(null);
			}
		});
		groupe.getChildren().add(groupe.getChildren().size(), musicButtonPane);
	}
}
