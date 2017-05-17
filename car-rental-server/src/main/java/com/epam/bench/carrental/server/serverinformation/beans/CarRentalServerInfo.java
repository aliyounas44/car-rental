package com.epam.bench.carrental.server.serverinformation.beans;

import com.epam.bench.carrental.server.threadlocal.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;

public class CarRentalServerInfo {
    private LocalTime time;
    private String ipAddress;

    public CarRentalServerInfo() {
        time = LocalTime.now();
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public CarRentalServerInfo(String tenantId) {
        time = LocalTime.now();
        try {
            ipAddress = tenantId + " @ " + InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public CarRentalServerInfo(LocalTime time, String ipAddress) {
        this.time = time;
        this.ipAddress = ipAddress;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CarRentalServerInfo [time=" + time + ", ipAddress=" + ipAddress + "]";
    }
}
