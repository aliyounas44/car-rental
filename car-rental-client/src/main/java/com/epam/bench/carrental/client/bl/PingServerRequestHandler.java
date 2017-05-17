package com.epam.bench.carrental.client.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.services.ServerInformation;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class PingServerRequestHandler {

    @Autowired
    ServerInformation application;

    public void pingServer() {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(application::getServerInfo, result -> CommonUI.showMessage("Server returned IP: " + result), exception -> CommonUI.showMessage("Connection failed"));
    }

}