package com.cisco.exam.model;

import org.springframework.data.annotation.Id;

public class Admin {
	
	/*private String name;
	private String sapId;*/
	@Id
	private String userName;
	private String password;
	
	public Admin(){}
	public Admin(String userName,String password){
		this.userName=userName;
		this.password=password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admins [userName=" + userName + ", password=" + password + "]";
	}
	
}
