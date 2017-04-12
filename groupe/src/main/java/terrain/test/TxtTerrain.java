package terrain.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.log4j.Logger;

import terrain.domain.Jardin;
import terrain.domain.Terrain;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;

import java.util.ArrayList;

import terrain.domain.abstractcase.Deplacement;
import terrain.domain.abstractcase.Enfant;
import terrain.domain.abstractcase.Orientation;

public class TxtTerrain {
	private File file_terrain;
	private File file_enfants;
    private Terrain terrain;
    
    private static final Logger LOGGER = Logger.getLogger(TxtTerrain.class);
    
    /**
     * Initialisation du terrain
     * 
     * @param file
     */
    public void init(File file_terrain, File file_enfants){
    	LOGGER.debug("init");
    	this.file_terrain = file_terrain;
    	this.file_enfants = file_enfants;
    	
    	loadTerrain();
    	loadEnfants();
    }

    /**
     * Chargement du terrain depuis fichier
     */
    
    private void loadTerrain(){
    	
    	Integer ligne,colonne;
    	
    	String toread;

		String[] listtoread;

		String[] location;

		Integer flag;

		try{

			FileInputStream ips = new FileInputStream(file_terrain); 
			
			InputStreamReader ipsr = new InputStreamReader(ips);
			
			BufferedReader br = new BufferedReader(ipsr);
			
			do{

				flag = 1;

				toread = br.readLine();

				switch(toread.charAt(0)){

					case '#':
						break;

					case 'J':
					case 'j':
						flag = 0;
						listtoread = toread.split(" ");
						ligne = Integer.parseInt(listtoread[2]);
						colonne = Integer.parseInt(listtoread[1]);
						terrain = Jardin.getInstance(colonne, ligne);
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toread + " ~ a été ignorée.");
						break;

				}

			}while(flag == 1);
			
			while((toread = br.readLine()) != null){

				switch(toread.charAt(0)){

					case '#':
						break;

					case 'C':
					case 'c':
						listtoread = toread.split(" ");
						location = listtoread[1].split("-");
						terrain.setCase(Integer.parseInt(location[0])-1,Integer.parseInt(location[1])-1,new Chocolat(Integer.parseInt(listtoread[2])));
						break;

					case 'R':
					case 'r':
						listtoread = toread.split(" ");
						location = listtoread[1].split("-");
						terrain.setCase(Integer.parseInt(location[0])-1,Integer.parseInt(location[1])-1,new Rocher());
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toread + " ~ a été ignorée.");
						break;

				}

			}

			br.close(); 
		
		}catch (Exception e){
		
			LOGGER.debug(e.toString());
		
		}

    }
    
    private void loadEnfants(){
    	
    	try{

			FileInputStream ips = new FileInputStream(file_enfants); 
			
			InputStreamReader ipsr = new InputStreamReader(ips);
			
			BufferedReader br = new BufferedReader(ipsr);
			
			String toread;
			
			String[] listtoread;

			String[] location;

			while((toread = br.readLine()) != null){

				switch(toread.charAt(0)){

					case '#':
						break;

					case 'E':
					case 'e':
						Orientation orientation = null;
						listtoread = toread.split(" ");
						location = listtoread[1].split("-");
						switch(listtoread[2].charAt(0)){
							default:
								orientation = Orientation.NORD;
								break;
							case 'E':
								orientation = Orientation.EST;
								break;
							case 'S':
								orientation = Orientation.SUD;
								break;
							case 'W':
								orientation = Orientation.OUEST;
								break;
						}
						List<Deplacement> deplacements = new ArrayList<Deplacement>();
						for(int i=0; i<listtoread[3].length(); i++){
							switch(listtoread[3].charAt(i)){
								default:
									deplacements.add(Deplacement.AVANT);
									break;
								case 'D':
								case 'd':
									deplacements.add(Deplacement.DROITE);
									break;
								case 'G':
								case 'g':
									deplacements.add(Deplacement.GAUCHE);
									break;
							}

						}
						terrain.setCase(Integer.parseInt(location[0])-1, Integer.parseInt(location[1])-1, new Enfant(orientation,deplacements,listtoread[4]));
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toread + " ~ a été ignorée.");
						break;

				}


			}

			br.close(); 
		
		}catch (Exception e){
		
			System.out.println(e.toString());
		
		}
    	
    }
    
    public Terrain getTerrain(){
    	
    	return terrain;
    }
}
