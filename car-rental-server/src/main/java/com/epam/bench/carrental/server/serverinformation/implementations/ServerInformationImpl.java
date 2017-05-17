package com.epam.bench.carrental.server.serverinformation.implementations;

import com.epam.bench.carrental.commons.beans.CarRentalServerInfoDTO;
import com.epam.bench.carrental.commons.services.ServerInformation;
import com.epam.bench.carrental.server.aspect.customannotation.RequiredTenant;
import com.epam.bench.carrental.server.serverinformation.beans.CarRentalServerInfo;
import com.epam.bench.carrental.server.threadlocal.TenantId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ServerInformationImpl implements ServerInformation {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TenantId tenantId;

    @RequiredTenant
    public CarRentalServerInfoDTO getServerInfo() {
        return modelMapper.map(new CarRentalServerInfo(tenantId.getTenantId()), CarRentalServerInfoDTO.class);
    }

}