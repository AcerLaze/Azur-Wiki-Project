import java.util.ArrayList;
import java.util.List;

public class ShipContainer{

	final public static int NORMAL_SHIP = 0;
	final public static int PRIORITY_SHIP = 1;
	final public static int COLLAB_SHIP = 2;
	
	private List<Ship> NormalShip;
	private List<Ship> PriorityShip;
	private List<Ship> CollabShip;
	
	private Ship[] avg_ship_holder;
	private Integer[] total_ship;
	
	public static final int DD = 0;
	public static final int CA = 1;
	public static final int CL = 2;
	public static final int BB = 3;
	public static final int BC = 4;
	public static final int BM = 5;
	public static final int CV = 6;
	public static final int CVL = 7;
	public static final int AR = 8;
	public static final int SS = 9;
	public static final int AM = 10;
	
	public ShipContainer() {
		// TODO Auto-generated constructor stub
		
		NormalShip = new ArrayList<Ship>();
		PriorityShip = new ArrayList<Ship>();
		CollabShip = new ArrayList<Ship>();
		avg_ship_holder = new Ship[11];
		total_ship = new Integer[11];
		
		for(int i = 0; i < 11; i++) {
			
			total_ship[i] = 0;
			avg_ship_holder[i] = new Ship();
			
		}
		
	}
	
	public void addShip(int type, Ship ship) {	
		//Adding a ship based on category
		if(type == NORMAL_SHIP) NormalShip.add(ship);
		if(type == PRIORITY_SHIP) PriorityShip.add(ship);
		if(type == COLLAB_SHIP) CollabShip.add(ship);
		
	}
	
	public List<Ship> getShip(int type){
		//A method to get the ship list depends on what the request type
		if(type == NORMAL_SHIP) return NormalShip;
		else if(type == PRIORITY_SHIP) return PriorityShip;
		else if(type == COLLAB_SHIP) return CollabShip;
		else return null;
		
	}
	
	public int findType(Ship ship) {
		
		int type = -1;
		
		switch (ship.getType()) {
		
		case "Destroyer":
			
			type = DD;
			
			break;
			
		case "Heavy Cruiser":
			
			type = CA;
			
			break;
			
		case "Light Cruiser":
			
			type = CL;
			
			break;
			
		case "Battleship":
			
			type = BB;
			
			break;
			
		case "Battlecruiser":
			
			type = BC;
			
			break;
			
		case "Monitor":
			
			type = BM;
			
			break;
			
		case "Aircraft Carrier":
			
			type = CV;
			
			break;
			
		case "Light Aircraft Carrier":
			
			type = CVL;
		
			break;
			
		case "Repair Ship":
			
			type = AR;
			
			break;
			
		case "Submarine":
			
			type = SS;
			
			break;
			
		case "Submarine Carrier":
			
			type = AM;
			
			break;
		
		}
		
		return type;
		
	}
	
