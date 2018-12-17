package com.cisco.exam.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cisco.exam.exception.ApplicationException;
import com.cisco.exam.model.Admin;
import com.cisco.exam.model.Examinee;
import com.cisco.exam.model.Results;
import com.cisco.exam.repository.AdminRepository;
import com.cisco.exam.repository.MasterAdminRepository;
import com.cisco.exam.repository.UserRepository;

@Service
public class DBOperations {

	private final AdminRepository adminRepository;
	private final MasterAdminRepository master;
	@Autowired
	DBOperations operations;

	@Autowired
	private MongoOperations mongo;
	private final UserRepository userRepository;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	Examinee examinee;

	// constructor to initialize mongoDB
	public DBOperations(AdminRepository adminRepository, UserRepository userRepository,MasterAdminRepository master) {
		this.adminRepository = adminRepository;
		this.userRepository = userRepository;
		this.master=master;
	}
	public List<Admin> retriveAllAdmins(){
		
		return master.findAll();
	}
	public String removeAdminFromDB(Admin admin) throws ApplicationException{
		try{
			LOG.info("trying to remove admin credntials. --> removeAdminFromDB(String userName)");
			master.delete(admin.getUserName());
			LOG.info("Admin credntials are removed from DB. -->removeAdminFromDB(String userName)");
		}catch(Exception e){
			throw new ApplicationException("Admin details are not available");
		}
		
		return "Admin credentials removed succesfully";
		
		
	}
	public Admin retriveAdmin(String userName){
		
		Admin admin=master.findOne(userName);
		return admin;
	}
	public String createAdmin(String userName,String password){
		
		master.save(new Admin(userName,password));
		Admin admin=master.findOne(userName);
		if(admin!=null){
			return "admin created succefully";
		}else{
			return "unable to create admin";
		}
	}
	public String storeConsolidatedReport(Results results) {

		results.setId(getNextSequence("customSequences"));
		LOG.info("Saving Results.");
		userRepository.save(results);
		operations.deleteExamineeRecord(results.getSapId());
		return "Successfuly Completed!!!";
		
		/*
		 * examinee = adminRepository.findOne(results.getSapId());
		 * 
		 * 
		 * LOG.info("Removing examinee details for logging again."); //removing
		 * examinee details from examinee table
		 * adminRepository.delete(examinee); if(examinee != null){ return
		 * "successfully completed"; } else return "Not a valid Examinee";
		 */

	}
	public Admin getAdmin(String user){
	
		return master.findOne(user);
	}
	public void removeAdmin(String user){
		master.delete(user);
	}

	public Examinee getExaminee(String sapId) {

		String[] userSapId = sapId.split("_");
		examinee = adminRepository.findOne(userSapId[0]);
		return examinee;

	}

	public Results getResults(String sapId) {
		Results result=null;
		List<Results>list=new ArrayList<>();
		list =userRepository.findAllBySapId(sapId);
		
		System.out.println(list);
		if(list.size()!=0)
		result=list.get((list.size()-1));
		//result = userRepository.findOneBySapId(sapId);

		if (!(result != null)) {
			return null;
		}
		return result;
	}

	public void deleteExamineeRecord(String sapId) {
		LOG.info("Removing examinee details from logging again.");
		adminRepository.delete(sapId);
	}

	public int getNextSequence(String seqName) {
		CustomSequences counter = mongo.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
				options().returnNew(true).upsert(true), CustomSequences.class);
		return counter.getSeq();
	}
}
