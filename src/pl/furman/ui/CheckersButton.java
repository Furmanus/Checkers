package pl.furman.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pl.furman.game.Game;

/**
 * Class representing graphical visualisation of single game board cell.
 * @author £ukasz Lach
 *
 */
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

	/**
	 * Public constructor. Sets size and style for this button and also sets its {@code id}. {@code id} is a {@code String} "ixj", where "i" is
	 * board row and j is board column where this button will be placed. Event handler on mouse click will trigger {@code Game} object instance
	 * method {@code nextStep} with active player and {@code id} of this button as parameters.
	 * @param id {@code String} "ixj" where "i" is board row and "j" is board column where this button will be placed.
	 */
	public CheckersButton(String id){
		
		this.setId(id);
		this.setPrefHeight(96);
		this.setFont(new javafx.scene.text.Font(32));
		this.setPrefWidth(this.getPrefHeight());
		this.setBackgroundColour();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.buttonEventHandler);
	}
	
	/**
	 * Sets background color for this button, which basically means adding one of two style classes from {@code BasicStyle.css}.
	 */
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
	
	/**
	 * Setter for private {@code game} field.
	 * @param game {@code Game} class object.
	 */
	public void setGame(Game game){
		
		this.game = game;
	}
	
	/**
	 * 
	 * @return Returns {@code boolean} value of private {@code isActive} field.
	 */
	public boolean isActive(){
		
		return this.isActive;
	}
	
	/**
	 * 
	 * @return Returns {@code boolean} value of private {@code isHighlighted} field.
	 */
	public boolean isHighlighted(){
		
		return this.isHighlighted;
	}
	
	/**
	 * Method which changes value of private {@code boolean isActive} field and adds(or removes) style class {@code selectedButton}.
	 */
	public void changeActiveState(){
		
		if(instance.isActive == false){
			
			this.getStyleClass().add("selectedButton");
			this.isActive = true;
		}else{
			
			this.getStyleClass().removeAll("selectedButton");
			this.isActive = false;
		}
	}
	
	/**
	 * Method which changes value of private {@code boolean isHighlighted} field and adds(or removes) style class {@code highlightedButton}.
	 */
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
