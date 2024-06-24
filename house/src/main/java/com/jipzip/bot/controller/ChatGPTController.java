package com.jipzip.bot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jipzip.bot.model.dto.ChatGPTRequest;
import com.jipzip.bot.model.dto.ChatGPTResponse;

@RestController
@RequestMapping("/botapi")
public class ChatGPTController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;
    
    @Value("${openai.temperature}")
    private int temperature;
    
    @Value("${openai.max_tokens}")
    private int max_tokens;

    @Autowired
    private RestTemplate template;

    @PostMapping("/fortuneTell")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> prompt){
    	try {    		
    		ChatGPTRequest request = new ChatGPTRequest(model, temperature, max_tokens, prompt.get("question"));
    		ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
    		return ResponseEntity.ok(chatGPTResponse.getChoices().get(0).getMessage().getContent());
    	}catch (Exception e) {
    		return exceptionHandling(e);
		}
    }
    
    private ResponseEntity<?> exceptionHandling(Exception e) {
		return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
	}

}