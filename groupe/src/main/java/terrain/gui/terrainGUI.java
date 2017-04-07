package terrain.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.lowagie.text.Image;

import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Enfant;
import terrain.domain.abstractcase.Rocher;

public class terrainGUI extends JFrame{
	
	private JTable terrain;
	
	public terrainGUI(){
		
		this.setTitle("Chasse aux oeufs");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
		this.setVisible(true);
		//ZModel model = new ZModel(data);
		//this.terrain = new JTable(model);
		//this.getContentPane().add(terrain, BorderLayout.CENTER);
		this.setContentPane(new JLabel(new ImageIcon("D:\\Java\\git\\GLPOO\\groupe\\src\\main\\resources\\background.jpg")));
	//	terrain.setDefaultRenderer(CustomCellRenderer.class, new CustomCellRenderer());
	}
	/*class ZModel extends AbstractTableModel{

	    private Object[][] data;
	    
	    public ZModel(Object[][] data){
	      this.data = data;
	    }
	    public int getColumnCount() {
	      return this.data[0].length;
	    }
	    public int getRowCount() {
	      return this.data.length;
	    }
	    public Object getValueAt(int row, int col) {
	      return this.data[row][col];
	    }
	    
	}
	public class CustomCellRenderer extends DefaultTableCellRenderer {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        if (table.getValueAt(row,column) instanceof Enfant)
	        {	
	        	setIcon(new javax.swing.ImageIcon(getClass().getResource("/")));
	        }if (table.getValueAt(row,column) instanceof Chocolat)
	        {	
	        	setIcon(new javax.swing.ImageIcon(getClass().getResource("/")));
	        }if (table.getValueAt(row,column) instanceof CaseVide)
	        {	
	        	setIcon(new javax.swing.ImageIcon(getClass().getResource("/")));
	        }if (table.getValueAt(row,column) instanceof Rocher)
	        {	
	        	setIcon(new javax.swing.ImageIcon(getClass().getResource("/")));
	        }
	        return l;
	    }
	}*/
	public static void main(String[] args){
		    terrainGUI terrainGUI = new terrainGUI();
		    terrainGUI.setVisible(true);
	}       
}
