package terrain.launcher;

import terrain.domain.Jardin;
import terrain.domain.abstractcase.enfant.Enfant;
import terrain.domain.abstractcase.enfant.Orientation;
import terrain.gui.TerrainGUI;

public class Launcher {

	public static void main(String[] args) {
		//TerrainGUI terrainGUI = new TerrainGUI();
	    //terrainGUI.setVisible(true);
		
		Jardin jardin = Jardin.getInstance(5, 5);
		jardin.setCase(1, 1, new Enfant('E', "AADA", "OuiOui"));
		jardin.setCase(4, 2, new Enfant('S', "AGAG", "OuiOui"));
		jardin.bougerEnfants();
	}
}
