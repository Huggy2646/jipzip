package com.jipzip.news.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jipzip.news.model.dto.NewsDto;

@Mapper
public interface NewsMapper {

	int insert(NewsDto news);

}
