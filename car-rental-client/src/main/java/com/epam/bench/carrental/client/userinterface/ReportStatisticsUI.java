package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.bl.ReportStatisticsRequestHandler;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePicker;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePickerImpl;
import com.epam.bench.carrental.commons.beans.ReportDTO;

@org.springframework.stereotype.Component
public class ReportStatisticsUI {
    @Autowired
    ReportStatisticsRequestHandler reportStatisticsRequestHandler;
    
    private JButton showReportButton = null;
    private JToolBar reportToolBar = null;
    private JTextArea reportTextArea = null;
    private JScrollPane reportTextAreaScrollPane = null;
    private JPanel reportPanel = null;
    private DatePicker dateFrom = null;
    private DatePicker dateTo = null;

    @PostConstruct
    private void initializeControls() {
	showReportButton = CommonUI.createJButton("Show Report");
	reportTextArea = CommonUI.createTextArea();
	reportTextAreaScrollPane = CommonUI.createScrollPane(reportTextArea);
	reportPanel = CommonUI.createCustomPanel(new BorderLayout());
	dateFrom = new DatePickerImpl();
	dateTo = new DatePickerImpl();
	reportToolBar = CommonUI.createToolbar(showReportButton, dateFrom.getComponent(), dateTo.getComponent());
    }

    public void addListeners() {
	showReportButton.addActionListener(this::showReportListener);
    }

    public Component getRentalReportPanel() {
	reportPanel.add(reportTextAreaScrollPane, BorderLayout.CENTER);
	reportPanel.add(reportToolBar, BorderLayout.NORTH);
	return reportPanel;
    }

    public void showReportListener(ActionEvent arg0) {
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
	reportStatisticsRequestHandler.fetchReport(this::setDataInHistoryModel, dateF, dateT);
    }

    private void setDataInHistoryModel(ReportDTO reportDTO) {
	reportTextArea.setText(reportDTO.toString());
    }
    
}
