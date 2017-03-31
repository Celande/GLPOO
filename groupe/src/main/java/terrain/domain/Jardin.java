package terrain.domain;

import terrain.domain.abstractcase.AbstractCase;

public class Jardin implements Terrain {

	private int ligne;
	private int colonne;
	private AbstractCase[][] table;
	
	private static Jardin instance = null;
	
	private Jardin(int colonne, int ligne){
		this.ligne = ligne;
		this.colonne = colonne;
		// TODO : generation de la table
	}
	
	public static Jardin getInstance(int colonne, int ligne){
		if(instance == null){
			instance = new Jardin(colonne, ligne);
		}
		return instance;
	}

	public AbstractCase[][] getTable() {

		return table;
	}

	public AbstractCase getCase(int colonne, int ligne) {
		
		return table[ligne][colonne];
	}

	public void setCase(int colonne, int ligne, AbstractCase abstractCase) {

		table[ligne][colonne] = abstractCase;
	}

	public Integer getLigne() {

		return ligne;
	}

	public Integer getColonne() {

		return colonne;
	}

	public void bougerEnfants() {
		// TODO Auto-generated method stub
		
	}

	public boolean equals(Terrain terrain) {
		// TODO Auto-generated method stub
		return false;
	}
}
