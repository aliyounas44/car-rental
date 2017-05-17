package com.epam.bench.carrental.client.bl;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class FleetRequestHandler {
    @Autowired
    FleetService fleetService;

    public void addFleet(CarDTO fleetDto) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(() -> this.add(fleetDto), result -> CommonUI.showMessage("Car added to fleet."), exception -> CommonUI.showMessage("Unable to add car to fleet."));
    }

    public void getFleets(Consumer<List<CarDTO>> consumer) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(fleetService::getFleet, consumer, exception -> CommonUI.showMessage("Unable to get cars from fleet."));
    }

    private CarDTO add(CarDTO carDTO){
	fleetService.addCar(carDTO);
	return carDTO;
    }
}
