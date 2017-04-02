package terrain.domain.abstractcase;

import org.apache.log4j.Logger;

public enum Deplacement {
	AVANT,
	GAUCHE,
	DROITE;
	
	public static Deplacement deLettreADeplacement(char c) throws UnsupportedOperationException{
		switch(c){
		case 'a':
		case 'A':
			return AVANT;
		case 'g':
		case 'G':
			return GAUCHE;
		case 'd':
		case 'D':
			return DROITE;
		}
		
		throw new UnsupportedOperationException("La lettre " + c + " ne correspond � aucun d�placement.");
	}
}
