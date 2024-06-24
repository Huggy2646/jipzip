package com.jipzip.buildingtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.jipzip.AbstractTest;
import com.jipzip.building.controller.BuildingRestController;
import com.jipzip.building.mapper.BuildingMapper;
import com.jipzip.building.model.dto.DetailCondition;

public class ApartCodeTest extends AbstractTest {

	static DetailCondition dc = new DetailCondition("4146311800","동원로얄듀크");
	@Autowired
	BuildingMapper bm;
	
	@Autowired
	BuildingRestController bc;
	
	
	@Test
	public void apartcodemppertest() {
		
		List<String>result=bm.aptCode(dc);
		if(result.size()==0) {
			System.out.println("empty");
			return;
		}
		assertEquals("A44697610",result.get(0));
	}
	
	@Test
	public void apartcontrollertest() {
		ResponseEntity<?> result = bc.aptcode(dc);
		assertEquals("A44697610", result.getBody());
	}
}
