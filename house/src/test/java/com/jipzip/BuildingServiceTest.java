package com.jipzip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jipzip.building.model.dto.Building;
import com.jipzip.building.model.dto.SearchCondition;
import com.jipzip.building.model.service.BuildingService;

public class BuildingServiceTest extends AbstractTest {

	private static final Logger logger = LoggerFactory.getLogger(BuildingMapperTest.class);
	
	// 픽스처(fixture): 테스트를 수행하는데 필요한 정보나 객체
	@Autowired
	private BuildingService buildingService;
	
	private SearchCondition searchCondition1;
	private SearchCondition searchCondition2;
	private SearchCondition searchCondition3;
	
	@Before
	public void setup() {
//		searchCondition1 = new SearchCondition("sido", "gugun", "1111011500", 2018, 3, "floor");
//		searchCondition2 = new SearchCondition("sido", "gugun", "1111018300", 2015, 1, "dealAmount", "desc");
//		searchCondition3 = new SearchCondition("sido", "gugun", "325235620", 2015, 1);
	}
	
	@Test
	public void searchTest() { // 검색 테스트
		// 개수 테스트 1
		List<Building> buildings1 = buildingService.search(searchCondition1);
		assertEquals(6, buildings1.size());
		// 개수 테스트 2
		List<Building> buildings2 = buildingService.search(searchCondition2);
		assertEquals(4, buildings2.size());
		// 개수 테스트 3
		List<Building> buildings3 = buildingService.search(searchCondition3);
		assertEquals(0, buildings3.size());
		
		// 정렬 테스트 1
		// 대표적으로 dealAmount로 비교
		assertEquals("1", buildings1.get(0).getFloor());
		// 정렬 테스트 2
		// 대표적으로 dealAmount로 비교
		assertEquals("112,000", buildings2.get(0).getDealAmount());
	}
}
