package com.epam.bench.carrental.client.bl;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.commons.services.RentalClassService;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class RentalClassRequestHandler {
    @Autowired
    RentalClassService rentalClassService;

    public void addRentalClass(RentalClassDTO rentalClassDto) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(() -> this.add(rentalClassDto), result -> CommonUI.showMessage("Rental Class added."), exception -> CommonUI.showMessage("Unable to add Rental Class."));
    }

    public void getRentalClasses(Consumer<List<RentalClassDTO>> consumer) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(rentalClassService::getRentalClasses, consumer, exception -> CommonUI.showMessage("Unable to get Rental Classes."));
    }

    private RentalClassDTO add(RentalClassDTO rentalClassDTO){
	rentalClassService.addRentalClass(rentalClassDTO);
	return rentalClassDTO;
    }
}
