package terrain.domain;

import static terrain.domain.abstractcase.enfant.Orientation.EST;
import static terrain.domain.abstractcase.enfant.Orientation.NORD;
import static terrain.domain.abstractcase.enfant.Orientation.OUEST;
import static terrain.domain.abstractcase.enfant.Orientation.SUD;

import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.enfant.Deplacement;
import terrain.domain.abstractcase.enfant.Enfant;
import terrain.domain.abstractcase.enfant.Orientation;
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
	public boolean setCase(int colonne, int ligne, AbstractCase abstractCase) throws UnsupportedOperationException {

		ligne--;
		colonne--;
		
		// L'objet doit être ajoutée dans le tableau
		if(ligne >= this.ligne || ligne < 0 || colonne >= this.colonne || colonne < 0)
			return false;
		
		// On ne peut pas ajouter un objet nul
		if(abstractCase == null)
			return false;
		
		// Ajout à un emplacement vide
		if(this.table[ligne][colonne] instanceof CaseVide) {
			this.table[ligne][colonne] = abstractCase;
			return true;
		}
		// Remplace un enfant par une case vide dans le cadre d'un déplacment
		else if(this.table[ligne][colonne] instanceof Enfant && abstractCase instanceof CaseVide) {
			this.table[ligne][colonne] = abstractCase;
			return true;
		}
		else{
			LOGGER.debug("La case n'est pas vide ou il ne s'agit pas d'un enfant.");
			return false;
		}
	}

	public Integer getLigne() {

		return this.ligne;
	}

	public Integer getColonne() {

		return this.colonne;
	}

	public void bougerEnfants() {
		
		Enfant monEnfant[] = new Enfant[10];
		int monEnfantInit[] = new int[10];
		int ligneEnfantInit[] = new int[10];
		int colonneEnfantInit[] = new int[10];
		int ligneEnfant[] = new int[10];
		int colonneEnfant[] = new int [10];
		
		int compteur = 0;
		
		for (int i = 0; i<getLigne(); i++){
			for (int j = 0; j<getColonne(); j++){
				if (table[i][j] instanceof Enfant){
					monEnfant[compteur] = (Enfant) table[i][j];
					ligneEnfant[compteur] = i;
					colonneEnfant[compteur] = j;
					ligneEnfantInit[compteur] = i;
					colonneEnfantInit[compteur] = j;
					compteur += 1;
				}
			}
		}
		
		for (int l = 0; l<compteur; l++) {
			
			Orientation orientationEnfant = monEnfant[l].getOrientation();
			int nbDeplacement = monEnfant[l].getDeplacements().size();
			
			LOGGER.debug("nouveau enfant");
			
			for (int k = 0; k<nbDeplacement; k++) {
				Deplacement deplacementEnfant = monEnfant[l].getDeplacements().get(k);
				
				switch (deplacementEnfant) {
					case AVANT:
						LOGGER.debug("avant");
						switch (orientationEnfant) {
							case NORD:
								ligneEnfant[l] -= 1;
								if (setCase(colonneEnfant[l], ligneEnfant[l], new CaseVide()) == false) {
									ligneEnfant[l] += 1;
								}
								LOGGER.debug("nord");
								break;
							case SUD:
								ligneEnfant[l] += 1;
								if (setCase(colonneEnfant[l], ligneEnfant[l], new CaseVide()) == false) {
									ligneEnfant[l] -= 1;
								}
								LOGGER.debug("sud");
								break;
							case OUEST:
								colonneEnfant[l] -= 1;
								if (setCase(colonneEnfant[l], ligneEnfant[l], new CaseVide()) == false) {
									colonneEnfant[l] += 1;
								}
								LOGGER.debug("ouest");
								break;
							case EST:
								colonneEnfant[l] += 1;
								if (setCase(colonneEnfant[l], ligneEnfant[l], new CaseVide()) == false) {
									colonneEnfant[l] -= 1;
								}
								LOGGER.debug("est");
								break;
							default:
								break;
						}
						break;
					case GAUCHE:
						LOGGER.debug("gauche");
						switch (orientationEnfant) {
							case NORD:
								orientationEnfant = OUEST;
								LOGGER.debug("nord->ouest");
								break;
							case SUD:
								orientationEnfant = EST;
								LOGGER.debug("sud->est");
								break;
							case OUEST:
								orientationEnfant = SUD;
								LOGGER.debug("ouest->sud");
								break;
							case EST:
								orientationEnfant = NORD;
								LOGGER.debug("est->nord");
								break;
							default:
								break;
						}
						break;
					case DROITE:
						LOGGER.debug("droite");
						switch (orientationEnfant) {
							case NORD:
								orientationEnfant = Orientation.EST;
								LOGGER.debug("nord->est");
								break;
							case SUD:
								orientationEnfant = Orientation.OUEST;
								LOGGER.debug("sud->ouest");
								break;
							case OUEST:
								orientationEnfant = Orientation.NORD;
								LOGGER.debug("ouest->nord");
								break;
							case EST:
								orientationEnfant = Orientation.SUD;
								LOGGER.debug("est->sud");
								break;
							default:
								break;
						}
						break;
					default:
						break;
				}
				table[ligneEnfantInit[l]][colonneEnfantInit[l]] = new CaseVide();
				LOGGER.debug("effacement enfant precedent");
				table[ligneEnfant[l]][colonneEnfant[l]] = monEnfant[l];
				LOGGER.debug("nouvelle position enfant");
			}
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
