package terrain.test;

import org.apache.log4j.Logger;

public class TxtTerrainTest extends AbstractTxtTerrainTest {
    private static final Logger LOGGER = Logger.getLogger(TxtTerrainTest.class);
    
    private final static String RESOURCES_PATH = "src/test/resources/";
	private final static String TERRAIN_FILE_NAME = "terrain-01.txt";
	private final static String ENFANT_FILE_NAME = "enfant-01.txt";
    
    public TxtTerrain terre;

    public TxtTerrainTest() {
        LOGGER.debug("Constructeur...");

        terre = new TxtTerrain();
        TxtTerrain.init(new File(RESOURCES_PATH + TERRAIN_FILE_NAME),new File(RESOURCES_PATH + ENFANT_FILE_NAME));
        
    }

public static void main(String[] args){
	TxtTerrainTest tests = new TxtTerrainTest();
}

}