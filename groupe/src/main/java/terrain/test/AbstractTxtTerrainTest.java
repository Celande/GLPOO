package terrain.test;

import static org.junit.Assert.*;
import static terrain.domain.abstractcase.enfant.Deplacement.AVANT;
import static terrain.domain.abstractcase.enfant.Orientation.SUD;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import terrain.domain.Terrain;
import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Deplacement;
import terrain.domain.abstractcase.enfant.Enfant;

public class AbstractTxtTerrainTest {
	private static final Logger LOGGER = Logger.getLogger(AbstractTxtTerrainTest.class);

	private final static String RESOURCES_PATH = "src/test/resources/";
	private final static String TERRAIN_FILE_NAME = "terrain-01.txt";
	private final static String ENFANT_FILE_NAME = "enfant-01.txt";

	protected TxtTerrain terre;

	@Before 
	public void doBefore() {
		LOGGER.debug("doBefore Debut");
		final File fileTerrain = new File(RESOURCES_PATH + TERRAIN_FILE_NAME);
		final File fileEnfant = new File(RESOURCES_PATH + ENFANT_FILE_NAME);

		terre = new TxtTerrain();
		terre.init(fileTerrain, fileEnfant);

		LOGGER.debug("doBefore Fin");
	}

	/**
	 * On vérifie que le nombre de lignes est bien 5
	 * PARAM : nombreLigneAttendu
	 */
	@Test
	public void testNombreLignesCinq(){
		LOGGER.debug("testNombreLignesCinq... Debut");

		// Arrange
		final int nombreLigneAttendu = 5;

		// Act
		final AbstractCase[][] table = terre.getTerrain().getTable();
		final int ligne = table.length;

		// Assert
		assertEquals(nombreLigneAttendu, ligne);	


		LOGGER.debug("testNombreLignesCinq... Fin");
	}

	/**
	 * On vérifie que le nombre de colonnes est de 6
	 * PARAM : nombreColonneAttendu
	 */
	@Test
	public void testNombreColonnesSix(){ 
		LOGGER.debug("testNombreColonnesSix... Debut");

		// Arrange
		final int nombreColonneAttendu = 6;

		// Act
		boolean colonne = true;
		final AbstractCase[][] table = terre.getTerrain().getTable();
		Iterable<AbstractCase[]> iterableLigne = Arrays.asList(table);
		for(AbstractCase[] ligneCase : iterableLigne)
		{
			if(ligneCase.length != nombreColonneAttendu)
				colonne = false;
		}


		// Assert
		assertTrue(colonne);			

		LOGGER.debug("testNombreColonnesSix... Fin");
	}

	@Test
	public void testNombreChocolats(){

		LOGGER.debug("testNombreChocolats... Debut");

		// Arrange
		final int nbChocolatsAttendu = 3;

		// Act
		final int ligne = 4;
		final int colonne = 1;
		final int nbChocolats = ((Chocolat)terre.getTerrain().getCase(colonne, ligne)).getNombre();

		// Assert
		assertEquals(nbChocolatsAttendu, nbChocolats);

		LOGGER.debug("testNombreChocolats... Fin");
	}

