package nifax.control.util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NB
 */
public final class Table {

    public static void hiddenColumns(JTable tbl, int[] column) {

        for (int i = 0; i < column.length; i++) {

            tbl.getColumnModel().getColumn(column[i]).setMaxWidth(0);

            tbl.getColumnModel().getColumn(column[i]).setMinWidth(0);

            tbl.getTableHeader().getColumnModel().getColumn(column[i]).setMaxWidth(0);

            tbl.getTableHeader().getColumnModel().getColumn(column[i]).setMinWidth(0);

        }
    }

    public static void clear(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

}
