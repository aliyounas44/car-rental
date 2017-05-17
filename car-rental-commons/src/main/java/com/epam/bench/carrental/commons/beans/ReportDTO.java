package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Map;

public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Double> rentalClassesWiseEarning;
    private Map<String, Double> rentalClassesWiseUtilization;
    private double totalEarning;
    private double totalUtilization;
    private Map<DayOfWeek, Map<String, Double>> dayOfWeekWiseUtilizationForEveryClass;
    
    public ReportDTO() {
    }

    public ReportDTO(Map<String, Double> rentalClassesWiseEarning, Map<String, Double> rentalClassesWiseUtilization, double totalEarning,
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

    public Map<String, Double> getRentalClassesWiseUtilization() {
        return rentalClassesWiseUtilization;
    }

    public double getTotalEarning() {
        return totalEarning;
    }

    public double getTotalUtilization() {
        return totalUtilization;
    }

    public Map<DayOfWeek, Map<String, Double>> getDayOfWeekWiseUtilizationForEveryClass() {
        return dayOfWeekWiseUtilizationForEveryClass;
    }

    @Override
    public String toString() {
	return "Report [rentalClassesWiseEarning=" + rentalClassesWiseEarning + "," + System.lineSeparator() + 
		"rentalClassesWiseUtilization=" + rentalClassesWiseUtilization  + "," + System.lineSeparator() + 
		"totalEarning=" + totalEarning  + "," + System.lineSeparator() + 
		"totalUtilization=" + totalUtilization  + "," + System.lineSeparator() + 
		"dayOfWeekWiseUtilizationForEveryClass=" + dayOfWeekWiseUtilizationForEveryClass
		+ "]";
    }
    
}
