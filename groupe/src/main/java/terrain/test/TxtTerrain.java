package terrain.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import terrain.domain.Jardin;
import terrain.domain.Terrain;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

public class TxtTerrain {
	private File fileTerrain;
	private File fileEnfants;
	private Terrain terrain;
	private static boolean initialized = false; // true lorsque l'initialisation est arrivee a son terme

	private static final Logger LOGGER = Logger.getLogger(TxtTerrain.class);

	/**
	 * Initialisation du terrain
	 * 
	 * @param file
	 */
	public void init(File fileTerrain, File fileEnfants){
		initialized = false;
		LOGGER.debug("init");
		
		this.fileTerrain = fileTerrain;
		this.fileEnfants = fileEnfants;
		
		LOGGER.debug("loading");
		loadTerrain();
		loadEnfants();
		
		initialized = true;
	}

	/**
	 * Chargement du terrain depuis fichier
	 */

	private void loadTerrain(){
		initialized = false;
		LOGGER.debug("loadTerrain");
		int ligne;    	
		int colonne; 
		
		int nbObjet = 0;
		int nbObjetHorsTable = 0;

		String toRead = null;
		String[] listToRead;
		String[] location;
		Integer flag; // Mise en place du jardin

		try{
			FileInputStream ips = new FileInputStream(fileTerrain); 			
			InputStreamReader ipsr = new InputStreamReader(ips);			
			BufferedReader br = new BufferedReader(ipsr);

			LOGGER.debug("load Terrain");
			do{
				flag = 1;
				if((toRead = br.readLine()) != null && !toRead.isEmpty()){ // La ligne n'est pas vide
					switch(toRead.charAt(0)){
					case '#': // Commentaires
						break;

						// Jardin
					case 'J':
					case 'j':
						flag = 0;
						listToRead = toRead.split(" ");
						ligne = Integer.parseInt(listToRead[2]);
						colonne = Integer.parseInt(listToRead[1]);
						terrain = new Jardin(colonne, ligne);
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toRead + " ~ a ete ignoree.");
						break;
					}
				}

			}while(flag == 1);
			
			LOGGER.debug("load Objects");

			while((toRead = br.readLine()) != null){ // La ligne n'est pas vide
				if(!toRead.isEmpty()){
					switch(toRead.charAt(0)){
					case '#': // Commentaires
						break;
						
						// Chocolat
					case 'C':
					case 'c':
						nbObjet++;
						listToRead = toRead.split(" ");
						location = listToRead[1].split("-");
						if(!terrain.setCase(Integer.parseInt(location[0]),Integer.parseInt(location[1]),new Chocolat(Integer.parseInt(listToRead[2])))){
							nbObjetHorsTable++;
						}
						break;

						// Rocher
					case 'R':
					case 'r':
						nbObjet++;
						listToRead = toRead.split(" ");
						location = listToRead[1].split("-");
						
						if(!terrain.setCase(Integer.parseInt(location[0]),Integer.parseInt(location[1]),new Rocher())){
							nbObjetHorsTable++; // Objet pas ajoute
						}
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toRead + " ~ a ete ignoree.");
						break;
					}
				}
			}

			br.close(); 
			
			// Aucun objet n'a pu etre ajoute
			if(nbObjet == nbObjetHorsTable && nbObjet != 0){
				JOptionPane.showMessageDialog(null, "Le terrain est trop petit.", "Erreur", JOptionPane.WARNING_MESSAGE);
			}

		}catch (Exception e){		
			LOGGER.debug(e.toString());
			JOptionPane.showMessageDialog(null, "Le fichier selectionne n'est pas compatible.", "Erreur", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

	}

	private void loadEnfants(){  
		initialized = false;
		LOGGER.debug("loadEnfants");
		
		int nbEnfant = 0;
		int nbEnfantHorsTable = 0;
		
		try{
			FileInputStream ips = new FileInputStream(fileEnfants); 			
			InputStreamReader ipsr = new InputStreamReader(ips);			
			BufferedReader br = new BufferedReader(ipsr);			
			String toRead;			
			String[] listToRead;
			String[] location;

			while((toRead = br.readLine()) != null){ // La ligne n'est pas vide
				switch(toRead.charAt(0)){
				case '#': // Commentaires
					break;

					// Enfant
				case 'E':
				case 'e':
					nbEnfant++;
					listToRead = toRead.split(" ");
					location = listToRead[1].split("-");
					if(!terrain.setCase(Integer.parseInt(location[0]), Integer.parseInt(location[1]), new Enfant(listToRead[2].charAt(0),listToRead[3],listToRead[4]))){
						nbEnfantHorsTable++; // Enfant pas ajoute
					}
					break;

				default:
					LOGGER.debug("Attention, la ligne ~ " + toRead + " ~ a ete ignoree.");
					break;
				}
			}

			br.close(); 
			
			// Initialisation de la hashmap coordEnfants
			terrain.initCoordEnfants();
			
			// Aucun enfant sur le terrain
			if(nbEnfant == nbEnfantHorsTable || nbEnfant == 0){
				JOptionPane.showMessageDialog(null, "Il n'y a aucun enfant sur le terrain.", "Erreur", JOptionPane.WARNING_MESSAGE);
			}

		}catch (Exception e){		
			System.out.println(e.toString());	
			JOptionPane.showMessageDialog(null, "Le fichier selectionne n'est pas compatible.", "Erreur", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

	}

	public Terrain getTerrain(){    	
		return terrain;
	}
	
	public boolean getInitialized(){
		return initialized;
	}
}
