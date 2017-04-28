package terrain.gui;

import static java.awt.Color.BLACK;
import static java.awt.Color.white;
import static terrain.gui.Global.Tour.GAME;
import static terrain.gui.Global.Tour.GUI;
import static terrain.gui.Global.Tour.MENU;
import static terrain.gui.Global.Tour.NONE;
import static terrain.gui.Global.Tour.START;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import terrain.domain.Terrain;
import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

public class TerrainGUI extends JFrame{
	
	private static final long serialVersionUID = -2555556685103719347L;

	private static final Logger LOGGER = Logger.getLogger(TerrainGUI.class);

	private final static String RESOURCES_PATH = "src/main/resources/";

	private static BufferedImage BACKGROUND_IMG;
	private static BufferedImage OEUF_IMG;
	private static BufferedImage ROCHER_IMG;
	private static BufferedImage PERSO_N_IMG;
	private static BufferedImage PERSO_S_IMG;
	private static BufferedImage PERSO_E_IMG;
	private static BufferedImage PERSO_O_IMG;

	private static int cellWidth;
	private static int cellHeight;
	private static int nbLine;
	private static int nbCol;

	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 800;

	private JPanel pan = new ImagePanel();

	private static TerrainGUI instance = null;
	
	private static enum Version{CLASSIQUE, MEXICO};
	private static Version version = Version.CLASSIQUE;
	
	private static int countCactus = 1;

	public static TerrainGUI getInstance(){
		if(instance == null){
			instance = new TerrainGUI();
		}
		return instance;
	}

	private TerrainGUI(){

		super();
		this.setTitle("Chasse aux oeufs");
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		// Bar de menu
		final JMenuBar menuBar = new JMenuBar();	
		// Fichier
		final JMenu menuFichier = new JMenu("Fichier"); 
		final JMenuItem menuTerrain = new JMenuItem(new TerrainAction());
		menuFichier.add(menuTerrain); 

		final JMenuItem menuEnfant = new JMenuItem(new EnfantAction());		
		menuFichier.add(menuEnfant);

		menuBar.add(menuFichier);
		//Jardin
		final JMenu menuJardin = new JMenu("Jardin");
		final JMenuItem menuPause = new JMenuItem(new PauseAction());
		menuJardin.add(menuPause);

		final JMenuItem menuPlay = new JMenuItem(new PlayAction());
		menuJardin.add(menuPlay);

		final JMenuItem menuReplay = new JMenuItem(new ReplayAction());
		menuJardin.add(menuReplay);

		menuBar.add(menuJardin);

		this.setJMenuBar(menuBar);

		startScreen();

	}

