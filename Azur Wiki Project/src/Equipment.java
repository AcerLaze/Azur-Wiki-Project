
public class Equipment {

	private String efficiency;
	private String equipment_name;
	
	public Equipment(String efficiency, String equipment_name) {
		//Basic Stuff
		this.efficiency = efficiency;
		this.equipment_name = equipment_name;
	}
	
	public String getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	
}
