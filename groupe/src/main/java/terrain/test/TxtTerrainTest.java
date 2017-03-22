package terrain.test;

import org.apache.log4j.Logger;

public class TxtTerrainTest extends AbstractTxtTerrainTest {
    private static final Logger LOGGER = Logger.getLogger(TxtTerrainTest.class);

    public TxtTerrainTest() {
        LOGGER.debug("Constructeur...");

        terre = new TxtTerrain();
    }
}