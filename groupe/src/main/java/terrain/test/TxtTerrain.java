package terrain.test;

import java.io.File;
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
    	
    	String toread;

		String[] listtoread;

		String[] location;

		Integer flag;

		try{

			InputStream ips = new FileInputStream(file); 
			
			InputStreamReader ipsr = new InputStreamReader(ips);
			
			BufferedReader br = new BufferedReader(ipsr);
			
			do{

				flag = 1;

				toread = br.readline();

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
			
			while((toread = br.readline()) != null){

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
		
			System.out.println(e.toString());
		
		}

	}
    }
    
    public Terrain getTerrain(){
    	
    	// TODO
    	return terrain;
    }
}
