package com.cisco.exam.model;

import java.util.Map;

public class Questions {
	private String topic;
	private String totalTopics;
	
	private String stream;
	private String type;
	
	private String question;
	
	
	private Map<String,String>options;
	private Map<String,String> answers;	
	public Questions() {
		
	}
	public Questions(String topic, String totalTopics, String stream, String type, String question,
			Map<String,String> options, Map<String,String> answers) {
		super();
		this.topic = topic;
		this.totalTopics = totalTopics;
		this.stream = stream;
		this.type = type;
		this.question = question;
		this.options = options;
		this.answers = answers;
	}


	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public void setAnswers(Map<String, String> answers) {
		this.answers = answers;
	}
	public String getTotalTopics() {
		return totalTopics;
	}

	public void setTotalTopics(String totalTopics) {
		this.totalTopics = totalTopics;
	}

	public String getQuestion() {
		return question;
	}
	public Map<String, String> getOptions() {
		return options;
	}

	public Map<String, String> getAnswers() {
		return answers;
	}
	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public String getTopic(){
		return topic;
	}
	public String getType(){
		return type;
	}
	@Override
	public String toString() {
		return "Questions [topic=" + topic + ", totalTopics=" + totalTopics + ", stream=" + stream + ", type=" + type
				+ ", question=" + question + ", options=" + options + ", answers=" + answers + "]";
	}
	
	
	
	

}
