package terrain.gui;

import javax.swing.table.AbstractTableModel;

class ZModel extends AbstractTableModel{

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
