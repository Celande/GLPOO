package terrain.domain;

import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

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
	 * Indices consid�r�s comme �tant de 1 � ligne ou colonne
	 */
	public AbstractCase getCase(int colonne, int ligne) {

		ligne--;
		colonne--;
		return this.table[ligne][colonne];
	}

	/**
	 * Indices consid�r�s comme �tant de 1 � ligne ou colonne
	 */
	public void setCase(int colonne, int ligne, AbstractCase abstractCase) throws UnsupportedOperationException {

		ligne--;
		colonne--;
		
		// L'objet doit �tre ajout�e dans le tableau
		if(ligne >= this.ligne || ligne < 0 || colonne >= this.colonne || colonne < 0)
			throw new UnsupportedOperationException("La ligne ou la colonne est hors du tableau.");
		
		// On ne peut pas ajouter un objet nul
		if(abstractCase == null)
			throw new UnsupportedOperationException("L'objet � ajouter est nul.");
		
		// Ajout � un emplacement vide
		if(this.table[ligne][colonne] instanceof CaseVide)
			this.table[ligne][colonne] = abstractCase;
		// Remplace un enfant par une case vide dans le cadre d'un d�placment
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
		// TODO Auto-generated method stub

	}

	public boolean equals(Terrain terrain) {
		try{ // Au cas o� une des valeurs seraient nulle

			// Doit �tre un Jardin
			if(terrain instanceof Jardin == false)
				return false;
			
			// Doit avoir le m�me nombre de lignes
			if(this.ligne != terrain.getLigne())
				return false;

			// Doit avoir le m�me nombre de colonnes
			if(this.colonne != terrain.getColonne())
				return false;

			// On doit retrouver � chaque case du tableau
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
							// Avec le m�me nombre
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
