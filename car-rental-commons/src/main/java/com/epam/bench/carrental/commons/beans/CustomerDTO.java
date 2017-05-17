package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;

public final class CustomerDTO implements Serializable  {

    private static final long serialVersionUID = 1L;
    private int id = 0;
    private String name;
    private String emailAddress;

    public CustomerDTO() {

    }

    public CustomerDTO(String name, String emailAddress) {
	this.name = name;
	this.emailAddress = emailAddress;
    }

    public CustomerDTO(String name, String emailAddress, int id) {
	this.name = name;
	this.emailAddress = emailAddress;
	this.id = id;
    }

    public String getName() {
	return name;
    }
    public String getEmailAddress() {
	return emailAddress;
    }
    public int getId() {
	return id;
    }

    @Override
    public String toString() {
	return "Customer [name=" + name + ", emailAddress=" + emailAddress + "]";
    }

}
