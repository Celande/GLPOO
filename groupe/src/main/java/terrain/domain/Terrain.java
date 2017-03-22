package terrain.domain;

import terrain.domain.abstractcase.AbstractCase;

public interface Terrain {
	
	public AbstractCase[][] getTable();
	public AbstractCase getCase(int colonne, int ligne);
	public void setCase(int colonne, int ligne, AbstractCase abstractCase);
	public Integer getLigne();
	public Integer getColonne();
	public void bougerEnfants();
	public boolean equals(Terrain terrain);
}
