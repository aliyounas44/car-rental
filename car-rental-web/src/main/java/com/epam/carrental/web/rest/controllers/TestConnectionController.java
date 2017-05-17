package com.epam.carrental.web.rest.controllers;

import com.epam.bench.carrental.commons.services.ServerInformation;
import com.epam.carrental.web.beans.ServerInformationRequest;
import com.epam.carrental.web.beans.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConnectionController {
    @Autowired
    ServerInformation serverInformation;

    @RequestMapping(value = "/serverInfo", method = RequestMethod.POST)
    public ServerResponse getServerInformation(@RequestBody ServerInformationRequest serverInformationRequest) {
        return new ServerResponse(SecurityContextHolder.getContext().getAuthentication().getName(), serverInformation.getServerInfo(), serverInformationRequest.getHandShake());
    }

}
