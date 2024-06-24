package com.jipzip.building.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jipzip.building.model.dto.Building;
import com.jipzip.building.model.dto.DetailCondition;
import com.jipzip.building.model.dto.SearchCondition;
import com.jipzip.building.model.service.BuildingService;

@RestController
@RequestMapping("/buildingapi")
public class BuildingRestController {

	@Autowired
	private BuildingService buildingService;

	@GetMapping("/search")
	public ResponseEntity<?> search(@ModelAttribute SearchCondition searchCondition) {
		List<Building> buildings = null;
		try {
			System.out.println("!searchCondition: " + searchCondition);
			buildings = buildingService.search(searchCondition);
			if (buildings != null && buildings.size() != 0) {
				return ResponseEntity.ok(buildings);
			}
		} catch (Exception e) {
			exceptionHandling(e);
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/aptcode")
	public ResponseEntity<?> aptcode(@ModelAttribute DetailCondition detailCondition) {
		System.out.println(detailCondition.getDongCode());
		List<String> result=null;
		try {
			result = buildingService.aptCode(detailCondition);
			if (result==null || result.size() == 0) {
				return ResponseEntity.ok(null);
			}

		} catch (Exception e) {
			exceptionHandling(e);
		}
		return ResponseEntity.ok(result.get(0));
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
	}
}
