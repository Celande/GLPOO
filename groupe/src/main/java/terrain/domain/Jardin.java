package terrain.domain;

import terrain.domain.abstractcase.AbstractCase;

public class Jardin implements Terrain {

	private static int ligne;
	private static int colonne;
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
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractCase getCase(int colonne, int ligne) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCase(int colonne, int ligne, AbstractCase abstractCase) {
		// TODO Auto-generated method stub
		
	}

	public Integer getLigne() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getColonne() {
		// TODO Auto-generated method stub
		return null;
	}

	public void bougerEnfants() {
		// TODO Auto-generated method stub
		
	}

	public boolean equals(Terrain terrain) {
		// TODO Auto-generated method stub
		return false;
	}
}
