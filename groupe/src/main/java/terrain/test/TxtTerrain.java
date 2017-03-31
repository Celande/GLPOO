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

public class TxtTerrain {
	private File file;
    private Terrain terrain;
    
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

    /**
     * Chargement du terrain depuis fichier
     */
    
    private void loadTerrain(){
    	
    	Integer ligne,colonne;
    	
    	String toRead;

		String[] listToRead;

		String[] location;

		Integer flag;

		try{

			InputStream ips = new FileInputStream(file); 
			
			InputStreamReader ipsr = new InputStreamReader(ips);
			
			BufferedReader br = new BufferedReader(ipsr);
			
			do{

				flag = 1;

				toRead = br.readLine();

				switch(toRead.charAt(0)){

					case '#':
						break;

					case 'J':
					case 'j':
						flag = 0;
						listToRead = toRead.split(" ");
						ligne = Integer.parseInt(listToRead[2]);
						colonne = Integer.parseInt(listToRead[1]);
						terrain = Jardin.getInstance(colonne, ligne);
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toRead + " ~ a été ignorée.");
						break;

				}

			}while(flag == 1);
			
			while((toRead = br.readLine()) != null){

				switch(toRead.charAt(0)){

					case '#':
						break;

					case 'C':
					case 'c':
						listToRead = toRead.split(" ");
						location = listToRead[1].split("-");
						terrain.setCase(Integer.parseInt(location[0])-1,Integer.parseInt(location[1])-1,new Chocolat(Integer.parseInt(listToRead[2])));
						break;

					case 'R':
					case 'r':
						listToRead = toRead.split(" ");
						location = listToRead[1].split("-");
						terrain.setCase(Integer.parseInt(location[0])-1,Integer.parseInt(location[1])-1,new Rocher());
						break;

					default:
						LOGGER.debug("Attention, la ligne ~ " + toRead + " ~ a été ignorée.");
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
