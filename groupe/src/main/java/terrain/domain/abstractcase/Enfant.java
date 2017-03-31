package terrain.domain.abstractcase;

import java.util.List;

public class Enfant extends AbstractCase {
	
	private String nom;
	private Orientation  orientation;
	private List<Deplacement> deplacement;
	
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
		
		return deplacement;
	}
}
