package pl.furman.game;

import java.util.ArrayList;

import pl.furman.interfaces.Piece;

/**
 * Class representing one of two players.
 * @author £ukasz Lach
 *
 */
public class Player {

	private ArrayList<Piece> pawns = new ArrayList<Piece>();
	private ArrayList<Piece> pawnsToMove;
	private ArrayList<Piece> pawnsToKill;
	private String name;
	private char colour;
	private String sourceCoords = null;
	private String targetCoords = null;
	
	public Player(String name, char colour) {
		
		this.name = name;
		this.colour = colour;	
	}

	public ArrayList<Piece> getPawns() {
		return pawns;
	}

	public void setPawns(ArrayList<Piece> pawns) {
		this.pawns = pawns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getColour() {
		return colour;
	}

	public String getSourceCoords() {
		return sourceCoords;
	}

	public void setSourceCoords(String sourceCoords) {
		this.sourceCoords = sourceCoords;
	}

	public String getTargetCoords() {
		return targetCoords;
	}

	public void setTargetCoords(String targetCoords) {
		this.targetCoords = targetCoords;
	}

	public ArrayList<Piece> getPawnsToMove() {
		return pawnsToMove;
	}

	public void setPawnsToMove(ArrayList<Piece> pawnsToMove) {
		this.pawnsToMove = pawnsToMove;
	}

	public ArrayList<Piece> getPawnsToKill() {
		return pawnsToKill;
	}

	public void setPawnsToKill(ArrayList<Piece> pawnsToKill) {
		this.pawnsToKill = pawnsToKill;
	}
}
