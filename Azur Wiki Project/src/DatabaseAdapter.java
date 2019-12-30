import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class DatabaseAdapter {
	
	private static String url = "jdbc:sqlite:Source/Database/Data.db";
	private static Connection conn;
	private static Statement stmt;
	
	public static void connect() {
		
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void checkDB() {
		
		new File("Source/Database/").mkdirs();
	
		String sql;
		
		try {
			
			sql = "CREATE TABLE IF NOT EXISTS faction(\r\n" + 
					"	factionID INTEGER PRIMARY KEY,\r\n" + 
					"	factionName TEXT\r\n" + 
					");";
			
			stmt.execute(sql);
			
			System.out.println(" > Faction Table DB checked");
			
			sql = "CREATE TABLE IF NOT EXISTS ship(\r\n" + 
					"	id TEXT PRIMARY KEY,\r\n" + 
					"	shipTypeID INTEGER,\r\n" + 
					"	rarity TEXT,\r\n" + 
					"	classType TEXT,\r\n" + 
					"	factionID INTEGER,\r\n" + 
					"	armourType TEXT,\r\n" + 
					"	name TEXT,\r\n" + 
					"	shipClass TEXT,\r\n" + 
					"	constructionTime TEXT,\r\n" + 
					"	link TEXT,\r\n" + 
					"	firepower INTEGER,\r\n" + 
					"	health INTEGER,\r\n" + 
					"	antiAir INTEGER,\r\n" + 
					"	evasion INTEGER,\r\n" + 
					"	aviation INTEGER,\r\n" + 
					"	torpedo INTEGER,\r\n" + 
					"	reload INTEGER,\r\n" + 
					"	luck INTEGER,\r\n" + 
					"	oilConsumtion INTEGER,\r\n" + 
					"	accuracy INTEGER,\r\n" + 
					"	speed INTEGER,\r\n" + 
					"	antiSubmarineWarfare INTEGER,\r\n" + 
					"	FOREIGN KEY (factionID) REFERENCES faction(factionID)\r\n" + 
					");";
			
			stmt.execute(sql);
			
			System.out.println(" > Ship Table DB checked");
			
			sql = "CREATE TABLE IF NOT EXISTS equipment(\r\n" + 
					"	shipID TEXT,\r\n" + 
					"	name TEXT,\r\n" + 
					"	efficiency TEXT,\r\n" + 
					"	FOREIGN KEY (shipID) REFERENCES ship(id)\r\n" + 
					");";
			
			stmt.execute(sql);
			
			System.out.println(" > Equipment Table DB checked");
			
			sql = "CREATE TABLE IF NOT EXISTS skill(\r\n" + 
					"	shipID TEXT,\r\n" + 
					"	name TEXT,\r\n" + 
					"	description TEXT,\r\n" +
					"   type INTEGER,\r\n" +	
					"	FOREIGN KEY (shipID) REFERENCES ship(id),\r\n" + 
					"	PRIMARY KEY(shipID, name)\r\n" + 
					");";
			
			stmt.execute(sql);
			
			System.out.println(" > Skill Table DB checked");
			
			sql = "CREATE TABLE IF NOT EXISTS skin(\r\n" + 
					"	shipID TEXT,\r\n" + 
					"	directory TEXT,\r\n" + 
					"	FOREIGN KEY (shipID) REFERENCES ship(id),\r\n" + 
					"	PRIMARY KEY(shipID, directory)\r\n" + 
					");";
			
			stmt.execute(sql);
			
			System.out.println(" > Ship Skin Table checked");
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		} 
		
	}
	
	public static void selectAllShip() {
		
		String sql;
		
		try {
	
			ResultSet set;

			sql = "SELECT * FROM ship;";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				System.out.println(set.getString("id") + " " + set.getString("name"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
			
		}
		
	}
	
	public static void selectAllFaction() {
		
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM faction;";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				System.out.println(set.getString("id") + " " + set.getString("name"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public static List<Ship> getShip(int type, ShipContainer ship){
		
		List<Ship> ships = new ArrayList<Ship>();
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT id, name, rarity, classType, link, factionName FROM ship JOIN faction ON ship.factionID = faction.factionID WHERE shipTypeID = " + type + "; ";
			
			//System.out.println(sql);
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {

				for (Faction faction : ship.getFactionList()) {
					
					if(faction.getName().contentEquals(set.getString("factionName"))) {
						
						ships.add(new Ship(set.getString("id"), set.getString("name"), set.getString("rarity"), set.getString("classType"), faction, set.getString("link"), type));
						
					}
					
				}
				
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return ships;
		
	}
	
	public static List<Faction> getFactions(){
		
		List<Faction> factions = new ArrayList<Faction>();

		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM faction;";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				String directory = "Source/Faction/" + set.getString("factionName") + ".png";
				
				Image img = null;
				
				try {
					
					img = ImageIO.read(new File(directory));
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				factions.add(new Faction(set.getString("factionName"), img));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return factions;
		
	}
	
	public static List<Skill> getSkills(String id){
		
		List<Skill> skills = new ArrayList<Skill>();
		
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM skill WHERE shipID = \"" + id + "\";";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				skills.add(new Skill(set.getString("name"), set.getString("description"), set.getInt("type")));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return skills;
		
	}
	
	public static List<Equipment> getEquipments(String id){
		
		List<Equipment> equipments = new ArrayList<Equipment>();
		
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM equipment WHERE shipID = \"" + id + "\";";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				equipments.add(new Equipment(set.getString("efficiency"), set.getString("name")));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return equipments;
		
	}
	
	public static List<String> getSkins(String id){
		
		List<String> skins = new ArrayList<String>();
		
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT directory FROM skin WHERE shipID = \"" + id + "\";";
			
			set = stmt.executeQuery(sql);
			
			while(set.next()) {
				
				skins.add(set.getString("directory"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return skins;
		
	}
	
	public static String getConsTime(String id) {
		
		String sql;
		String constructionTime = "";
		
		try {
			
			ResultSet set;

			sql = "SELECT constructionTime FROM ship WHERE id = \"" + id + "\";";
			
			set = stmt.executeQuery(sql);
			
			constructionTime = set.getString("constructionTime");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return constructionTime;
		
	}

	public static String getShipClass(String id) {
		
		String sql;
		String shipClass = "";
		
		try {
			
			ResultSet set;

			sql = "SELECT shipClass FROM ship WHERE id = \"" + id + "\";";
			
			set = stmt.executeQuery(sql);
			
			shipClass = set.getString("shipClass");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
		return shipClass;
		
	}
	
	public static String getFactionName(int id) {
		
		String factionName = null;

		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM faction WHERE factionID = " + id + "; ";
			
			set = stmt.executeQuery(sql);
			
			factionName = set.getString("factionName");


		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
			
		}
		
		return factionName;
		
	}
	
	public static void getDetails(Ship ship) {
		
		String sql;
		
		try {
			
			ResultSet set;

			sql = "SELECT * FROM ship WHERE id = \"" + ship.getId() + "\";";
			
			set = stmt.executeQuery(sql);
			
			ship.setHealth(set.getInt("health"));
			ship.setArmour_type(set.getString("armourType"));
			ship.setReload(set.getInt("reload"));
			ship.setLuck(set.getInt("luck"));
			
			ship.setFirepower(set.getInt("firepower"));
			ship.setTorpedo(set.getInt("torpedo"));
			ship.setEvasion(set.getInt("evasion"));
			ship.setSpeed(set.getInt("speed"));
			
			ship.setAnti_air(set.getInt("antiAir"));
			ship.setAviation(set.getInt("aviation"));
			ship.setOil_consumtion(set.getInt("oilConsumtion"));
			ship.setAccuracy(set.getInt("accuracy"));
			
			ship.setAnti_submarine_warfare(set.getInt("antiSubmarineWarfare"));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public static void saveMinData(Ship ship) {

		String sql;
		
		try {

			ResultSet set;

			sql = "SELECT EXISTS(SELECT id FROM ship WHERE id = \"" + ship.getId() + "\");";
			set = stmt.executeQuery(sql);
					
			if(set.getInt(1) == 0) {

				sql = "INSERT INTO ship(id, name, rarity, factionID, classType, link, shipTypeID)\r\n" + 
						"VALUES (\"" + ship.getId() + "\", \"" + ship.getName() + "\", \""+ ship.getRarity() + "\", " + "(SELECT factionID FROM faction WHERE factionName = \"" + ship.getFaction().getName() + "\")" + ", \"" + ship.getType() + "\", \"" + ship.getLink() + "\"," + ship.getShip_type() + ");";
				
				//System.out.println(sql);
				
				stmt.execute(sql);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public static void saveSkill(List<Skill> skills, String shipID) {
		
		String sql;
		
		boolean save = false;
		
		String insertSql = "INSERT INTO skill VALUES \n";
		
		try {

			ResultSet set;
			
			//System.out.println(sql);
			
			for (Skill skill : skills) {
				
				
				sql = "SELECT EXISTS(SELECT * FROM skill WHERE shipID = \"" + shipID + "\" AND name = \"" + skill.getName() + "\");";
				set = stmt.executeQuery(sql);
				
				//System.out.println(set.getInt(1));
				
				if(set.getInt(1) == 0) {
					
					save = true;
					insertSql += "(\"" + shipID + "\", \"" + skill.getName() + "\", \"" + skill.getDescription().replace('\"', ' ') + "\", " + skill.getType() + "),\n";
					
				}
				
			}
			
			insertSql = insertSql.substring(0, insertSql.length() - 2) + ";";
			
			//System.out.println(insertSql);
			
			if(save) stmt.execute(insertSql);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(insertSql);
			
		}
		
	}
	
	public static void saveEquipment(List<Equipment> equipments, String shipID) {
		
		String sql;
		
		boolean save = false;
		
		try {

			ResultSet set;
			
			//System.out.println(sql);
			String insertSql = "INSERT INTO equipment VALUES \n";
			
			for (Equipment equipment : equipments) {
				
				
				sql = "SELECT EXISTS(SELECT * FROM equipment WHERE shipID = \"" + shipID + "\" AND name = \"" + equipment.getEquipment_name() + "\");";
				set = stmt.executeQuery(sql);
				
				if(set.getInt(1) == 0) {
					
					save = true;
					insertSql += "(\"" + shipID + "\", \"" + equipment.getEquipment_name() + "\", \"" + equipment.getEfficiency() + "\"),\n";
					
				}
				
			}
			
			insertSql = insertSql.substring(0, insertSql.length() - 2) + ";";
			
			//System.out.println(insertSql);
			
			if(save) stmt.execute(insertSql);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public static void saveConsTime(String time, String shipID) {

		String sql;
		
		try {

			sql = "UPDATE ship SET constructionTime = \"" + time + "\" WHERE id = \"" + shipID + "\";";
			//System.out.println(sql);
			stmt.execute(sql);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public static void saveShipClass(String shipClass, String shipID) {
		
		String sql;
		
		try {

			sql = "UPDATE ship SET shipClass = \"" + shipClass + "\" WHERE id = \"" + shipID + "\";";
			//System.out.println(sql);
			stmt.execute(sql);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void saveSkin(String dir, String shipID) {
		
		String sql;
		
		try {

			ResultSet set;
			
			//System.out.println(sql);
			
			sql = "SELECT EXISTS(SELECT * FROM skin WHERE shipID = \"" + shipID + "\" AND directory = \"" + dir + "\");";
			
			set = stmt.executeQuery(sql);
				
			if(set.getInt(1) == 0) {
					
				sql = "INSERT INTO skin VALUES (\"" + shipID + "\", \"" + dir + "\");";
				
				//System.out.println(sql);
				
				stmt.execute(sql);
					
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void saveDetails(Ship ship) {
		
		String sql;
		
		try {
			
			sql = "UPDATE ship SET health = " + ship.getHealth() + ",\n" +
				  " armourType = \"" + ship.getArmour_type() + "\",\n" +
				  " reload = " + ship.getReload() + ",\n" +
				  " luck = " + ship.getLuck() + ",\n" +
				  
				  " firepower = " + ship.getFirepower() + ",\n" +
				  " torpedo = " + ship.getTorpedo() + ",\n" +
				  " evasion = " + ship.getEvasion() + ",\n" +
				  " speed = " + ship.getSpeed() + ",\n" +
				  
				  " antiAir = " + ship.getAnti_air() + ",\n" +
				  " aviation = " + ship.getAviation() + ",\n" +
				  " oilConsumtion = " + ship.getOil_consumtion() + ",\n" +
				  " accuracy = " + ship.getAccuracy() + ",\n" +
				  " antiSubmarineWarfare = " + ship.getAnti_submarine_warfare() + "\n" +
				  
				  "WHERE id = \"" + ship.getId() + "\";";
			
			//System.out.println(sql);
			
			stmt.execute(sql);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
			
		}
		
	}
	
 	
	public static void saveFaction(List<Faction> factions) {
		
		String sql;
		
		try {
			
			ResultSet set;

			for (Faction faction : factions) {
				
				sql = "SELECT EXISTS(SELECT factionName FROM faction WHERE factionName = \"" + faction.getName() + "\");";
				set = stmt.executeQuery(sql);
				
				//System.out.println(sql);
				
				if(set.getInt(1) == 0) {
					
					int idx;
					sql = "SELECT COUNT(*) FROM faction;";
					set = stmt.executeQuery(sql);
					
					idx = set.getInt(1);
					
					sql = "INSERT INTO faction VALUES (" + idx + ", \"" + faction.getName() + "\");";
					stmt.execute(sql);
					//System.out.println(sql);
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
}
