package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.bl.CarRentalDetailsRequestHandler;
import com.epam.bench.carrental.client.userinterface.controllers.CarRentalDetailsController;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePicker;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePickerImpl;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;

@org.springframework.stereotype.Component
public class RentalHistoryUI {
    @Autowired
    CustomTableModel<CarRentalDetailDTO> rentalHistoryInformationModel;
    @Autowired
    CarRentalDetailsController carRentalDetailsController;
    @Autowired
    CarRentalDetailsRequestHandler carRentalDetailRequestHandler;

    private JButton showHistoryButton = null;
    private JToolBar historyToolBar = null;
    private JTable historyTable = null;
    private JScrollPane historyTableScrollPane = null;
    private JPanel historyPanel = null;
    private DatePicker dateFrom = null;
    private DatePicker dateTo = null;

    @PostConstruct
    private void initializeControls() {
	showHistoryButton = CommonUI.createJButton("Show History");
	historyTable = CommonUI.createTable(rentalHistoryInformationModel);
	historyTableScrollPane = CommonUI.createScrollPane(historyTable);
	historyPanel = CommonUI.createCustomPanel(new BorderLayout());
	dateFrom = new DatePickerImpl();
	dateTo = new DatePickerImpl();
	historyToolBar = CommonUI.createToolbar(showHistoryButton, dateFrom.getComponent(), dateTo.getComponent());
    }

    public void addListeners() {
	showHistoryButton.addActionListener(this::rentalHistoryListener);
    }

    public Component getRentalHistoryPanel() {
	historyPanel.add(historyTableScrollPane, BorderLayout.CENTER);
	historyPanel.add(historyToolBar, BorderLayout.NORTH);
	return historyPanel;
    }

    public void rentalHistoryListener(ActionEvent arg0) {
	LocalDate dateF = dateFrom.getSelectedDate();
	LocalDate dateT = dateTo.getSelectedDate();
	if (dateF == null || dateT == null) {
	    CommonUI.showMessage("You have to provide date to and date from for the Report.");
	    return;
	}
	if (dateF.isAfter(dateT)) {
	    CommonUI.showMessage("Date From can not be greate than date to.");
	    return;
	}
	carRentalDetailRequestHandler.fetchHistory(this::setDataInHistoryModel, dateF, dateT);
    }

    private void setDataInHistoryModel(List<CarRentalDetailDTO> list) {
	rentalHistoryInformationModel.setData(list);
    }
}
