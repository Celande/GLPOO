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
		
		if(!this.deplacements.equals(enfant.getDeplacements()))
			return false;
		
		return true;
	}
}
