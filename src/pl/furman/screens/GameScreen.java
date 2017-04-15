package pl.furman.screens;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.furman.game.Game;
import pl.furman.ui.CheckersButton;

/**
 * Singleton class representing main game screen, graphical visualisation of {@code Board} class object. Only way to access its only instance
 * is through {@code getInstance} method.
 * @author £ukasz Lach
 *
 */
public class GameScreen extends Scene {

	private static GameScreen instance;
	private static GridPane grid;
	private static Game game;
	
	static{
		
		grid = new GridPane();
		setButtons();
		game = new Game(grid);
	}
	
	/**
	 * Private constructor. Triggers constructor from {@code Scene} class with private {@code GridPane} field parameter and preferred size.
	 * Adds to self {@code BasicStyle.css} style sheet.
	 * @param mainStage {@code Stage} type mainStage, main argument of application {@code launch()} method.
	 */
	private GameScreen(Stage mainStage) {
		
		super(grid, 96*8, 96*8);
		this.getStylesheets().add("./pl/furman/styles/BasicStyle.css");
	}
	
	/**
	 * Returns only instance of this class if it was initialized. In other case, private constructor is triggered and after that instance is returned.
	 * @param mainStage {@code Stage} type mainStage object is passed as parameter, because if instance field of {@code GameScreen} wasn't
	 * initialized, it is passed to class private constructor as parameter. 
	 * @return Returns only instance of {@code GameScreen} class.
	 */
	public static GameScreen getInstance(Stage mainStage){
		
		if(instance == null){
			
			instance = new GameScreen(mainStage);
		}
			
		return instance;
		
	}
	
	/**
	 * Sets graphical visalisation of game board. Board cells are styled {@code CheckersButton} class objects.
	 */
	private static void setButtons(){
		
		CheckersButton field;
		
		for(int i=0; i<8; i++){
			
			for(int j=0; j<8; j++){
				
				field = new CheckersButton(i + "x" + j);
				grid.add(field, i, j);
			}
		}
	}
}
