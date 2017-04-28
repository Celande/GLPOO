package terrain.gui;

import static terrain.gui.Global.Tour.MENU;
import java.io.File;

import terrain.test.TxtTerrain;



public class Launcher {

	private final static String FILE_RESOURCES_PATH = "src/test/resources/";
	private final static String TERRAIN_FILE_NAME = "terrain-01.txt";
	private final static String ENFANT_FILE_NAME = "enfant-02.txt";

	private static File TERRAIN_FILE = new File(FILE_RESOURCES_PATH + TERRAIN_FILE_NAME);
	private static File ENFANT_FILE = new File(FILE_RESOURCES_PATH + ENFANT_FILE_NAME);
 
	public static void main(String[] args) {
		// Initialisation des variables globales
		Global.tour = MENU;
		Global.txtTerrain = new TxtTerrain();
		
		// Creation des threads
		final Thread gameThread = new Thread(new GameThread(TERRAIN_FILE, ENFANT_FILE));
		final Thread guiThread = new Thread(new GuiThread());
		
		// Demarrage du programme
		guiThread.start();
		gameThread.start();
	}

}
