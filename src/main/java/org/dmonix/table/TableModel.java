package org.dmonix.table;

import java.util.*;
import javax.swing.table.*;

/**
 * A simple table model.
 * <p>
 * Copyright: Copyright (c) 2001
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class TableModel extends AbstractTableModel {

    private static final long serialVersionUID = 7526472295622776147L;
    private List<String> columnNames = new Vector<String>();
    private List<List> rowData = new Vector<List>();

    /**
     * Creates a default table model.
     * 
     */
    public TableModel() {
    }

    /**
     * Creates a table model with the provided columns.
     * 
     * @param columnNames
     * @since 1.1
     */
    public TableModel(List<String> columnNames) {
        setColumnNames(columnNames);
    }

    /**
     * Creates a table model with the provided columns.
     * 
     * @param columnNames
     * @since 1.1
     */
    public TableModel(String[] columnNames) {
        setColumnNames(columnNames);
    }

    /**
     * Add a row
     * 
     * @param row
     *            A vector with the new row data
     */
    public void addRow(List row) {
        rowData.add(row);
        fireTableRowsInserted(rowData.size() - 1, rowData.size() - 1);
    }

    /**
     * Return the number of columns
     * 
     * @return int
     */
    public int getColumnCount() {
        return columnNames.size();
    }

    /**
     * Return the number of rows
     * 
     * @return int
     */
    public int getRowCount() {
        return rowData.size();
    }

    /**
     * Returns the name of the choosen column
     * 
     * @param col
     *            Column index
     * @return String
     */
    public String getColumnName(int col) {
        return columnNames.get(col).toString();
    }

    /**
     * Returns the object at the specified index
     * 
     * @param row
     *            The row
     * @param col
     *            The column
     * @return Object
     */
    public Object getValueAt(int row, int col) {
        return rowData.get(row).get(col);
    }

    /**
     * Set the row data list
     * 
     * @param rowData
     *            List with all the rows
     */
    public void setRowData(List<List> rowData) {
        this.rowData = rowData;
        fireTableDataChanged();
    }

    /**
     * Set column names
     * 
     * @param columnNames
     *            Array with the column names
     * @since 1.1
     */
    public void setColumnNames(String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++) {
            this.columnNames.add(columnNames[i]);
        }
    }

    /**
     * Set column names
     * 
     * @param columnNames
     *            List with the column names
     */
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    /*
     * JTable uses this method to determine the default renderer/ editor for each cell.
     */
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}