package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.userinterface.controllers.PingServerController;

@org.springframework.stereotype.Component
public class UserInterface {

    private JFrame mainFrame;
    private JButton requestButton = CommonUI.createJButton("Test Connection");
    private JTabbedPane jTabbedPane = CommonUI.createTabbedPane(JTabbedPane.TOP);

    @Autowired
    PingServerController pingServerController;
    @Autowired
    CustomerUI customerUserInterface;
    @Autowired
    FleetUI fleetUserInterface;
    @Autowired
    RentalInformationUI rentalInformationInterface;
    @Autowired
    BookingInformationUI bookingInformationUI;
    @Autowired
    CurrentRentalsUI currentRentalUserInterface;
    @Autowired
    RentalHistoryUI rentalHistoryUI;
    @Autowired
    RentalClassUI rentalClassUI;
    @Autowired
    ReportStatisticsUI reportStatisticsUI;

    @PostConstruct
    public void createUserInterface() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        createFrame();
        createTabPane();
        linkControlToFrame(jTabbedPane);
        addListeners();
    }

    private void createFrame() {
        mainFrame = new JFrame("Car-Rental-Client");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 3));
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(400, 400, 700, 500);
    }

    private void createTabPane() {
        jTabbedPane.addTab("Customers", customerUserInterface.getCustomerPanel());
        jTabbedPane.addTab("Fleets", fleetUserInterface.getFleetPanel());
        jTabbedPane.addTab("Available to Rent", rentalInformationInterface.getRentalInformationPanel());
        jTabbedPane.addTab("Available to Book", bookingInformationUI.getBookingInformationPanel());
        jTabbedPane.addTab("Currently Rentals", currentRentalUserInterface.getCurrentRentalInformationPanel());
        jTabbedPane.addTab("History", rentalHistoryUI.getRentalHistoryPanel());
        jTabbedPane.addTab("Rental Classes", rentalClassUI.getRentalClassPanel());
        jTabbedPane.addTab("Report Statistics", reportStatisticsUI.getRentalReportPanel());
        jTabbedPane.addTab("Other", CommonUI.createCustomPanel(new BorderLayout()).add(requestButton));
    }

    private void linkControlToFrame(Component component) {
        mainFrame.add(component);
    }

    private void addListeners() {
        requestButton.addActionListener(pingServerController::pingServerButtonListener);
        customerUserInterface.addListeners();
        fleetUserInterface.addListeners();
        rentalInformationInterface.addListeners();
        currentRentalUserInterface.addListeners();
        rentalHistoryUI.addListeners();
        rentalClassUI.addListeners();
        reportStatisticsUI.addListeners();
        bookingInformationUI.addListeners();
    }

}
