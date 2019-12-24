
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Run {

	private int system_mod = 0;
	private boolean console_mode = true;
	private WindowsManager window;
	
	Run(){
		//For debugging just change system_mod to 1 and don't forget to change it back ( O w O )
		
		if(system_mod == 0) mainRun();
		else if (system_mod == 1) debug();
		
	}
	
	private void mainRun() {
		
		WebAdapter webAdapter;
		ShipContainer container = null;
		try {
			
			webAdapter = new WebAdapter("https://azurlane.koumakan.jp/List_of_Ships", WebAdapter.SHIP_LIST);
			container = webAdapter.requestShipList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Something Went Wrong When Connecting To The Server. Perhaps the server is down or your connection.");

		}
		
		
		if(console_mode) console(container);
		//else run_windows(container);
		
		//
		
		
	}
	
	private void run_windows(Ship ship) {
		
		window = new WindowsManager();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				window.ship_detail_window(ship);
				
			}
		});
		
	}
	
	private void console(ShipContainer container) {
	
		Scanner scan = new Scanner(System.in);
		String text;
		
//		for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
//			
//			if(ship.getName().contentEquals("San Diego")) {
//				
//				ship.getData();
//				WindowsManager.ship_detail_window(ship);
//				
//				break;
//				
//			}
//			
//		}
		
		while(true) {
			
			System.out.println("Insert Command : ");
			text = scan.nextLine();
			
			String[] cmd = text.split(" ");
			
			try {
				switch(cmd[0]) {
			
					case "list":
				
						try {
							
							switch(cmd[1]) {
							
								case "normal":
								
									for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
									
										System.out.println(" > " + ship.getName());
									
									}
									
									break;
								
								case "priority":
									
									for (Ship ship : container.getShip(ShipContainer.PRIORITY_SHIP)) {
										
										System.out.println(" > " + ship.getName());
									
									}
									
									break;
									
								case "collab":
									
									for (Ship ship : container.getShip(ShipContainer.COLLAB_SHIP)) {
									
										System.out.println(" > " + ship.getName());
									
									}
									
									break;
									
								default:
										
									for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
										
										System.out.println(" > " + ship.getName());
									
									}
									
									for (Ship ship : container.getShip(ShipContainer.PRIORITY_SHIP)) {
										
										System.out.println(" > " + ship.getName());
									
									}
									
									for (Ship ship : container.getShip(ShipContainer.COLLAB_SHIP)) {
										
										System.out.println(" > " + ship.getName());
									
									}
									
									break;
									
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							
							e.getMessage();
							
						}
				
						break;
						
					case "get":
						
						String name = "";
						
						try {
							
							try {
								String temp[] = cmd[2].split("_");
								name = "";
								
								for (String string : temp) {
									
									name += " " + string;
									
								}
								
								name = name.substring(1);
								
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							switch (cmd[1]) {
							case "all":
								
								for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
									
									ship.getData();
									System.out.println(" > " + ship.getName());
								
								}
								
								System.out.println("Normal ship download success !");
								
								for (Ship ship : container.getShip(ShipContainer.PRIORITY_SHIP)) {
									
									ship.getData();
									System.out.println(" > " + ship.getName());
								
								}
								
								System.out.println("Priority ship download success !");
								
								for (Ship ship : container.getShip(ShipContainer.COLLAB_SHIP)) {
									
									ship.getData();
									System.out.println(" > " + ship.getName());
								
								}
								
								System.out.println("Collab ship download success !");
								
								break;
								
							case "normal":
								
								for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
								
									if(ship.getName().contentEquals(name)) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction().getName());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
										run_windows(ship);
										
										break;
										
									}
								
								}
								
								break;
							
							case "priority":
								
								String temp[] = cmd[2].split("_");
								
								name = "";
								
								for (String string : temp) {
									
									name += " " + string;
									
								}
								
								name = name.substring(1);
								
								for (Ship ship : container.getShip(ShipContainer.PRIORITY_SHIP)) {
									
									if(ship.getName().contentEquals(name)) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction().getName());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
										run_windows(ship);
										
										break;
										
									}
									
								}
								
								break;
								
							case "collab":
								
								for (Ship ship : container.getShip(ShipContainer.COLLAB_SHIP)) {
								
									if(ship.getName().contentEquals(name)) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction().getName());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
										run_windows(ship);
										
										break;
										
									}
								
								}
								
								break;
							
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							
							System.out.println(" > " + e.getMessage());
						}
						
						break;
						
					case "exit" :
						scan.close();
						return;
			
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("> " + e.getMessage());
				
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Run();
		
	}
	
	private void debug() {
		
		WebAdapter webAdapter;
		
		try {
			
			webAdapter = new WebAdapter("https://azurlane.koumakan.jp/List_of_Ships", WebAdapter.SHIP_LIST);
			ShipContainer ship = webAdapter.requestShipList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
//
//		for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) {
//			
//			//if(ship.getName().contentEquals("San Diego")) { ship.getData(); run_windows(ship);}
//			if(ship.getName().contentEquals("Jean Bart")) { ship.getData(); run_windows(ship);}
//			//if(ship.getName().contentEquals("Enterprise")) { ship.getData(); run_windows(ship);}
//			if(ship.getName().contentEquals("Ayanami")) { ship.getData(); run_windows(ship);}
//			if(ship.getName().contentEquals("Bogue")) { ship.getData(); run_windows(ship);}
//			if(ship.getName().contentEquals("Long Island")) { ship.getData(); run_windows(ship);}
//			
//		}
		
		
	}

}
