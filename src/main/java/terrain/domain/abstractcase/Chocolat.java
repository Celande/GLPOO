package terrain.domain.abstractcase;

/**
 * Chocolat a ramasser
 * @author C�lande
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
