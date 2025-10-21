package com.ojt.OJT19SpringBoot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojt.OJT19SpringBoot.dto.CarDTO;
import com.ojt.OJT19SpringBoot.service.CarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	CarService carService;
	
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveCar(@Valid @RequestBody CarDTO car) {
		/*
		 * CarEntity car = new CarEntity(); car.setBrandName("BMW");
		 * car.setColor("Red"); car.setPrice(200.00);
		 */
		
		CarDTO dto = carService.saveCar(car);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "create successful!");
		response.put("data", dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/list")
	public List<CarDTO> getAllCars(){
		return carService.getAllCars();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> getById(@PathVariable Long id) {
		CarDTO dto = carService.getById(id);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/update")
	public CarDTO updateCar(@RequestBody CarDTO car) {
		return carService.updateCar(car);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCar(@PathVariable Long id) {
		carService.deleteCar(id);
	}
	
	@GetMapping("/find-brandname")
	public List<CarDTO> findByBrandName(@RequestParam String brandName){
		return carService.findByBrandName(brandName);
	}
	@GetMapping("/find-greaterthan-price")
	public List<CarDTO> findByPriceGreaterThan(@RequestParam Double price){
		return carService.findByPriceGreaterThan(price);
	}
	@GetMapping("/search-brandname")
	public List<CarDTO> searchByBrandName(@RequestParam String brandName){
		return carService.searchByBrandName(brandName);
	}
	@PutMapping("/updatecolor")
	public void updateColorById(@RequestParam Long id,@RequestParam String color) {
		carService.updateColorById(id, color);
	}
}
