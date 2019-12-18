import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class WindowsManager {

	public static JPanel ship_detail_window(Ship ship) {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); //UP LEFT DOWN RIGHT
		
		try {
			
			JPanel img = ship.loadImage(ship.getImageLink().get(0));
			img.setBorder(new EmptyBorder(10, 10, 10, 10)); 
			img.setBackground(new Color(255,255, 255));
			panel.add(img);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("test");
			
		}

		//panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		JLabel name = new JLabel(ship.getName(), SwingConstants.RIGHT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setOpaque(true);
		name.setBorder(new EmptyBorder(10, 0, 10, 0));
		name.setFont(new Font("Serif", Font.PLAIN, 40));
		name.setBackground(Color.darkGray);
		name.setForeground(Color.WHITE);
		
		panel.add(name);
		
		//panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		
		JPanel sub_panel = new JPanel();
		sub_panel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		
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
		
		ship_type.setBackground(new Color(255, 255, 255));
		ship_type.setOpaque(true);
		
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
		
		rarity.setBackground(new Color(255, 255, 255));
		rarity.setOpaque(true);
		
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
		
		faction.setBackground(new Color(255, 255, 255));
		faction.setOpaque(true);
		
		g.gridx = 0; g.gridy = 3;
		sub_panel.add(faction_label, g);
		g.gridx = 1;
		sub_panel.add(faction, g);
		System.out.println(panel.getSize());
		
		sub_panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		sub_panel.setBackground(Color.darkGray);
		
		panel.setBackground(Color.darkGray);
		panel.setPreferredSize(new Dimension(390, 850));
		
		panel.add(sub_panel);
		
		return panel;
		
	}
	
}
