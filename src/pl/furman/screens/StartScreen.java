package pl.furman.screens;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartScreen extends Scene {
	
	private static StartScreen instance = null;
	private static VBox box = new VBox();
	private double prefButtonWidth = 128;
	private double prefButtonHeight = 32;
	private Stage mainStage;

	private StartScreen(Stage mainStage) {
		
		super(box, 96*8, 96*8);
		
		this.mainStage = mainStage;
		this.getStylesheets().add("./pl/furman/styles/StartScreen.css");
		
		box.setSpacing(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(getStartButton(), getQuitButton());
	}
	
	private Button getStartButton(){
		
		Button startButton = new Button();
		startButton.setPrefSize(prefButtonWidth, prefButtonHeight);
		startButton.setText("START");
		startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				mainStage.setScene(GameScreen.getInstance(mainStage));
			}
		});
		
		return startButton;
	}
	
	private Button getQuitButton(){
		
		Button quitButton = new Button();
		quitButton.setPrefSize(prefButtonWidth, prefButtonHeight);
		quitButton.setText("QUIT");
		quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				Platform.exit();
			}
		});
		
		return quitButton;
	}
	
	public static StartScreen getStartScreen(Stage mainStage){
		
		if(instance == null){
			
			instance = new StartScreen(mainStage);
		}
		
		return instance;
	}
}
