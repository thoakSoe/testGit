package com.ojt.OJT19SpringBoot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {
	
	private Long id;
	@NotEmpty(message = "brand name is required")
	private String brandName;
	@NotEmpty(message = "color is required")
	private String color;
	@NotNull(message = "price is required")
	private Double price;

}
