package pl.furman.game;

/**
 * Abstract class which contains utility static methods.
 * @author £ukasz Lach
 *
 */
public abstract class Utility {

	/**
	 * Examines whether coordinates entered as {@code String} from player are valid. Currently not used, remnant of version of game version
	 * without graphical interface.
	 * @param text Text entered by player.
	 * @return Returns {@code true} if entered text is valid {@code String} in format "ixj" where i and j are coordinates. Returns {@code false} otherwise.
	 */
	public static boolean validateString(String text){
		
		if(text.length() != 3){
			
			return false;
		}
		
		if(text.substring(0, 1).matches("[0-9]") && text.charAt(1) == 'x' && text.substring(2, 3).matches("[0-9]")){
			
			return true;
		}else{
			
			return false;
		}
	}
	
	/**
	 * Method which returns distance between two points.
	 * @param x1 x coordinate of first point.
	 * @param y1 y coordinate of first point.
	 * @param x2 x coordinate of second point.
	 * @param y2 y coordinate of second point.
	 * @return Returns distance between two points.
	 */
	public static double getDistance(int x1, int y1, int x2, int y2){
		
		double result = (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
		
		return result;
	}
}
