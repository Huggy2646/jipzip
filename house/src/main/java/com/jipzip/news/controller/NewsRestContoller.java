package com.jipzip.news.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jipzip.news.model.dto.*;
import com.jipzip.news.model.service.NewsService;

@RestController
@RequestMapping("/newsapi")
public class NewsRestContoller {
	
	NewsService newsservice;
	
	public NewsRestContoller(NewsService newsservice) {
		super();
		this.newsservice = newsservice;
	}


	@PostMapping("/news")
	public ResponseEntity<?> postNews(@RequestBody NewsDto news){
		
		newsservice.insert(news);
		return null;
		
	}
	
}
