package com.epam.bench.carrental.server.carrentaldetail.service.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;

@Component
public class RentalDetailsFetcher {
	@Autowired
	CarRentalDetailService carRentalInformationImpl;
	@Autowired
	ModelMapper modelMapper;

	public List<CarRentalDetailDTO> fetchRentalDetailsInSpecifiedDateRange(LocalDate fromDate, LocalDate toDate, boolean isUIPurpose) {
		return carRentalInformationImpl.getAllRentalInformation().stream().map(carRentalDetailDTO -> {
			CarRentalDetail detail = modelMapper.map(carRentalDetailDTO, CarRentalDetail.class);
			return detail;
		}).filter(carRentalDetailDto -> this.isRentalFallsInDateRange(carRentalDetailDto, fromDate, toDate, isUIPurpose))
				.map(s ->  {
					CarRentalDetailDTO carRentalDetailDto = modelMapper.map(s, CarRentalDetailDTO.class);
					return carRentalDetailDto;
				}).collect(Collectors.toList());
	}

	private boolean isRentalFallsInDateRange(CarRentalDetail carRentalDetail, LocalDate dateFrom, LocalDate dateTo, boolean isUIPurpose) {
		return isRentalStartAndEndDateFallsInDateRange(carRentalDetail, dateFrom, dateTo)
				|| isRentalEndDateFallsInDateRange(carRentalDetail, dateFrom, dateTo, isUIPurpose)
				|| isRentalStartDateFallsInDateRange(carRentalDetail, dateFrom, dateTo, isUIPurpose);
	}

	private boolean isRentalStartAndEndDateFallsInDateRange(CarRentalDetail carRentalDetail, LocalDate dateFrom, LocalDate dateTo) {
		return this.isRentalStartDateAfterOrOnFromDate(carRentalDetail, dateFrom)
				&& this.isRentalEndDateBeforeOrOnToDate(carRentalDetail, dateTo);
	}

	private boolean isRentalStartDateAfterOrOnFromDate(CarRentalDetail carRentalDetail, LocalDate dateFrom) {
		return carRentalDetail.getRentalStartDateTime().toLocalDate().isAfter(dateFrom)
				|| carRentalDetail.getRentalStartDateTime().toLocalDate().equals(dateFrom);
	}

	private boolean isRentalEndDateBeforeOrOnToDate(CarRentalDetail carRentalDetail, LocalDate dateTo) {
		return carRentalDetail.getRentalEndDateTime() != null
				&& (carRentalDetail.getRentalEndDateTime().toLocalDate().isBefore(dateTo)
				|| carRentalDetail.getRentalEndDateTime().toLocalDate().equals(dateTo));
	}

	private boolean isRentalEndDateFallsInDateRange(CarRentalDetail carRentalDetail, LocalDate dateFrom, LocalDate dateTo, boolean isUIPurpose) {
		if (isRentalStartDateBeforeOrOnFromDate(carRentalDetail, dateFrom)
				&& isRentalEndDateAfterOrOnFromDateAndBeforeOrOnToDate(carRentalDetail, dateFrom, dateTo)) {
			if (!isUIPurpose) {
				updateRentalDetailStartDateTimeWithFromDate(carRentalDetail, dateFrom);
			}
			return true;
		}
		return false;
	}

	private boolean isRentalStartDateBeforeOrOnFromDate(CarRentalDetail carRentalDetail, LocalDate dateFrom) {
		return carRentalDetail.getRentalStartDateTime().toLocalDate().isBefore(dateFrom)
				|| carRentalDetail.getRentalStartDateTime().toLocalDate().equals(dateFrom);
	}

	private boolean isRentalEndDateAfterOrOnFromDateAndBeforeOrOnToDate(CarRentalDetail carRentalDetail, LocalDate dateFrom, LocalDate dateTo) {
		return carRentalDetail.getRentalEndDateTime() != null
				&& isRentalEndDateAfterOrOnFromDate(carRentalDetail, dateFrom)
				&& isRentalEndDateBeforeOrOnToDate(carRentalDetail, dateTo);
	}

	private boolean isRentalEndDateAfterOrOnFromDate(CarRentalDetail carRentalDetail, LocalDate dateFrom) {
		return carRentalDetail.getRentalEndDateTime().toLocalDate().isAfter(dateFrom)
				|| carRentalDetail.getRentalEndDateTime().toLocalDate().equals(dateFrom);
	}

	private boolean isRentalStartDateFallsInDateRange(CarRentalDetail carRentalDetail, LocalDate dateFrom, LocalDate dateTo, boolean isUIPurpose) {
		if (isRentalStartDateAfterOrOnFromDate(carRentalDetail, dateFrom)
				&& isRentalStartDateBeforeOrOnToDate(carRentalDetail, dateTo)
				&& isRentalEndDateAfterOrOnToDate(carRentalDetail, dateTo)) {
			if(!isUIPurpose) {
				updateRentalDetailEndDateTimeWithToDate(carRentalDetail, dateTo);
			}
			return true;
		}
		return false;
	}

	private boolean isRentalStartDateBeforeOrOnToDate(CarRentalDetail carRentalDetail, LocalDate dateTo) {
		return carRentalDetail.getRentalStartDateTime().toLocalDate().isBefore(dateTo)
				|| carRentalDetail.getRentalStartDateTime().toLocalDate().equals(dateTo);
	}

	private boolean isRentalEndDateAfterOrOnToDate(CarRentalDetail carRentalDetail, LocalDate dateTo) {
		return carRentalDetail.getRentalEndDateTime() != null
				&& (carRentalDetail.getRentalEndDateTime().toLocalDate().isAfter(dateTo)
				|| carRentalDetail.getRentalEndDateTime().toLocalDate().equals(dateTo));
	}

	private void updateRentalDetailStartDateTimeWithFromDate(CarRentalDetail carRentalDetail, LocalDate dateFrom) {
		carRentalDetail.setRentalStartDateTime(dateFrom.atStartOfDay());
	}

	private void updateRentalDetailEndDateTimeWithToDate(CarRentalDetail carRentalDetail, LocalDate dateTo) {
		carRentalDetail.setRentalEndDateTime(dateTo.atTime(23, 59, 59));
	}
}
