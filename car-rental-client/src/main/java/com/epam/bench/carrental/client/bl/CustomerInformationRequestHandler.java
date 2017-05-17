package com.epam.bench.carrental.client.bl;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CustomerService;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class CustomerInformationRequestHandler {
    @Autowired
    CustomerService customerService;

    public void addCustomer(CustomerDTO customerDto) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(() -> this.add(customerDto), result -> CommonUI.showMessage("Customer added: "), exception -> CommonUI.showMessage("Unable to add customer."));
    }

    public void getCustomers(Consumer<List<CustomerDTO>> consumer) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(customerService::getCustomer, consumer, exception -> CommonUI.showMessage("Unable to get customers."));
    }

    private CustomerDTO add(CustomerDTO customerDTO){
	customerService.addCustomer(customerDTO);
	return customerDTO;
    }
}
