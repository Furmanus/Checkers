package pl.furman.screens;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Singleton class representing start screen. Inherits from {@code Scene} class.
 * @author £ukasz Lach
 *
 */
public class StartScreen extends Scene {
	
	private static StartScreen instance = null;
	private static VBox box = new VBox();
	private double prefButtonWidth = 128;
	private double prefButtonHeight = 32;
	private Stage mainStage;

	/**
	 * Private constructor called only once by static {@code getInstance} method. Constructor calls {@code Scene} super class constructor with root node
	 * {@code box} which is JavaFx {@code VBox} object. It also sets stylesheet {@code StartScreen.css} and adds two buttons(start and quit) to 
	 * {@code VBox}.
	 * @param mainStage
	 */
	private StartScreen(Stage mainStage) {
		
		super(box, 96*8, 96*8);
		
		this.mainStage = mainStage;
		this.getStylesheets().add("./pl/furman/styles/StartScreen.css");
		
		box.setSpacing(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(getStartButton(), getQuitButton());
	}
	
	/**
	 * Private method responsible for creating and returning start button which will be set in start screen. Start button is a JavaFx {@code Button}
	 * with mouse click event handler. Button when clicked sets {@code Stage} mainStage new {@code GameScreen} screen.
	 * @return Returns created {@code Button} object.
	 */
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
	
	/**
	 * Private method responsible for creating and returning quit button which will be set in start screen. Quit button is a JavaFx {@code Button}
	 * with mouse click event handler. Button when clicked sets {@code Stage} terminates running application.
	 * @return Returns created {@code Button} object.
	 */
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
	
	/**
	 * Method responsible for creating (only when this method is triggered for first time) and returning only instance of {@code StartScreen} class.
	 * @param mainStage {@code Stage} object which will be passed into {@code StartScreen} constructor when this method will be triggered first time.
	 * @return Returns {@code StartScreen} only instance.
	 */
	public static StartScreen getStartScreen(Stage mainStage){
		
		if(instance == null){
			
			instance = new StartScreen(mainStage);
		}
		
		return instance;
	}
}
