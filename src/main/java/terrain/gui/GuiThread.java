package terrain.gui;

import static terrain.gui.Global.Tour.MENU;
import static terrain.gui.Global.Tour.START;

import org.apache.log4j.Logger;

public class GuiThread implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(GameThread.class);
	private TerrainGUI terrainGUI;

	public GuiThread(){
		terrainGUI = TerrainGUI.getInstance();
		terrainGUI.setVisible(true);
	}

	public void run(){
		LOGGER.debug("run");
		while(true){

			if(terrainGUI.init()){ // Initialisation terminée
				// Lance la fenetre
				terrainGUI.run();

				if(Global.tour != MENU){
					// Ecran de fin
					terrainGUI.endScreen();
				}

				// On attend un nouveau lancement
				while(Global.tour != START){
					System.out.print(""); // le while en a besoin pour tourner
				}
			}
		}
	}

}
