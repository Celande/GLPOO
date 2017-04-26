package terrain.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import terrain.domain.abstractcase.CaseVide;
import terrain.domain.abstractcase.Chocolat;
import terrain.domain.abstractcase.Rocher;
import terrain.domain.abstractcase.enfant.Enfant;

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
}
