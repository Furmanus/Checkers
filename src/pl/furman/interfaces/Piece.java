package pl.furman.interfaces;

import java.util.ArrayList;

import pl.furman.game.Board;
import pl.furman.game.Player;
import pl.furman.pieces.Pawn;

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
