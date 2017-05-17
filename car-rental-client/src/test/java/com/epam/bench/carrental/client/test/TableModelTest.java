package com.epam.bench.carrental.client.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.client.userinterface.models.GenericColumn;
import com.epam.bench.carrental.commons.beans.CustomerDTO;

public class TableModelTest {
    CustomTableModel<CustomerDTO> customTableModel;
    List<GenericColumn<CustomerDTO>> columns;
    List<CustomerDTO> customersList;
    public final String COLUMN_NAME = "Name";
    public final String COLUMN_EMAIL = "Email";
    public final String[] SAMPLE_DATA_NAMES = {"Ali", "Younas"};
    public final String[] SAMPLE_DATA_EMAILS = {"aliyounas44@yahoo.com", "email@yahoo.com"};
    public final int SAMPLE_DATA_FIRST_INDEX = 0;
    public final int SAMPLE_DATA_SECOND_INDEX = 1;

    @Before
    public void reset() {
	columns = new ArrayList<>();
	customTableModel = new CustomTableModel<>(columns);
	customersList = new ArrayList<>();	
    }

    private void prepareColumnsOfTableModel() {
	columns.add(new GenericColumn<>(COLUMN_NAME, CustomerDTO::getName));
	columns.add(new GenericColumn<>(COLUMN_EMAIL, CustomerDTO::getEmailAddress));
    }

    @Test
    public void tableModelContainsCountOfColumns() {
	prepareColumnsOfTableModel();
	Assert.assertEquals(customTableModel.getColumnCount(), columns.size());
    }

    @Test
    public void tableModelContainsCountOfCurrentRows() {
	prepareColumnsOfTableModel();
	addRowsInTableModel();
	Assert.assertEquals(customTableModel.getRowCount(), customersList.size());
    }

    private void addRowsInTableModel() {
	customersList.add(new CustomerDTO(SAMPLE_DATA_NAMES[SAMPLE_DATA_FIRST_INDEX], SAMPLE_DATA_EMAILS[SAMPLE_DATA_FIRST_INDEX]));
	customersList.add(new CustomerDTO(SAMPLE_DATA_NAMES[SAMPLE_DATA_SECOND_INDEX], SAMPLE_DATA_EMAILS[SAMPLE_DATA_SECOND_INDEX]));
	customTableModel.setData(customersList);
    }

    @Test
    public void tableModelContainsMetaData() {
	prepareColumnsOfTableModel();
	Assert.assertEquals(customTableModel.getColumnName(SAMPLE_DATA_SECOND_INDEX), COLUMN_EMAIL);
    }

    @Test
    public void tableModelContainsData() {
	prepareColumnsOfTableModel();
	addRowsInTableModel();
	Assert.assertEquals(customTableModel.getValueAt(SAMPLE_DATA_FIRST_INDEX, SAMPLE_DATA_SECOND_INDEX), SAMPLE_DATA_EMAILS[SAMPLE_DATA_FIRST_INDEX]);
    }
}
