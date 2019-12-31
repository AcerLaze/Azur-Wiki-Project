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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class WindowsManager extends JFrame{
	
	private ShipContainer shipList;
	private List<Ship> ships;
	
	private String shipCategoryFilter;
	private String shipFactionFilter;
	private Map<Integer, Integer> shipTypeFilter = new HashMap<Integer, Integer>();
	
	private JScrollPane main_scroll_panel = new JScrollPane();
	private JFrame loadZone;
	
	WindowsManager(ShipContainer shipList){
		
		this.shipList = shipList;
		
		setTitle("Azur Lane Ship Wiki (ALSW)");
		
		resetFilter();
		
		setLayout(null);
		
		setUndecorated(false);
		
		setPreferredSize(new Dimension(1500, 880));
		
		setResizable(false);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pack();
		
	}
	
	public void startLoading() {
		
		loadZone = new JFrame();
		loadZone.setPreferredSize(new Dimension(200, 200));
		
		JPanel panel = new JPanel();
		
		JLabel loadTitle = new JLabel("Loading");
		
		panel.add(loadTitle);
		
		loadZone.add(panel);
		
		loadZone.setVisible(true);
		loadZone.pack();
		loadZone.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		loadZone.repaint();
		
	}
	
	public void finishLoading() {
		
		loadZone.dispose();
		
	}
	
	private void resetFilter() {
		
		shipCategoryFilter = shipFactionFilter = "";
		
		for (Faction faction : shipList.getFactionList()) {
			
			shipFactionFilter += faction.getName() + "#";
			
		}
		
		shipCategoryFilter = "NPC";
		
		shipTypeFilter.put(ShipContainer.DD, 1);
		shipTypeFilter.put(ShipContainer.CL, 1);
		shipTypeFilter.put(ShipContainer.CA, 1);
		shipTypeFilter.put(ShipContainer.BC, 1);
		shipTypeFilter.put(ShipContainer.BB, 1);
		shipTypeFilter.put(ShipContainer.AR, 1);
		shipTypeFilter.put(ShipContainer.CV, 1);
		shipTypeFilter.put(ShipContainer.CVL, 1);
		shipTypeFilter.put(ShipContainer.SS, 1);
		shipTypeFilter.put(ShipContainer.BM, 1);
		shipTypeFilter.put(ShipContainer.AM, 1);
		shipTypeFilter.put(ShipContainer.CB, 1);
		
	}
	
	private void filterWindow() {
		
		JFrame frame = new JFrame("Filter");
		frame.setLocationRelativeTo(this);
		
		frame.setResizable(false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel leftColumn = new JPanel();
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		
		JPanel shipFilter = new JPanel();
		shipFilter.setBorder(BorderFactory.createTitledBorder("Ship"));
		
		JCheckBox normalType = new JCheckBox("Normal");
		if(shipCategoryFilter.contains("N")) normalType.setSelected(true);
		normalType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					shipCategoryFilter = shipCategoryFilter.replace("N", "");
					
				} else {
					
					shipCategoryFilter += "N";
					
				}
				
			}
		});
		
		JCheckBox priorityType = new JCheckBox("Priority");
		if(shipCategoryFilter.contains("P")) priorityType.setSelected(true);
		priorityType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					shipCategoryFilter = shipCategoryFilter.replace("P", "");
					
				} else {
					
					shipCategoryFilter += "P";

				}
			}
		});
		
		JCheckBox collabType = new JCheckBox("Collab");
		if(shipCategoryFilter.contains("C")) collabType.setSelected(true);
		collabType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					shipCategoryFilter = shipCategoryFilter.replace("C", "");
					
				} else {
					
					shipCategoryFilter += "C";
					
				}
			}
		});
		
		JPanel factionFilter = new JPanel();
		factionFilter.setBorder(BorderFactory.createTitledBorder("Faction"));
		
		JPanel factionList = new JPanel();
		factionList.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.gridx = g.gridy = 0;
		g.weightx = g.weighty = 0.1;
		g.fill = GridBagConstraints.BOTH;
		g.anchor = GridBagConstraints.WEST;
		
		for (Faction fac : shipList.getFactionList()) {
			
			JCheckBox faction = new JCheckBox(fac.getName());
			if(shipFactionFilter.contains(fac.getName())) faction.setSelected(true);
			faction.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(e.getStateChange() == ItemEvent.DESELECTED) {
						
						shipFactionFilter = shipFactionFilter.replace(fac.getName() + "#", "");
						
					} else {
						
						shipFactionFilter += fac.getName() + "#";
						
					}
				}
			});
			
			factionList.add(faction, g);
			
			g.gridx++;
			
			if(g.gridx == 3) {
				
				g.gridy++;
				g.gridx = 0;
				
			}
			
		}
		
		shipFilter.add(normalType);
		shipFilter.add(priorityType);
		shipFilter.add(collabType);
		
		factionFilter.add(factionList);
		
		leftColumn.add(shipFilter);
		leftColumn.add(factionFilter);
		
		JPanel rightColumn = new JPanel();
		
		JPanel shipTypeFilterPanel = new JPanel();
		shipTypeFilterPanel.setBorder(BorderFactory.createTitledBorder("Ship Type"));
		
		JPanel shipTypeList = new JPanel();
		shipTypeList.setLayout(new GridBagLayout());
		g.gridx = g.gridy = 0;
		g.weightx = g.weighty = 0.1;
		g.fill = GridBagConstraints.BOTH;
		g.anchor = GridBagConstraints.WEST;
		
		//shipType
		JCheckBox largeCruiser = new JCheckBox("Large Cruiser");
		if(shipTypeFilter.get(ShipContainer.CB) == 1) largeCruiser.setSelected(true);
		largeCruiser.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.CB, 1);
				else shipTypeFilter.replace(ShipContainer.CB, 0);
				
			}
		});
		shipTypeList.add(largeCruiser, g);
		g.gridy++;
		
		JCheckBox heavyCruiser = new JCheckBox("Heavy Cruiser");
		if(shipTypeFilter.get(ShipContainer.CA) == 1) heavyCruiser.setSelected(true);
		heavyCruiser.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.CA, 1);
				else shipTypeFilter.replace(ShipContainer.CA, 0);
				
			}
		});
		shipTypeList.add(heavyCruiser, g);
		g.gridy++;
		
		JCheckBox lightCruiser = new JCheckBox("Light Cruiser");
		if(shipTypeFilter.get(ShipContainer.CL) == 1) lightCruiser.setSelected(true);
		lightCruiser.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.CL, 1);
				else shipTypeFilter.replace(ShipContainer.CL, 0);
				
			}
		});
		shipTypeList.add(lightCruiser, g);
		g.gridy++;
		
		JCheckBox battleship = new JCheckBox("Battleship");
		if(shipTypeFilter.get(ShipContainer.BB) == 1) battleship.setSelected(true);
		battleship.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.BB, 1);
				else shipTypeFilter.replace(ShipContainer.BB, 0);
				
			}
		});
		shipTypeList.add(battleship, g);
		g.gridy++;
		
		JCheckBox battlecruiser = new JCheckBox("Battlecruiser");
		if(shipTypeFilter.get(ShipContainer.BC) == 1) battlecruiser.setSelected(true);
		battlecruiser.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.BC, 1);
				else shipTypeFilter.replace(ShipContainer.BC, 0);
				
			}
		});
		shipTypeList.add(battlecruiser, g);
		g.gridy++;
		
		JCheckBox monitor = new JCheckBox("Monitor");
		if(shipTypeFilter.get(ShipContainer.BM) == 1) monitor.setSelected(true);
		monitor.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.BM, 1);
				else shipTypeFilter.replace(ShipContainer.BM, 0);
				
			}
		});
		shipTypeList.add(monitor, g);
		g.gridy++;
		
		g.gridx = 1;
		g.gridy = 0;
		
		JCheckBox destroyer = new JCheckBox("Destroyer");
		if(shipTypeFilter.get(ShipContainer.DD) == 1) destroyer.setSelected(true);
		destroyer.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.DD, 1);
				else shipTypeFilter.replace(ShipContainer.DD, 0);
				
			}
		});
		shipTypeList.add(destroyer, g);
		g.gridy++;
		
		JCheckBox repairShip = new JCheckBox("Repair Ship");
		if(shipTypeFilter.get(ShipContainer.AR) == 1) repairShip.setSelected(true);
		repairShip.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.AR, 1);
				else shipTypeFilter.replace(ShipContainer.AR, 0);
				
			}
		});
		shipTypeList.add(repairShip, g);
		g.gridy++;
		
		JCheckBox aircraftCarrier = new JCheckBox("Aircraft Carrier");
		if(shipTypeFilter.get(ShipContainer.CV) == 1) aircraftCarrier.setSelected(true);
		aircraftCarrier.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.CV, 1);
				else shipTypeFilter.replace(ShipContainer.CV, 0);
				
			}
		});
		shipTypeList.add(aircraftCarrier, g);
		g.gridy++;
		
		JCheckBox lightAircraftCarrier = new JCheckBox("Light Aircraft Carrier");
		if(shipTypeFilter.get(ShipContainer.CVL) == 1) lightAircraftCarrier.setSelected(true);
		lightAircraftCarrier.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.CVL, 1);
				else shipTypeFilter.replace(ShipContainer.CVL, 0);
				
			}
		});
		shipTypeList.add(lightAircraftCarrier, g);
		g.gridy++;

		JCheckBox submarine = new JCheckBox("Submarine");
		if(shipTypeFilter.get(ShipContainer.SS) == 1) submarine.setSelected(true);
		submarine.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.SS, 1);
				else shipTypeFilter.replace(ShipContainer.SS, 0);
				
			}
		});
		shipTypeList.add(submarine, g);
		g.gridy++;

		JCheckBox submarineCarrier = new JCheckBox("Submarine Carrier");
		if(shipTypeFilter.get(ShipContainer.AM) == 1) submarineCarrier.setSelected(true);
		submarineCarrier.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getStateChange() == ItemEvent.SELECTED) shipTypeFilter.replace(ShipContainer.AM, 1);
				else shipTypeFilter.replace(ShipContainer.AM, 0);
				
			}
		});
		shipTypeList.add(submarineCarrier, g);
		g.gridy++;
		
		shipTypeFilterPanel.add(shipTypeList);
		
		JButton okButton = new JButton("Ok");
		okButton.setPreferredSize(new Dimension(100, 20));
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ships.clear();
				
				if(shipCategoryFilter.contains("N"))
					for (Ship ship : shipList.getShip(ShipContainer.NORMAL_SHIP))
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1) 
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);

									
						
				if(shipCategoryFilter.contains("P"))
					for (Ship ship : shipList.getShip(ShipContainer.PRIORITY_SHIP))
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);
									

				if(shipCategoryFilter.contains("C"))
					for (Ship ship : shipList.getShip(ShipContainer.COLLAB_SHIP)) 
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);
				
				main_scroll_panel.setViewportView(ship_select_scroll_panel(ships));
				repaint();
				
				setEnabled(true);
				
				frame.dispose();
				
			}
		});
		
		rightColumn.add(shipTypeFilterPanel);
		rightColumn.add(okButton);
		
		mainPanel.add(leftColumn);
		mainPanel.add(rightColumn);
		
		frame.add(mainPanel);
		
		frame.pack();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	
	public void shipSelect() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.setBounds(0, 0, 1500, 870);
		panel.setBackground(new Color(0, 0, 0, 0));
		
		panel.add(ship_select_main_window());
		
		add(panel);
		
		setBackground();
		
		pack();
		
		repaint();
		
		setVisible(true);
		
	}
	
	private JPanel ship_select_main_window() {
		
		ships = new ArrayList<Ship>();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0));
		panel.setPreferredSize(new Dimension(1500, 870));
		panel.setBorder(new EmptyBorder(10, 50, 10, 50));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(1000, 820));
		mainPanel.setBackground(new Color(0, 0, 0, 200));
		
		JPanel header = new JPanel();
		header.setBackground(Color.DARK_GRAY);
		header.setBorder(new EmptyBorder(10, 50, 10, 50));
		header.setMaximumSize(new Dimension(1000, 140));
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("Azur Lane Ship Wiki", JLabel.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 40));
		title.setForeground(Color.white);
		
		header.add(title);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		searchPanel.setMinimumSize(new Dimension(800, 70));
		searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		searchPanel.setAlignmentX(CENTER_ALIGNMENT);
		searchPanel.setBackground(new Color(0, 0, 0, 0));
		
		JButton filter = new JButton();
		filter.setMaximumSize(new Dimension(40, 40));
		filter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				filterWindow();
				setEnabled(false);
				
			}
		});
		
		JTextField searchBar = new JTextField();
		searchBar.setMaximumSize(new Dimension(700, 40));
		searchBar.setFont(new Font(searchBar.getFont().getFontName(), Font.PLAIN, 20));
		
		searchBar.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

				ships.clear();
				
				if(shipCategoryFilter.contains("N"))
					for (Ship ship : shipList.getShip(ShipContainer.NORMAL_SHIP)) 
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
							if(ship.getName().toLowerCase().contains(searchBar.getText().toLowerCase()))
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);
						
				if(shipCategoryFilter.contains("P"))
					for (Ship ship : shipList.getShip(ShipContainer.PRIORITY_SHIP))
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
							if(ship.getName().toLowerCase().contains(searchBar.getText().toLowerCase()))
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);

				if(shipCategoryFilter.contains("C"))
					for (Ship ship : shipList.getShip(ShipContainer.COLLAB_SHIP)) 
						if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
							if(ship.getName().toLowerCase().contains(searchBar.getText().toLowerCase()))
								if(shipFactionFilter.contains(ship.getFaction().getName()))
									ships.add(ship);
				
				main_scroll_panel.setViewportView(ship_select_scroll_panel(ships));
				repaint();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel emptyContent = new JLabel();
		emptyContent.setMaximumSize(new Dimension(20, 20));
		
		searchPanel.add(filter);
		searchPanel.add(emptyContent);
		searchPanel.add(searchBar);
		
		header.add(searchPanel);
		
		mainPanel.add(header);
		
		//main_scroll_panel.setPreferredSize(new Dimension(1000, 500));
		
		main_scroll_panel.setOpaque(false);
		
		ships.clear();
		
		if(shipCategoryFilter.contains("N"))
			for (Ship ship : shipList.getShip(ShipContainer.NORMAL_SHIP))
				if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1) 
						if(shipFactionFilter.contains(ship.getFaction().getName()))
							ships.add(ship);

							
				
		if(shipCategoryFilter.contains("P"))
			for (Ship ship : shipList.getShip(ShipContainer.PRIORITY_SHIP))
				if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
						if(shipFactionFilter.contains(ship.getFaction().getName()))
							ships.add(ship);
							

		if(shipCategoryFilter.contains("C"))
			for (Ship ship : shipList.getShip(ShipContainer.COLLAB_SHIP)) 
				if(shipList.findType(ship) != -1 && shipTypeFilter.get(shipList.findType(ship)) == 1)
						if(shipFactionFilter.contains(ship.getFaction().getName()))
							ships.add(ship);
		
		main_scroll_panel.setViewportView(ship_select_scroll_panel(ships));
		main_scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		main_scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		main_scroll_panel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				
				repaint();
				
			}
		});
		
		main_scroll_panel.getViewport().setOpaque(false);
		main_scroll_panel.getViewport().setBackground(new Color(0, 0, 0, 0));
		main_scroll_panel.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
		
		main_scroll_panel.setBackground(new Color(0, 0, 0, 0));
		mainPanel.add(main_scroll_panel);
		
		panel.add(mainPanel);
		
		return panel;
		
	}
	
	private JPanel ship_select_scroll_panel(List<Ship> ships) {
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.gridx = g.gridy = 0;
		g.weightx = g.weighty = 0.5;
		
		for (Ship ship : ships) {
			
			if(!ship.isDownloaded())ship.getData();
			
			JPanel shipHolder = new JPanel();
			shipHolder.setBorder(new EmptyBorder(5, 5, 5, 5));
			shipHolder.setPreferredSize(new Dimension(120, 150));
			shipHolder.setLayout(new BoxLayout(shipHolder, BoxLayout.Y_AXIS));
			shipHolder.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			shipHolder.setBackground(new Color(255, 255, 255, 255));
			
			JLabel shipName = new JLabel("<html><body style='text-align: center'>" + ship.getName() + "</body></html>", JLabel.CENTER);
			shipName.setMinimumSize(new Dimension(100, 30));
			shipName.setOpaque(true);
			shipName.setBackground(new Color(0, 0, 0, 0));
			shipName.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			
			shipHolder.add(ship.loadIcon());
			shipHolder.add(new JSeparator());
			shipHolder.add(shipName);
			
			shipHolder.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
					getContentPane().removeAll();
					ship_detail_window(ship);
					repaint();
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
					shipHolder.setBackground(new Color(255, 255, 255, 255));
					repaint();
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
					shipHolder.setBackground(new Color(255, 255, 255, 100));
					repaint();
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});;
			
			
			panel.add(shipHolder, g);
			
			g.gridx++;
			
			if(g.gridx > 6) {
				
				g.gridx = 0;
				g.gridy++;
				
			}
			
		}
		
		panel.setPreferredSize(new Dimension(950, g.gridy * 170));
		
		
		return panel;
		
	}
	
	public void ship_detail_window(Ship ship) {
		
		if(ship == null) return;
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		panel.add(ship_detail_sidebar_window(ship), BorderLayout.WEST);
		
		panel.add(ship_detail_main_window(ship), BorderLayout.CENTER);
		
		panel.setBounds(0, 0, 1500, 870);
		panel.setBackground(new Color(0, 0, 0, 0));
		add(panel);
	
		setBackground();
		
		pack();
		
		repaint();
		
	}
	
	private void setBackground() {
		
		try {
			
			Image background = ImageIO.read(new File("Source/bg.png"));
			
			JLabel bakcground_label = new JLabel(new ImageIcon(background.getScaledInstance(1511, 850, Image.SCALE_SMOOTH)));

			bakcground_label.setLayout(null);
			bakcground_label.setBounds(0, 0, 1511, 850);
			bakcground_label.setPreferredSize(new Dimension(1000, 850));
			
			add(bakcground_label);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private JPanel ship_detail_sidebar_window(Ship ship) {
	
		Color panel_background = new Color(0, 0, 0, 100);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); //UP LEFT DOWN RIGHT
		
		JPanel img_container = new JPanel();
		try {
			
			img_container = ship.loadImage(0);
			
			//img_container.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(" > Error loading Image");
			
		}
		
		panel.add(img_container);
		
		JPanel name_panel = new JPanel();
		name_panel.setLayout(new FlowLayout());
		name_panel.setPreferredSize(new Dimension(300, 85));
		name_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		name_panel.setBackground(new Color(0, 0, 0, 0));
		
		JLabel name = new JLabel("<html><body style='text-align: center'>" + ship.getName() + "</body></html>", JLabel.CENTER);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setOpaque(true);
		name.setBorder(new EmptyBorder(10, 0, 10, 0));
		name.setFont(new Font("Serif", Font.PLAIN, 30));
		name.setBackground(new Color(0, 0, 0, 0));
		name.setForeground(Color.WHITE);
		name.setPreferredSize(new Dimension(180, 70));
		
		JButton next = new JButton();
		JButton prev = new JButton();
		
		next.setPreferredSize(new Dimension(50, 50));
		prev.setPreferredSize(new Dimension(50, 50));
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ship.nextImage();
				repaint();
				
			}
		});
		
		prev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ship.prevImage();
				repaint();
				
			}
		});
		
		name_panel.add(prev);
		name_panel.add(name);
		name_panel.add(next);
		
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
		ship_type.setBackground(ship.getTypeColor());
		
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
		rarity.setBackground(ship.getRarityColor());
		
		
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
		JLabel faction = new JLabel(ship.getFaction().getName(), SwingConstants.CENTER);
		
		faction_label.setBorder(border);
		faction.setBorder(border);
		
		faction_label.setPreferredSize(new Dimension(170, 50));
		faction.setPreferredSize(new Dimension(170, 50));
		
		faction_label.setFont(new Font(faction_label.getFont().getFontName(), Font.BOLD, 15));
		faction.setFont(new Font(faction.getFont().getFontName(), Font.PLAIN, 13));
		
		faction_label.setBackground(new Color(102, 204, 255));
		faction_label.setOpaque(true);
		
		faction.setOpaque(true);
		faction.setBackground(ship.getFaction().getFactionColor());
		
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
	
	private JPanel ship_detail_main_window(Ship ship) {
		
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
		ship_name.setPreferredSize(new Dimension(500, 50));
		ship_name.setForeground(Color.white);
		
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(100, 50));
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getContentPane().removeAll();
				shipSelect();
				repaint();
				
			}
		});
		
		header.add(back);
		header.add(ship_name);
		header.setBackground(new Color(100, 100, 100, 180));
		header.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel main_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		main_panel.setBackground(new Color(0, 0, 0, 0));
		
		sub_panel.add(header);
		
		if(!ship.getRarity().contentEquals("Unreleased")) {

			JScrollPane main_scroll_panel = new JScrollPane();
			main_scroll_panel.setPreferredSize(new Dimension(770, 730));
			
			main_scroll_panel.setOpaque(false);
			
			main_scroll_panel.setViewportView(ship_detail_main_scroll_page(ship));
			main_scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			main_scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			main_scroll_panel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
				
				@Override
				public void adjustmentValueChanged(AdjustmentEvent arg0) {
					// TODO Auto-generated method stub
					
					repaint();
					
				}
			});
			
			main_scroll_panel.getViewport().setOpaque(false);
			main_scroll_panel.getViewport().setBackground(new Color(0, 0, 0, 0));
			main_scroll_panel.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
			
			main_scroll_panel.setBackground(new Color(0, 0, 0, 0));
			
			JPanel faction_banner = new JPanel();
			faction_banner.setLayout(new BoxLayout(faction_banner, BoxLayout.Y_AXIS));
			
			faction_banner.setBackground(new Color(ship.getFaction().getFactionColor().getRed(), 
												   ship.getFaction().getFactionColor().getGreen(), 
												   ship.getFaction().getFactionColor().getBlue(), 
												   180));
			faction_banner.setPreferredSize(new Dimension(285, 730));
			
			
			if(ship.getFaction().getImg() != null) {
				
				JPanel image_container = new JPanel();
			
				JLabel image = new JLabel(new ImageIcon(ship.getFaction().getImg()), JLabel.CENTER);
				image.setMaximumSize(new Dimension(100, 100));
				image_container.add(image);
				image_container.setMaximumSize(new Dimension(100, 100));
				image_container.setBorder(new EmptyBorder(10, 10, 10, 10));
				image.setBackground(new Color(0, 0, 0, 0));
				image_container.setBackground(new Color(0, 0, 0, 0));
			
				faction_banner.add(image_container);
			
			}
			
			JLabel faction_title = new JLabel("<html><body style='text-align: center'>" + ship.getFaction().getName() + "</body></html>", JLabel.CENTER);
			faction_title.setFont(new Font("Serif", Font.BOLD, 40));
			faction_title.setPreferredSize(new Dimension(270, 50));
			faction_title.setForeground(Color.black);
			faction_title.setAlignmentX(Component.CENTER_ALIGNMENT);
			faction_title.setOpaque(true);
			faction_title.setBackground(new Color(0, 0, 0, 0));
			faction_title.setBorder(new EmptyBorder(10, 10, 10, 10));
			
			faction_banner.add(faction_title);
			faction_banner.setBorder(new EmptyBorder(230, 10, 10, 10));
			
			main_panel.add(main_scroll_panel);
			main_panel.add(faction_banner);
			sub_panel.add(main_panel);
			
		}

		panel.add(sub_panel);
		
		return panel;
		
	}
	
	private JPanel ship_detail_main_scroll_page(Ship ship) {
		
		JPanel panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(1050, (110 * ship.getSkills().size()) + 600));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel sub_header_title = new JPanel();
		sub_header_title.setLayout(new BoxLayout(sub_header_title, BoxLayout.Y_AXIS));
		
		JLabel stat_title = new JLabel("Stats");
		stat_title.setFont(new Font("Serif", Font.PLAIN, 30));
		stat_title.setForeground(Color.white);
		stat_title.setAlignmentX(Component.LEFT_ALIGNMENT);
		stat_title.setBorder(new EmptyBorder(0, 10, 0, 10));
		
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		
		separator.setBorder(new EmptyBorder(10, 10, 10, 0));
		separator.setForeground(Color.white);
		separator.setPreferredSize(new Dimension(1000, 5));
		
		sub_header_title.add(stat_title);
		sub_header_title.add(separator);
		
		sub_header_title.setMaximumSize(new Dimension(1000, 50));
		sub_header_title.setBackground(null);
		
		panel.add(sub_header_title);
		
		JPanel stat_table = new JPanel(new BorderLayout());
		stat_table.add(stat_table(ship), BorderLayout.CENTER);
		stat_table.setAlignmentX(Component.LEFT_ALIGNMENT);
		stat_table.setMaximumSize(new Dimension(620, 220));
		
		panel.add(stat_table);
		
		JPanel sub_header_skill = new JPanel();
		
		sub_header_skill.setLayout(new BoxLayout(sub_header_skill, BoxLayout.Y_AXIS));
		
		JLabel skill_title = new JLabel("Skills");
		skill_title.setFont(new Font("Serif", Font.PLAIN, 30));
		skill_title.setForeground(Color.white);
		skill_title.setAlignmentX(Component.LEFT_ALIGNMENT);
		skill_title.setBorder(new EmptyBorder(10, 10, 0, 10));
		
		JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);
		
		separator1.setBorder(new EmptyBorder(10, 0, 10, 0));
		separator1.setForeground(Color.white);
		separator1.setPreferredSize(new Dimension(1000, 5));
		
		sub_header_skill.add(skill_title);
		sub_header_skill.add(separator1);
		
		sub_header_skill.setMaximumSize(new Dimension(1000, 60));
		sub_header_skill.setBackground(null);
		
		sub_header_skill.setBorder(new EmptyBorder(0, 10, 0, 10));
		
		panel.add(sub_header_skill);
		
		JPanel skill_table = new JPanel(new BorderLayout());
		skill_table.add(skill_table(ship), BorderLayout.CENTER);
		skill_table.setAlignmentX(Component.LEFT_ALIGNMENT);
		skill_table.setMaximumSize(new Dimension(740, 110 * ship.getSkills().size() + 20));
		skill_table.setBackground(null);
		skill_table.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel.add(skill_table);
		
		JPanel sub_header_equipment = new JPanel();
		sub_header_equipment.setLayout(new BoxLayout(sub_header_equipment, BoxLayout.Y_AXIS));
		
		JLabel equipment_title = new JLabel("Equipment");
		equipment_title.setFont(new Font("Serif", Font.PLAIN, 30));
		equipment_title.setForeground(Color.white);
		equipment_title.setAlignmentX(Component.LEFT_ALIGNMENT);
		equipment_title.setBorder(new EmptyBorder(0, 10, 0, 10));
		
		JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
		
		separator2.setBorder(new EmptyBorder(10, 0, 10, 0));
		separator2.setForeground(Color.white);
		separator2.setPreferredSize(new Dimension(1000, 5));
		
		sub_header_equipment.add(equipment_title);
		sub_header_equipment.add(separator2);
		
		sub_header_equipment.setMaximumSize(new Dimension(1000, 50));
		sub_header_equipment.setBackground(null);
		
		sub_header_equipment.setBorder(new EmptyBorder(0, 10, 0, 10));
		
		panel.add(sub_header_equipment);
		
		JPanel equipment_table = new JPanel(new BorderLayout());
		equipment_table.add(equipment_table(ship), BorderLayout.CENTER);
		equipment_table.setAlignmentX(Component.LEFT_ALIGNMENT);
		equipment_table.setMaximumSize(new Dimension(320, 180));
		equipment_table.setBackground(null);
		equipment_table.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel.add(equipment_table);
		
		panel.setBackground(new Color(0,0,0,0));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		
		return panel;
		
	}
	
	private JPanel stat_table(Ship ship) {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		//g.anchor = GridBagConstraints.WEST;
		//g.fill = GridBagConstraints.BOTH;
		g.insets.bottom = g.insets.left = g.insets.right = g.insets.top = 0;
		g.weightx = g.weighty = 1;
		
		Dimension head_dimension = new Dimension(50, 50);
		Dimension content_dimension = new Dimension(100, 50);
		
		Color content_column = new Color(220, 220, 220, 255);
		Color head_column = new Color(255, 255, 255, 200);
		
		Border border = BorderFactory.createLineBorder(Color.black);
		
		JLabel health_label = new JLabel("HP", JLabel.CENTER);
		JLabel armor_label = new JLabel("Armour", JLabel.CENTER);
		JLabel oc_label = new JLabel("OC", JLabel.CENTER);
		JLabel speed_label = new JLabel("SPD", JLabel.CENTER);
		
		health_label.setPreferredSize(head_dimension);
		armor_label.setPreferredSize(head_dimension);
		oc_label.setPreferredSize(head_dimension);
		speed_label.setPreferredSize(head_dimension);
		
		health_label.setOpaque(true);
		armor_label.setOpaque(true);
		oc_label.setOpaque(true);
		speed_label.setOpaque(true);
		
		health_label.setBackground(head_column);
		armor_label.setBackground(head_column);
		oc_label.setBackground(head_column);
		speed_label.setBackground(head_column);
		
		health_label.setBorder(border);
		armor_label.setBorder(border);
		oc_label.setBorder(border);
		speed_label.setBorder(border);
		
		JLabel luck_label = new JLabel("Luck", JLabel.CENTER);
		JLabel evasion_label = new JLabel("EVA", JLabel.CENTER);
		JLabel reload_label = new JLabel("Reload", JLabel.CENTER);
		JLabel accuracy_label = new JLabel("ACC", JLabel.CENTER);
		
		luck_label.setPreferredSize(head_dimension);
		evasion_label.setPreferredSize(head_dimension);
		reload_label.setPreferredSize(head_dimension);
		accuracy_label.setPreferredSize(head_dimension);
		
		luck_label.setOpaque(true);
		evasion_label.setOpaque(true);
		reload_label.setOpaque(true);
		accuracy_label.setOpaque(true);
		
		luck_label.setBackground(head_column);
		evasion_label.setBackground(head_column);
		reload_label.setBackground(head_column);
		accuracy_label.setBackground(head_column);
		
		luck_label.setBorder(border);
		evasion_label.setBorder(border);
		reload_label.setBorder(border);
		accuracy_label.setBorder(border);
		
		JLabel firepower_label = new JLabel("FP", JLabel.CENTER);
		JLabel torpedo_label = new JLabel("TORP", JLabel.CENTER);
		JLabel aviation_label = new JLabel("AVI", JLabel.CENTER);
		JLabel aa_label = new JLabel("AA", JLabel.CENTER);
		
		firepower_label.setPreferredSize(head_dimension);
		torpedo_label.setPreferredSize(head_dimension);
		aviation_label.setPreferredSize(head_dimension);
		aa_label.setPreferredSize(head_dimension);
		
		firepower_label.setOpaque(true);
		torpedo_label.setOpaque(true);
		aviation_label.setOpaque(true);
		aa_label.setOpaque(true);
		
		firepower_label.setBackground(head_column);
		torpedo_label.setBackground(head_column);
		aviation_label.setBackground(head_column);
		aa_label.setBackground(head_column);
		
		firepower_label.setBorder(border);
		torpedo_label.setBorder(border);
		aviation_label.setBorder(border);
		aa_label.setBorder(border);
		
		JLabel asw_label = new JLabel("ASW", JLabel.CENTER);
		
		asw_label.setPreferredSize(head_dimension);
		asw_label.setOpaque(true);
		asw_label.setBackground(head_column);
		asw_label.setBorder(border);
		
		JLabel health = new JLabel(Integer.toString(ship.getHealth()), JLabel.CENTER);
		JLabel armor = new JLabel(Utilities.nvl(ship.getArmour_type(), "-"), JLabel.CENTER);
		JLabel oc = new JLabel(Integer.toString(ship.getOil_consumtion()), JLabel.CENTER);
		JLabel speed = new JLabel(Integer.toString(ship.getSpeed()), JLabel.CENTER);
		
		health.setPreferredSize(content_dimension);
		health.setOpaque(true);
		health.setBackground(content_column);
		health.setBorder(border);
		
		armor.setPreferredSize(content_dimension);
		armor.setOpaque(true);
		armor.setBackground(content_column);
		armor.setBorder(border);
		
		oc.setPreferredSize(content_dimension);
		oc.setOpaque(true);
		oc.setBackground(content_column);
		oc.setBorder(border);
		
		speed.setPreferredSize(content_dimension);
		speed.setOpaque(true);
		speed.setBackground(content_column);
		speed.setBorder(border);
		
		JLabel luck = new JLabel(Integer.toString(ship.getLuck()), JLabel.CENTER);
		JLabel evasion = new JLabel(Integer.toString(ship.getEvasion()), JLabel.CENTER);
		JLabel reload = new JLabel(Integer.toString(ship.getReload()), JLabel.CENTER);
		JLabel accuracy = new JLabel(Integer.toString(ship.getAccuracy()), JLabel.CENTER);
		
		luck.setPreferredSize(content_dimension);
		luck.setOpaque(true);
		luck.setBackground(content_column);
		luck.setBorder(border);
		
		evasion.setPreferredSize(content_dimension);
		evasion.setOpaque(true);
		evasion.setBackground(content_column);
		evasion.setBorder(border);
		
		reload.setPreferredSize(content_dimension);
		reload.setOpaque(true);
		reload.setBackground(content_column);
		reload.setBorder(border);
		
		accuracy.setPreferredSize(content_dimension);
		accuracy.setOpaque(true);
		accuracy.setBackground(content_column);
		accuracy.setBorder(border);
		
		JLabel firepower = new JLabel(Integer.toString(ship.getFirepower()), JLabel.CENTER);
		JLabel torpedo = new JLabel(Integer.toString(ship.getTorpedo()), JLabel.CENTER);
		JLabel aviation = new JLabel(Integer.toString(ship.getAviation()), JLabel.CENTER);
		JLabel aa = new JLabel(Integer.toString(ship.getAnti_air()), JLabel.CENTER);
		
		firepower.setPreferredSize(content_dimension);
		firepower.setOpaque(true);
		firepower.setBackground(content_column);
		firepower.setBorder(border);
		
		torpedo.setPreferredSize(content_dimension);
		torpedo.setOpaque(true);
		torpedo.setBackground(content_column);
		torpedo.setBorder(border);
		
		aviation.setPreferredSize(content_dimension);
		aviation.setOpaque(true);
		aviation.setBackground(content_column);
		aviation.setBorder(border);
		
		aa.setPreferredSize(content_dimension);
		aa.setOpaque(true);
		aa.setBackground(content_column);
		aa.setBorder(border);
		
		JLabel asw = new JLabel(Integer.toString(ship.getAnti_submarine_warfare()), JLabel.CENTER);
		
		asw.setPreferredSize(content_dimension);
		asw.setOpaque(true);
		asw.setBackground(content_column);
		asw.setBorder(border);
		
		g.gridx = g.gridy = 0;
		panel.add(health_label, g);
		g.gridx = 1;
		panel.add(health, g);
		g.gridx = 2;
		panel.add(armor_label, g);
		g.gridx = 3;
		panel.add(armor, g);
		g.gridx = 4;
		panel.add(speed_label, g);
		g.gridx = 5;
		panel.add(speed, g);
		g.gridx = 6;
		panel.add(oc_label, g);
		g.gridx = 7;
		panel.add(oc, g);
		
		g.gridy = 1; g.gridx = 0;
		panel.add(luck_label, g);
		g.gridx = 1;
		panel.add(luck, g);
		g.gridx = 2;
		panel.add(evasion_label, g);
		g.gridx = 3;
		panel.add(evasion, g);
		g.gridx = 4;
		panel.add(reload_label, g);
		g.gridx = 5;
		panel.add(reload, g);
		g.gridx = 6;
		panel.add(accuracy_label, g);
		g.gridx = 7;
		panel.add(accuracy, g);
		
		g.gridy = 2; g.gridx = 0;
		panel.add(firepower_label, g);
		g.gridx = 1;
		panel.add(firepower, g);
		g.gridx = 2;
		panel.add(torpedo_label, g);
		g.gridx = 3;
		panel.add(torpedo, g);
		g.gridx = 4;
		panel.add(aviation_label, g);
		g.gridx = 5;
		panel.add(aviation, g);
		g.gridx = 6;
		panel.add(aa_label, g);
		g.gridx = 7;
		panel.add(aa, g);
		
		g.gridy = 3; g.gridx = 0;
		panel.add(asw_label, g);
		g.gridx = 1;
		panel.add(asw, g);
		
		panel.setBackground(new Color(0, 0, 0, 0));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setPreferredSize(new Dimension(840, 200));
		
		return panel;
		
	}
	
	private JPanel skill_table(Ship ship) {
		
		JPanel panel = new JPanel();
		
		if(ship.getSkills() == null) {
			
			System.out.println("No skill");
			
		} else {
			
			panel.setLayout(new GridBagLayout());
			
			GridBagConstraints g = new GridBagConstraints();
			
			g.gridx = g.gridy = 0;
			
			g.insets.bottom = g.insets.left = g.insets.right = g.insets.top = 0;
			g.weightx = g.weighty = 1;
			
			for (Skill skill : ship.getSkills()) {
				
				g.gridx = 0;
				
				JPanel skill_title_container = new JPanel();
				JLabel skill_title = new JLabel("<html><body style='text-align: center'>" + skill.getName() + "</body></html>", JLabel.CENTER);
				skill_title.setPreferredSize(new Dimension(100, 100));
				skill_title.setOpaque(true);
				skill_title.setBackground(skill.getSkillColor());
				skill_title.setAlignmentX(Component.CENTER_ALIGNMENT);
				skill_title.setFont(new Font(skill_title.getFont().getFontName(), Font.BOLD, 15));
				skill_title_container.add(skill_title);
				skill_title_container.setMaximumSize(new Dimension(110, 110));
				
				skill_title_container.setBackground(new Color(255, 255, 153, 100));
				
				panel.add(skill_title_container, g);
				
				g.gridx = 1;
				JPanel skill_desc_container = new JPanel();
				JLabel skill_desc = new JLabel("<html>" + skill.getDescription() + "</html>", JLabel.LEFT);
				skill_desc.setPreferredSize(new Dimension(600, 100));
				skill_desc.setOpaque(true);
				skill_desc.setBackground(new Color(220, 220, 220, 255));
				skill_desc.setBorder(new EmptyBorder(10, 10, 10, 10));
		
				skill_desc_container.add(skill_desc);
				skill_desc_container.setMaximumSize(new Dimension(620, 110));
				
				panel.add(skill_desc, g);
				
				g.gridy++;
				
			}
			
		}
		
		panel.setBackground(new Color(255, 255, 255, 100));
		
		return panel;
		
	}
	
	private JPanel equipment_table(Ship ship) {
		
		JPanel panel = new JPanel();
		
		if(ship.getSkills() == null) {
			
			System.out.println("No skill");
			
		} else {
			
			panel.setLayout(new GridBagLayout());
			
			GridBagConstraints g = new GridBagConstraints();
			
			g.gridx = g.gridy = 0;
			
			g.insets.bottom = g.insets.left = g.insets.right = g.insets.top = 0;
			g.weightx = g.weighty = 1;
			
			JLabel equipment_label = new JLabel("Equipment Name", JLabel.CENTER);
			
			equipment_label.setPreferredSize(new Dimension(150, 40));
			equipment_label.setOpaque(true);
			equipment_label.setBackground(Color.gray);
			equipment_label.setAlignmentX(Component.CENTER_ALIGNMENT);
			equipment_label.setBorder(BorderFactory.createLineBorder(Color.black));
			equipment_label.setFont(new Font(equipment_label.getFont().getFontName(), Font.BOLD, 15));
			
			panel.add(equipment_label, g);
			g.gridx++;
			
			JLabel efficiency_label = new JLabel("Equipment Name", JLabel.CENTER);
			
			efficiency_label.setPreferredSize(new Dimension(150, 40));
			efficiency_label.setOpaque(true);
			efficiency_label.setBackground(Color.gray);
			efficiency_label.setAlignmentX(Component.CENTER_ALIGNMENT);
			efficiency_label.setBorder(BorderFactory.createLineBorder(Color.black));
			efficiency_label.setFont(new Font(equipment_label.getFont().getFontName(), Font.BOLD, 15));
			
			panel.add(efficiency_label, g);
			
			g.gridy++;
			
			for (Equipment equip : ship.getEquipments()) {
				
				g.gridx = 0;
				
				JLabel equipment_name = new JLabel(equip.getEquipment_name(), JLabel.CENTER);
				equipment_name.setPreferredSize(new Dimension(150, 40));
				equipment_name.setOpaque(true);
				equipment_name.setBackground(new Color(255, 255, 255, 200));
				equipment_name.setBorder(BorderFactory.createLineBorder(Color.black));
				equipment_name.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				panel.add(equipment_name, g);
				
				g.gridx = 1;
				JLabel efficiency = new JLabel(equip.getEfficiency(), JLabel.CENTER);
				efficiency.setPreferredSize(new Dimension(150, 40));
				efficiency.setOpaque(true);
				efficiency.setBackground(new Color(255, 255, 255, 200));
				efficiency.setBorder(BorderFactory.createLineBorder(Color.black));
		
				panel.add(efficiency, g);
				
				g.gridy++;
				
			}
			
		}
		
		panel.setBackground(new Color(255, 255, 255, 100));
		
		return panel;
		
	}
	
}
