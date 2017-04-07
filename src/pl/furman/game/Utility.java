package pl.furman.game;

public abstract class Utility {

	
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
	
	public static double getDistance(int x1, int y1, int x2, int y2){
		
		double result = (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
		
		return result;
	}
}
