package com.epam.bench.carrental.client.userinterface.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel<T> extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private List<GenericColumn<T>> columns;
    private List<T> data;

    public CustomTableModel(List<GenericColumn<T>> columnNames) {
	super();
	this.columns = columnNames;
	this.data = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
	return columns.size();
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	GenericColumn<T> t = columns.get(columnIndex);
	T dto = data.get(rowIndex);
	return t.getData(dto);
    }

    @Override
    public String getColumnName(int column) {
	return columns.get(column).getColumnName();
    }

    public void setData(List<T> t) {
	data = t;
	fireTableDataChanged();
    }

    public T getRow(int rowIndex) {
	return data.get(rowIndex);
    }

}
