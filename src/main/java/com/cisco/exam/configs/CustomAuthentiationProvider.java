package com.cisco.exam.configs;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cisco.exam.exception.ApplicationException;
import com.cisco.exam.model.Admin;
import com.cisco.exam.model.Examinee;
import com.cisco.exam.service.DBOperations;

@Component
public class CustomAuthentiationProvider implements AuthenticationProvider {
	 @Value("${app.admin.username}")
	   private String uname;
	 
	 @Value("${app.admin.password}")
	   private String pwd;
	 @Autowired
	 public DBOperations dpOperations;
	 private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userid = authentication.getName();
		boolean isAdmin=false;
		boolean isUser=false;
		Admin admin = null;
		Examinee examinee=null;
		if(userid.contains("admin")&& !(userid.contains(uname))){
			isAdmin=true;	
			String []arr=userid.split("_");
			admin=dpOperations.getAdmin(arr[0]);
			
			
		}
		else if(userid.contains("admin")&&(userid.contains(uname))){
			isAdmin=true;
			LOG.info("Checking as Admin ---> Super Admin credentials");
			
		}
		else if(userid.contains("user")){
			isUser=true;
			String []arr=userid.split("_");
			examinee=dpOperations.getExaminee(arr[0]);
		}
		
		String password = authentication.getCredentials().toString();
		//System.out.println("*****USER_:" + uname + "*******PWD_:" + pwd);
		System.out.println("*****USERNAME:" + userid + "*******PWD:" + password);
		Authentication auth = null;
		if(admin==null && isAdmin){
			try{
				if((isAdmin)&& (admin==null) && (userid.equals(uname+"_admin")) && password.equals(pwd)){
					LOG.info("Super-Admin credentials are validating !!");
					auth = new UsernamePasswordAuthenticationToken(userid, password, new ArrayList<>());
					LOG.info("Super-Admin credentials are validated !!");
				}
				
			}catch(Exception e){
				
			}
		}
		else if(admin!=null || examinee!=null){
			System.out.println("inside if block");
			try {
				
				if ((isAdmin)&& (admin!=null) && (userid.equals(admin.getUserName()+"_admin")) && (password.equals(admin.getPassword()))) {
					LOG.info("Admin credentials are validating !!");
					auth = new UsernamePasswordAuthenticationToken(userid, password, new ArrayList<>());
					LOG.info("Admin credentials are validated !!");
				} else if ((isUser)&&userid.equals(examinee.getSapId()+"_user") && password.equals(examinee.getPassword())) {
					// write your custom logic to match username, password
					LOG.info("Examinee credentials are validating !!");
					auth = new UsernamePasswordAuthenticationToken(userid, password, new ArrayList<>());
					LOG.info("Examinee credentials are validated !!");
				} else {
					LOG.info("Invalid credentials entered from autherization header !!!");
					throw new ApplicationException("Invalid credentials are Entered");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		System.out.println("*******"+auth);
		return auth;
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		System.out.println("----"+authentication.equals(UsernamePasswordAuthenticationToken.class));
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	


}