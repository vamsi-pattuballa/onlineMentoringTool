package com.cisco.exam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.exam.model.Examinee;
import com.cisco.exam.model.Questions;
import com.cisco.exam.model.Results;
import com.cisco.exam.model.Topic;
import com.cisco.exam.service.DBOperations;
import com.cisco.exam.service.ExamineeServices;

@RestController
@RequestMapping("/examinee")
public class ExamineeController {

	
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExamineeServices excelQuestionReader;
	List<Questions> question;
	Examinee examinee;
	@Autowired
	private DBOperations dbOperations;
	
	// produces = MediaType.TEXT_PLAIN_VALUE
	@RequestMapping(value=("/authenticate"), method = RequestMethod.POST)
	public Examinee authentication(@RequestParam("sapid")String sapId,@RequestParam("password")String password){
		LOG.info("Checking Examinee credentials .....");
		examinee=dbOperations.getExaminee(sapId);
		System.out.println(examinee+"******");
		System.out.println("sapId:"+sapId+";Password:"+password);
		//if(sapId.contains("user")){
			if(sapId!=null){
			
			if(sapId.equals(examinee.getSapId())&& password.equals(examinee.getPassword())){
				LOG.info(examinee.getSapId()+"Logged Correctly");
				return examinee;
			}else{
				LOG.info(examinee.getSapId()+"Wrong Credentials");
				return null;
			}		
		}
		return null;
	}
	@RequestMapping(value=("/sample"),method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE) 
	public Results sample(){
		
		List<Topic> topics=new ArrayList<>();
		topics.add(new Topic("2","3","0","5","11","collections"));
		topics.add(new Topic("1","0","2","3","6","Inheritence"));
		topics.add(new Topic("0","3","0","3","8","Polymorphism"));
		topics.add(new Topic("0","6","3","9","12","Object"));
		topics.add(new Topic("0","0","0","0","6","Threads"));
		//System.out.println("***************"+new Results("vamsi","12345",topics,"25"));
		LOG.info("sample data rendered from Examinee Controller for consolidated report format !!!");
		return new Results("vamsi","51706440","john","12345",topics);
		
	}
	
	@RequestMapping(value=("/questionsGenerator/topic"), produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
	public List<Questions> questionsGenerator(@RequestParam("stream")String stream,@RequestParam("topic")String topic) throws InvalidFormatException, IOException{	
		//stream data ="core java" or "advanced"
		System.out.println("questionsgenerator --------------------------->");
		
		String f=excelQuestionReader.getFileBasedOnStream(stream);
		question=excelQuestionReader.pullQuestions(f,stream,topic);
				int presentTopic=Integer.parseInt(topic);
				int totalTopics=Integer.parseInt(question.get(0).getTotalTopics());
				if(presentTopic>totalTopics){
					List<Questions>list=new ArrayList<>();
					list.add(new Questions());
					return list;
					//return new List<Questions>(new Questions());
				}else
					return question;
		
	}
	
	@RequestMapping(value=("/submit"),method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public String submit(@RequestBody Results results){
		
		if(results.getName()==null || results.getSapId()==null || results.getTopics()==null){
			return "Details are not available";
		}
		return dbOperations.storeConsolidatedReport(results);	
	}
}
