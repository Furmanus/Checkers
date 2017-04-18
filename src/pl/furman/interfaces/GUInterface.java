package pl.furman.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import pl.furman.game.Player;

/**
 * Interface for operating on graphical user interface.
 * @author £ukasz Lach
 *
 */
public interface GUInterface {

	public void setPawn(int x, int y, Piece piece);
	
	public int getPaneIndex(int x, int y, ObservableList<Node> nodes);
	
	public void setVictoryScreen(Player player);
	
	public void toogleButtonById(String id);
	
	public void toogleButtonById2(String id);
	
	public void clearButtons();
	
	public GridPane getGrid();
	
	public void deactivateButtons();
}
