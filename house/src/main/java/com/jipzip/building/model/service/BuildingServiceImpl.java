package com.jipzip.building.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jipzip.building.mapper.BuildingMapper;
import com.jipzip.building.model.dto.Building;
import com.jipzip.building.model.dto.DetailCondition;
import com.jipzip.building.model.dto.SearchCondition;

@Service
public class BuildingServiceImpl implements BuildingService {
	
	@Autowired
	BuildingMapper buildingMapper;

	@Override
	public List<Building> search(SearchCondition searchCondition) {
		System.out.println("servie: "+searchCondition);
		// TODO: searchCondition 유효성 검사하기(ex year로 1009)..
		 List<Building> result = buildingMapper.search(searchCondition);
//		 System.out.println(result.size());
//		 for(Building b : result) {
//			 System.out.println(b);
//		 }
		return result;
	}

	@Override
	public List<String> aptCode(DetailCondition detailCondition) {
		List<String> result = buildingMapper.aptCode(detailCondition);
		return result;
	}
	
	
	

}
