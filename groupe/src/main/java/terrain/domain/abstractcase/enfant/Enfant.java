package terrain.domain.abstractcase.enfant;

import static terrain.domain.abstractcase.enfant.Orientation.EST;
import static terrain.domain.abstractcase.enfant.Orientation.NORD;
import static terrain.domain.abstractcase.enfant.Orientation.OUEST;
import static terrain.domain.abstractcase.enfant.Orientation.SUD;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.Chocolat;

/**
 * Enfant qui se depmace sur le terrain
 * @author Célande
 *
 */

public class Enfant extends AbstractCase {

	private String nom;
	private Orientation  orientation;
	private List<Deplacement> deplacements;
	private int score;
	private int prendChoco;
	private static final Logger LOGGER = Logger.getLogger(Enfant.class);

	public Enfant(Orientation orientation, List<Deplacement> deplacements, String nom) {
		this.orientation = orientation;
		this.nom = nom;
		this.deplacements = deplacements;
		this.score = 0;
		this.prendChoco = 0;
	}

	public Enfant(char orientation, String deplacements, String nom){
		this.orientation = Orientation.deLettreAOrientation(orientation);
		this.nom = nom;
		this.score = 0;
		this.prendChoco = 0;

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

	public List<Deplacement> getDeplacements(){

		return this.deplacements;
	}
	
	public int getScore(){
		return this.score;
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

	/**
	 * Par defaut le premier deplacement est applique
	 * @return
	 */
	public boolean bougerParDefaut(){
		if(deplacements != null)
			return bouger(deplacements.get(0));

		return false;
	}

	/**
	 * Retourne si l'enfant doit avancer
	 * Sinon change juste l'orientation
	 * @param d
	 * @return
	 */
	public boolean bouger(Deplacement d){
		switch(d){
		case GAUCHE:
		case DROITE:
			reorientation(d);
			return false;
		case AVANT:
			//LOGGER.debug(nom + " bouge vers l'avant");
			return true;
		}
		return false;
	}

	/**
	 * Change l'orientation de l'enfant
	 * en fonction de son etat initial et du deplacement applique
	 * @param deplacement
	 */
	public void reorientation(Deplacement deplacement){
		switch(deplacement){
		case GAUCHE:
			//LOGGER.debug(nom + " tourne a gauche");
			if(orientation == NORD)
				orientation = OUEST;
			else if(orientation == SUD)
				orientation = EST;
			else if(orientation == EST)
				orientation = NORD;
			else // OUEST
				orientation = SUD;
			break;
		case DROITE:
			//LOGGER.debug(nom + " tourne a droite");
			if(orientation == NORD)
				orientation = EST;
			else if(orientation == SUD)
				orientation = OUEST;
			else if(orientation == EST)
				orientation = SUD;
			else // OUEST
				orientation = NORD;
			break;
		default:
			JOptionPane.showMessageDialog(null, nom+" ne peut pas tourner.", "Erreur", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Recuperation des chocolats
	 * donc augmentation du score
	 * @param choco
	 */
	public void recupChoco(Chocolat choco){
		this.score += choco.getNombre();
		this.prendChoco = choco.getNombre();
	}
	
	public int getPrendChoco(){
		return this.prendChoco;
	}
	
	public void setPrendChoco(int n){
		this.prendChoco = n;
	}
	
	public void remPrendChoco(){
		this.prendChoco--;
	}
}
