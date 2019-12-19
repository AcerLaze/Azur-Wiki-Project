import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class WindowsManager {
	
	public static void ship_detail_window(Ship ship) {
		
		if(ship == null) return;
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setPreferredSize(new Dimension(1500, 875));
		frame.setResizable(false);
		frame.setUndecorated(false);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		panel.add(ship_detail_sidebar_window(ship), BorderLayout.WEST);
		panel.add(ship_detail_main_window(ship), BorderLayout.CENTER);
		
		panel.setBounds(0, 0, 1500, 870);
		panel.setBackground(new Color(0, 0, 0, 0));
		frame.add(panel);
	
		try {
			
			Image background = ImageIO.read(new File("Source/bg.png"));
			
			JLabel bakcground_label = new JLabel(new ImageIcon(background.getScaledInstance(1511, 850, Image.SCALE_SMOOTH)));

			bakcground_label.setLayout(null);
			bakcground_label.setBounds(0, 0, 1511, 850);
			bakcground_label.setPreferredSize(new Dimension(1000, 850));
			
			frame.add(bakcground_label);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static JPanel ship_detail_sidebar_window(Ship ship) {
		
		Color background = new Color(255,255,255);
		Color panel_background = new Color(0, 0, 0, 100);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); //UP LEFT DOWN RIGHT
		try {
			
			JPanel img_container = new JPanel();
			
			img_container.setPreferredSize(new Dimension(337, 512));
			img_container = ship.loadImage(0);
			
			//img_container.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			
			panel.add(img_container);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			
		}
		
		JPanel name_panel = new JPanel();
		name_panel.setLayout(new FlowLayout());
		name_panel.setPreferredSize(new Dimension(300, 75));
		name_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		name_panel.setBackground(new Color(0, 0, 0, 0));
		
		JLabel name = new JLabel(ship.getName(), SwingConstants.RIGHT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setOpaque(true);
		name.setBorder(new EmptyBorder(10, 0, 10, 0));
		name.setFont(new Font("Serif", Font.PLAIN, 40));
		name.setBackground(new Color(0, 0, 0, 0));
		name.setForeground(Color.WHITE);
		
		name_panel.add(name);
		
		panel.add(name_panel);
		
		JPanel sub_panel = new JPanel();
		sub_panel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		Border border = BorderFactory.createLineBorder(Color.black);
		
		JLabel ship_type_label = new JLabel("Type", SwingConstants.CENTER);
		JLabel ship_type = new JLabel(ship.getType(), SwingConstants.CENTER);
		
		ship_type_label.setBorder(border);
		ship_type.setBorder(border);
		
		ship_type_label.setPreferredSize(new Dimension(170, 50));
		ship_type.setPreferredSize(new Dimension(170, 50));
		
		ship_type_label.setFont(new Font(ship_type_label.getFont().getFontName(), Font.BOLD, 15));
		ship_type.setFont(new Font(ship_type.getFont().getFontName(), Font.PLAIN, 13));
		
		ship_type_label.setBackground(new Color(102, 204, 255));
		ship_type_label.setOpaque(true);
		
		
		ship_type.setOpaque(true);
		
		switch(ship.getType()) {
		
		case "Destroyer":
			
			background = new Color(176, 224, 230);
			
			break;
			
		case "Light Cruiser":
		case "Heavy Cruiser":
			background = new Color(255, 222, 173);
			
			break;
			
		case "Large Cruiser":
			
			background = new Color(255, 160, 122);
			
			break;
			
		case "Monitor":
			
			background = new Color(255, 222, 173);
			
			break;
			
		case "Battleship":
		case "Battlecruiser":
		case "Aviation Battleship":
			background = new Color(255, 192, 203);
			
			break;
			
		case "Aircraft Carrier":
		case "Light Aircraft Carrier":
			
			background = new Color(221, 160, 221);
			
			break;
			
		case "Submarine":	
		case "Submarine Carrier":
			background = new Color(102, 255, 153);
			
			break;
			
		case "Repair Ship":
			
			background = new Color(127, 255, 212);
			
			break;
		
		}
		
		ship_type.setBackground(background);
		
		g.gridx = g.gridy = 0;
		sub_panel.add(ship_type_label, g);
		g.gridx = 1;
		sub_panel.add(ship_type, g);
		
		JLabel rarity_label = new JLabel("Rarity", SwingConstants.CENTER);
		JLabel rarity = new JLabel(ship.getRarity(), SwingConstants.CENTER);
		
		rarity_label.setBorder(border);
		rarity.setBorder(border);
		
		rarity_label.setPreferredSize(new Dimension(170, 50));
		rarity.setPreferredSize(new Dimension(170, 50));
		
		rarity_label.setFont(new Font(rarity_label.getFont().getFontName(), Font.BOLD, 15));
		rarity.setFont(new Font(rarity.getFont().getFontName(), Font.PLAIN, 13));
		
		rarity_label.setBackground(new Color(102, 204, 255));
		rarity_label.setOpaque(true);
		
		rarity.setOpaque(true);
		background = new Color(255, 255, 255);
		
		switch (ship.getRarity()) {
		case "Unreleased":
		case "Normal":
			background = new Color(220, 220, 220);
			
			break;
			
		case "Rare":
			
			background = new Color(176, 224, 230);
			
			break;
			
		case "Elite":
			
			background = new Color(221, 160, 221);
			
			break;
			
		case "Super Rare":
			
			background = new Color(238, 232, 170);
			
			break;

		}
		
		rarity.setBackground(background);
		
		
		g.gridx = 0; g.gridy = 1;
		sub_panel.add(rarity_label, g);
		g.gridx = 1;
		sub_panel.add(rarity, g);
		
		JLabel ship_class_label = new JLabel("Class", SwingConstants.CENTER);
		JLabel ship_class = new JLabel(ship.getShip_class(), SwingConstants.CENTER);
		
		ship_class_label.setBorder(border);
		ship_class.setBorder(border);
		
		ship_class_label.setPreferredSize(new Dimension(170, 50));
		ship_class.setPreferredSize(new Dimension(170, 50));
		
		ship_class_label.setFont(new Font(ship_class_label.getFont().getFontName(), Font.BOLD, 15));
		ship_class.setFont(new Font(ship_class.getFont().getFontName(), Font.PLAIN, 13));
		
		ship_class_label.setBackground(new Color(102, 204, 255));
		ship_class_label.setOpaque(true);
		
		ship_class.setBackground(new Color(255, 255, 255));
		ship_class.setOpaque(true);
		
		g.gridx = 0; g.gridy = 2;
		sub_panel.add(ship_class_label, g);
		g.gridx = 1;
		sub_panel.add(ship_class, g);
		
		JLabel faction_label = new JLabel("Faction", SwingConstants.CENTER);
		JLabel faction = new JLabel(ship.getFaction(), SwingConstants.CENTER);
		
		faction_label.setBorder(border);
		faction.setBorder(border);
		
		faction_label.setPreferredSize(new Dimension(170, 50));
		faction.setPreferredSize(new Dimension(170, 50));
		
		faction_label.setFont(new Font(faction_label.getFont().getFontName(), Font.BOLD, 15));
		faction.setFont(new Font(faction.getFont().getFontName(), Font.PLAIN, 13));
		
		faction_label.setBackground(new Color(102, 204, 255));
		faction_label.setOpaque(true);
		
		faction.setOpaque(true);
		background = new Color(220, 220, 220);
		
		switch (ship.getFaction()) {
		case "Eagle Union":
			
			background = new Color(176, 224, 230);
			
			break;
			
		case "Eastern Radiance":
			
			background = new Color(221, 160, 221);
			
			break;
			
		case "Iris Libre":
			
			background = new Color(255, 215, 0);
			
			break;
			
		case "Ironblood":
			
			background = new Color(255, 192, 203);
			
			break;
			
		case "Royal Navy":
			
			background = new Color(131, 170, 240);
			
			break;
			
		case "Sakura Empire":
			
			background = new Color(255, 240, 245);
			
			break;
			
		case "Sardegna Empire":
			
			background = new Color(110, 190, 147);
			
			break;
			
		case "Vichya Dominion":
			
			background = new Color(215, 124, 124);
			
			break;

		}
		
		faction.setBackground(background);
		
		g.gridx = 0; g.gridy = 3;
		sub_panel.add(faction_label, g);
		g.gridx = 1;
		sub_panel.add(faction, g);
		
		sub_panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		sub_panel.setBackground(new Color(0, 0, 0, 0));
		
		panel.setBackground(panel_background);
		panel.setPreferredSize(new Dimension(390, 850));
		
		panel.add(sub_panel);
		
		return panel;
		
	}
	
	public static JPanel ship_detail_main_window(Ship ship) {
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel sub_panel = new JPanel();
		sub_panel.setPreferredSize(new Dimension(1070, 820));
		sub_panel.setLayout(new BoxLayout(sub_panel, BoxLayout.Y_AXIS));
		sub_panel.setBackground(new Color(0, 0, 0, 190));
		
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel ship_name = new JLabel(ship.getName(), JLabel.LEFT);
		ship_name.setFont(new Font("Serif", Font.PLAIN, 40));
		ship_name.setPreferredSize(new Dimension(200, 50));
		ship_name.setForeground(Color.white);
		
		header.add(ship_name);
		header.setBackground(new Color(102, 204, 255, 190));
		header.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//header.setPreferredSize(new Dimension(1100, 50));
		
		JScrollPane main_panel = new JScrollPane();
		main_panel.setPreferredSize(new Dimension(1070, 750));
		
		JViewport viewport = new JViewport();
		
		main_panel.setViewport(viewport);
		main_panel.getViewport().setOpaque(false);
		main_panel.setOpaque(false);
		
		sub_panel.add(header);
		sub_panel.add(main_panel);
		
		panel.add(sub_panel);
		
		return panel;
		
	}
	
}