	/**
	 * On vérifie qu'il y a au moins un enfant sur le terrain
	 * Sinon il n'y a pas de jeu
	 */
	@Test
	public void testAuMoinsUnEnfant(){
		LOGGER.debug("testAuMoinsUnEnfant... Debut");

		// Arrange & Act
		int nbEnfant = 0;

		/* Parcours du tableau */

		AbstractCase[][] table = terre.getTerrain().getTable();

		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[i].length; j++)
			{
				if(table[i][j] instanceof Enfant)
					nbEnfant ++;
			}
		}

		// Assert
		LOGGER.debug("nbEnfant = " + nbEnfant);
		assertTrue(nbEnfant > 0);

		LOGGER.debug("testAuMoinsUnEnfant... Fin");
	}

	/**
	 * On vérifie qu'il y a au moins un chocolat sur le terrain
	 * Sinon il n'y a pas de jeu
	 */
	@Test
	public void testAuMoinsUnChocolat(){
		LOGGER.debug("testAuMoinsUnChocolat... Debut");

		// Arrange & Act
		int nbChoco = 0;

		/* Parcours du tableau */
		AbstractCase[][] table = terre.getTerrain().getTable();

		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[i].length; j++)
			{
				if(table[i][j] instanceof Chocolat)
					nbChoco += ((Chocolat)table[i][j]).getNombre();
			}
		}

		// Assert
		assertTrue(nbChoco > 0);

		LOGGER.debug("testAuMoinsUnChocolat... Fin");
	}

	/**
	 * On vérifie que l'enfant arrive à la bonne case
	 * PARAM : colonneAttendu, ligneAttendu
	 */
	@Test
	public void testParcoursEnfant(){
		LOGGER.debug("testParcoursEnfant... Debut");
		// Arrange 

		final int colonneAttendu = 2;
		final int ligneAttendu = 3;
		LOGGER.debug("Arrange");

		// Act

		terre.getTerrain().bougerEnfantsBoucle();
		AbstractCase abstractCase = terre.getTerrain().getCase(colonneAttendu, ligneAttendu);
		LOGGER.debug("Act");
		// Assert

		assertTrue(abstractCase instanceof Enfant);
		LOGGER.debug("testParcoursEnfant... Fin");
	}

	/**
	 * On vérifie ce qu'il se passe lorsque l'enfant tente de sortir du terrrain
	 * Il ne devrait pas pouvoir 
	 * Son tour est passé vu qu'il a épuisé une aciton
	 */
	@Test
	public void testParcoursEnfantHorsTerrain(){
		LOGGER.debug("testParcoursEnfantHorsTerrain... Debut");
		// Arrange
		final int colonneAttendu = 2;
		final int ligneAttendu = 5;

		final int colonne = 2;
		final int ligne = 2;

		final Enfant paul = new Enfant(SUD, 
				new ArrayList<Deplacement>(Arrays.asList(
						AVANT, 
						AVANT, 
						AVANT, 
						AVANT, 
						AVANT, 
						AVANT, 
						AVANT)), "Paul");

		// Act
		final Terrain terrain = terre.getTerrain();
		LOGGER.debug("0.0 = " + terrain.getCase(1, 1).getClass().getName());
		terrain.effacerEnfant(0, 0);
		terrain.setCase(colonne, ligne, paul);
		terrain.bougerEnfantsBoucle();

		AbstractCase abstractCase = terrain.getCase(colonneAttendu, ligneAttendu);

		// Assert
		assertTrue(abstractCase instanceof Enfant && ((Enfant)abstractCase).equals(paul));

		LOGGER.debug("testParcoursEnfantHorsTerrain... Fin");
	}

	/**
	 * Un objet ne devrait pas pouvoir ête ajouté hors du terrain
	 */
	public void testAjoutObjetHorsTerrain(){

		LOGGER.debug("testAjoutObjetHorsTerrain... Debut");

		// Arrange
		final Terrain terrain = terre.getTerrain();
		final int colonne = terrain.getColonne() + 1;
		final int ligne = terrain.getLigne() + 3;
		final Rocher roc = new Rocher();

		// Act
		boolean testFalse = terrain.setCase(colonne, ligne, roc);

		// Assert
		assertFalse(testFalse);

		LOGGER.debug("testAjoutObjetHorsTerrain... Fin");
	}


	/**
	 * L'ajout d'un objet sur un autre objet ne peut se faire que 
	 * - du vide sur un enfant (pour marquer le déplacement)
	 * - un objet sur du vide
	 * et lors de la fonction bougerEnfants et de 
	 * jamais autrement
	 */
	public void testAjoutObjetSurAutreObjet(){

		LOGGER.debug("testAjoutObjetSurAutreObjet... Debut");

		// Arrange
		final Terrain terrain = terre.getTerrain();
		final int colonne = terrain.getColonne();
		final int ligne = terrain.getLigne();
		final Rocher roc = new Rocher();

		// Act
		boolean testTrue = terrain.setCase(colonne, ligne, roc);
		boolean testFalse = terrain.setCase(colonne, ligne, roc);

		// Assert
		assertTrue(testTrue);
		assertFalse(testFalse);

		LOGGER.debug("testAjoutObjetSurAutreObjet... Fin");
	}

	/**
	 * On vérifie qu'on ne peut pas ajouter d'objet nul au tableau
	 */
	@Test (expected = UnsupportedOperationException.class)
	public void testAjoutObjetNul(){

		LOGGER.debug("testAjoutObjetNul... Debut");

		// Arrange
		final Terrain terrain = terre.getTerrain();
		final int colonne = terrain.getColonne();
		final int ligne = terrain.getLigne();

		// Act
		terrain.setCase(colonne, ligne, null); // Cette case devrait être vide.

		LOGGER.debug("testAjoutObjetNul... Fin");
	}

	/**
	 * On peut ajouter n'importe quel objet sur une case vide
	 */
	@Test
	public void testAjoutObjetsurVide(){

		LOGGER.debug("testAjoutObjetsurVide... Debut");

		// Arrange
		final Terrain terrain = terre.getTerrain();
		final int colonne = terrain.getColonne();
		final int ligne = terrain.getLigne();
		final Rocher roc = new Rocher();

		// Act
		terrain.setCase(colonne, ligne, roc); // Cette case devrait etre vide
		AbstractCase abstractCase = terrain.getCase(colonne, ligne);

		// Assert
		assertTrue(abstractCase instanceof Rocher);

		LOGGER.debug("testAjoutObjetsurVide... Fin");
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testDeplacementInexistant(){
		LOGGER.debug("testDeplacementInexistant... Debut");

		new Enfant('s', "AZA", "Aza");

		LOGGER.debug("testDeplacementInexistant... Fin");
	}

	@Test 
	public void testRecupChoco(){
		LOGGER.debug("testRecupChoco... Debut");
		// Arrange
		final int colonneAttendu = 1;
		final int ligneAttendu = 5;
		final int colonne = 1;
		final int ligne = 3;

		final Enfant paul = new Enfant(SUD, 
				new ArrayList<Deplacement>(Arrays.asList(
						AVANT, 
						AVANT)), "Paul");

		// Act
		final Terrain terrain = terre.getTerrain();
		//terrain.effacerEnfant(0, 0);
		terrain.setCase(colonne, ligne, paul);
		terrain.bougerEnfantsBoucle();

		AbstractCase abstractCase = terrain.getCase(colonneAttendu, ligneAttendu);
		
		LOGGER.debug("score = " +((Enfant)abstractCase).getScore());

		// Assert
		assertTrue(abstractCase instanceof Enfant && ((Enfant)abstractCase).getScore() == 3);

		LOGGER.debug("testRecupChoco... Fin");
	}
}
