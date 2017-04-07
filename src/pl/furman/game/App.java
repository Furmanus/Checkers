package pl.furman.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.furman.screens.StartScreen;
import pl.furman.ui.CheckersButton;

public class App extends Application {
	
	@Override
	public void start(Stage mainStage) throws Exception {
		
		mainStage.setTitle("Checkers by £ukasz Lach");
		mainStage.setScene(StartScreen.getStartScreen(mainStage));
		mainStage.setResizable(false);
		mainStage.show();
	}
}
