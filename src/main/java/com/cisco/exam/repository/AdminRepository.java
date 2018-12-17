package com.cisco.exam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.exam.model.Examinee;

public interface AdminRepository  extends  MongoRepository<Examinee, String> {

	
	
}
