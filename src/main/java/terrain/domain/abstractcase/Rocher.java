package terrain.domain.abstractcase;

import java.awt.image.BufferedImage;

/**
 * Rocher a eviter
 * @author C�lande
 *
 */

public class Rocher extends AbstractCase {
	private BufferedImage img;
	public Rocher(){
		img = null;
	}
	
	public BufferedImage getImg(){
		return this.img;
	}
	public void setImg(BufferedImage img){
		this.img = img;
	}
}
