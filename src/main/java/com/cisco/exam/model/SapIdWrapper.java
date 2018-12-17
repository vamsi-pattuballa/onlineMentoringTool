package com.cisco.exam.model;

import java.util.List;

public class SapIdWrapper {
	
	
	private List<String> sapId;

	public List<String> getSapId() {
		return sapId;
	}

	public void setSapId(List<String> sapId) {
		this.sapId = sapId;
	}

	@Override
	public String toString() {
		return "SapIdWrapper [sapId=" + sapId + "]";
	}
	
	
	
	
	
	
	
	/*private String sapId;
	public SapIdWrapper(String sapId){
		this.sapId=sapId;
	}
	public String getSapId(){
		return this.sapId;
	}
	public void setSapId(){
		this.sapId=sapId;
	}
	@Override
	public String toString() {
		return "SapIdWrapper [sapId=" + sapId + "]";
	}
	*/

}
