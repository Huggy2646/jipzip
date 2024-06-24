package com.jipzip.building.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jipzip.building.model.dto.Building;
import com.jipzip.building.model.dto.DetailCondition;
import com.jipzip.building.model.dto.SearchCondition;

@Mapper
public interface BuildingMapper {
	
	public List<Building> search(SearchCondition searchCondition);
	
	public List<String> aptCode(DetailCondition detailCondition);
	
}
