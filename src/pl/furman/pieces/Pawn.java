package pl.furman.pieces;

import java.util.ArrayList;

import pl.furman.game.Board;
import pl.furman.game.Player;
import pl.furman.game.Utility;
import pl.furman.interfaces.Piece;

/**
 * Class representing player figure Pawn. Figures implements {@code Piece} interface and contains logic for operating(moving and validating movement)
 * them on board.
 * @author £ukasz Lach
 *
 */
public class Pawn implements Piece{

	private int x;
	private int y;
	private char colour;
	private Player owner;
	private final String name = "Pawn";
	
	/**
	 * Public constructor for {@code Pawn} object. Sets initial private coordinates {@code x} and {@code y} and also private String field 
	 * {@code colour} and {@code Player} private field {@code owner}.
	 * @param x {@code int} x coordinate(row on game board) of pawn on board.
	 * @param y {@code int} y coordinate(column on game board) of pawn on board.
	 * @param owner {@code Player} object, owner of this pawn.
	 */
	public Pawn(int x, int y, Player owner) {
		
		this.x = x;
		this.y = y;
		this.colour = owner.getColour();
		this.owner = owner;
		
		owner.getPawns().add(this);
	}
	
	/**
	 * Function responsible for validating whether Pawn has any enemy Pawns on any field close to him.
	 * @param board {@code Board} object containing Pawns.
	 * @return Returns {@code ArrayList<Piece>} of any enemy {@code Piece} that are on field surround examined Pawn.
	 */
	private ArrayList<Piece> validateVicinity(Board board){
		
		ArrayList<Piece> hostileList = new ArrayList<Piece>();
		Piece examinedField;
		int dx;
		int dy;
		
		for(int i=-1; i<=1; i++){
			
			for(int j=-1; j<=1; j++){
				
				if(this.x + i < 0 || this.x + i > 7 || this.y + j < 0 || this.y + j > 7){
					
					continue;
				}
				
				examinedField = board.getFields()[this.x + i][this.y + j];
				
				if(examinedField != null && examinedField.getColour() != this.colour){
					
					dx = examinedField.getX() - this.x;
					dy = examinedField.getY() - this.y;
					
					if(examinedField.getX() + dx < 0 || examinedField.getX() + dx > 7 || examinedField.getY() + dy < 0 || examinedField.getY() + dy > 7){
						
						continue;
					}
					
					if(board.getFields()[examinedField.getX() + dx][examinedField.getY() + dy] == null){
						
						if(examinedField.getX() + dx < 0 || examinedField.getX() + dx > 7 || examinedField.getY() + dy < 0 || examinedField.getY() + dy > 7){
							
							continue;
						}
						
						hostileList.add(examinedField);
					}
				}
			}
		}
		
		return hostileList;
	}
	
	/**
	 * method validating whether Pawn can move to {@code targetX},{@code targetY} coordinates.
	 * @param targetX {@code int} x coordinate(row) of choosen board cell.
	 * @param targetY {@code int} y coordinate(column) of choosen board cell.
	 * @param board {@code Board} game object which contains all figures in game.
	 * @return Returns String: "false" if Pawn can't move to choosen coordinates, "true" if Pawn can move to choosen coordinates and "kill" if
	 * Pawn can move to choosen coordinates and enemy figure was captured in that move (enemy's figure {@code killPawn} method is triggered
	 * in that case).
	 */
	public String validateMovement(int targetX, int targetY, Board board){
		
		ArrayList<Piece> hostileList = this.validateVicinity(board);
		Player hostileOwner = null;
		int facingDirection = this.colour == 'w' ? -1 : 1;
		int dx;
		int dy;
		
		if(hostileList.size() == 0){
			
			if(Utility.getDistance(this.x, this.y, targetX, targetY) > 1.5){
				
				return "false";
			}else{
				
				if(board.getFields()[targetX][targetY] != null){
					
					return "false";
				}
				
				if((this.x - targetX) == facingDirection && Math.abs(this.y - targetY) == 1){
					
					return "true";
				}else{
					
					return "false";
				}
			}
		}else{
			
			for(int i=0; i<hostileList.size(); i++){
				
				dx = hostileList.get(i).getX() - this.x;
				dy = hostileList.get(i).getY() - this.y;
				
				if(this.x + 2*dx == targetX && this.y + 2*dy == targetY){
					
					hostileOwner = hostileList.get(i).getOwner();
					
					hostileList.get(i).killPawn(board);
					
					return "kill";
				}
			}
			
			return "false";
		}
	}
	
