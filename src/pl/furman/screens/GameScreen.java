package pl.furman.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pl.furman.game.Game;
import pl.furman.ui.CheckersButton;

public class GameScreen extends Scene {

	private static GameScreen instance;
	private static GridPane grid;
	private static Game game;
	
	static{
		
		grid = new GridPane();
		setButtons();
		game = new Game(grid);
	}
	
	private GameScreen(Stage mainStage) {
		
		super(grid, 96*8, 96*8);
		this.getStylesheets().add("./pl/furman/styles/BasicStyle.css");
	}
	
	public static GameScreen getInstance(Stage mainStage){
		
		if(instance == null){
			
			instance = new GameScreen(mainStage);
		}
			
		return instance;
		
	}
	
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
