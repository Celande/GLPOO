package terrain.domain.abstractcase;

import java.util.ArrayList;
import java.util.List;

public class Enfant extends AbstractCase {
	
	private String nom;
	private Orientation  orientation;
	private List<Deplacement> deplacements;
	
	public Enfant(Orientation orientation, List<Deplacement> deplacements, String nom) {
		this.orientation = orientation;
		this.nom = nom;
		this.deplacements = deplacements;
	}
	
	public Enfant(Orientation orientation, String deplacements, String nom){
		this.orientation = orientation;
		this.nom = nom;
		
		this.deplacements = new ArrayList<Deplacement>();
		for(char c : deplacements.toCharArray()){
			this.deplacements.add(Deplacement.deLettreADeplacement(c));
		}
	}

	public String getNom(){
		
		return this.nom;
	}
	
	public Orientation getOrientation(){
		
		return this.orientation;
	}
	
	public void setOrientation(Orientation orientation){
		
		this.orientation = orientation;
	}
	
	public List<Deplacement> getDeplacements(){
		
		return this.deplacements;
	}
	
	public boolean equals(Enfant enfant){
		if(this.nom != enfant.getNom())
			return false;
		
		if(this.orientation != enfant.getOrientation())
			return false;
		
		// TODO
		// Vérifier si le equals de List ne fait pas déjà ça
		// Voir si moyen d'implémenter un iterator
		List<Deplacement> deplacementsCopie = enfant.getDeplacements();
		for(int i=0; i<deplacements.size(); i++){
			if(deplacements.get(i) != deplacementsCopie.get(i))
				return false;
		}
		
		return true;
	}
}
