package terrain.domain.abstractcase;

import java.awt.image.BufferedImage;

/**
 * Rocher a eviter
 * @author Célande
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
