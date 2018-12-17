package com.cisco.exam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.exam.exception.ApplicationException;
import com.cisco.exam.model.Admin;
import com.cisco.exam.model.Examinee;
import com.cisco.exam.model.Results;
import com.cisco.exam.model.SapIdWrapper;
import com.cisco.exam.model.Topic;
import com.cisco.exam.service.AdminServices;
import com.cisco.exam.service.DBOperations;
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	 @Value("${app.admin.username}")
	   private String uname;
	 
	 @Value("${app.admin.password}")
	   private String pwd;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	 @Autowired
	 private AdminServices adminServices;
	 @Autowired
	 private DBOperations operations;
	
	//API for Admin login validating
	@RequestMapping(value=("/authenticate"),method={RequestMethod.POST,RequestMethod.GET})
	public String authenticate(@RequestParam("username")String userName,@RequestParam("password")String password){
		
		Admin admin=operations.retriveAdmin(userName);
		
			if((admin!=null) && (admin.getPassword().equals(password))){
				LOG.info(admin.getUserName()+"---> Logged successfully");
				return "Admin successfully logged in !!!";
			}
			else if((admin==null) &&(userName.equals(uname))&&(password.equals(pwd))){	
				LOG.info("Logged succesfully as Super-admin");
				return "Super Admin Succesfully logged in !!!";
			}
			LOG.info("Invalid credentials --->"+userName+":"+password);
				return "Invalid credentials";
	}
	
	//API for Master-admin to create new admin credentials
	/*@RequestMapping(value=("/createadmin"),method={RequestMethod.POST,RequestMethod.GET})
	public String createAdmin(@RequestParam("username")String userName,@RequestParam("password")String password){
		LOG.info("Admin controller called creating credentials to -->"+userName);
		return operations.createAdmin(userName, password);
	
	}*/
	
	/*//API for Master-Admin to see all the admins available 
	@RequestMapping(value=("/showadmins"),method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Admin> showAdmins(){
		LOG.info(" Getting list of admins from showadmin controller***");
		return operations.retriveAllAdmins();
	}*/
	/*@RequestMapping(value=("/removeadmin"),method={RequestMethod.POST,RequestMethod.GET})
	public String removeAdmin(@RequestBody Admin admin){
		
		LOG.info("Controller ts remove admin credntials" );
		operations.removeAdmin(admin.getUserName());
		
		return null;
	}*/
	
	//API to generate password for examinee's (multiple or single).
	@RequestMapping(value=("/generatepassword"),method = RequestMethod.POST)
	public String generatePassword(@RequestBody List<Examinee> list){
		//public String generatePassword(@RequestParam("name")String name,@RequestParam("sapId")String sapId,@RequestParam("stream")String stream){
		for(Examinee examinee : list){
			String password=adminServices.generateRandomPassword(10);
			examinee.setPassword(password);
			LOG.info("Dynamically generated password for user -->"+examinee.getName()+", Password:"+examinee.getPassword());
			adminServices.storeAndMailExamineeDetails(examinee);
		}
		return "Succesfully generated";
		/*String password=adminServices.generateRandomPassword(10);
		LOG.info("Dynamically generated password for user -->"+name+", Password:"+password);
		 return adminServices.storeAndMailExamineeDetails(new Examinee(sapId,name,stream,password))+"password:"+password;*/
		 
	}
	
	//API to generate report for examinee's(Multiple or single).
	@RequestMapping(value=("/generatereport"),method=RequestMethod.POST)
	public String generateReport(@RequestBody SapIdWrapper sapId) throws InvalidFormatException, IOException, ApplicationException{
		
		
		return adminServices.getInvalidUsers(sapId);
		
	}
	 
	//produces=MediaType.APPLICATION_JSON_VALUE
	//sample method for data format to generate report.
	@RequestMapping(value=("/sample"),method={RequestMethod.POST,RequestMethod.GET},produces=MediaType.APPLICATION_XML_VALUE)
	public List<Results> sampleForReports(){
		/*SapIdWrapper sapWrapper=new SapIdWrapper();
		List<String>list=new ArrayList<>();
		list.add("543265");
		list.add("133465");
		list.add("51706440");
		sapWrapper.setSapId(list);
		return sapWrapper;*/
		/*List<Examinee>list=new ArrayList<>();
		Examinee e=new Examinee("123","vamsi"," ","java","john","321");
		//e.setPassword(adminServices.generateRandomPassword(10));
		
		list.add(e);
		list.add(new Examinee("12345","pattuballa"," ","core","senthil","54321"));
		list.add(new Examinee("67890","aditya","","UI","Hemanth","09876"));
		return list;*/
		List<Topic>topic=new ArrayList<>();
		topic.add(new Topic("2","2","1","5", "10","collections"));
		
		List<Results>list1=new ArrayList<>();
		list1.add( new Results("vamsi","51706440","John","12345",topic));
		
		
		System.out.println("***************");
		return list1;
		
		
	}
	
	
	
}
