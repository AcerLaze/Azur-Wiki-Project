import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	
	private List<Skill> skills;
	private List<Equipment> equipments;
	private List<Image> image;
	private Image icon;
	private JLabel img_container;

	private int index = 0;
	
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
			
			image = new ArrayList<Image>();
			
			for (String url : web_adapter.getImage()) {
			
				Image img = null;
				
				String directory = "Source/" + name + "/";
				
				try {
					
					String[] temp = url.split("/");
					
					directory += temp[temp.length - 1];
					
					System.out.println(" > Looking picture at" + directory);
					
					img = ImageIO.read(new File(directory));
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(" > Failed to find picture fetching from internet");
					try {
						
						//System.out.println(url);
						
						img = ImageIO.read(new URL(url));
						
						new File("Source/" + name + "/").mkdirs();
						
						ImageIO.write((BufferedImage)img, "png", new File(directory));
						
					} catch (Exception ee) {
						// TODO Auto-generated catch block
						
						System.out.println(" > " + name + " failed to fetch image.");
						
					}
					
				}
				
				int width = (int)(img.getWidth(null) * 0.5);
				int height = (int)(img.getHeight(null) * 0.5);
				image.add(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
				
			};
		
			web_adapter.getShipStat(this);
		
		} else {
			
			retrofitable = false;
			skills = null;
			equipments = null;
			construction_time = "-";
			ship_class = "?";
			
		}
		
	}

	public JPanel loadImage(int index) throws Exception{
		
		JPanel image_panel = new JPanel();
		
		img_container = new JLabel(new ImageIcon(image.get(index)), JLabel.CENTER);
		
		int width = image.get(index).getWidth(null) / 2;
		int height = image.get(index).getHeight(null) / 2;
		
		width = (337 / 2) - (width);
		height = (512 / 2) - (height);
		
		image_panel.add(img_container);
		image_panel.setPreferredSize(new Dimension(337, 512));
		
		image_panel.setBorder(new EmptyBorder(height, width, 0, 0));
	
		Color background;
		
		switch (rarity) {
		case "Unreleased":
		case "Normal":
		default:
			background = new Color(220, 220, 220, 180);
			
			break;
			
		case "Rare":
			
			background = new Color(176, 224, 230, 180);
			
			break;
			
		case "Elite":
			
			background = new Color(221, 160, 221, 180);
			
			break;
			
		case "Super Rare":
			
			background = new Color(255, 232, 170, 180);
		}
		
		image_panel.setBackground(background);
		
		return image_panel;
		
	}
	
	public void nextImage() {
		
		index++;
		if(index >= image.size()) index = 0;
		System.out.println(index);
		
		try {
			
			img_container = new JLabel(new ImageIcon(image.get(index)), JLabel.CENTER);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		
	}
	
	public void prevImage() {
		
		index--;
		if(index < 0) index = image.size() - 1;
		System.out.println(index);
		
		try {
			
			img_container = new JLabel(new ImageIcon(image.get(index)), JLabel.CENTER);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	//Getter & Setter For Each Attribute (Just Ignore)
	
	public List<Image> getImageLink() {
		return image;
	}

	public void setImageLink(List<Image> image) {
		this.image = image;
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
