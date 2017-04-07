package pl.furman.pieces;

import pl.furman.game.Board;
import pl.furman.game.Player;
import pl.furman.interfaces.Piece;

public class Queen implements Piece {

	private int x;
	private int y;
	private char colour;
	private Player owner;
	private final String name = "Queen";
	
	public Queen(int x, int y, Player owner) {
		
		this.x = x;
		this.y = y;
		this.colour = owner.getColour();
		this.owner = owner;
		
		owner.getPawns().add(this);
	}
	
	@Override
	public String validateMovement(int targetX, int targetY, Board board) {
		
		int dx = 0;
		int dy = 0;
		
		if(Math.abs(targetX - this.x) - Math.abs(targetY - this.y) != 0){
			
			return "false";
		}else{
			
			String validateDirection = null;
			exit:
			for(int i=-1; i<=1; i++){
				
				for(int j=-1; j<=1; j++){
					
					if(i==0 || j==0){
						
						continue;
					}else{
						
						validateDirection = canMoveInDirection(this.x, this.y, i, j, board);
						
						if(validateDirection == "kill"){
							
							dx = i;
							dy = j;
							
							break exit;
						}
					}
				}
			}
			
			if(validateDirection != "kill"){
				
				validateDirection = canMoveInDirection(this.x, this.y, this.x - targetX < 0 ? 1 : -1, this.y - targetY < 0 ? 1 : -1, board);
				
				if(validateDirection == "true"){
					
					dx = this.x - targetX < 0 ? 1 : -1;
					dy = this.y - targetY < 0 ? 1 : -1; 
				}
			}
			
			if(validateDirection == "kill"){
				
				return checkDirection(this.x, this.y, dx, dy, targetX, targetY, board, true);
			}else{
				
				return checkDirection(this.x, this.y, dx, dy, targetX, targetY, board, false);
			}
		}
	}
	
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

	@Override
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

	@Override
	public boolean move(int x, int y, Board board) {
		
		String canKill;
		
		board.setPawn(this.x, this.y, null);
		
		board.setPawn(x, y, this);
		
		this.x = x;
		this.y = y;
		
		canKill = canMove(board);
		
		return canKill == "kill";
	}

	@Override
	public void killPawn(Board board) {
		
		this.owner.getPawns().remove(0);
		board.setPawn(this.x, this.y, null);
	}
	
	@Override
	public void promoteToQueen(Player player, Board board){
		
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getColour() {
		return colour;
	}

	public void setColour(char colour) {
		this.colour = colour;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public String getName(){
		
		return name;
	}
}
