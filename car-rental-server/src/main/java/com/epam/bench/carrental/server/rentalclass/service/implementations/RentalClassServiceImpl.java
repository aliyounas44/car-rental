package com.epam.bench.carrental.server.rentalclass.service.implementations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.commons.services.RentalClassService;
import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;
import com.epam.bench.carrental.server.rentalclass.repositories.RentalClassRepository;

public class RentalClassServiceImpl implements RentalClassService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RentalClassRepository rentalClassRepository;
    @Override
    public void addRentalClass(RentalClassDTO rentalClassDTO) {
	rentalClassRepository.save(modelMapper.map(rentalClassDTO, RentalClass.class));
    }

    @Override
    public List<RentalClassDTO> getRentalClasses() {
	return StreamSupport.stream(rentalClassRepository.findAll().spliterator(), false).map(s -> modelMapper.map(s, RentalClassDTO.class)).collect(Collectors.toList());
    }

}
