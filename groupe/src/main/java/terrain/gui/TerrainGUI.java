package terrain.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.lowagie.text.Image;

import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

public class TerrainGUI extends JFrame{

	/**
	 * http://research.jacquet.xyz/teaching/java/swing-selection-fichier/
	 */
	private static final long serialVersionUID = -2555556685103719347L;
	private JTable terrain;
	private final static String RESOURCES_PATH = "src/main/resources/";
	private static File TERRAIN_FILE = null;
	private static File ENFANT_FILE = null;

	public TerrainGUI(){

		super();
		setTitle("Chasse aux oeufs");
		setPreferredSize(new Dimension(1200, 800));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ZModel model = new ZModel(data);
		//this.terrain = new JTable(model);
		//this.getContentPane().add(terrain, BorderLayout.CENTER);
		this.setContentPane(new JLabel(new ImageIcon(RESOURCES_PATH + "background.jpg")));
		//	terrain.setDefaultRenderer(CustomCellRenderer.class, new CustomCellRenderer());

		final JMenuBar menuBar = new JMenuBar();		 
		final JMenu menuFichier = new JMenu("Fichier"); 
		final JMenuItem menuTerrain = new JMenuItem(new TerrainAction());
		menuFichier.add(menuTerrain); 
		final JMenuItem menuEnfant = new JMenuItem(new EnfantAction());
		menuFichier.add(menuEnfant);
		menuBar.add(menuFichier);

		setJMenuBar(menuBar);

		pack();
	}

	private class TerrainAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8428959055156680998L;

		private TerrainAction() {
			super("Ouvrir le fichier Terrain");
		}

		public void actionPerformed(ActionEvent e) {
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch(Exception ex) {	}

			JFileChooser fileChooser = new JFileChooser(repertoireCourant);			
			fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier texte", "txt", "text"));
			fileChooser.showOpenDialog(null);

			TERRAIN_FILE = fileChooser.getSelectedFile();

			if(TERRAIN_FILE != null && ENFANT_FILE != null){
				// update the panel
				// TODO
			}
		}
	}

	private class EnfantAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -719439687184717555L;

		private EnfantAction() {
			super("Ouvrir le fichier Enfant");
		}

		public void actionPerformed(ActionEvent e) {
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch(Exception ex) {}

			JFileChooser fileChooser = new JFileChooser(repertoireCourant);			
			fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier texte", "txt", "text"));
			fileChooser.showOpenDialog(null);

			ENFANT_FILE = fileChooser.getSelectedFile();

			if(TERRAIN_FILE != null && ENFANT_FILE != null){
				// update the panel
				// TODO
			}
		}
	}     
}
