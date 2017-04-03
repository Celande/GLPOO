package terrain.domain;

import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Enfant;
import terrain.domain.abstractcase.Rocher;

public class Jardin implements Terrain {

	private int ligne;
	private int colonne;
	private AbstractCase[][] table;

	private static final Logger LOGGER = Logger.getLogger(Jardin.class);

	private static Jardin instance = null;

	private Jardin(int colonne, int ligne){
		this.ligne = ligne;
		this.colonne = colonne;
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
		
		if(ligne >= this.ligne || ligne < 0 || colonne >= this.colonne || colonne < 0)
			throw new UnsupportedOperationException("La ligne ou la colonne est hors du tableau.");
		
		if(abstractCase == null)
			throw new UnsupportedOperationException("L'objet à ajouter est nul.");
		
		if(this.table[ligne][colonne] instanceof CaseVide)
			this.table[ligne][colonne] = abstractCase;
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
		try{ // Au cas où une des valeurs seraient nulle

			if(terrain instanceof Jardin == false)
				return false;

			if(this.ligne != terrain.getLigne())
				return false;

			if(this.colonne != terrain.getColonne())
				return false;

			AbstractCase[][] tableCopie = terrain.getTable();
			for(int i=0; i<this.ligne; i++){
				for(int j=0; j<this.colonne; j++){
					if(this.table[i][j] instanceof CaseVide && (tableCopie[i][j] instanceof CaseVide == false))
						return false;

					else if(this.table[i][j] instanceof Rocher && (tableCopie[i][j] instanceof Rocher == false))
						return false;

					else if(this.table[i][j] instanceof Chocolat){
						if(tableCopie[i][j] instanceof Chocolat){
							if(((Chocolat)table[i][j]).getNombre() != ((Chocolat)tableCopie[i][j]).getNombre())
								return false;
						}
						else
							return false;
					}

					else if(this.table[i][j] instanceof Enfant){
						if(tableCopie[i][j] instanceof Enfant){
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
