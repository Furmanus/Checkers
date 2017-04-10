package pl.furman.game;

import javafx.scene.layout.GridPane;
import pl.furman.interfaces.Piece;
import pl.furman.pieces.Pawn;

public class Game {
	
	private Board board;
	private Player white = new Player("white", 'w');
	private Player black = new Player("black", 'b');
	private Player activePlayer = white;
	
	public Game(GridPane grid){
		
		this.board = new Board(grid, this);
		setBoard();
		board.highlightPawnsToMove(activePlayer);
	}
	
	private void setBoard(){
		
		for(int i=0; i<8; i++){
			
			for(int j=0; j<8; j++){
				
				if(j%2 == i%2 && (i!=3 && i!=4)){
					
					if(i==0 || i==1 || i==2){
						
						this.board.setPawn(i, j, new Pawn(i, j, white));
					}else if(i==5 || i==6 || i==7){
						
						this.board.setPawn(i, j, new Pawn(i, j, black));
					}else{
						
						this.board.setPawn(i, j, null);
					}
				}
			}
		}
	}
	
	public void nextStep(Player player, String coords){
		
		String examinedCoords = "";
		boolean killStreak = false;
		String killMade = "false";
		int targetX;
		int targetY;
		Piece examinedPawn;
		
		if(player.getSourceCoords() == null){
			
			char x = coords.charAt(0);
			char y = coords.charAt(2);
			
			if(board.validateSourceCoords(x, y, player)){
				
				player.setSourceCoords(coords);
				board.getGui().clearButtons();
				board.getGui().toogleButtonById(coords);
			}
		}else{
			
			examinedPawn = this.board.getFields()[Character.getNumericValue(player.getSourceCoords().charAt(0))][Character.getNumericValue(player.getSourceCoords().charAt(2))];
			examinedCoords = coords;
			
			targetX = Character.getNumericValue(examinedCoords.charAt(0));
			targetY = Character.getNumericValue(examinedCoords.charAt(2));
			
			if(player.getSourceCoords().equals(examinedCoords)){
				
				player.setSourceCoords(null);
				board.getGui().clearButtons();
				board.highlightPawnsToMove(this.activePlayer);
				
				return;
			}
			
			killMade = examinedPawn.validateMovement(targetX, targetY, this.board);
			
			if(killMade != "false"){
				
				killStreak = examinedPawn.move(targetX, targetY, board);
				board.getGui().clearButtons();
				
				if(killMade == "kill" && killStreak == true){
					
					player.setSourceCoords(coords);
					player.setTargetCoords(null);
					
					board.getGui().toogleButtonById(player.getSourceCoords());
				}else{
					
					if((examinedPawn.getOwner().getName() == "white" && examinedPawn.getX() == 7) || (examinedPawn.getOwner().getName() == "black" && examinedPawn.getX() == 0)){
						
						if(examinedPawn.getName() == "Pawn"){
							
							examinedPawn.promoteToQueen(this.activePlayer, this.board);
						}
					}
					
					player.setSourceCoords(null);
					player.setTargetCoords(null);
					
					this.activePlayer = player.getName() == "white" ? this.black : this.white;
					board.highlightPawnsToMove(this.activePlayer);
				}
			}
		}
		
		Player winner = validateVictory();
		
		if(winner != null){
			
			board.getGui().setVictoryScreen(winner);
		}
	}
	
	private Player validateVictory(){
		
		if(white.getPawns().size() == 0){
			
			return black;
		}else if(black.getPawns().size() == 0){
			
			return white;
		}else{
			
			return null;
		}
	}

	public Board getBoard() {
		return board;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
}
