package terrain.domain.abstractcase.enfant;

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
}
