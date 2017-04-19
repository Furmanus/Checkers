package pl.furman.pieces;

import java.util.ArrayList;

import pl.furman.game.Board;
import pl.furman.game.Player;
import pl.furman.interfaces.Piece;

/**
 * Class representing player figure Queen. Figures implements {@code Piece} interface and contains logic for operating(moving and validating movement)
 * them on board.
 * @author £ukasz Lach
 *
 */
public class Queen implements Piece {

	private int x;
	private int y;
	private char colour;
	private Player owner;
	private final String name = "Queen";
	
	/**
	 * Public constructor for {@code Queen} object. Sets initial private coordinates {@code x} and {@code y} and also private String field 
	 * {@code colour} and {@code Player} private field {@code owner}.
	 * @param x {@code int} x coordinate(row on game board) of this queen on board.
	 * @param y {@code int} y coordinate(column on game board) of this queen on board.
	 * @param owner {@code Player} object, owner of this queen.
	 */
	public Queen(int x, int y, Player owner) {
		
		this.x = x;
		this.y = y;
		this.colour = owner.getColour();
		this.owner = owner;
		
		owner.getPawns().add(this);
	}
	
	/**
	 * method validating whether this queen can move to {@code targetX},{@code targetY} coordinates.
	 * @param targetX {@code int} x coordinate(row) of choosen board cell.
	 * @param targetY {@code int} y coordinate(column) of choosen board cell.
	 * @param board {@code Board} game object which contains all figures in game.
	 * @return Returns String: "false" if this queen can't move to choosen coordinates, "true" if this queen can move to choosen coordinates and "kill" if
	 * this queen can move to choosen coordinates and enemy figure was captured in that move (enemy's figure {@code killPawn} method is triggered
	 * in that case).
	 */
	public String validateMovement(int targetX, int targetY, Board board) {
		
		int dx = 0;
		int dy = 0;
		ArrayList<int[]> directions = new ArrayList<int[]>();
		
		if(Math.abs(targetX - this.x) - Math.abs(targetY - this.y) != 0){
			
			return "false";
		}else{
			
			String validateDirection = null;
			
			for(int i=-1; i<=1; i++){
				
				for(int j=-1; j<=1; j++){
					
					if(i==0 || j==0){
						
						continue;
					}else{
						
						validateDirection = canMoveInDirection(this.x, this.y, i, j, board);
						
						if(validateDirection == "kill"){
							
							directions.add(new int[]{i,j});
						}
					}
				}
			}
			
			if(directions.size() > 0){
				
				validateDirection = "kill";
			}
			
			if(validateDirection != "kill"){
				
				validateDirection = canMoveInDirection(this.x, this.y, this.x - targetX < 0 ? 1 : -1, this.y - targetY < 0 ? 1 : -1, board);
				
				if(validateDirection == "true"){
					
					dx = this.x - targetX < 0 ? 1 : -1;
					dy = this.y - targetY < 0 ? 1 : -1; 
				}
			}
			
			if(validateDirection == "kill"){
				
				String result = null;
				
				for(int i=0; i<directions.size(); i++){
					
					result = checkDirection(this.x, this.y, directions.get(i)[0], directions.get(i)[1], targetX, targetY, board, true);
					
					if(result == "kill"){
						
						break;
					}
				}
				
				return result;
			}else{
				
				return checkDirection(this.x, this.y, dx, dy, targetX, targetY, board, false);
			}
		}
	}
	
	/**
	 * Method which checks if this queen can move in certain {@code dx,dy} direction up to {@code targetX,targetY} board game cell. Consist
	 * {@code while} instruction which examines every game board cell in selected direction.
	 * @param startX {@code int} x coordinate of currently examined game board cell. Initially it should be equal to x position of this queen.
	 * @param startY {@code int} y coordinate of currently examined game board cell. Initially it should be equal to y position of this queen.
	 * @param dx {@code int} x coordinate of examined direction. It can be either -1 or 1 (only diagonal movement is allowed).
	 * @param dy {@code int} y coordinate of examined direction. It can be either -1 or 1 (only diagonal movement is allowed).
	 * @param targetX {@code int} x coordinate (row) of target game board cell.
	 * @param targetY {@code int} y coordinate (column) of target game board cell.
	 * @param board {@code Board} game object which contains all figures in game.
	 * @param forceKill {@code boolean} value determining whether this queen is forced to capture enemy figure in this move.
	 * @return Returns String: "false" if this queen can't move to choosen coordinates, "true" if this queen can move to choosen coordinates and "kill" if
	 * this queen can move to choosen coordinates and enemy figure was captured in that move (enemy's figure {@code killPawn} method is triggered
	 * in that case).
	 */
	private String checkDirection(int startX, int startY, int dx, int dy, int targetX, int targetY, Board board, boolean forceKill){
		
		String result = "false";
		boolean jumpMade = false;
		Piece pieceKilled = null;
		
		while(true){
			
			if(startX == targetX && startY == targetY){
				
				if(forceKill == false){
					
					if(!result.equals("kill")){
						
						result = "ok";
					}
					break;
				}else{
					
					if(!result.equals("kill") && jumpMade == true){
						
						result = "ok";
					}
					break;
				}
			}
				
			if(startX < 0 || startX > 7 || startY < 0 || startY > 7){
					
				result = "false";
				break;
			}
			
			if(startX != this.x || startY != this.y){
				
				if(board.getFields()[startX][startY] != null && board.getFields()[startX][startY].getOwner().equals(this.owner)){
					
					result = "false";
					break;
				}
			}
			
			if(board.getFields()[startX][startY] != null && !board.getFields()[startX][startY].getOwner().equals(this.owner)){
				
				if(board.getFields()[startX + dx][startY + dy] != null){
					
					result = "false";
					break;
				}else{
					
					if(jumpMade == false){
						
						pieceKilled = board.getFields()[startX][startY];
						result = "kill";
						jumpMade = true;
					}else{
						
						result = "false";
						break;
					}
				}
			}
			
			startX += dx;
			startY += dy;
		}
		
		if(result == "kill"){
			
			pieceKilled.killPawn(board);
		}
		
		return result;
	}
	
