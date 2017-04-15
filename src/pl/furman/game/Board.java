package pl.furman.game;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import pl.furman.interfaces.*;
import pl.furman.ui.CheckersButton;
import pl.furman.ui.GUI;

/**
 * Class representing game board. Contains two important private field. Two dimensional array of {@code Piece} class objects and {@codeGUInterface} 
 * class object used to draw and manipulate visible to used {@code GridPane} from {@code MainScene}.
 * @author £ukasz Lach
 *
 */
public class Board {
	
	private Piece[][] fields = new Piece[8][8];
	private GUInterface gui;

	/**
	 * Public constructor for {@code Board} class object.
	 * @param grid This parameter is a {@code GridPane} from {@code MainScene} class object. On this object information visible to user will be drawn.
	 * @param game This parameter is an instance of {@code Game} class, which contains logic for this application.
	 */
	public Board(GridPane grid, Game game){
		
		this.gui = new GUI(grid, game);
	}
	
	/**
	 * Getter for {@code Piece[][]} array which contains game pieces belonging to one of two players.
	 * @return Returns {@code Piece[][]} two dimensional array. Elements of this array can either contain a {@code Piece} instance belonging to one
	 * of two players or can be {@code null} meaning that certain field is empty.
	 */
	public Piece[][] getFields() {
		return fields;
	}

	/**
	 * Method which sets certain {@code Piece} in [x, y] coordinates on game board and draws appropriate information on game screen.  
	 * @param i This parameter equals to row on game board where piece should be placed on.
	 * @param j This parameter equals to column on game board where piece should be placed on.
	 * @param piece {@code Piece} instance which should be placed on game board.
	 * @return Returns nothing.
	 */
	public void setPawn(int i, int j, Piece piece) {
		
		this.fields[i][j] = piece;
		gui.setPawn(i, j, piece);
	}
	
	/**
	 * Method responsible for validating whether board cell clicked by player contains piece belonging to player and whether that piece is able to move.
	 * @param x X(row) coordinate of chosen board cell.
	 * @param y Y(column) coordinate of chosen board cell.
	 * @param player Player which piece method is examining.
	 * @return Returns {@code true} if chosen board cell contains piece which belongs to player and can move. Returns {@code false} otherwise.
	 */
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
	
	/**
	 * Function responsible for calculating and highlighting on board all pieces of certain player which are able to move. If any of examined
	 * pieces is able to capture enemy piece, only pieces able to capture will be highlighted. Function doesn't return anything, instead it modifies
	 * player private fields {@code pawnsAbleToKill} and {@code pawnsAbleToMove} which both are type {@code ArrayList<Piece>}. Function iterates
	 * through whole game board and for every piece owned by player, it triggers its {@code canMove} method. During iteration on game board, method
	 * also unhighlights any board cells highlighted on previous turn. In last step method by {@code GUInterface} highlights all pieces able to
	 * capture enemy pieces and if there are none, highlights all pieces able to move.
	 * @param player Player which pieces should be examined and highlighted.
	 * @return Returns nothing.
	 */
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
	/**
	 * Determines whether board cell at given coordinates has piece which belongs to player given as parameter and whether that piece can move
	 * (or capture enemy piece).
	 * @param player Player to which piece should belong.
	 * @param coords Coordinates given in form of String {@code "ixj"}, where i and j are examined cell row and column.
	 * @return Returns {@code true} if cells of given coordinates has piece able to move(or capture), returns {@code false} otherwise.
	 */
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
	
	/**
	 * Getter for private {@code GUInterface} field.
	 * @return Returns {@code GUInterface} object.
	 */
	public GUInterface getGui(){
		
		return this.gui;
	}
}
