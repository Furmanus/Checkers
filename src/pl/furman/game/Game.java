package pl.furman.game;

import javafx.scene.layout.GridPane;
import pl.furman.interfaces.Piece;
import pl.furman.pieces.Pawn;

/**
 * Main class responsible for application logic. Contains three important private fields: instance of {@code Board} class which represents game board,
 * and {@code Player} black and white.
 * @author £ukasz Lach
 *
 */
public class Game {
	
	private Board board;
	private Player white = new Player("white", 'w');
	private Player black = new Player("black", 'b');
	private Player activePlayer = white;
	
	/**
	 * Public constructor. Creates instance on {@code Board} class and sets it on private field, and triggers method responsible for initiating
	 * game (highlighting white player able to move pieces).
	 * @param grid This parameter is a {@code GridPane} from {@code MainScene} class instance. On grid visualisation of game logic is drawn. 
	 */
	public Game(GridPane grid){
		
		this.board = new Board(grid, this);
		setBoard();
		board.highlightPawnsToMove(activePlayer);
	}
	
	/**
	 * Method responsible for making initial setting of two players pieces on board. Function places pieces in first three and last three rows, if
	 * the remainder of column's divison by two is equal to remainder of row's division by two, or sets {@code null} value otherwise.
	 */
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
	
	/**
	 * Game main method. It is triggered by user clicking certain cell on board. It examines whether active player private field {@code sourceCoords}
	 * equal to {@code null} or not. If {@code sourceCoords} are equal to null, it examines whether piece on chosen game board cell can move or not. In former 
	 * case chosen cell is highlighted and player {@code sourceCoords} are set to chosen coords, in latter case method does nothing. If player
	 * {@code sourceCoords} are not equal to null, method examines whether choosen player piece can move to selected board cell. If it does move is
	 * performed and if selected piece didn't captured any enemy piece and isn't able to perform further capture, {@code activePlayer} private
	 * field is changed.
	 * @param player Player which pieces shall be examined.
	 * @param coords Board cell coordinates choosen by user as String {@code "ixj"}, where i and j are choosen cell rows and columns.
	 */
	public void nextStep(Player player, String coords){
		
		String examinedCoords = "";
		boolean killStreak = false;
		String killMade = "false";
		int targetX;
		int targetY;
		Piece examinedPawn;
		Player opponent = activePlayer.getName() == "white" ? black : white;
		
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
		
		if(board.canPlayerMove(opponent) == false){
			
			board.getGui().setVictoryScreen(opponent.getName() == "white" ? black : white);
		}
	}
	
	/**
	 * Method which examines whether any of players have no pieces left and therefore has lost the game.
	 * @return Returs {@code Player} which has no pieces left, or null if every player has at least one piece.
	 */
	private Player validateVictory(){
		
		if(white.getPawns().size() == 0){
			
			return black;
		}else if(black.getPawns().size() == 0){
			
			return white;
		}else{
			
			return null;
		}
	}

	/**
	 * Getter for private {@code Board} board field.
	 * @return Returns {@code Board} board private field.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * 
	 * @return Returns {@code Player} currently active player.
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * 
	 * @param activePlayer Sets {@code Player} currently active player.
	 */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
}
