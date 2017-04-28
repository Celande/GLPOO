package terrain.domain;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

/**
 * Zone ou va se passer la chasse aux oeufs
 * @author Célande
 *
 */

public class Jardin implements Terrain {

	private int ligne;
	private int colonne;
	private AbstractCase[][] table;
	private HashMap<int[], Enfant> coordEnfants;
	private HashMap<String, Integer> scoreEnfants;

	private static final Logger LOGGER = Logger.getLogger(Jardin.class);

	public Jardin(int colonne, int ligne){
		// Initialisation des lignes et colonnes
		this.ligne = ligne;
		this.colonne = colonne;
		this.coordEnfants = new HashMap<int[], Enfant>();

		// Initialisation du tableau avec toutes les cases vides
		table = new AbstractCase[ligne][];
		for(int i=0; i<ligne;i++){
			table[i] = new AbstractCase[colonne];

			for(int j=0; j<colonne;j++){
				table[i][j] = new CaseVide();
			}
		}
	}

	/**
	 * Initialisation des HashMap
	 * Elles evitent d'avoir a parcourir le tableau a chaque fois qu'on met a jour un enfant
	 */
	public void initCoordEnfants(){
		LOGGER.debug("initCoordEnfants"); 

		// Initialisation
		coordEnfants = new HashMap<int[], Enfant>();
		scoreEnfants = new HashMap<String, Integer>();

		// Parcours de la table
		for(int i=0; i<ligne; i++){
			for(int j=0; j<colonne; j++){

				if(table[i][j] instanceof Enfant){
					int[] coord = {i, j};
					coordEnfants.put(coord, ((Enfant)table[i][j]));
					scoreEnfants.put(((Enfant)table[i][j]).getNom(), ((Enfant)table[i][j]).getScore());
				}
			}
		}
	}

	public AbstractCase[][] getTable() {

		return this.table;
	}

	/**
	 * Indices considérés comme étant de 1 à ligne ou colonne
	 */
	public AbstractCase getCase(int colonne, int ligne) {
		ligne--;
		colonne--;
		return this.table[ligne][colonne];
	}

	/**
	 * Indices considérés comme étant de 1 à ligne ou colonne
	 */
	public boolean setCase(int colonne, int ligne, AbstractCase abstractCase) throws UnsupportedOperationException {

		ligne--;
		colonne--;
		
		// L'objet doit être ajoutée dans le tableau
		if(ligne >= this.ligne || ligne < 0 || colonne >= this.colonne || colonne < 0){
			LOGGER.debug("La ligne ou la colonne est hors du tableau.");
			return false;
		}

		// On ne peut pas ajouter un objet nul
		if(abstractCase == null)
			throw new UnsupportedOperationException("L'objet à ajouter est nul.");

		// Ajout à un emplacement vide
		if(this.table[ligne][colonne] instanceof CaseVide){
			this.table[ligne][colonne] = abstractCase;
			return true;
		}

		// L'enfant rencontre un chocolat
		if(this.table[ligne][colonne] instanceof Chocolat && abstractCase instanceof Enfant){
			((Enfant)abstractCase).recupChoco((Chocolat)table[ligne][colonne]);
			this.table[ligne][colonne] = abstractCase;
			return true;
		}
		else{ // La case n'est pas vide ou il ne s'agit pas d'un enfant
			return false;
		}
	}

	public void effacerEnfant(int ligne, int colonne)throws UnsupportedOperationException{
		if(this.table[ligne][colonne] instanceof Enfant)
			this.table[ligne][colonne] = new CaseVide();
		else{
			LOGGER.debug("ligne = " + (ligne+1) + " colonne = " + (colonne+1));
			throw new UnsupportedOperationException("La case n'est pas vide ou il ne s'agit pas d'un enfant.");
		}
	}

	public Integer getLigne() {

		return this.ligne;
	}

	public Integer getColonne() {

		return this.colonne;
	}

