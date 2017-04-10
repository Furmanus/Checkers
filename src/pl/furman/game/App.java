package pl.furman.game;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.furman.screens.StartScreen;

public class App extends Application {
	
	@Override
	public void start(Stage mainStage) throws Exception {
		
		mainStage.setTitle("Checkers by £ukasz Lach");
		mainStage.setScene(StartScreen.getStartScreen(mainStage));
		mainStage.setResizable(false);
		mainStage.show();
	}
}
