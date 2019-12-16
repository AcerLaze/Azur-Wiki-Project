
public class Run {

	private int system_mod = 0;
	
	Run(){
		//For debugging just change system_mod to 1 and don't forget to change it back ( O w O )
		if(system_mod == 0) mainRun();
		else if (system_mod == 1) debug();
		
	}
	
	private void mainRun() {
		
		WebAdapter webAdapter = new WebAdapter("https://azurlane.koumakan.jp/List_of_Ships", WebAdapter.SHIP_LIST);
		ShipContainer container = webAdapter.requestShipList();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Run();
		
	}
	
	private void debug() {
		
		WebAdapter web_adapter = new WebAdapter("https://azurlane.koumakan.jp/Vestal", WebAdapter.SHIP_DETAIL);
		
		web_adapter.requestShipEquipment();
		
	}

}