	public void calculateAvg() {
		//Function to calculate the average ship status that will be used to dictate how good a ship status (note priority ship don't need to be included in calculation because it's a special ship a.k.a all status above average except maybe for luck but everyone knows luck doesnt mean anything)
		//Another note if the average calculated status is 0 that means currently there is no ship data. Fetch it first from the web
		try {
			
			System.out.println("Starting Calculate Average Stats !");
			
			for (Ship ship : NormalShip) {
				
				int type = findType(ship);
				
				if(type != -1) {
				
					if(ship.getFirepower() != 0)				avg_ship_holder[type].setFirepower(avg_ship_holder[type].getFirepower() + Utilities.nvl(ship.getFirepower(), 0));					
					if(ship.getHealth() != 0) 					avg_ship_holder[type].setHealth(avg_ship_holder[type].getHealth() + Utilities.nvl(ship.getHealth(), 0));					
					if(ship.getAntiair() != 0) 					avg_ship_holder[type].setAntiair(avg_ship_holder[type].getAntiair() + Utilities.nvl(ship.getAntiair(), 0));					
					if(ship.getEvasion() != 0) 					avg_ship_holder[type].setEvasion(avg_ship_holder[type].getEvasion() + Utilities.nvl(ship.getEvasion(), 0));					
					if(ship.getAviation() != 0) 				avg_ship_holder[type].setAviation(avg_ship_holder[type].getAviation() + Utilities.nvl(ship.getAviation(), 0));					
					if(ship.getTorpedo() != 0) 					avg_ship_holder[type].setTorpedo(avg_ship_holder[type].getTorpedo() + Utilities.nvl(ship.getTorpedo(), 0));					
					if(ship.getReload() != 0) 					avg_ship_holder[type].setReload(avg_ship_holder[type].getReload() + Utilities.nvl(ship.getReload(), 0));					
					if(ship.getLuck() != 0) 					avg_ship_holder[type].setLuck(avg_ship_holder[type].getLuck() + Utilities.nvl(ship.getLuck(), 0));					
					if(ship.getAccuracy() != 0) 				avg_ship_holder[type].setAccuracy(avg_ship_holder[type].getAccuracy() + Utilities.nvl(ship.getAccuracy(), 0));					
					if(ship.getSpeed() != 0) 					avg_ship_holder[type].setSpeed(avg_ship_holder[type].getSpeed() + Utilities.nvl(ship.getSpeed(), 0));					
					if(ship.getAnti_submarine_warfare() != 0) 	avg_ship_holder[type].setAnti_submarine_warfare(avg_ship_holder[type].getAnti_submarine_warfare() + Utilities.nvl(ship.getAnti_submarine_warfare(), 0));
					
					total_ship[type]++;
					
				} else System.out.println(ship.getName() + " type error");
				
			}
			
			for (Ship ship : CollabShip) {
				
				int type = findType(ship);
				
				if(type != -1) {
					
					if(ship.getFirepower() != 0)				avg_ship_holder[type].setFirepower(avg_ship_holder[type].getFirepower() + Utilities.nvl(ship.getFirepower(), 0));					
					if(ship.getHealth() != 0) 					avg_ship_holder[type].setHealth(avg_ship_holder[type].getHealth() + Utilities.nvl(ship.getHealth(), 0));					
					if(ship.getAntiair() != 0) 					avg_ship_holder[type].setAntiair(avg_ship_holder[type].getAntiair() + Utilities.nvl(ship.getAntiair(), 0));					
					if(ship.getEvasion() != 0) 					avg_ship_holder[type].setEvasion(avg_ship_holder[type].getEvasion() + Utilities.nvl(ship.getEvasion(), 0));					
					if(ship.getAviation() != 0) 				avg_ship_holder[type].setAviation(avg_ship_holder[type].getAviation() + Utilities.nvl(ship.getAviation(), 0));					
					if(ship.getTorpedo() != 0) 					avg_ship_holder[type].setTorpedo(avg_ship_holder[type].getTorpedo() + Utilities.nvl(ship.getTorpedo(), 0));					
					if(ship.getReload() != 0) 					avg_ship_holder[type].setReload(avg_ship_holder[type].getReload() + Utilities.nvl(ship.getReload(), 0));					
					if(ship.getLuck() != 0) 					avg_ship_holder[type].setLuck(avg_ship_holder[type].getLuck() + Utilities.nvl(ship.getLuck(), 0));					
					if(ship.getAccuracy() != 0) 				avg_ship_holder[type].setAccuracy(avg_ship_holder[type].getAccuracy() + Utilities.nvl(ship.getAccuracy(), 0));					
					if(ship.getSpeed() != 0) 					avg_ship_holder[type].setSpeed(avg_ship_holder[type].getSpeed() + Utilities.nvl(ship.getSpeed(), 0));					
					if(ship.getAnti_submarine_warfare() != 0) 	avg_ship_holder[type].setAnti_submarine_warfare(avg_ship_holder[type].getAnti_submarine_warfare() + Utilities.nvl(ship.getAnti_submarine_warfare(), 0));
					
					total_ship[type]++;
					
				} else System.out.println(ship.getName() + " type error");

				
			}
			
			for(int i = 0; i < avg_ship_holder.length; i++) {
				
				try {
					
					avg_ship_holder[i].setFirepower(avg_ship_holder[i].getFirepower() / total_ship[i]);                                                          
					avg_ship_holder[i].setHealth(avg_ship_holder[i].getHealth() / total_ship[i]);                                                					                                                                                                                                      
					avg_ship_holder[i].setAntiair(avg_ship_holder[i].getAntiair() / total_ship[i]);                                             					                                                                                                                                      
					avg_ship_holder[i].setEvasion(avg_ship_holder[i].getEvasion() / total_ship[i]);                                             					                                                                                                                                      
					avg_ship_holder[i].setAviation(avg_ship_holder[i].getAviation() / total_ship[i]);                                          					                                                                                                                                      
					avg_ship_holder[i].setTorpedo(avg_ship_holder[i].getTorpedo() / total_ship[i]);                                             					                                                                                                                                      
					avg_ship_holder[i].setReload(avg_ship_holder[i].getReload() / total_ship[i]);                                                					                                                                                                                                      
					avg_ship_holder[i].setLuck(avg_ship_holder[i].getLuck() / total_ship[i]);                                                      					                                                                                                                                      
					avg_ship_holder[i].setAccuracy(avg_ship_holder[i].getAccuracy() / total_ship[i]);                                          					                                                                                                                                      
					avg_ship_holder[i].setSpeed(avg_ship_holder[i].getSpeed() / total_ship[i]);                                                   					                                                                                                                                   
					avg_ship_holder[i].setAnti_submarine_warfare(avg_ship_holder[i].getAnti_submarine_warfare() / total_ship[i]);
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					
				}
			
			}
			
			System.out.println("Calculating The Average Stats Success !");
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println("Something Wrong When Calculating the Stat !");
			
		}
	
	}

	public void getAllShipDetails(int type) {
		//Get All Ship Details (If you want to download it all)
		if(type == NORMAL_SHIP) for (Ship ship : NormalShip) {
			
			ship.getData();
			calculateAvg();
			
		} else if(type == PRIORITY_SHIP) for (Ship ship : PriorityShip) {
			
			ship.getData();
			
		} else if(type == COLLAB_SHIP) for (Ship ship : CollabShip) {
			
			ship.getData();
			calculateAvg();
			
		}
		
	}
	
	//Just a getter and setter. Ignore it
	public Ship[] getAvg_ship_holder() {
		return avg_ship_holder;
	}

	public void setAvg_ship_holder(Ship[] avg_ship_holder) {
		this.avg_ship_holder = avg_ship_holder;
	}

}
