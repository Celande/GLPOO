package terrain.domain.abstractcase;

import javax.swing.ImageIcon;

public abstract class AbstractCase {
	protected ImageIcon symbole;
	
	public ImageIcon getSymbole(){
		return symbole;
	}
	
	protected void initSymbole(){
		symbole = null; // trouver une image
	}
}
