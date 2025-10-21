package com.ojt.OJT19SpringBoot.service;


import java.util.List;


import com.ojt.OJT19SpringBoot.dto.CarDTO;

public interface CarService {
	
	public CarDTO saveCar(CarDTO car);
	public List<CarDTO> getAllCars();
	public CarDTO getById(Long id);
	public CarDTO updateCar(CarDTO car);
	public void deleteCar(Long id);
	public List<CarDTO> findByBrandName(String brandName);
	public List<CarDTO> findByPriceGreaterThan(Double price);
	public List<CarDTO> searchByBrandName(String brandName);
	public void updateColorById(Long id, String color);


}