	public void bougerEnfants() {
		LOGGER.debug("bougerEnfants");

		// Garde en mémoire la nouvelle position de l'enfant
		HashMap<int[], Enfant> save = new HashMap<int[], Enfant>();

		// Parcours de coordEnfants
		for (Map.Entry<int[], Enfant> entry : coordEnfants.entrySet()) {
			int[] oldCoord = entry.getKey();
			Enfant enfant = entry.getValue();

			if(enfant.getPrendChoco() == 0){
				// Il reste des deplacements
				if(enfant.getDeplacements() != null && enfant.getDeplacements().size() > 0){
					int[] newCoord = new int[2]; // Prend les coordonnees initiales
					newCoord[0] = oldCoord[0];
					newCoord[1] = oldCoord[1];

					// Avance
					if(enfant.bougerParDefaut()){ // Gere le changement d'orientation
						switch(enfant.getOrientation()){
						case NORD:
							if(oldCoord[0] > 0) // Collision avec les bords
								newCoord[0] = oldCoord[0]-1; // Monte
							break;
						case SUD:
							if(oldCoord[0] < ligne) // Collision avec les bords
								newCoord[0] = oldCoord[0]+1; // Descend
							break;
						case EST:
							if(oldCoord[1] < colonne) // Collision avec les bords
								newCoord[1] = oldCoord[1]+1; // Va a droite
							break;
						case OUEST:
							if(oldCoord[1] > 0) // Collision avec les bords
								newCoord[1] = oldCoord[1]-1; // Va a gauche
							break;
						}
					}

					// Retrait du deplacement traite
					enfant.getDeplacements().remove(0);

					// Ajout de l'enfant a sa nouvelle position
					if(setCase(newCoord[1]+1, newCoord[0]+1, enfant)){
						effacerEnfant(oldCoord[0], oldCoord[1]);
						save.put(newCoord, enfant);
						//LOGGER.debug("[new] " + enfant.getNom() + " est en [" + (newCoord[1]+1) + ", " + (newCoord[0]+1) + "] vers " + enfant.getOrientation() );
					}
					// L'enfant reste au meme endroit
					else{
						save.put(oldCoord, enfant);
						//LOGGER.debug("[old] " + enfant.getNom() + " est en [" + (oldCoord[1]+1) + ", " + (oldCoord[0]+1) + "] vers " + enfant.getOrientation() );
					}
					scoreEnfants.put(enfant.getNom(), enfant.getScore());
				}
			}else{ // L'enfant prend un choco => pas besoin de bouger
				//LOGGER.debug(enfant.getNom() + " prendChoco");
				enfant.remPrendChoco();

				save.put(oldCoord, enfant);

				// Score
				scoreEnfants.put(enfant.getNom(), enfant.getScore());
			}
		}
		// Mise a jour de coordEnfant
		coordEnfants = save;
	}

	public void bougerEnfantsBoucle(){
		// Tant qu'il y a des enfants
		initCoordEnfants();
		while(coordEnfants != null && coordEnfants.size() > 0){
			bougerEnfants();
		}
	}

	public boolean equals(Terrain terrain) {
		try{ // Au cas où une des valeurs seraient nulle

			// Doit être un Jardin
			if(terrain instanceof Jardin == false)
				return false;

			// Doit avoir le même nombre de lignes
			if(this.ligne != terrain.getLigne())
				return false;

			// Doit avoir le même nombre de colonnes
			if(this.colonne != terrain.getColonne())
				return false;

			// On doit retrouver à chaque case du tableau
			AbstractCase[][] tableCopie = terrain.getTable();
			for(int i=0; i<this.ligne; i++){
				for(int j=0; j<this.colonne; j++){
					// Une case vide
					if(this.table[i][j] instanceof CaseVide)
						if((tableCopie[i][j] instanceof CaseVide == false))
							return false;

						// Un rocher
						else if(this.table[i][j] instanceof Rocher)
							if((tableCopie[i][j] instanceof Rocher == false))
								return false;

							else if(this.table[i][j] instanceof Chocolat){
								// Un chocolat
								if(tableCopie[i][j] instanceof Chocolat){
									// Avec le même nombre
									if(((Chocolat)table[i][j]).getNombre() != ((Chocolat)tableCopie[i][j]).getNombre())
										return false;
								}
								else
									return false;
							}

							else if(this.table[i][j] instanceof Enfant){
								// Un enfant
								if(tableCopie[i][j] instanceof Enfant){
									// Identique
									if(!((Enfant)this.table[i][j]).equals((Enfant)tableCopie[i][j]))
										return false;
								}
								else
									return false;
							}
							else{
								LOGGER.debug("Le type de case n'est pas reconnu.");
								return false;
							}
				}
			}
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	public HashMap<int[], Enfant> getCoordEnfants(){
		return coordEnfants;
	}

	public HashMap<String, Integer> getScoreEnfants(){
		return scoreEnfants;
	}
}
