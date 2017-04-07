package pl.furman.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pl.furman.game.Game;
import pl.furman.game.Player;
import pl.furman.interfaces.GUInterface;
import pl.furman.interfaces.Piece;
import pl.furman.pieces.Pawn;

public class GUI implements GUInterface {
	
	private GridPane grid;
	
	public GUI(GridPane grid, Game game){
		
		this.grid = grid;
		ObservableList<Node> nodes = grid.getChildren();
		
		for(int i=0; i<nodes.size(); i++){
			
			((CheckersButton)nodes.get(i)).setGame(game);
		}
	}

	public void setPawn(int x, int y, Piece piece) {
		
		ObservableList<Node> nodes = this.grid.getChildren();
		int searchedIndex = getPaneIndex(x, y, nodes);

		if(piece == null){
			
			((CheckersButton)nodes.get(searchedIndex)).getStyleClass().removeAll("blackPawn", "whitePawn", "whiteQueen", "blackQueen");
		}else{
			
			((CheckersButton)nodes.get(searchedIndex)).getStyleClass().add(piece.getOwner().getName() + piece.getName());
		}
	}

	public int getPaneIndex(int x, int y, ObservableList<Node> nodes) {
		
		for(int i=0; i<nodes.size(); i++){
			
			if(nodes.get(i).getId().equals(x + "x" + y)){
				
				return i;
			}
		}
		
		return -1;
	}

	public void setVictoryScreen(Player player) {
		
		Text text = new Text("Player " + player.getName() + " won the game!");
		text.setFont(new Font(64));
		grid.add(text, 0, 0, 8, 8);
	}
	
	public void toogleButtonById(String id){
		
		((CheckersButton)grid.lookup("#" + id)).changeActiveState();
	}
	
	public void toogleButtonById2(String id){
		
		((CheckersButton)grid.lookup("#" + id)).changeHighlightedState();
	}
	
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
	
	public GridPane getGrid(){
		
		return this.grid;
	}
}
