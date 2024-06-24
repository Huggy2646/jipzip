package com.jipzip.news.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jipzip.news.mapper.NewsMapper;
import com.jipzip.news.model.dto.NewsDto;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsMapper newsmapper;
	
	@Override
	public int insert(NewsDto news) {
		
		
		return newsmapper.insert(news);
		
	}

}
