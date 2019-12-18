
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Run {

	private int system_mod = 0;
	private boolean console_mode = false;
	
	Run(){
		//For debugging just change system_mod to 1 and don't forget to change it back ( O w O )
		if(system_mod == 0) mainRun();
		else if (system_mod == 1) debug();
		
	}
	
	private void mainRun() {
		
		WebAdapter webAdapter = new WebAdapter("https://azurlane.koumakan.jp/List_of_Ships", WebAdapter.SHIP_LIST);
		ShipContainer container = webAdapter.requestShipList();
		
		if(console_mode) console(container);
		else run_windows(container);
		
		
	}
	
	private void run_windows(ShipContainer container) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Ship x = null;
				
				for (Ship ship : container.getShip(ShipContainer.NORMAL_SHIP)) if(ship.getName().contentEquals("San Diego")) x = ship;
				x.getData();
				ship_details(x);
				
			}
		});
		
	}
	
	private void ship_details(Ship ship) {
		
		if(ship == null) return;

		JFrame frame = new JFrame();
		
		//frame.setPreferredSize(new Dimension(1500, 860));
		frame.setResizable(false);
		
		frame.add(WindowsManager.ship_detail_window(ship), BorderLayout.WEST);
	
		try {
			Image background = ImageIO.read(new File("Source/bg.png"));
			
			JLabel bakcground_label = new JLabel(new ImageIcon(background.getScaledInstance(1511, 850, Image.SCALE_SMOOTH)));

			bakcground_label.setLayout(null);
			bakcground_label.setBounds(0, 0, background.getWidth(null), background.getHeight(null));
			bakcground_label.setPreferredSize(new Dimension(1000, 850));
			
			frame.add(bakcground_label);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	private void console(ShipContainer container) {
	
		Scanner scan = new Scanner(System.in);
		String text;
		
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
						
						try {
							
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
								
									if(ship.getName().contentEquals(cmd[2])) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
									}
								
								}
								
								break;
							
							case "priority":
								
								for (Ship ship : container.getShip(ShipContainer.PRIORITY_SHIP)) {
									
									if(ship.getName().contentEquals(cmd[2])) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
									}
									
								}
								
								break;
								
							case "collab":
								
								for (Ship ship : container.getShip(ShipContainer.COLLAB_SHIP)) {
								
									if(ship.getName().contentEquals(cmd[2])) {
										
										if(!ship.isDownloaded()) ship.getData();
										
										System.out.println("Name      : " + ship.getName());
										System.out.println("Faction   : " + ship.getFaction());
										System.out.println("Health    : " + ship.getHealth());
										System.out.println("Firepower : " + ship.getFirepower());
										
									}
								
								}
								
								break;
							
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							
							System.out.println(e.getMessage());
						}
						
						break;
						
					case "exit" :
						scan.close();
						break;
			
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
		
		//WebAdapter webAdapter = new WebAdapter("https://azurlane.koumakan.jp/List_of_Ships", WebAdapter.SHIP_LIST);
		//ShipContainer container = webAdapter.requestShipList();

		
	}

}
