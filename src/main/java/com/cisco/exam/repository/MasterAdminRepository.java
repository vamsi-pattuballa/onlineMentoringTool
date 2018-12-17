package com.cisco.exam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.exam.model.Admin;

public interface MasterAdminRepository extends MongoRepository<Admin, String> {

	
	
}
