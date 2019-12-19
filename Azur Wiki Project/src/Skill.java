import java.awt.Color;

public class Skill {

	private String name;
	private String description;
	private int type;
	
	final static public int ASSAULT = 0;
	final static public int SUPPORT = 1;
	final static public int DEFENSE = 2;
	
	Skill(String name, String description, int type){
		//Basic Stuff
		this.name = name;
		this.description = description;
		this.type = type;
		
	}
	
	public Color getSkillColor() {
		
		Color background = Color.white;
		
		switch(type) {
		
		case ASSAULT:
			
			background = new Color(255, 192, 203);
			
			break;
			
		case SUPPORT:
			
			background = new Color(255, 215, 0);
			
			break;
			
		case DEFENSE:
			
			background = new Color(0, 191, 255);
			
			break;
		
		}
		
		return background;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
