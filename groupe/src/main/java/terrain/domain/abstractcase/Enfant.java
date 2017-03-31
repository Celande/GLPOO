package terrain.domain.abstractcase;

import java.util.Iterator;
import java.util.List;

public class Enfant extends AbstractCase {
	
	private String nom;
	private Orientation  orientation;
	private List<Deplacement> deplacements;
	
	public Enfant(Orientation orientation, List<Deplacement> deplacements, String string) {
		// TODO Auto-generated constructor stub
	}

	public String getNom(){
		
		return nom;
	}
	
	public Orientation getOrientation(){
		
		return orientation;
	}
	
	public void setOrientation(Orientation orientation){
		
		this.orientation = orientation;
	}
	
	public List<Deplacement> getDeplacements(){
		
		return deplacements;
	}
	
	public boolean equals(Enfant enfant){
		if(this.nom != enfant.getNom())
			return false;
		
		if(this.orientation != enfant.getOrientation())
			return false;
		
		// TODO
		// V�rifier si le equals de List ne fait pas d�j� �a
		// Voir si moyen d'impl�menter un iterator
		List<Deplacement> deplacementsCopie = enfant.getDeplacements();
		for(int i=0; i<deplacements.size(); i++){
			if(deplacements.get(i) != deplacementsCopie.get(i))
				return false;
		}
		
		return true;
	}
}
