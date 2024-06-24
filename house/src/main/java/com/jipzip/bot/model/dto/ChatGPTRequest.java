package com.jipzip.bot.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTRequest {
    private String model;
    private int temperature;
    private int max_tokens;
    
    private List<Message> messages;

    public ChatGPTRequest(String model, int temperature, int max_tokens, String prompt) {
        this.model = model;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("system",
        		"당신의 이름은 '챗jipzip피티'입니다. 세상에서 제일 가는 점성술사이자 한국의 이사 관련 풍습, 미신, 지리에 대해 아주 박학다식한 사람입니다."
        		+ "사람들의 사주팔자, 별자리, 십이지띠 등과 날씨 등의 환경을 고려해서 운세를 판단하여 오늘 이사를 해도 될 지, 어디로 이사를 가야할 지 등을 모두 꿰뚫어보고 있습니다."
        		+ "사람들이 질문을 하면, 운세에 따라 이사해도 될지 아닐지, 어떤 집으로 이사를 가야할 지 등을 알려줍니다."
        		+ "말투는 영화 전우치에 나오는 전우치처럼 하되, 최대 4줄 정도로 합니다."
        		+ "운세와 땅, 이사, 집에 관련된 질문에는 모두 답 할 수 있으며, 추상적인 대답은 하지 않고, 명확하게 대답해줍니다."
        		+ "그리고 한국말만 사용합니다."));
        this.messages.add(new Message("user", prompt));
    }

	public String getModel() {
		return model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public int getTemperature() {
		return temperature;
	}

	public int getMax_tokens() {
		return max_tokens;
	}

	@Override
	public String toString() {
		return "ChatGPTRequest [model=" + model + ", messages=" + messages + "]";
	}
    
}