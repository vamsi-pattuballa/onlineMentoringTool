package com.cisco.exam.model;

import javax.persistence.Embeddable;

@Embeddable
public class Topic {

	private String easy;
	private String hard;
	private String medium;
	private String examineeScore;
	private String maximumScore;
	private String topicName;
	
	public Topic(){
		
	}
	public Topic(String easy, String hard, String medium,String examineeScore,String maximumScore,String topicName) {
		super();
		this.easy = easy;
		this.hard = hard;
		this.medium = medium;
		this.examineeScore=examineeScore;
		this.maximumScore=maximumScore;
		this.topicName = topicName;
	}
	
	
	
	public String getEasy() {
		return easy;
	}
	public void setEasy(String easy) {
		this.easy = easy;
	}
	public String getHard() {
		return hard;
	}
	public void setHard(String hard) {
		this.hard = hard;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getExamineeScore() {
		return examineeScore;
	}
	public void setExamineeScore(String examineeScore) {
		this.examineeScore = examineeScore;
	}
	public String getMaximumScore() {
		return maximumScore;
	}
	public void setMaximumScore(String maximumScore) {
		this.maximumScore = maximumScore;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	@Override
	public String toString() {
		return "Topic [easy=" + easy + ", hard=" + hard + ", medium=" + medium + ", examineeScore=" + examineeScore
				+ ", maximumScore=" + maximumScore + ", topicName=" + topicName + "]";
	}
	
	
}
