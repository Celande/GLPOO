package terrain.domain;

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import terrain.domain.abstractcase.AbstractCase;
import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.enfant.Enfant;
import terrain.domain.abstractcase.enfant.Orientation;
import terrain.test.TxtTerrain;
import terrain.domain.abstractcase.Rocher;

public class TerrainGUI extends JFrame{
	
	private final static String RESOURCES_PATH = "src/main/resources/";
	static JFrame frame;
	public TerrainGUI(){
		frame=new JFrame();
		frame.setTitle("Easter Tacos");
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
		frame.setVisible(true);
		frame.setContentPane(new JLabel(new ImageIcon(new ImageIcon(RESOURCES_PATH+"desert2cartoon.png").getImage().getScaledInstance(1900, 1070,Image.SCALE_SMOOTH))));
		Jardin jardin=Jardin.getInstance(4,4);
		jardin.setCase(1, 1, new Rocher());
		jardin.setCase(1, 2, new Rocher());
		jardin.setCase(1, 3, new Rocher());
		jardin.setCase(2, 1, new Rocher());
		jardin.setCase(2, 2, new Rocher());
		jardin.setCase(3, 3, new Chocolat(6));
		jardin.setCase(3, 2, new Enfant('E',"A","Pedro"));
		update(jardin.getTable(),jardin.getLigne(),jardin.getColonne());
		jardin.setCase(3, 4, new Chocolat(6));
		update(jardin.getTable(),jardin.getLigne(),jardin.getColonne());
	}
	public static void update(AbstractCase[][] table,int line,int column)
	{
		
		Insets p=frame.getInsets();
		double h=0;
		double w=0;
		h=frame.getHeight()-p.top-p.bottom;
		w=frame.getWidth()-p.left-p.right;
		h=h/line;
		w=w/column;
		int i=line;
		int wtemp;
		int htemp;
		if (h<=w)
			  htemp=wtemp=(int)h;
		else
			  htemp=wtemp=(int)w;
		int k=1;
		int j=column;
		frame.getContentPane().removeAll();
		JLabel[][] pan = new JLabel[i][j];    
		frame.setLayout(new GridLayout(i,j));
		for(int m=0;m<i;m++) 
		{
		   for(int n=0;n<j;n++) 
		   {
		      pan[m][n] = new JLabel();
		      frame.add(pan[m][n]);
		      if (table[m][n] instanceof CaseVide)
		      {
		    	  pan[m][n].setIcon(null);
		      }
		      if (table[m][n] instanceof Chocolat)
		      {
		    	  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
		    	  pan[m][n].setIcon(null);

		    	  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"tacos_cartoon.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		      }
		      if (table[m][n] instanceof Rocher)
		      {
		    	  pan[m][n].setIcon(null);
		    	  if (k==1)
		    	  {   
		    		  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
			    	  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"cactus1cartoon.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  }
		    	  if (k==2)
		    	  {   
		    		  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
			    	  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"cactus2cartoon.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  }
		    	  if (k==3)
		    	  {   
		    		  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
			    	  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"cactus3cartoon.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  }
		    	  if (k==4)
		    	  {   
		    		  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
			    	  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"cactus4cartoon.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  }
		    	  k++;
		    	  if (k==5)
		    		  k=1;
		      }
		      if (table[m][n] instanceof Enfant)
		      {
		    	  pan[m][n].setHorizontalAlignment(JLabel.CENTER);
		    	  pan[m][n].setIcon(null);
		    	  if (((Enfant)(table[m][n])).getOrientation()==Orientation.NORD)
		    			  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"mexicain1cartoonnord.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  else if (((Enfant)(table[m][n])).getOrientation()==Orientation.SUD)
	    			  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"mexicain1cartoonsud.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  else if (((Enfant)(table[m][n])).getOrientation()==Orientation.OUEST)
	    			  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"mexicain1cartoonouest.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		    	  else if (((Enfant)(table[m][n])).getOrientation()==Orientation.EST)
	    			  pan[m][n].setIcon(new ImageIcon(new ImageIcon(RESOURCES_PATH+"mexicain1cartoonest.png").getImage().getScaledInstance(wtemp, htemp,Image.SCALE_SMOOTH)));
		      }
		   }
		}
	}
	public static void main(String[] args){
		    TerrainGUI terrainGUI = new TerrainGUI();
		    terrainGUI.setVisible(true);
	}       
}

