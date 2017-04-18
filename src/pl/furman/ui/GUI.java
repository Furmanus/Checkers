package pl.furman.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.furman.game.Game;
import pl.furman.game.Player;
import pl.furman.interfaces.GUInterface;
import pl.furman.interfaces.Piece;
import pl.furman.pieces.Pawn;

/**
 * Class representing graphical user interface, a connection between graphical JavaFx display and game logic. Has only one private field
 * {@code GridPane} grid, which consist 8*8 {@code CheckersButton} cells.
 * @author £ukasz Lach
 *
 */
public class GUI implements GUInterface {
	
	private GridPane grid;
	
	/**
	 * Public constructor. Gets all child nodes of {@code GridPane} grid field, and triggers their {@code setGame} method with {@code game}
	 * parameter (it passes game object to buttons, so clicked buttons can manipulate it).
	 * @param grid {@code GridPane} instance. Must contain {@code CheckersButton} nodes with {@code setGame} method.
	 * @param game {@code Game} object.
	 */
	public GUI(GridPane grid, Game game){
		
		this.grid = grid;
		ObservableList<Node> nodes = grid.getChildren();
		
		for(int i=0; i<nodes.size(); i++){
			
			((CheckersButton)nodes.get(i)).setGame(game);
		}
	}

	/**
	 * Sets {@code Piece} figure passed as parameter at coordinates {@code int x} and {@code int y}.
	 * @param x {@code int} x(row) coordinate of where we want to set chosen figure.
	 * @param y {@code int} y(column) coordinate of where we want to set chosen figure.
	 * @param piece {@code Piece} object figure (either {@code Pawn} or {@code Queen} to set.
	 */
	public void setPawn(int x, int y, Piece piece) {
		
		ObservableList<Node> nodes = this.grid.getChildren();
		int searchedIndex = getPaneIndex(x, y, nodes);

		if(piece == null){
			
			((CheckersButton)nodes.get(searchedIndex)).getStyleClass().removeAll("blackPawn", "whitePawn", "whiteQueen", "blackQueen");
		}else{
			
			((CheckersButton)nodes.get(searchedIndex)).getStyleClass().add(piece.getOwner().getName() + piece.getName());
		}
	}

	/**
	 * Method responsible for finding index of {@code CheckersButton} board cell clicked by user inside of {@code GridPane} grid object. Method
	 * takes clicked button id, and then iterates through child nodes of grid and compares id's.
	 * @param x {@code int} x(row) coordinate of cell we want go get index.
	 * @param y {@code int} y(column) coordinate of cell we want go get index.
	 * @param nodex {@code ObservableList<Node>} list of all children of {@code GridPane} private field grid.
	 * @return Returns {@code int} index of node with id choosen id.
	 */
	public int getPaneIndex(int x, int y, ObservableList<Node> nodes) {
		
		for(int i=0; i<nodes.size(); i++){
			
			if(nodes.get(i).getId().equals(x + "x" + y)){
				
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * Method which sets victory screen after one player has been defeated.
	 * @param player {@code Player} object who has won the game.
	 */
	public void setVictoryScreen(Player player) {
		
		if(player != null){
			
			Text text = new Text("Player " + player.getName() + " won the game!");
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(new Font(64));
			grid.add(text, 0, 0, 8, 8);
		}else{
			
			Text text = new Text("Draw");
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(new Font(64));
			grid.add(text, 0, 0, 8, 8);
		}
	}
	
	/**
	 * Triggers {@code CheckersButton}(found by its id) {@code changeActiveState} method.
	 * @param id {@code String} id number. 
	 */
	public void toogleButtonById(String id){
		
		((CheckersButton)grid.lookup("#" + id)).changeActiveState();
	}
	
	/**
	 * Triggers {@code CheckersButton}(found by its id) {@code changeHighlightedState} method.
	 * @param id {@code String} id number. 
	 */
	public void toogleButtonById2(String id){
		
		((CheckersButton)grid.lookup("#" + id)).changeHighlightedState();
	}
	
	/**
	 * Method which iterates through whole 8x8 game board and deactivates (it means triggering their {@code changeActiveState} and
	 * {@code changeHighlightedState} methods if buttons are active and highlighted.
	 */
	public void clearButtons(){
		
		CheckersButton examinedButton;
		
		for(int i=0; i<8; i++){
			
			for(int j=0; j<8; j++){
				
				examinedButton = ((CheckersButton)grid.lookup("#" + i + "x" + j));
				
				if(examinedButton.isActive()){
					
					examinedButton.changeActiveState();
				}else if(examinedButton.isHighlighted()){
					
					examinedButton.changeHighlightedState();
				}
			}
		}
	}
	
	/**
	 * Removes event listeners from all buttons on board.
	 */
	public void deactivateButtons(){
		
		CheckersButton examinedButton;
		
		for(int i=0; i<8; i++){
			
			for(int j=0; j<8; j++){
				
				examinedButton = ((CheckersButton)grid.lookup("#" + i + "x" + j));
				examinedButton.removeEventHandler();
			}
		}
	}
	
	/**
	 * @return Returns private {@code GridPane} grid field.
	 */
	public GridPane getGrid(){
		
		return this.grid;
	}
}
