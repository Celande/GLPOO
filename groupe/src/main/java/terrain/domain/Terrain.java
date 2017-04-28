package terrain.domain;

/**
 * Interface permettant de mettre en place un terrain de chasse
 */

import java.util.HashMap;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.enfant.Enfant;

public interface Terrain {
	public Integer getLigne();
	public Integer getColonne();
	
	public void initCoordEnfants();
	
	public AbstractCase[][] getTable();
	public AbstractCase getCase(int colonne, int ligne);
	public boolean setCase(int colonne, int ligne, AbstractCase abstractCase);
	public void effacerEnfant(int ligne, int colonne);
	
	public void bougerEnfants();
	public void bougerEnfantsBoucle();
	
	public boolean equals(Terrain terrain);
	
	public HashMap<int[], Enfant> getCoordEnfants();
	public HashMap<String, Integer> getScoreEnfants();
}
