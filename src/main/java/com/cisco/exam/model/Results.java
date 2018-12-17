package com.cisco.exam.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "results")
@XmlRootElement(name="Result")
public class Results {
	
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Field("_id")
	private int Id;
	//private Date date=new Date();
	//private int id;
	@Field("name")
	private String name;
	@Field("sapId")
	private String sapId;
	private String manager;
	private String managerSapId;
	@Embedded
	@Field("topics")
	private  List<Topic> topics;
	private Date creationDate = new Date();
	
	public Results(){}
	public Results(String name, String sapId,String manager,String managerSapId,List<Topic> topics) {
		super();
		
		this.name = name;
		this.sapId = sapId;
		this.manager=manager;
		this.managerSapId=managerSapId;
		//this.topic = topic;
		this.topics = topics;
		//this.totalScore = totalScore;
	}
	public String getName() {
		return name;
	}

	public int getId() {
		return Id;
	}
	@XmlElement(name="Id")
	public void setId(int id) {
		Id = id;
	}
	@XmlElement(name="Name")
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSapId() {
		return sapId;
	}
	@XmlElement(name="SapId")
	public void setSapId(String sapId) {
		this.sapId = sapId;
	}
	
	public String getManager() {
		return manager;
	}
	@XmlElement(name="Manager")
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManagerSapId() {
		return managerSapId;
	}
	@XmlElement(name="Manager-SapId")
	public void setManagerSapId(String managerSapId) {
		this.managerSapId = managerSapId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	@XmlElement(name="Date")
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public List<Topic> getTopics() {
		return topics;
	}
	@XmlElement(name="Topics")
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	@Override
	public String toString() {
		return "Results [Id=" + Id + ", name=" + name + ", sapId=" + sapId + ", topics=" + topics + ", creationDate=" + creationDate + "]";
	}

	
	
}
