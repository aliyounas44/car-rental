package com.epam.bench.carrental.server.report.beans;

import java.time.DayOfWeek;
import java.util.Map;

public class Report {
    private Map<String, Double> rentalClassesWiseEarning;
    private Map<String, Double> rentalClassesWiseUtilization;
    private double totalEarning;
    private double totalUtilization;
    private Map<DayOfWeek, Map<String, Double>> dayOfWeekWiseUtilizationForEveryClass;
    
    public Report() {
    }

    public Report(Map<String, Double> rentalClassesWiseEarning, Map<String, Double> rentalClassesWiseUtilization, double totalEarning,
	    double totalUtilization, Map<DayOfWeek, Map<String, Double>> dayOfWeekWiseUtilizationForEveryClass) {
	this.rentalClassesWiseEarning = rentalClassesWiseEarning;
	this.rentalClassesWiseUtilization = rentalClassesWiseUtilization; 
	this.totalEarning = totalEarning;
	this.totalUtilization = totalUtilization;
	this.dayOfWeekWiseUtilizationForEveryClass = dayOfWeekWiseUtilizationForEveryClass;
    }

    public Map<String, Double> getRentalClassesWiseEarning() {
        return rentalClassesWiseEarning;
    }

    public void setRentalClassesWiseEarning(Map<String, Double> rentalClassesWiseEarning) {
        this.rentalClassesWiseEarning = rentalClassesWiseEarning;
    }

    public Map<String, Double> getRentalClassesWiseUtilization() {
        return rentalClassesWiseUtilization;
    }

    public void setRentalClassesWiseUtilization(Map<String, Double> rentalClassesWiseUtilization) {
        this.rentalClassesWiseUtilization = rentalClassesWiseUtilization;
    }

    public double getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(double totalEarning) {
        this.totalEarning = totalEarning;
    }

    public double getTotalUtilization() {
        return totalUtilization;
    }

    public void setTotalUtilization(double totalUtilization) {
        this.totalUtilization = totalUtilization;
    }

    public Map<DayOfWeek, Map<String, Double>> getDayOfWeekWiseUtilizationForEveryClass() {
        return dayOfWeekWiseUtilizationForEveryClass;
    }

    public void setDayOfWeekWiseUtilizationForEveryClass(
    	Map<DayOfWeek, Map<String, Double>> dayOfWeekWiseUtilizationForEveryClass) {
        this.dayOfWeekWiseUtilizationForEveryClass = dayOfWeekWiseUtilizationForEveryClass;
    }

    @Override
    public String toString() {
	return "Report [rentalClassesWiseEarning=" + rentalClassesWiseEarning + ", rentalClassesWiseUtilization="
		+ rentalClassesWiseUtilization + ", totalEarning=" + totalEarning + ", totalUtilization="
		+ totalUtilization + ", dayOfWeekWiseUtilizationForEveryClass=" + dayOfWeekWiseUtilizationForEveryClass
		+ "]";
    }
    
}
