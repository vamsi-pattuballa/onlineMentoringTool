package com.cisco.exam.model;

import org.springframework.data.annotation.Id;

public class Examinee {
	@Id
	private String sapId;
	private String name;
	private String stream;
	private String password;
	private String manager;
	private String managerSapId;
	public Examinee(){}
	public Examinee(String sapId,String name,String password,String stream,String manager,String managerSapId){
		this.sapId=sapId;
		this.name=name;
		this.stream=stream;
		this.password=password;
		this.manager=manager;
		this.managerSapId=managerSapId;
	}
	
	public Examinee(String sapId, String name, String stream, String password) {
		super();
		this.sapId = sapId;
		this.name = name;
		this.stream = stream;
		this.password = password;
		//this.totalQuestions=totalQuestions;
		//this.minutes=minutes;
	}
	public String getSapId() {
		return sapId;
	}
	public void setSapId(String sapId) {
		this.sapId = sapId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerSapId() {
		return managerSapId;
	}

	public void setManagerSapId(String managerSapId) {
		this.managerSapId = managerSapId;
	}

	/*public String getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(String totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}*/
	@Override
	public String toString() {
		return "Examinee [sapId=" + sapId + ", name=" + name + ", stream=" + stream + ", password=" + password + "]";
	}
	
	
	
	
	
}
