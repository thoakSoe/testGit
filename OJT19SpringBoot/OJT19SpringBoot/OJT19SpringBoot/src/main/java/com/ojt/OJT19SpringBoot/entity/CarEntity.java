package com.ojt.OJT19SpringBoot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cars")
public class CarEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="brand_name", nullable = false, length = 30)
	private String brandName;
	
	@Column(name="color", nullable = false, length = 30)
	private String color;
	
	@Column(name="price", nullable = false)
	private Double price;

}
