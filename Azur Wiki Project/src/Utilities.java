import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utilities {

	public static <T> T nvl(T a, T b) {
		
		if(a == null) return b;
		else return a;
		
	}
	
	public static BufferedImage resize(URL url, float size) {
		
		try {
			
			BufferedImage img = ImageIO.read(url);
			int width = (int)(img.getWidth() * size);
			int height = (int)(img.getHeight() * size);
			
			BufferedImage resized_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized_img.createGraphics();
			
			g.drawImage(img, 0, 0, width, height, null);
			g.dispose();
			
			return resized_img;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}
	
}
