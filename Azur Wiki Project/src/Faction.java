import java.awt.Color;
import java.awt.Image;

public class Faction {

	private String name;
	private Image img;
	
	Faction(String name, Image img){
		
		this.name = name;
		this.img = img;
		
	}
	
	public Color getFactionColor() {
		
		Color background;
		
		switch (name) {
		case "Eagle Union":
			
			background = new Color(176, 224, 230);
			
			break;
			
		case "Eastern Radiance":
			
			background = new Color(221, 160, 221);
			
			break;
			
		case "Iris Libre":
			
			background = new Color(255, 215, 0);
			
			break;
			
		case "Ironblood":
			
			background = new Color(255, 192, 203);
			
			break;
			
		case "Royal Navy":
			
			background = new Color(131, 170, 240);
			
			break;
			
		case "Sakura Empire":
			
			background = new Color(255, 240, 245);
			
			break;
			
		case "Sardegna Empire":
			
			background = new Color(110, 190, 147);
			
			break;
			
		case "Vichya Dominion":
			
			background = new Color(215, 124, 124);
			
			break;
		default:
			background = new Color(220, 220, 220);	

		}
		
		return background;
		
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public Image getImg() {
		return img;
	}
	
}
