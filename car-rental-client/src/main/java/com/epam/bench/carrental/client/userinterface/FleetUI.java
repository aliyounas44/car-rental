package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.userinterface.controllers.FleetController;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarDTO;

@org.springframework.stereotype.Component
public class FleetUI {
    @Autowired
    CustomTableModel<CarDTO> fleetTableModel;
    @Autowired
    FleetController fleetController;

    private JButton jButtonAddFleet = null;
    private JButton jButtonRefreshFleet = null;
    private JToolBar fleetToolBar = null;
    private JTable fleetTable = null;
    private JScrollPane fleetTableScrollPane = null;
    private JPanel fleetPanel = null;

    @PostConstruct
    private void initializeControls() {
        jButtonAddFleet = CommonUI.createJButton("Add Fleet");
        jButtonRefreshFleet = CommonUI.createJButton("Refresh Fleet");
        fleetToolBar = CommonUI.createToolbar(jButtonAddFleet, jButtonRefreshFleet);
        fleetTable = CommonUI.createTable(fleetTableModel);
        fleetTableScrollPane = CommonUI.createScrollPane(fleetTable);
        fleetPanel = CommonUI.createCustomPanel(new BorderLayout());
    }

    public void addListeners() {
        jButtonAddFleet.addActionListener(fleetController::addFleet);
        jButtonRefreshFleet.addActionListener(fleetController::refreshFleet);
    }

    public Component getFleetPanel() {
        fleetPanel.add(fleetTableScrollPane, BorderLayout.CENTER);
        fleetPanel.add(fleetToolBar, BorderLayout.NORTH);
        return fleetPanel;
    }
}
