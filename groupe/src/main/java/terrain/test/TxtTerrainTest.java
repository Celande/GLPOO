package terrain.test;

import org.apache.log4j.Logger;

/**
 * Execution pour les tests
 * @author Célande
 *
 */

public class TxtTerrainTest extends AbstractTxtTerrainTest {
    private static final Logger LOGGER = Logger.getLogger(TxtTerrainTest.class);
    
    public TxtTerrain terre;

    public TxtTerrainTest() {
        LOGGER.debug("Constructeur...");

        terre = new TxtTerrain();
    }
}