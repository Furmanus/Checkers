package pl.furman.interfaces;

import pl.furman.game.Board;
import pl.furman.game.Player;

/**
 * Interface for players checkers figures. Figures actually are interfaces of this type, because both Pawns and Queens have same methods.
 * @author £ukasz Lach
 *
 */
public interface Piece {
	
	String validateMovement(int targetX, int targetY, Board board);
	
	String canMove(Board board);
	
	boolean move(int x, int y, Board board);
	
	void killPawn(Board board);
	
	int getX();
	
	int getY();
	
	char getColour();
	
	Player getOwner();
	
	String getName();
	
	void promoteToQueen(Player player, Board board);
}
