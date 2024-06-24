package com.jipzip.bot.model.dto;

import java.util.List;

public class ChatGPTResponse {
    private List<Choice> choices;

    public static class Choice {
        private int index;
        private Message message;
        
		public Choice() {
			super();
		}
		public Choice(int index, Message message) {
			super();
			this.index = index;
			this.message = message;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public Message getMessage() {
			return message;
		}
		public void setMessage(Message message) {
			this.message = message;
		}
		@Override
		public String toString() {
			return "Choice [index=" + index + ", message=" + message + "]";
		}
                
    }

	public ChatGPTResponse() {
		super();
	}

	public ChatGPTResponse(List<Choice> choices) {
		super();
		this.choices = choices;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	@Override
	public String toString() {
		return "ChatGPTResponse [choices=" + choices + "]";
	}
    
}