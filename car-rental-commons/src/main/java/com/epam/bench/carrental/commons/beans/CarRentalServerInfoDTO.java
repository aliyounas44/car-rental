package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;
import java.time.LocalTime;

public class CarRentalServerInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalTime time;
    private String ipAddress;

    public CarRentalServerInfoDTO() {
	
    }

    public CarRentalServerInfoDTO(LocalTime time, String ipAddress) {
	this.time = time;
	this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public LocalTime getTime() {
	return time;
    }

    @Override
    public String toString() {
	return "CarRentalServerInfoDTO [time=" + time + ", ipAddress=" + ipAddress + "]";
    }

}
