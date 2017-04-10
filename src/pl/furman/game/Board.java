package pl.furman.game;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import pl.furman.interfaces.*;
import pl.furman.ui.CheckersButton;
import pl.furman.ui.GUI;

public class Board {
	
	private Piece[][] fields = new Piece[8][8];
	private GUInterface gui;

	public Board(GridPane grid, Game game){
		
		this.gui = new GUI(grid, game);
	}
	
	public Piece[][] getFields() {
		return fields;
	}

	public void setPawn(int i, int j, Piece piece) {
		
		this.fields[i][j] = piece;
		gui.setPawn(i, j, piece);
	}
	
	public boolean validateSourceCoords(char x, char y, Player player){
		
		if(Character.getNumericValue(x) < 0 || Character.getNumericValue(x) > 7 || Character.getNumericValue(y) < 0 || Character.getNumericValue(y) > 7){
			
			return false;
		}
		
		if(this.fields[Character.getNumericValue(x)][Character.getNumericValue(y)] == null){
			
			return false;
		}
		
		if(fields[Character.getNumericValue(x)][Character.getNumericValue(y)].canMove(this) == "false"){
			
			return false;
		}
		
		if(!canPawnMove(player, "" + x + "x" + y)){
			
			return false;
		}
		
		if(this.fields[Character.getNumericValue(x)][Character.getNumericValue(y)].getColour() == player.getColour()){
			
			return true;
		}else{
			
			return false;
		}
	}
	
	public void highlightPawnsToMove(Player player){
		
		ArrayList<Piece> pawnsAbleToKill = new ArrayList<Piece>();
		ArrayList<Piece> pawnsAbleToMove = new ArrayList<Piece>();
		Piece examinedPawn;
		
		for(int i=0; i<fields.length; i++){
			
			for(int j=0; j<fields[i].length; j++){
				
				examinedPawn = fields[i][j];
				
				if(examinedPawn != null && examinedPawn.getOwner().equals(player) && examinedPawn.canMove(this) == "kill"){
					
					pawnsAbleToKill.add(examinedPawn);
				}else if(examinedPawn != null && examinedPawn.getOwner().equals(player) && examinedPawn.canMove(this) == "true"){
					
					pawnsAbleToMove.add(examinedPawn);
				}else if(examinedPawn == null && ((CheckersButton)gui.getGrid().lookup("#" + i + "x" + j)).isHighlighted()){
					
					gui.toogleButtonById2(i + "x" + j);
				}
			}
		}
		
		player.setPawnsToMove(pawnsAbleToMove);
		player.setPawnsToKill(pawnsAbleToKill);
		
		if(pawnsAbleToKill.size() > 0){
			
			for(int i=0; i<pawnsAbleToKill.size(); i++){
				
				gui.toogleButtonById2(pawnsAbleToKill.get(i).getX() + "x" + pawnsAbleToKill.get(i).getY());
			}
		}else{
			
			for(int i=0; i<pawnsAbleToMove.size(); i++){
				
				gui.toogleButtonById2(pawnsAbleToMove.get(i).getX() + "x" + pawnsAbleToMove.get(i).getY());
			}
		}
	}
	
	private boolean canPawnMove(Player player, String coords){
		
		if(player.getPawnsToKill().size() > 0){
			
			
			for(int i=0; i<player.getPawnsToKill().size(); i++){
				
				if(fields[Character.getNumericValue(coords.charAt(0))][Character.getNumericValue(coords.charAt(2))].equals(player.getPawnsToKill().get(i))){
				
					return true;
				}
			}
		}else if(player.getPawnsToMove().size() > 0){
			
			for(int i=0; i<player.getPawnsToMove().size(); i++){
				
				if(fields[Character.getNumericValue(coords.charAt(0))][Character.getNumericValue(coords.charAt(2))].equals(player.getPawnsToMove().get(i))){
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public GUInterface getGui(){
		
		return this.gui;
	}
}
