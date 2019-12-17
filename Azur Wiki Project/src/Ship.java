import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ship {

	private String id;
	private String name;
	private String rarity;
	private String type;
	private String faction;
	private String armour_type;
	private String ship_class;
	private String construction_time;
	
	private String link;
	
	private int firepower;
	private int health;
	private int anti_air;
	private int evasion;
	private int aviation;
	private int torpedo;
	private int reload;
	private int luck;
	private int oil_consumtion;
	private int accuracy;
	private int speed;
	private int anti_submarine_warfare;
	
	private int post_retrofit_firepower;
	private int post_retrofit_health;
	private int post_retrofit_anti_air;
	private int post_retrofit_evasion;
	private int post_retrofit_aviation;
	private int post_retrofit_torpedo;
	private int post_retrofit_reload;
	private int post_retrofit_luck;
	private int post_retrofit_oil_consumtion;
	private int post_retrofit_accuracy;
	private int post_retrofit_speed;
	private int post_retrofit_anti_submarine_warfare;
	
	private int ship_type;
	
	private boolean retrofitable;
	private boolean downloaded = false;
	
	List<Skill> skills;
	List<Equipment> equipments;
	List<String> imageLink;

	public Ship() {
		
		firepower = health = anti_air = evasion = aviation = torpedo = reload = luck = oil_consumtion = accuracy = speed = anti_submarine_warfare = 0;
		
	}
	
	public Ship(String id, String name, String rarity, String type, String faction, String link, int ship_type) {
		
		this.id = id;
		this.name = name;
		this.rarity = rarity;
		this.type = type;
		this.faction = faction;
		this.ship_type = ship_type;
		
		this.link = link;
		
	}
	
	public void getData() {
		
		WebAdapter web_adapter = new WebAdapter(link, WebAdapter.SHIP_DETAIL);
		setDownloaded(true);
		
		if(!rarity.contentEquals("Unreleased")) {
			//If the ship is unrelease, dont bother to fetch more data or it will cause error
			retrofitable = web_adapter.requestIsShipRetrofitable();
		
			skills = web_adapter.requestShipSkill();
			equipments = web_adapter.requestShipEquipment();
			construction_time = web_adapter.requestConstructionTime();
			ship_class = web_adapter.requestClass();
			
			imageLink = web_adapter.getImage();
		
			web_adapter.getShipStat(this);
		
		} else {
			
			retrofitable = false;
			skills = null;
			equipments = null;
			construction_time = "-";
			ship_class = "?";
			
		}
		
	}

	public JPanel loadImage(String url) throws Exception{
		
		JPanel image_panel = new JPanel();
		
		Image img = ImageIO.read(new URL(url));
		
		int width = (int)(img.getWidth(null) * 0.5);
		int height = (int)(img.getHeight(null) * 0.5);
		
		JLabel img_container = new JLabel(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
		
		image_panel.add(img_container);
		System.out.println(width);
		
		return image_panel;
		
	}
	
	//Getter & Setter For Each Attribute (Just Ignore)
	
	public List<String> getImageLink() {
		return imageLink;
	}

	public void setImageLink(List<String> imageLink) {
		this.imageLink = imageLink;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArmour_type(String armour_type) {
		this.armour_type = armour_type;
	}

	public void setShip_class(String ship_class) {
		this.ship_class = ship_class;
	}

	public void setConstruction_time(String construction_time) {
		this.construction_time = construction_time;
	}

	public void setAnti_air(int anti_air) {
		this.anti_air = anti_air;
	}

	public void setReload(int reload) {
		this.reload = reload;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public void setOil_consumtion(int oil_consumtion) {
		this.oil_consumtion = oil_consumtion;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setAnti_submarine_warfare(int anti_submarine_warfare) {
		this.anti_submarine_warfare = anti_submarine_warfare;
	}

	public void setShip_type(int ship_type) {
		this.ship_type = ship_type;
	}

	public void setRetrofitable(boolean retrofitable) {
		this.retrofitable = retrofitable;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAntiair() {
		return anti_air;
	}

	public void setAntiair(int antiair) {
		this.anti_air = antiair;
	}

	public int getEvasion() {
		return evasion;
	}

	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}

	public int getAviation() {
		return aviation;
	}

	public void setAviation(int aviation) {
		this.aviation = aviation;
	}

	public int getTorpedo() {
		return torpedo;
	}

	public void setTorpedo(int torpedo) {
		this.torpedo = torpedo;
	}

	public String getArmour_type() {
		return armour_type;
	}

	public String getShip_class() {
		return ship_class;
	}

	public String getConstruction_time() {
		return construction_time;
	}

	public int getAnti_air() {
		return anti_air;
	}

	public int getReload() {
		return reload;
	}

	public int getLuck() {
		return luck;
	}

	public int getOil_consumtion() {
		return oil_consumtion;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getSpeed() {
		return speed;
	}

	public int getAnti_submarine_warfare() {
		return anti_submarine_warfare;
	}

	public int getShip_type() {
		return ship_type;
	}

	public boolean isRetrofitable() {
		return retrofitable;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}



	public int getPost_retrofit_firepower() {
		return post_retrofit_firepower;
	}



	public void setPost_retrofit_firepower(int post_retrofit_firepower) {
		this.post_retrofit_firepower = post_retrofit_firepower;
	}



	public int getPost_retrofit_health() {
		return post_retrofit_health;
	}



	public void setPost_retrofit_health(int post_retrofit_health) {
		this.post_retrofit_health = post_retrofit_health;
	}



	public int getPost_retrofit_anti_air() {
		return post_retrofit_anti_air;
	}



	public void setPost_retrofit_anti_air(int post_retrofit_anti_air) {
		this.post_retrofit_anti_air = post_retrofit_anti_air;
	}



	public int getPost_retrofit_evasion() {
		return post_retrofit_evasion;
	}



	public void setPost_retrofit_evasion(int post_retrofit_evasion) {
		this.post_retrofit_evasion = post_retrofit_evasion;
	}



	public int getPost_retrofit_aviation() {
		return post_retrofit_aviation;
	}



	public void setPost_retrofit_aviation(int post_retrofit_aviation) {
		this.post_retrofit_aviation = post_retrofit_aviation;
	}



	public int getPost_retrofit_torpedo() {
		return post_retrofit_torpedo;
	}



	public void setPost_retrofit_torpedo(int post_retrofit_torpedo) {
		this.post_retrofit_torpedo = post_retrofit_torpedo;
	}



	public int getPost_retrofit_reload() {
		return post_retrofit_reload;
	}



	public void setPost_retrofit_reload(int post_retrofit_reload) {
		this.post_retrofit_reload = post_retrofit_reload;
	}



	public int getPost_retrofit_luck() {
		return post_retrofit_luck;
	}



	public void setPost_retrofit_luck(int post_retrofit_luck) {
		this.post_retrofit_luck = post_retrofit_luck;
	}



	public int getPost_retrofit_oil_consumtion() {
		return post_retrofit_oil_consumtion;
	}

	public void setPost_retrofit_oil_consumtion(int post_retrofit_oil_consumtion) {
		this.post_retrofit_oil_consumtion = post_retrofit_oil_consumtion;
	}

	public int getPost_retrofit_accuracy() {
		return post_retrofit_accuracy;
	}

	public void setPost_retrofit_accuracy(int post_retrofit_accuracy) {
		this.post_retrofit_accuracy = post_retrofit_accuracy;
	}

	public int getPost_retrofit_speed() {
		return post_retrofit_speed;
	}

	public void setPost_retrofit_speed(int post_retrofit_speed) {
		this.post_retrofit_speed = post_retrofit_speed;
	}

	public int getPost_retrofit_anti_submarine_warfare() {
		return post_retrofit_anti_submarine_warfare;
	}

	public void setPost_retrofit_anti_submarine_warfare(int post_retrofit_anti_submarine_warfare) {
		this.post_retrofit_anti_submarine_warfare = post_retrofit_anti_submarine_warfare;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}
	
}
