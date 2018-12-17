package com.cisco.exam.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cisco.exam.model.Results;

@Repository
public interface UserRepository extends MongoRepository<Results, String> {

	//Results findOne(Query query2, Class<Results> class1);
	Results findOneBySapId(String sapId);

	List<Results> findAllBySapId(String sapId);

	//Results find(Query query, Class<Results> class1);
	
	
}
