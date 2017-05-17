package com.epam.bench.carrental.client.configurations;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import com.epam.bench.carrental.commons.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.client.userinterface.models.GenericColumn;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;

@Configuration
@ComponentScan("com.epam.bench.carrental")
public class SpringConfigurations {
	private final String BASE_URL = "http://localhost:9090/SpringHttpService/";

	@Bean
	public CustomSimpleHttpInvokerRequestExecutor getCustomSimpleHttpInvokerRequestExecutor() {
		return new CustomSimpleHttpInvokerRequestExecutor();
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "greeting.http");
		obj.setServiceInterface(ServerInformation.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyCustomerFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "customer.http");
		obj.setServiceInterface(CustomerService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyFleetFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "fleet.http");
		obj.setServiceInterface(FleetService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyCarRentalDetailsFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "rentdetails.http");
		obj.setServiceInterface(CarRentalDetailService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyRentalClassFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "rentalClass.http");
		obj.setServiceInterface(RentalClassService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyReportFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "report.http");
		obj.setServiceInterface(ReportingService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public HttpInvokerProxyFactoryBean getHttpInvokerProxyCarBookingDetailFactoryBean() {
		HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
		obj.setServiceUrl(BASE_URL + "booking.http");
		obj.setServiceInterface(CarBookingDetailService.class);
		obj.setHttpInvokerRequestExecutor(getCustomSimpleHttpInvokerRequestExecutor());
		return obj;
	}

	@Bean
	public CustomTableModel<CarDTO> fleetTableModel() {
		List<GenericColumn<CarDTO>> columnsFleet = new ArrayList<>();
		columnsFleet.add(new GenericColumn<>("Model", CarDTO::getModel));
		columnsFleet.add(new GenericColumn<>("Registration Number", CarDTO::getRegistrationNumber));
		return new CustomTableModel<>(columnsFleet);
	}

	@Bean
	public CustomTableModel<CarDTO> carRentalInformationModel() {
		List<GenericColumn<CarDTO>> columnsFleet = new ArrayList<>();
		columnsFleet.add(new GenericColumn<>("Model", CarDTO::getModel));
		columnsFleet.add(new GenericColumn<>("Registration Number", CarDTO::getRegistrationNumber));
		return new CustomTableModel<>(columnsFleet);
	}

	@Bean
	public CustomTableModel<CarDTO> carBookingInformationModel() {
		List<GenericColumn<CarDTO>> columnsFleet = new ArrayList<>();
		columnsFleet.add(new GenericColumn<>("Model", CarDTO::getModel));
		columnsFleet.add(new GenericColumn<>("Registration Number", CarDTO::getRegistrationNumber));
		return new CustomTableModel<>(columnsFleet);
	}

	@Bean
	public CustomTableModel<CustomerDTO> customerDataModel() {
		List<GenericColumn<CustomerDTO>> columns = new ArrayList<>();
		columns.add(new GenericColumn<>("Name", CustomerDTO::getName));
		columns.add(new GenericColumn<>("Email", CustomerDTO::getEmailAddress));
		return new CustomTableModel<>(columns);
	}

	@Bean
	public CustomTableModel<RentalClassDTO> rentalClassDataModel() {
		List<GenericColumn<RentalClassDTO>> columns = new ArrayList<>();
		columns.add(new GenericColumn<>("Name", RentalClassDTO::getName));
		columns.add(new GenericColumn<>("Hourly Rate", RentalClassDTO::getHourlyRate));
		return new CustomTableModel<>(columns);
	}

	@Bean
	public CustomTableModel<CarRentalDetailDTO> currentlyRentalInformationModel() {
		List<GenericColumn<CarRentalDetailDTO>> columns = new ArrayList<>();
		columns.add(new GenericColumn<>("Car Model", carRentalDetail -> carRentalDetail.getCar().getModel()));
		columns.add(new GenericColumn<>("Car Registration No", carRentalDetail -> carRentalDetail.getCar().getRegistrationNumber()));
		columns.add(new GenericColumn<>("Customer Name", carRentalDetail -> carRentalDetail.getCustomer().getName()));
		columns.add(new GenericColumn<>("Customer Email", carRentalDetail -> carRentalDetail.getCustomer().getEmailAddress()));
		columns.add(new GenericColumn<>("Rental Start Date Time", CarRentalDetailDTO::getRentalStartDateTime));
		columns.add(new GenericColumn<>("Planned Returned Date Time", carRentalDetail -> carRentalDetail.getCar().getPlannedReturnedDateTime()));
		return new CustomTableModel<>(columns);
	}

	@Bean
	public CustomTableModel<CarRentalDetailDTO> rentalHistoryInformationModel() {
		List<GenericColumn<CarRentalDetailDTO>> columns = new ArrayList<>();
		columns.add(new GenericColumn<>("Car Model", carRentalDetail -> carRentalDetail.getCar().getModel()));
		columns.add(new GenericColumn<>("Car Registration No", carRentalDetail -> carRentalDetail.getCar().getModel()));
		columns.add(new GenericColumn<>("Customer Name", carRentalDetail -> carRentalDetail.getCustomer().getName()));
		columns.add(new GenericColumn<>("Customer Email", carRentalDetail -> carRentalDetail.getCustomer().getEmailAddress()));
		columns.add(new GenericColumn<>("Rental Start Date Time", CarRentalDetailDTO::getRentalStartDateTime));
		columns.add(new GenericColumn<>("Rental End Date Time", CarRentalDetailDTO::getRentalEndDateTime));
		return new CustomTableModel<>(columns);
	}

	@Bean
	@Scope("prototype")
	public JComboBox<String> comboBox() {
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem(" ");
		return comboBox;
	}

}
