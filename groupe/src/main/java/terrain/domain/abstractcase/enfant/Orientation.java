package terrain.domain.abstractcase.enfant;

/**
 * Enumeration des orientations possibles de l'enfant
 * @author Célande
 *
 */

public enum Orientation {
	NORD,
	SUD,
	EST,
	OUEST;
	
	public static Orientation deLettreAOrientation(char c) throws UnsupportedOperationException{
		switch(c){
		case 'n':
		case 'N':
			return NORD;
		case 's':
		case 'S':
			return SUD;
		case 'e':
		case 'E':
			return EST;
		case 'w':
		case 'W':
		case 'o':
		case 'O':
			return OUEST;
		}
		throw new UnsupportedOperationException("La lettre " + c + " ne correspond à aucune orientation.");
	}
	
	public String toString(){
		switch(this){
		case NORD:
			return "Nord";
		case SUD:
			return "Sud";
		case EST:
			return "Est";
		case OUEST:
			return "Ouest";
		}
		return "";
	}
}