	/**
	 * TODO
	 * @param board
	 * @return
	 */
	public String[] getPossibleMoves(Board board){
		
		ArrayList<String> result = new ArrayList<String>();
			
		return null;
	}
	/**
	 * Method determining if {@code Pawn} object can move anywhere from its current position.
	 * @param board {@code Board} game object which contains all figures in game.
	 * @return Returns {@code String}: "false" if Pawn can't move to choosen coordinates, "true" if Pawn can move to choosen coordinates and "kill" if
	 * Pawn can move to choosen coordinates and enemy figure will be captured in that move.
	 */
	public String canMove(Board board){
		
		String result = "false";
		int dx;
		int dy;
		int forbiddenX = this.colour == 'w' ? -1 : 1;
		
		ArrayList<Piece> hostileList = this.validateVicinity(board);
		
		if(hostileList.size() == 0){
			
			for(int i=-1; i<=1; i++){
				
				for(int j=-1; j<=1; j++){
					
					if(i==0 || j==0 || i == forbiddenX){
						
						continue;
					}else{
						
						if(validateAddedCoordinates(i, j) && board.getFields()[this.x + i][this.y + j] == null){
							
							result = "true";
						}
					}
				}
			}
		}else{
			
			for(int i=0; i<hostileList.size(); i++){
				
				dx = hostileList.get(i).getX() - this.x;
				dy = hostileList.get(i).getY() - this.y;
				
				if(validateAddedCoordinates(2*dx, 2*dy) && board.getFields()[this.x + 2 * dx][this.y + 2 * dy] == null){
					
					result = "kill";
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Method responsible for moving {@code Pawn} object to certain x,y coordinates on game board.
	 * @param x {@code int} x coordinate (row) of cell where {@code Pawn} object will be moved.
	 * @param y {@code int} y coordinate (column) of cell where {@code Pawn} object will be moved.
	 * @return Returns {@code boolean}. {@code true} if at new coordinates moved {@code Pawn} object has any enemy figures in its surrounding, returns {@code false}
	 * otherwise. It is used to determine if moved pawn can perform another enemy figure capture.
	 */
	public boolean move(int x, int y, Board board){
		
		ArrayList<Piece> hostileVicinity;
		
		board.setPawn(this.x, this.y, null);
		
		board.setPawn(x, y, this);
		
		this.x = x;
		this.y = y;
		
		hostileVicinity = this.validateVicinity(board);
		
		return hostileVicinity.size() > 0;
	}
	
	/**
	 * Method which removes {@code Pawn} object from game board.
	 * @param board {@code Board} game object which contains all figures in game. 
	 * 
	 */
	public void killPawn(Board board){
		
		this.owner.getPawns().remove(0);
		board.setPawn(this.x, this.y, null);
	}
	
	/**
	 * Validates if certain game board coordinates {@code x,y} added to this pawn doesn't go beyond game board.
	 * @param x {@code int} value added to {@code x} position of this pawn.
	 * @param y {@code int} value added to {@code y} position of this pawn.
	 * @return Returns {@code false} if any of this pawn coordinate summed with coordinate given as parameter went beyond game board. Returns {@code true}
	 * otherwise.
	 */
	private boolean validateAddedCoordinates(int x, int y){
		
		if(this.x + x < 0 || this.x + x > 7 || this.y + y < 0 || this.y + y > 7){
			
			return false;
		}else{
			
			return true;
		}
	}
	
	/**
	 * Method which promotes this pawn into queen.
	 * @param player {@code Player} owner of this figure.
	 * @param board {@code Board} game object which contains all figures in game.
	 */
	public void promoteToQueen(Player player, Board board){
		
		Piece newQueen = new Queen(this.x, this.y, this.owner);
		killPawn(board);
		board.setPawn(this.x, this.y, newQueen);
	}

	/**
	 * @return Returns x(row) position on game board of this pawn.
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @param x Sets new x(row) position on game board of this pawn.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return Returns y(column) position on game board of this pawn.
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y Sets new y(column) position on game board of this pawn.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return Returns {@code char} property of this pawn (either {@code 'b'} or {@code 'w'}).
	 */
	public char getColour() {
		return colour;
	}

	/**
	 * 
	 * @param colour Sets new {@code char} colour property of this pawn (either {@code 'b'} or {@code 'w'}).
	 */
	public void setColour(char colour) {
		this.colour = colour;
	}

	/**
	 * @return Returns {@code Player} object to which this pawn belongs.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * 
	 * @param owner Sets new {@code Player} private variable {@code owner} of this pawn.
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * @return Returns {@code String name} property of this pawn. {@code name} is description of type of this figure.
	 */
	public String getName(){
		
		return name;
	}
}
