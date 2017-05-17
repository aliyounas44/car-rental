package com.epam.bench.carrental.server.rentalclass.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RentalClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(unique=true)
    private String name;
    private double hourlyRate;
    
    public RentalClass() {

    }
    public RentalClass(String name, double hourlyRate) {
	this.name = name;
	this.hourlyRate = hourlyRate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    @Override
    public String toString() {
	return "RentalClass [id=" + id + ", name=" + name + ", hourlyRate=" + hourlyRate + "]";
    }
    
}
