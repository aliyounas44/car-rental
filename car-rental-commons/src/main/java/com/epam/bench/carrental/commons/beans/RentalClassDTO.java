package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;

public class RentalClassDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private double hourlyRate;
    private int id;

    public RentalClassDTO() {

    }
    public RentalClassDTO(String name, double hourlyRate) {
	this.name = name;
	this.hourlyRate = hourlyRate;
    }
    public RentalClassDTO(String name, double hourlyRate, int id) {
	this.name = name;
	this.hourlyRate = hourlyRate;
	this.id = id;
    }
    public String getName() {
        return name;
    }
    public double getHourlyRate() {
        return hourlyRate;
    }
    public int getId() {
	return id;
    }
    @Override
    public String toString() {
	return "RentalClassDTO [name=" + name + ", hourlyRate=" + hourlyRate + "]";
    }
    
}
