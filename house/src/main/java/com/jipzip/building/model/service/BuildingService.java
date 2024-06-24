package com.jipzip.building.model.service;

import java.util.List;
import java.util.Map;

import com.jipzip.building.model.dto.Building;
import com.jipzip.building.model.dto.DetailCondition;
import com.jipzip.building.model.dto.SearchCondition;

public interface BuildingService {

	// 매물 검색
	public List<Building> search(SearchCondition searchCondition);
	public List<String> aptCode(DetailCondition detailCondition);
}
