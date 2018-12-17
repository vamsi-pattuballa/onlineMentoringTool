package com.cisco.exam.exception;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	public ApplicationException(String str){
		super(str);
	}
	public ApplicationException(String str,Exception e){
		super(str,e);
		
	}
}
