package com.ojt.OJT19SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.OJT19SpringBoot.dto.CarDTO;
import com.ojt.OJT19SpringBoot.entity.CarEntity;
import com.ojt.OJT19SpringBoot.exception.CarNotFoundException;
import com.ojt.OJT19SpringBoot.repository.CarRepository;

@Service
public class CarImpl implements CarService{

	@Autowired
	CarRepository carRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	public CarDTO saveCar(CarDTO car) {
		
		CarEntity output = mapper.map(car, CarEntity.class);
		CarEntity entity = carRepo.save(output);
		return mapper.map(entity, CarDTO.class);
	}
	
	@Override
	public List<CarDTO> getAllCars(){
		List<CarEntity> entitylist = carRepo.findAll();
		return entitylist
				.stream()
				.map(entity -> mapper.map(entity, CarDTO.class)).toList();
	}

	@Override
	public CarDTO getById(Long id) {
		CarEntity entity = carRepo
							.findById(id)
							.orElseThrow(()-> new CarNotFoundException("Car not found!"));
		
		return mapper.map(entity, CarDTO.class);
	}

	@Override
	public CarDTO updateCar(CarDTO car) {
		
		Optional<CarEntity> resCar = carRepo.findById(car.getId());
		CarEntity carObj = resCar.get();
		
		carObj.setBrandName(car.getBrandName());
		carObj.setColor(car.getColor());
		carObj.setPrice(car.getPrice());
		
		CarEntity entity = carRepo.save(carObj);
		return mapper.map(entity, CarDTO.class);
	}

	@Override
	public void deleteCar(Long id) {
		Optional<CarEntity> resCar = carRepo.findById(id);
		CarEntity carObj = resCar.get();
		
		carRepo.delete(carObj);
	}

	@Override
	public List<CarDTO> findByBrandName(String brandName) {
		List<CarEntity> entityList = carRepo.findByBrandName(brandName);
		List<CarDTO> dtoList = entityList
								.stream()
								.map(entity-> mapper.map(entity, CarDTO.class)
								).toList();
		return dtoList;
	}

	@Override
	public List<CarDTO> findByPriceGreaterThan(Double price) {
		List<CarEntity> entityList = carRepo.findByPriceGreaterThan(price);
		List<CarDTO> dtoList = entityList
								.stream()
								.map(entity-> mapper.map(entity, CarDTO.class)
								).toList();
		return dtoList;
	}

	@Override
	public List<CarDTO> searchByBrandName(String brandName) {
		List<CarEntity> entityList = carRepo.searchByBrandName(brandName);
		List<CarDTO> dtoList = entityList
								.stream()
								.map(entity-> mapper.map(entity, CarDTO.class)
								).toList();
		return dtoList;
	}

	@Override
	public void updateColorById(Long id, String color) {
		carRepo.updateColorById(id, color);
	}

}
