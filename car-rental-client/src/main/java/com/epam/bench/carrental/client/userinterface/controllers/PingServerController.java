package com.epam.bench.carrental.client.userinterface.controllers;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.epam.bench.carrental.client.bl.PingServerRequestHandler;

@Controller
public class PingServerController {
    @Autowired
    private PingServerRequestHandler pingServerRequestHndlr;

    public void pingServerButtonListener(ActionEvent arg0) {
	pingServerRequestHndlr.pingServer();
    }
}
