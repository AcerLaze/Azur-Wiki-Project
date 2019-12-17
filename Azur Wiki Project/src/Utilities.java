
public class Utilities {

	public static <T> T nvl(T a, T b) {
		
		if(a == null) return b;
		else return a;
		
	}
	
}
