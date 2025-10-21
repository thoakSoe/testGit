package com.ojt.OJT19SpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojt.OJT19SpringBoot.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
	
	public List<CarEntity> findByBrandName(String brandName);
	public List<CarEntity> findByPriceGreaterThan(Double price);
	
	@Query("select c from CarEntity c where c.brandName=:bName")
	public List<CarEntity> searchByBrandName(@Param("bName") String brandName);
	
	//modifying and transcational ko update nae delete mhr 3 ya mal//
	@Modifying
	@Transactional
	@Query("update CarEntity c set c.color=:color where c.id=:id")
	public void updateColorById(@Param("id")Long id, @Param("color")String color);
}