	/**
	 * Method which checks if this queen can move in certain {@code dx,dy} direction on board game cell. Consist
	 * {@code while} instruction which examines every game board cell in selected direction.
	 * @param startX {@code int} x coordinate of currently examined game board cell. Initially it should be equal to x position of this queen.
	 * @param startY {@code int} y coordinate of currently examined game board cell. Initially it should be equal to y position of this queen.
	 * @param dx {@code int} x coordinate of examined direction. It can be either -1 or 1 (only diagonal movement is allowed).
	 * @param dy {@code int} y coordinate of examined direction. It can be either -1 or 1 (only diagonal movement is allowed).
	 * @param board {@code Board} game object which contains all figures in game.
	 * @return Returns String: "false" if this queen can't move to choosen coordinates, "ok" if this queen can move to choosen coordinates and "kill" if
	 * this queen can move to choosen coordinates and enemy figure would be captured in that move (enemy's figure {@code killPawn}.
	 */
	private String canMoveInDirection(int startX, int startY, int dx, int dy, Board board){
		
		String result = "false";
		
		startX += dx;
		startY += dy;
		
		do{
			
			if(startX < 0 || startX > 7 || startY < 0 || startY > 7){
				
				break;
			}
			
			if(board.getFields()[startX][startY] != null && !board.getFields()[startX][startY].equals(this) && board.getFields()[startX][startY].getOwner().equals(this.owner)){
				
				break;
			}
			
			if(board.getFields()[startX][startY] != null && !board.getFields()[startX][startY].getOwner().equals(this.owner)){
				
				if(startX + dx < 0 || startX + dx > 7 || startY + dy < 0 || startY + dy > 7){
					
					break;
				}
				
				if(board.getFields()[startX + dx][startY + dy] != null){
					
					break;
				}else{
					
					result = "kill";
				}
			}
			
			startX += dx;
			startY += dy;
			
			if(!result.equals("kill")){
				
				result = "true";
			}
		}while(true);
		
		return result;
	}

	/**
	 * Method which checks if this queen can move this turn(at any direction with any result).
	 * @param board {@code Board} game object which contains all figures in game.
	 * @return Returns String: "false" if this queen can't move to any coordinates, "true" if this queen can move to any coordinates and "kill" if
	 * this queen can move to choosen coordinates and enemy figure would be captured in that move (enemy's figure {@code killPawn}.
	 */
	public String canMove(Board board) {
		
		String result = "false";
		boolean canKill = false;
		boolean canMove = false;
		
		for(int i=-1; i<=1; i++){
			
			for(int j=-1; j<=1; j++){
				
				if(i==0 || j==0){
					
					continue;
				}else{
					
					result = canMoveInDirection(this.x, this.y, i, j, board);
					
					if(result == "kill"){
						
						canKill = true;
					}else if(result == "true"){
						
						canMove = true;
					}
				}
			}
		}
		
		if(canKill == true){
			
			result = "kill";
		}else if(canMove == true){
			
			result = "true";
		}
		
		return result;
	}

	/**
	 * Method responsible for moving this queen to certain x,y coordinates on game board.
	 * @param x {@code int} x coordinate (row) of cell where this queen object will be moved.
	 * @param y {@code int} y coordinate (column) of cell where this queen object will be moved.
	 * @return Returns {@code boolean}. {@code true} if at new coordinates this queen object can perform another move, returns {@code false}
	 * otherwise. It is used to determine if moved queen can perform another enemy figure capture.
	 */
	public boolean move(int x, int y, Board board) {
		
		String canKill;
		
		board.setPawn(this.x, this.y, null);
		
		board.setPawn(x, y, this);
		
		this.x = x;
		this.y = y;
		
		canKill = canMove(board);
		
		return canKill == "kill";
	}

	/**
	 * Method which removes this queen object from game board.
	 * @param board {@code Board} game object which contains all figures in game. 
	 */
	public void killPawn(Board board) {
		
		this.owner.getPawns().remove(0);
		board.setPawn(this.x, this.y, null);
	}
	
	/**
	 * Empty method(because this figure is already queen), forced to existence by interface.
	 */
	public void promoteToQueen(Player player, Board board){
		
		
	}

	/**
	 * @return Returns x(row) position on game board of this queen.
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @param x Sets new x(row) position on game board of this queen.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return Returns y(column) position on game board of this queen.
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y Sets new y(column) position on game board of this queen.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return Returns {@code char} property of this queen (either {@code 'b'} or {@code 'w'}).
	 */
	public char getColour() {
		return colour;
	}

	/**
	 * 
	 * @param colour Sets new {@code char} colour property of this queen (either {@code 'b'} or {@code 'w'}).
	 */
	public void setColour(char colour) {
		this.colour = colour;
	}

	/**
	 * @return Returns {@code Player} object to which this queen belongs.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * 
	 * @param owner Sets new {@code Player} private variable {@code owner} of this queen.
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * @return Returns {@code String name} property of this queen. {@code name} is description of type of this figure.
	 */
	public String getName(){
		
		return name;
	}
}
