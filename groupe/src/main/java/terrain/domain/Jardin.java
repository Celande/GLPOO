package terrain.domain;

import static terrain.domain.abstractcase.Orientation.EST;
import static terrain.domain.abstractcase.Orientation.NORD;
import static terrain.domain.abstractcase.Orientation.OUEST;
import static terrain.domain.abstractcase.Orientation.SUD;

import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Deplacement;
import terrain.domain.abstractcase.Enfant;
import terrain.domain.abstractcase.Orientation;
import terrain.domain.abstractcase.Rocher;

public class Jardin implements Terrain {

	private int ligne;
	private int colonne;
	private AbstractCase[][] table;

	private static final Logger LOGGER = Logger.getLogger(Jardin.class);

	private static Jardin instance = null;

	private Jardin(int colonne, int ligne){
		// Initialisation des lignes et colonnes
		this.ligne = ligne;
		this.colonne = colonne;
		
		// Initialisation du tableau avec toutes les cases vides
		table = new AbstractCase[ligne][];
		for(int i=0; i<ligne;i++){
			table[i] = new AbstractCase[colonne];
			
			for(int j=0; j<colonne;j++){
				table[i][j] = new CaseVide();
			}
		}
	}

	public static Jardin getInstance(int colonne, int ligne){
		if(instance == null){
			instance = new Jardin(colonne, ligne);
		}
		return instance;
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
	public void setCase(int colonne, int ligne, AbstractCase abstractCase) throws UnsupportedOperationException {

		ligne--;
		colonne--;
		
		// L'objet doit être ajoutée dans le tableau
		if(ligne >= this.ligne || ligne < 0 || colonne >= this.colonne || colonne < 0)
			throw new UnsupportedOperationException("La ligne ou la colonne est hors du tableau.");
		
		// On ne peut pas ajouter un objet nul
		if(abstractCase == null)
			throw new UnsupportedOperationException("L'objet à ajouter est nul.");
		
		// Ajout à un emplacement vide
		if(this.table[ligne][colonne] instanceof CaseVide)
			this.table[ligne][colonne] = abstractCase;
		// Remplace un enfant par une case vide dans le cadre d'un déplacment
		else if(this.table[ligne][colonne] instanceof Enfant && abstractCase instanceof CaseVide)
			this.table[ligne][colonne] = abstractCase;
		
		else{
			LOGGER.debug("La case n'est pas vide ou il ne s'agit pas d'un enfant.");
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
		Enfant monEnfant = null;
		int ligneEnfant = 0;
		int colonneEnfant = 0;
		Orientation orientationEnfant = monEnfant.getOrientation();
		Deplacement deplacementEnfant = monEnfant.getDeplacements().get(0);
		
		for (int i = 0; i<getLigne(); i++){
			for (int j = 0; j<getColonne(); j++){
				if (table[i][j] instanceof Enfant){
					monEnfant = (Enfant) table[i][j];
					ligneEnfant = i;
					colonneEnfant =j;
					break;
				}
			}
			if (monEnfant != null) {
				break;
			}
		}
			
		switch (deplacementEnfant) {
			case AVANT:
				switch (orientationEnfant) {
					case NORD:
						ligneEnfant -= 1;
						break;
					case SUD:
						ligneEnfant += 1;
						break;
					case OUEST:
						colonneEnfant -= 1;
						break;
					case EST:
						colonneEnfant += 1;
						break;
					default:
						break;
				}
				break;
			case GAUCHE:
				switch (orientationEnfant) {
					case NORD:
						orientationEnfant = OUEST;
						break;
					case SUD:
						orientationEnfant = EST;
						break;
					case OUEST:
						orientationEnfant = SUD;
						break;
					case EST:
						orientationEnfant = NORD;
						break;
					default:
						break;
				}
				break;
			case DROITE:
				switch (orientationEnfant) {
					case NORD:
						orientationEnfant = Orientation.EST;
						break;
					case SUD:
						orientationEnfant = Orientation.OUEST;
						break;
					case OUEST:
						orientationEnfant = Orientation.NORD;
						break;
					case EST:
						orientationEnfant = Orientation.SUD;
						break;
					default:
						break;
				}
				break;
			default:
				break;
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
					if(this.table[i][j] instanceof CaseVide && (tableCopie[i][j] instanceof CaseVide == false))
						return false;

					// Un rocher
					else if(this.table[i][j] instanceof Rocher && (tableCopie[i][j] instanceof Rocher == false))
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
}
