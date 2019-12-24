
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebAdapter {

	private Document Ship_Page;
	private int mod;
	
	private Elements Ship_List;
	private Elements Ship_Details;
	private Elements Ship_Image;
	private Elements Faction_List;
	
	final static public int SHIP_LIST = 0;
	final static public int SHIP_DETAIL = 1;
	final static public int FACTION = 2;
	
	
	WebAdapter(String url, int type) throws Exception{
		
		mod = 0;
		
		Connection connection = Jsoup.connect(url);
		
		connection.timeout(12000);
			
		Ship_Page = Jsoup.connect(url).timeout(12000).get();
			
		if(type == SHIP_LIST) Ship_List = Ship_Page.select("table.wikitable.sortable.jquery-tablesorter");
		else if(type == SHIP_DETAIL) {
				
			Ship_Details = Ship_Page.select("table.wikitable");
			Ship_Image = Ship_Page.select("div.adaptiveratioimg");
				
		} else if (type == FACTION) {
			
			Faction_List = Ship_Page.select("table.wikitable");
			
		}
		
	}
	
	public ShipContainer requestShipList(){
		//Fetching all available ship from wiki
		
		if(Ship_Page == null) {
			
			System.out.println("Can't connect to the server !");
			return null;
			
		}
		
		ShipContainer shipList = new ShipContainer();
		
		WebAdapter webAdapter;
		List<Faction> factions;
		
		try {
			
			webAdapter = new WebAdapter("https://azurlane.koumakan.jp/Nations", WebAdapter.FACTION);
			factions = webAdapter.getFactions();
			
			shipList.setFactionList(factions);
			
			Element Normal_Ship = Ship_List.get(0);
			Element Priority_Ship = Ship_List.get(1);
			Element Collab_Ship = Ship_List.get(2);
			
			for (Element element : Normal_Ship.select("tr")) {
				
				Elements ShipData = element.select("td");
				
				if(ShipData.size() < 1) continue;
				
				Elements links = ShipData.select("a");
				String link = links.get(1).attr("href");
				
				Faction faction = null;
				
				for (Faction e : factions) {
					
					if(e.getName().contentEquals(ShipData.get(4).text())) {
					
						faction = e;
						
						break;
						
					}
					
				}
				
				Ship ship = new Ship(ShipData.get(0).text(), 
						    ShipData.get(1).text(),
						    ShipData.get(2).text(), 
						    ShipData.get(3).text(),
						    faction,
							"https://azurlane.koumakan.jp" + link, ShipContainer.NORMAL_SHIP);
				
				shipList.addShip(ShipContainer.NORMAL_SHIP, ship);
				//System.out.println(ShipData.get(1).text() + " data downloaded!");
				
			}
			
			System.out.println(" > Normal Ship Download Success");
			
			for (Element element : Priority_Ship.select("tr")) {
				
				Elements ShipData = element.select("td");
				
				if(ShipData.size() < 1) continue;
				
				Elements links = ShipData.select("a");
				String link = links.get(1).attr("href");
				
				Faction faction = null;
				
				for (Faction e : factions) {
					
					if(e.getName().contentEquals(ShipData.get(4).text())) {
						
						faction = e;
						
						break;
						
					}
					
				}
				
				shipList.addShip(ShipContainer.PRIORITY_SHIP, new Ship(ShipData.get(0).text(), 
									  						  ShipData.get(1).text(),
									  						  ShipData.get(2).text(),
									  						  ShipData.get(3).text(),
									  						  faction,
									  						  "https://azurlane.koumakan.jp" + link, ShipContainer.PRIORITY_SHIP));
				//System.out.println(ShipData.get(1).text() + " data downloaded!");
			}
			
			System.out.println(" > Priority Ship Download Success");
			
			for (Element element : Collab_Ship.select("tr")) {
				
				Elements ShipData = element.select("td");
				
				if(ShipData.size() < 1) continue;
				
				Elements links = ShipData.select("a");
				String link = links.get(1).attr("href");
				
				Faction faction = null;
				
				for (Faction e : factions) {
					
					if(e.getName().contentEquals(ShipData.get(4).text())) {
						
						faction = e;
						
						break;
						
					}
					
				}
				
				shipList.addShip(ShipContainer.COLLAB_SHIP, new Ship(ShipData.get(0).text(), 
									  			    		ShipData.get(1).text(), 
									  			    		ShipData.get(2).text(), 
									  			    		ShipData.get(3).text(),
									  					    faction,
									  			    		"https://azurlane.koumakan.jp" + link, ShipContainer.COLLAB_SHIP));
				//System.out.println(ShipData.get(1).text() + " data downloaded!");
			}
			
			System.out.println(" > Collab Ship Download Success");
			
			shipList.calculateAvg();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return shipList;
		
	}
	
	
	public List<Skill> requestShipSkill() {
		
		//Returning all available Skill from wiki in form of List
		List<Skill> skills = new ArrayList<>();
			
		Element skill_table = Ship_Details.get(9 + mod);
			
		for (Element element : skill_table.select("tr")) {
				
			Elements row = element.select("td");
				
			if(row.size() != 3) continue;
				
			String name = row.get(1).text();
			String desc = row.get(2).text();
				
			if(name.isEmpty()) break;
			else name = name.substring(0, name.indexOf("CN:"));
				
			String style = row.get(1).attr("style");
			int type;
				
			if(style.contains("Gold")) type = Skill.SUPPORT;
			else if(style.contains("Pink")) type = Skill.ASSAULT;
			else if(style.contains("Blue")) type = Skill.DEFENSE;
			else type = -1;
				
			skills.add(new Skill(name, desc, type));
				
				
		}
			
		return skills;
		
	}
	
	
	public List<Equipment> requestShipEquipment() {
		//Returning available equipment from wiki in form of List
		List<Equipment> equipments = new ArrayList<>();
		
		Element equipment_table = Ship_Details.get(6 + mod);
				
		for (Element element : equipment_table.select("tr")) {
				
			Elements row = element.select("td");
				
			if(row.size() < 3) continue;
				
			String equipment_efficiency = row.get(1).text();
			String equipment_name = row.get(2).text();
				
			if(!equipment_efficiency.contentEquals("None")) equipment_efficiency = equipment_efficiency.split(" ")[2];
			
			equipments.add(new Equipment(equipment_efficiency, equipment_name));
			
		}

		return equipments;
		
	}
	
	
	public boolean requestIsShipRetrofitable() {
		//Checking if a ship can be retrofit by checking with the website
		try {
			
			if(Ship_Details.size() == 14) {
				
				mod = 2;
				
				return true;
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error retrofit" + e.getMessage());
			
		}
		
		return false;
		
	}
	
	
	public void getShipStat(Ship ship) {
		//Not the best method to extract ship status but it's more convenient
		
		Element table;
		
		if(ship.isRetrofitable()) {
			
			table = Ship_Details.get(4);
			
		} else table = Ship_Details.get(3);
		
		Elements row = table.select("tr");
		
		ship.setHealth(Integer.parseInt(row.get(0).select("td").get(0).text()));
		ship.setArmour_type(row.get(0).select("td").get(1).text());
		ship.setReload(Integer.parseInt(row.get(0).select("td").get(2).text()));
		ship.setLuck(Integer.parseInt(row.get(0).select("td").get(3).text()));
		
		ship.setFirepower(Integer.parseInt(row.get(1).select("td").get(0).text()));
		ship.setTorpedo(Integer.parseInt(row.get(1).select("td").get(1).text()));
		ship.setEvasion(Integer.parseInt(row.get(1).select("td").get(2).text()));
		ship.setSpeed(Integer.parseInt(row.get(1).select("td").get(3).text()));
		
		ship.setAnti_air(Integer.parseInt(row.get(2).select("td").get(0).text()));
		ship.setAviation(Integer.parseInt(row.get(2).select("td").get(1).text()));
		ship.setOil_consumtion(Integer.parseInt(row.get(2).select("td").get(2).text()));
		ship.setAccuracy(Integer.parseInt(row.get(2).select("td").get(3).text()));
		
		ship.setAnti_submarine_warfare(Integer.parseInt(row.get(3).select("td").get(0).text()));
		
		if(ship.isRetrofitable()) {
			
			ship.setPost_retrofit_health(Integer.parseInt(row.get(0).select("td").get(0).text()));
			ship.setPost_retrofit_reload(Integer.parseInt(row.get(0).select("td").get(2).text()));
			ship.setPost_retrofit_luck(Integer.parseInt(row.get(0).select("td").get(3).text()));
			
			ship.setPost_retrofit_firepower(Integer.parseInt(row.get(1).select("td").get(0).text()));
			ship.setPost_retrofit_torpedo(Integer.parseInt(row.get(1).select("td").get(1).text()));
			ship.setPost_retrofit_evasion(Integer.parseInt(row.get(1).select("td").get(2).text()));
			ship.setPost_retrofit_speed(Integer.parseInt(row.get(1).select("td").get(3).text()));
			
			ship.setPost_retrofit_anti_air(Integer.parseInt(row.get(2).select("td").get(0).text()));
			ship.setPost_retrofit_aviation(Integer.parseInt(row.get(2).select("td").get(1).text()));
			ship.setPost_retrofit_oil_consumtion(Integer.parseInt(row.get(2).select("td").get(2).text()));
			ship.setPost_retrofit_accuracy(Integer.parseInt(row.get(2).select("td").get(3).text()));
			
			ship.setPost_retrofit_anti_submarine_warfare(Integer.parseInt(row.get(3).select("td").get(0).text()));
			
		}
		
	}

	
	public String requestConstructionTime() {
		// TODO Auto-generated method stub
		//Getting the ship status
		Element table = Ship_Details.get(0);
		
		Elements row = table.select("tr");
		
		return row.get(0).select("td").text();
		
	}

	
	public String requestClass() {
		// TODO Auto-generated method stub
		//Get ship class
		Element table = Ship_Details.get(0);
		
		Elements row = table.select("tr");
		
		return row.get(2).select("td").text();
		
	}

	
	public List<String> getImage() {
		//Fetch all available skin available by saving link in a list
		List<String> links = new ArrayList<String>();
		
		Elements image = Ship_Image.select("img");
		
		for (Element element : image) {
			
			String link = element.attr("srcset");
			link = link.substring(0, link.indexOf(" "));
			
			links.add("https://azurlane.koumakan.jp" + link);
			
			//System.out.println("https://azurlane.koumakan.jp" + link);
			
		}
		
		return links;
		
	}

	
	public Image getIcon(String name) throws Exception{
		
		Image img = null;
		String link = "https://azurlane.koumakan.jp";
		String tag = "img[alt='" + name + "Icon.png']";
		
		Elements image_icon = Ship_Page.select(tag);
		
		link += image_icon.get(0).attr("src");
		
		img = ImageIO.read(new URL(link));
		
		return img;
		
	}
	
	public List<Faction> getFactions(){
		
		System.out.println(" > Getting faction list");
		List<Faction> factions = new ArrayList<Faction>();
		
		factions.add(new Faction("Universal", null));
		
		for (Element e : Faction_List.select("tr")) {
			
			Elements row = e.select("td");
			
			if(row.size() < 4) continue;
			
			String name = row.get(0).text();
			
			String link = row.get(3).select("img").attr("srcset");
			link = link.substring(0, link.length() - 3);
			
			while(link.contains(", ")) {
				
				link = link.substring(link.indexOf(", ")).substring(2);
				
			}
			
			Image img = null;
			String directory = "Source/Faction/" + name + ".png";
			
			try {
				
				img = ImageIO.read(new File(directory));
				
			} catch (Exception e2) {
				// TODO: handle exception
				
				System.out.println(" > Failed to get image from " + directory);
				
				try {
					img = ImageIO.read(new URL("https://azurlane.koumakan.jp" + link));
					
					new File("Source/Faction/").mkdirs();
					
					ImageIO.write((BufferedImage) img, "png", new File(directory));
					
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			
			
			factions.add(new Faction(name, img));
			
		}
		
		return factions;
		
	}
	
}
