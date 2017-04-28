package terrain.gui;

import java.io.File;

import terrain.test.TxtTerrain;

/**
 * Classe reunissant les variables globales utilisees
 * par les deux threads
 * @author Célande
 *
 */

class Global {
	// Utiliser pour determiner a quel thread c'est le tour
	public enum Tour {GUI, GAME, NONE, START, MENU};
	public static Tour tour;

	// Permet l'initialisation du terrain
	public static TxtTerrain txtTerrain;

	// Fichier utiliser pour initialiser les enfants et le terrain
	public static File TERRAIN_FILE;
	public static File ENFANT_FILE;
	
	public static boolean pause = false;
}
