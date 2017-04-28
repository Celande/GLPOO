package terrain.gui;

import static terrain.gui.Global.Tour.GAME;
import static terrain.gui.Global.Tour.GUI;
import static terrain.gui.Global.Tour.MENU;
import static terrain.gui.Global.Tour.NONE;
import static terrain.gui.Global.Tour.START;

import java.io.File;
import org.apache.log4j.Logger;

import terrain.domain.Terrain;
import terrain.test.TxtTerrain;

public class GameThread implements Runnable {
	private TxtTerrain txtTerrain;
	private Terrain terrain;
	private static final Logger LOGGER = Logger.getLogger(GameThread.class);
	private static final int SLEEP_DELAY = 100; // ms

	public GameThread(File fileTerrain, File fileEnfants){
		// Initialisation des variables globales
		Global.TERRAIN_FILE = fileTerrain;
		Global.ENFANT_FILE = fileEnfants;
	}

	public void init(){
		LOGGER.debug("init");

		// Attente du demarrage
		while(Global.tour != START){
			System.out.print(""); // le while en a besoin pour tourner
		}

		// Initialisation du terrain
		Global.txtTerrain = new TxtTerrain();
		Global.txtTerrain.init(Global.TERRAIN_FILE, Global.ENFANT_FILE);

		// Tour de l'affichage
		Global.tour = GUI;
	}

	public boolean runGame(){
		LOGGER.debug("runGame");
		
		callPause();

		// Ne peut tourner que s'il reste des mouvements aux enfants
		if(terrain.getCoordEnfants() != null && terrain.getCoordEnfants().size() > 0){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				LOGGER.debug("broken");
				e.printStackTrace();
			}
			if(Global.tour != START){ // En cours d'initialisation a partir des fichiers
				callPause();
				terrain.bougerEnfants();
				callPause();
			}
			else // Si initialisation, pas de mouvements
				return false;
			callPause();
			return true;
		}
		return false;
	}

	public void run(){
		// Initialisation
		init();
		
		// run
		while(true){
			// Le terrain est initialise
			if(Global.txtTerrain.getInitialized()){
				terrain = Global.txtTerrain.getTerrain();
				
				// Un lancement
				while(true){
					
					System.out.print(""); // le while en a besoin pour tourner
					
					// Tour du GameThread
					if(Global.tour == GAME){
						callPause();
						try {
							Thread.sleep(SLEEP_DELAY);
						} catch (InterruptedException e) {
							LOGGER.debug("broken");
							e.printStackTrace();
						}
						callPause();
						if(runGame() && Global.tour != MENU){ // Mise a jour du terrain
							callPause();
							Global.tour = GUI; // Tour du GUIThread
							callPause();
						}
						else{
							if(Global.tour == START){ // Demarrage donc on quitte la boucle
								break;
							}
							else if(Global.tour == MENU){ // Retour au menu
								break;
							}
							// Il ne se passe rien
							Global.tour = NONE;
							break;
						}
					}
					if(Global.tour == START) // Demarrage donc on quitte la boucle
						break;
					else if(Global.tour == MENU) // Retour au menu
						break;
					else if (Global.tour == NONE) // Il ne se passe rien
						break;
				}
				// On attend un nouveau demarrage
				while(Global.tour != START){
					System.out.print(""); // le while en a besoin pour tourner
				}
				
				// Initialisation
				init();
			}
		}
	}

	public TxtTerrain getTxtTerrain(){
		return txtTerrain;
	}
	
	private void callPause(){
		while(Global.pause){
			System.out.print(" ");
		}
	}
}
