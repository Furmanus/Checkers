package pl.furman.pieces;

import java.util.ArrayList;

import pl.furman.game.Board;
import pl.furman.game.Player;
import pl.furman.game.Utility;
import pl.furman.interfaces.Piece;

public class Pawn implements Piece{

	private int x;
	private int y;
	private char colour;
	private Player owner;
	private final String name = "Pawn";
	
	public Pawn(int x, int y, Player owner) {
		
		this.x = x;
		this.y = y;
		this.colour = owner.getColour();
		this.owner = owner;
		
		owner.getPawns().add(this);
	}
	
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
	
	//TODO
	public String[] getPossibleMoves(Board board){
		
		ArrayList<String> result = new ArrayList<String>();
			
		return null;
	}
	
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
	
	public boolean move(int x, int y, Board board){
		
		ArrayList<Piece> hostileVicinity;
		
		board.setPawn(this.x, this.y, null);
		
		board.setPawn(x, y, this);
		
		this.x = x;
		this.y = y;
		
		hostileVicinity = this.validateVicinity(board);
		
		return hostileVicinity.size() > 0;
	}
	
	public void killPawn(Board board){
		
		this.owner.getPawns().remove(0);
		board.setPawn(this.x, this.y, null);
	}
	
	private boolean validateAddedCoordinates(int x, int y){
		
		if(this.x + x < 0 || this.x + x > 7 || this.y + y < 0 || this.y + y > 7){
			
			return false;
		}else{
			
			return true;
		}
	}
	
	public void promoteToQueen(Player player, Board board){
		
		Piece newQueen = new Queen(this.x, this.y, this.owner);
		killPawn(board);
		board.setPawn(this.x, this.y, newQueen);
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
