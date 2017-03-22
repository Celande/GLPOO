package terrain.test;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

import terrain.domain.Terrain;

public class TxtTerrain {
	private File file;
    private Terrain terrain;
    private List<String> entetes;
    
    private static final Logger LOGGER = Logger.getLogger(TxtTerrain.class);
    
    /**
     * Initialisation du terrain
     * 
     * @param file
     */
    public void init(File file){
    	LOGGER.debug("init");
    	this.file = file;
    	
    	loadTerrain();
    }
    
    private void loadTerrain(){
    	// TODO
    }
    
    public Terrain getTerrain(){
    	
    	// TODO
    	return null;
    }
}
