package com.cisco.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.exam.exception.ApplicationException;
import com.cisco.exam.model.Admin;
import com.cisco.exam.service.DBOperations;


@RestController("/superadmin")
public class SuperAdminController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	 @Autowired
	 private DBOperations operations;
	
	@RequestMapping(value=("/createadmin"),method={RequestMethod.POST,RequestMethod.GET})
	public String createAdmin(@RequestParam("username")String userName,@RequestParam("password")String password){
		LOG.info("Admin controller called creating credentials to -->"+userName);
		return operations.createAdmin(userName, password);
	
	}
	//API for Master-Admin to see all the admins available 
	@RequestMapping(value=("/showadmins"),method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Admin> showAdmins(){
		LOG.info(" Getting list of admins from showadmin controller***");
		return operations.retriveAllAdmins();
	}
	//API to remove admin credentials
	@RequestMapping(value=("/removeAdmin"),method={RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	public void removeAdmins(@RequestParam Admin admin) throws ApplicationException{
		LOG.info("Removing admin credentials from DB --> In removeAdmins()");
		operations.removeAdminFromDB(admin);
		
		
	}
	//API to find username 
	@RequestMapping(value=("/usernamefinder"),method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String UserNameAvailability(@RequestParam("username")String userName){
		LOG.info("Validating username. --> UserNameAvailability(String userName)");
		Admin admin=operations.retriveAdmin(userName);
		if(admin==null){
			return "available";
		}
		return "Not available";
		
	}
	
	@RequestMapping(value=("/sample"),method={RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	public Admin sample(){
		LOG.info("sample method");
		return new Admin("vamsi","vamsi");
	}
	
	
}
