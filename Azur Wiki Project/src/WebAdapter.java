
import java.util.ArrayList;
import java.util.List;

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
	
	final static public int SHIP_LIST = 0;
	final static public int SHIP_DETAIL = 1;
	
	
	WebAdapter(String url, int type){
		
		mod = 0;
		
		Connection connection = Jsoup.connect(url);
		
		try {
			
			//System.out.println("Connecting to the server");
			
			connection.timeout(12000);
			
			Ship_Page = Jsoup.connect(url).timeout(12000).get();
			
			if(type == SHIP_LIST) Ship_List = Ship_Page.select("table.wikitable.sortable.jquery-tablesorter");
			else if(type == SHIP_DETAIL) {
				
				Ship_Details = Ship_Page.select("table.wikitable");
				Ship_Image = Ship_Page.select("div.adaptiveratioimg");
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something Went Wrong When Connecting To The Server. Perhaps the server is down or your connection.");
			Ship_Page = null;
			
		}
		
	}
	
	public ShipContainer requestShipList(){
		//Fetching all available ship from wiki
		
		if(Ship_Page == null) {
			
			System.out.println("Can't connect to the server !");
			return null;
			
		}
		
		ShipContainer shipList = new ShipContainer();
		
		Element Normal_Ship = Ship_List.get(0);
		Element Priority_Ship = Ship_List.get(1);
		Element Collab_Ship = Ship_List.get(2);
		
		for (Element element : Normal_Ship.select("tr")) {
			
			Elements ShipData = element.select("td");
			
			if(ShipData.size() < 1) continue;
			
			Elements links = ShipData.select("a");
			String link = links.get(1).attr("href");
			
			Ship ship = new Ship(ShipData.get(0).text(), 
					    ShipData.get(1).text(),
					    ShipData.get(2).text(), 
					    ShipData.get(3).text(),
					    ShipData.get(4).text(),
						"https://azurlane.koumakan.jp" + link, ShipContainer.NORMAL_SHIP);
			
			shipList.addShip(ShipContainer.NORMAL_SHIP, ship);
			//System.out.println(ShipData.get(1).text() + " data downloaded!");
			
		}
		
		System.out.println("Normal Ship Download Success");
		
		for (Element element : Priority_Ship.select("tr")) {
			
			Elements ShipData = element.select("td");
			
			if(ShipData.size() < 1) continue;
			
			Elements links = ShipData.select("a");
			String link = links.get(1).attr("href");
			
			shipList.addShip(ShipContainer.PRIORITY_SHIP, new Ship(ShipData.get(0).text(), 
								  						  ShipData.get(1).text(),
								  						  ShipData.get(2).text(),
								  						  ShipData.get(3).text(),
								  						  ShipData.get(4).text(),
								  						  "https://azurlane.koumakan.jp" + link, ShipContainer.PRIORITY_SHIP));
			//System.out.println(ShipData.get(1).text() + " data downloaded!");
		}
		
		System.out.println("Priority Ship Download Success");
		
		for (Element element : Collab_Ship.select("tr")) {
			
			Elements ShipData = element.select("td");
			
			if(ShipData.size() < 1) continue;
			
			Elements links = ShipData.select("a");
			String link = links.get(1).attr("href");
			
			shipList.addShip(ShipContainer.COLLAB_SHIP, new Ship(ShipData.get(0).text(), 
								  			    		ShipData.get(1).text(), 
								  			    		ShipData.get(2).text(), 
								  			    		ShipData.get(3).text(),
								  					    ShipData.get(4).text(),
								  			    		"https://azurlane.koumakan.jp" + link, ShipContainer.COLLAB_SHIP));
			//System.out.println(ShipData.get(1).text() + " data downloaded!");
		}
		
		System.out.println("Collab Ship Download Success");
		
		shipList.calculateAvg();
		
		return shipList;
		
	}
	
	public List<Skill> requestShipSkill() {
		
		//Returning all available Skill from wiki in form of List
		List<Skill> skills = new ArrayList<>();
		
		try {
			
			Element skill_table = Ship_Details.get(9 + mod);
			
			for (Element element : skill_table.select("tr")) {
				
				Elements row = element.select("td");
				
				if(row.size() != 3) continue;
				
				String name = row.get(1).text();
				String desc = row.get(2).text();
				
				if(name.isEmpty()) break;
				else name = name.substring(0, name.indexOf("C"));
				
				String style = row.get(1).attr("style");
				int type;
				
				if(style.contains("Gold")) type = Skill.YELLOW;
				else if(style.contains("Pink")) type = Skill.RED;
				else if(style.contains("Blue")) type = Skill.BLUE;
				else type = -1;
				
				//System.out.println(name + " " + type);
				//System.out.println(desc);
				//System.out.println("");
				
				skills.add(new Skill(name, desc, type));
				
				
			}
			
			return skills;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something when wrong when downloading data for skill");
			
			return null;
			
		}
		
	}
	
	public List<Equipment> requestShipEquipment() {
		//Returning available equipment from wiki in form of List
		List<Equipment> equipments = new ArrayList<>();
		
		try {
			
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
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println("Something when wrong when downloading data for equipment");
			
			return null;
			
		}
		
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

}