	public boolean init(){
		LOGGER.debug("init");

		// En attente de son tour
		while(Global.tour != GUI){
			System.out.print(""); // le while en a besoin pour tourner
		}

		// Recuperation du terrain
		Terrain terrain = Global.txtTerrain.getTerrain();

		// Initialisation des variables pour la taille des objets
		nbLine = terrain.getLigne();
		nbCol = terrain.getColonne();
		cellWidth = WINDOW_WIDTH / (nbCol+2);
		cellHeight = WINDOW_HEIGHT / (nbLine+2);

		// Initialisation des images
		try{
			if(version == Version.CLASSIQUE){
			BACKGROUND_IMG = ImageIO.read(new File(RESOURCES_PATH + "background.jpg"));
			OEUF_IMG = ImageIO.read(new File(RESOURCES_PATH + "oeuf.png"));
			ROCHER_IMG = ImageIO.read(new File(RESOURCES_PATH + "rocher2.png"));
			PERSO_N_IMG = ImageIO.read(new File(RESOURCES_PATH + "perso_nord.png"));
			PERSO_S_IMG = ImageIO.read(new File(RESOURCES_PATH + "perso_sud.png"));
			PERSO_E_IMG = ImageIO.read(new File(RESOURCES_PATH + "perso_est.png"));
			PERSO_O_IMG = ImageIO.read(new File(RESOURCES_PATH + "perso_ouest.png"));
			}
			else{ //Mexico
				BACKGROUND_IMG = ImageIO.read(new File(RESOURCES_PATH + "desert2cartoon.png"));
				OEUF_IMG = ImageIO.read(new File(RESOURCES_PATH + "tacos_cartoon.png"));
				ROCHER_IMG = ImageIO.read(new File(RESOURCES_PATH + "cactus1cartoon.png"));
				PERSO_N_IMG = ImageIO.read(new File(RESOURCES_PATH + "mexicain1cartoonnord.png"));
				PERSO_S_IMG = ImageIO.read(new File(RESOURCES_PATH + "mexicain1cartoonsud.png"));
				PERSO_E_IMG = ImageIO.read(new File(RESOURCES_PATH + "mexicain1cartoonest.png"));
				PERSO_O_IMG = ImageIO.read(new File(RESOURCES_PATH + "mexicain1cartoonouest.png"));
			}
		}catch(Exception e){
			LOGGER.debug("Un des fichiers image est manquant dans le dossier " + RESOURCES_PATH);
			JOptionPane.showMessageDialog(null, "Un des fichiers image est manquant dans le dossier " + RESOURCES_PATH, "Erreur", JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
			//return false;
		}

		return true;
	}

	public void run(){

		// Initialisation du panel et de la frame
		pan = new ImagePanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		this.setContentPane(pan);
		pack();
		this.setVisible(true);

		// run
		while(true){
			System.out.print(""); // le while en a besoin pour tourner

			// Tour de GuiThread
			if(Global.tour == GUI){
				pan.repaint(); // Mise a jour de l'affichage
				Global.tour = GAME; // Tour de GameThread
			}
			else if(Global.tour == NONE) // Il ne se passe rien
				break;
		}
	}

	private class TerrainAction extends AbstractAction {

		private static final long serialVersionUID = -8428959055156680998L;

		private TerrainAction() {
			super("Ouvrir le fichier Terrain");
		}

		public void actionPerformed(ActionEvent e) {
			// Acces au repertoire courant
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch(Exception ex) {	}

			// Initialisation du fileChooser
			JFileChooser fileChooser = new JFileChooser(repertoireCourant);			
			fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier texte", "txt", "text"));
			fileChooser.showOpenDialog(null);

			// Selection du fichier
			Global.TERRAIN_FILE = fileChooser.getSelectedFile();

			// Mise a jour de l'affichage
			if(Global.TERRAIN_FILE != null && Global.ENFANT_FILE != null){
				LOGGER.debug("Nouveau fichier Terrain");
				pan.repaint();
				Global.tour = START;
			}
		}
	}

	private class EnfantAction extends AbstractAction {

		private static final long serialVersionUID = -719439687184717555L;

		private EnfantAction() {
			super("Ouvrir le fichier Enfant");
		}

		public void actionPerformed(ActionEvent e) {
			// Acces au repertoire courant
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch(Exception ex) {}

			// Initialisation du fileChooser
			JFileChooser fileChooser = new JFileChooser(repertoireCourant);			
			fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier texte", "txt", "text"));
			fileChooser.showOpenDialog(null);

			// Selection du fichier
			Global.ENFANT_FILE = fileChooser.getSelectedFile();

			// Mise a jour de l'affichage
			if(Global.TERRAIN_FILE != null && Global.ENFANT_FILE != null){
				LOGGER.debug("Nouveau fichier Enfant");
				pan.repaint();
				Global.tour = START;
				System.out.print(" "); // en a besoin pour tourner
			}
		}
	}  

	private class PauseAction extends AbstractAction {

		private static final long serialVersionUID = -719439687184717555L;

		private PauseAction() {
			super("Pause");
		}

		public void actionPerformed(ActionEvent e) {
			Global.pause = true;
		}
	}

	private class PlayAction extends AbstractAction {

		private static final long serialVersionUID = -719439687184717555L;

		private PlayAction() {
			super("Play");
		}

		public void actionPerformed(ActionEvent e) {
			Global.pause = false;
		}
	}

	private class ReplayAction extends AbstractAction {

		private static final long serialVersionUID = -719439687184717555L;
		private Version v = null;

		private ReplayAction() {
			super("Replay");
		}

		private ReplayAction(String nom) {
			super(nom);
		}
		
		private ReplayAction(Version v) {
			super(v.toString());
			this.v = v;
		}

		public void actionPerformed(ActionEvent e) {
			if(this.v != null)
				version = this.v;
			
			Global.tour = START;
		}
	}

	private class StartAction extends AbstractAction {

		private static final long serialVersionUID = -719439687184717555L;

		private StartAction() {
			super("Retour au menu");
		}

		public void actionPerformed(ActionEvent e) {
			Global.tour = MENU;
			LOGGER.debug("MENU");
			startScreen();
		}
	}

	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = -7412338095369224319L;


		public ImagePanel(){
			super();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if(Global.txtTerrain.getInitialized()){
				synchronized (Global.txtTerrain){
					// Initialisation
					AbstractCase[][] table = Global.txtTerrain.getTerrain().getTable();
					g.drawImage(BACKGROUND_IMG, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

					// Parcours du terrain
					for(int i=0; i<nbLine; i++){
						for(int j=0; j<nbCol; j++){
							
							// Rocher
							if(table[i][j] instanceof Rocher){
								// On attribue un des 4 cactus au Rocher
								if(version == Version.MEXICO && ((Rocher)table[i][j]).getImg() == null){
									try {
										ROCHER_IMG = ImageIO.read(new File(RESOURCES_PATH + "cactus"+ countCactus +"cartoon.png"));
									} catch (IOException e) {
										LOGGER.debug("Un des fichiers image est manquant dans le dossier " + RESOURCES_PATH);
										JOptionPane.showMessageDialog(null, "Un des fichiers image est manquant dans le dossier " + RESOURCES_PATH, "Erreur", JOptionPane.WARNING_MESSAGE);
										System.exit(-1);
										//return false;
									}
									((Rocher)table[i][j]).setImg(ROCHER_IMG);
									countCactus++;
									if(countCactus > 4)
										countCactus = 1;
								}
								else if(version == Version.CLASSIQUE && ((Rocher)table[i][j]).getImg() == null){
									((Rocher)table[i][j]).setImg(ROCHER_IMG);
								}
								g.drawImage(((Rocher)table[i][j]).getImg(), cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
							}
							// Chocolat
							else if(table[i][j] instanceof Chocolat){
								g.drawImage(OEUF_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
								// Nombre de chocolats
								g.drawString(String.valueOf(((Chocolat)table[i][j]).getNombre()), cellHeight*j + cellWidth/2, cellWidth*i + cellHeight + 6);
							}
							// Enfant
							else if(table[i][j] instanceof Enfant){
								if(((Enfant)table[i][j]).getPrendChoco() > 0){ // Prend un choco
									g.drawImage(OEUF_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
									g.drawString(String.valueOf(((Enfant)table[i][j]).getPrendChoco()), cellHeight*j + cellWidth/2, cellWidth*i + cellHeight + 6);
								}
								switch(((Enfant)table[i][j]).getOrientation()){
								case NORD:
									g.drawImage(PERSO_N_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
									break;
								case SUD:
									g.drawImage(PERSO_S_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
									break;
								case EST:
									g.drawImage(PERSO_E_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
									break;
								case OUEST:
									g.drawImage(PERSO_O_IMG, cellHeight*j, cellWidth*i, cellWidth, cellHeight, null);
									break;
								}
								// Nom de l'enfant
								if(version == Version.MEXICO)
									g.setColor(Color.MAGENTA);
								g.drawString(((Enfant)table[i][j]).getNom(), cellHeight*j + cellWidth/3, cellWidth*i + cellHeight + 6);
							}
							else{
								// RIEN
							}
						}
					}
				}
			}
		}
	}
	public void endScreen(){
		LOGGER.debug("endScreen");
		pan.repaint();

		// Titre
		JLabel titreLabel = new JLabel("FIN");
		titreLabel.setAlignmentX(CENTER_ALIGNMENT);
		titreLabel.setFont(new Font(titreLabel.getFont().getName(), titreLabel.getFont().getStyle(), (WINDOW_HEIGHT+100)/10));
		titreLabel.setForeground(BLACK);
		if(version == Version.MEXICO)
			titreLabel.setForeground(white);
		pan.add(titreLabel);

		// Espace
		pan.add(new JLabel(" "));

		// Parcours des scores
		for (Map.Entry<String, Integer> entry : Global.txtTerrain.getTerrain().getScoreEnfants().entrySet()) {
			String nom = entry.getKey();
			int score = entry.getValue();

			// Affichage des scores
			JLabel label = new JLabel(nom + " : " + score);
			label.setAlignmentX(CENTER_ALIGNMENT);
			label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), (WINDOW_HEIGHT+100)/40));
			label.setForeground(BLACK);
			if(version == Version.MEXICO)
				label.setForeground(white);
			pan.add(label);
		}

		// Espace
		pan.add(new JLabel(" "));

		// Bouton
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton button = new JButton("Retour au menu");
		button.addActionListener(new StartAction());
		button.setPreferredSize(new Dimension(WINDOW_WIDTH/5, WINDOW_HEIGHT/10));
		buttonPanel.add(button);
		buttonPanel.setOpaque(false);
		pan.add(buttonPanel);

		this.pack();
	}

	public void startScreen(){
		// Initialisation
		pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		pan.setBackground(Color.CYAN);
		pan.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		pan.setAlignmentX(CENTER_ALIGNMENT);
		this.setContentPane(pan);
		
		// Titre
		JLabel label = new JLabel("Chasse aux oeufs");
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), (WINDOW_HEIGHT+100)/10));
		pan.add(label);

		// Espace
		label = new JLabel(" ");
		label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), (WINDOW_HEIGHT+100)/20));
		pan.add(label);

		// Bouton
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		JButton button = new JButton("START Classique");
		button.setAction(new ReplayAction(Version.CLASSIQUE));
		button.setPreferredSize(new Dimension(WINDOW_WIDTH/10, WINDOW_HEIGHT/10));
		button.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.setOpaque(false);
		buttonPanel.add(button);
		
		button = new JButton("START Mexico");
		button.setAction(new ReplayAction(Version.MEXICO));
		button.setPreferredSize(new Dimension(WINDOW_WIDTH/10, WINDOW_HEIGHT/10));
		button.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.setOpaque(false);
		buttonPanel.add(button);
		
		pan.add(buttonPanel);
		
		this.pack();
		this.setVisible(true);
	}
}
