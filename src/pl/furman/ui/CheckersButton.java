package pl.furman.ui;

import java.awt.Font;

import com.sun.javafx.css.Style;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;
import pl.furman.game.Game;

public class CheckersButton extends Button {
	
	private CheckersButton instance = this;
	private boolean isActive = false;
	private boolean isHighlighted = false;
	private Game game;
	
	private EventHandler<MouseEvent> buttonEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			
			instance.game.nextStep(instance.game.getActivePlayer(), instance.getId());
		}
	};

	public CheckersButton(String id){
		
		this.setId(id);
		this.setPrefHeight(96);
		this.setFont(new javafx.scene.text.Font(32));
		this.setPrefWidth(this.getPrefHeight());
		this.setBackgroundColour();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.buttonEventHandler);
	}
	
	private void setBackgroundColour(){
		
		String id = this.getId();
		int x = Character.getNumericValue(id.charAt(0));
		int y = Character.getNumericValue(id.charAt(2));
		
		if(x%2 == y%2){
			
			this.getStyleClass().add("button1");
		}else{
			
			this.getStyleClass().add("button2");
		}
	}
	
	public void setGame(Game game){
		
		this.game = game;
	}
	
	public boolean isActive(){
		
		return this.isActive;
	}
	
	public boolean isHighlighted(){
		
		return this.isHighlighted;
	}
	
	public void changeActiveState(){
		
		if(instance.isActive == false){
			
			this.getStyleClass().add("selectedButton");
			this.isActive = true;
		}else{
			
			this.getStyleClass().removeAll("selectedButton");
			this.isActive = false;
		}
	}
	
	public void changeHighlightedState(){
		
		if(instance.isHighlighted == false){
			
			this.getStyleClass().add("highlightedButton");
			this.isHighlighted = true;
		}else{
			
			this.getStyleClass().removeAll("highlightedButton");
			this.isHighlighted = false;
		}
	}
}
