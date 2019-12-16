
public class Skill {

	private String Name;
	private String Description;
	private int Type;
	
	final static public int RED = 0;
	final static public int YELLOW = 0;
	final static public int BLUE = 0;
	
	Skill(String Name, String Description, int Type){
		//Basic Stuff
		this.Name = Name;
		this.Description = Description;
		this.Type = Type;
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}
	
	
}
