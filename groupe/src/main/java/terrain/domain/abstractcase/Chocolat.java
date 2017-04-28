package terrain.domain.abstractcase;

/**
 * Chocolat a ramasser
 * @author Célande
 *
 */

public class Chocolat extends AbstractCase {

	private int nombre;
	
	public Chocolat(int nombre){
		this.nombre = nombre;
	}
	
	public int getNombre() {
		return nombre;
	}

}
