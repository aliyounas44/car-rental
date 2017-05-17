package com.epam.bench.carrental.server.customer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    private String name;

    @Column(unique=true)
    private String emailAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Customer() {

    }

    public Customer(String name, String emailAddress) {
	this.name = name;
	this.emailAddress = emailAddress;
    }

    public String getName() {
	return name;
    }
    public void setName(String name) {
	this.name = name;
    }
    public String getEmailAddress() {
	return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
	return "Customer [name=" + name + ", emailAddress=" + emailAddress + "]";
    }

}
