package com.cisco.exam.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cisco.exam.exception.ApplicationException;
import com.cisco.exam.model.Examinee;
import com.cisco.exam.model.Mail;
import com.cisco.exam.model.Results;
import com.cisco.exam.model.SapIdWrapper;
import com.cisco.exam.model.Topic;
import com.cisco.exam.repository.AdminRepository;
@Service
public class AdminServices {
		
		@Value("${app.admin.excel.reportsLocation}")
		private String reportsFileLocation;
		@Autowired
		private DBOperations operations;
		@Autowired
		private AdminServices adminOperations;
		
		private final Logger LOG = LoggerFactory.getLogger(getClass());
    	private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	    private static final String NUMERIC = "0123456789";
	   // private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
	    private static SecureRandom random = new SecureRandom();
	    @Autowired
	    private EmailService emailService;

	    
	    private final AdminRepository adminRepository;
	    public AdminServices( AdminRepository adminRepository){
	    	this.adminRepository=adminRepository;
	    }
		
	    public String generateRandomPassword(int len){
	    	String dic=ALPHA_CAPS+ALPHA+NUMERIC;
	    	String result = "";
	    	for (int i = 0; i < len; i++) {
	    		int index = random.nextInt(dic.length());
	    		result += dic.charAt(index);
	    	}
	    	return result;
		
	    }
	    
	    public String storeAndMailExamineeDetails(Examinee examinee){
	    	
	    	adminRepository.save(examinee);
	        Mail mail = new Mail();
	        mail.setFrom("vpattuba@cisco.com");//TODO-remove hardcoded values
	        mail.setTo("vpattuba@cisco.com");//TODO-remove hardcoded values
	       
	        mail.setSubject("Sending Email from spring-boot application");
	        mail.setContent("Name:"+examinee.getName()+",\nSapId:"+examinee.getSapId()+",\nStream:"+examinee.getStream()+",\nPassword:"+examinee.getPassword());

	        try {
				emailService.sendSimpleMessage(mail);
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
	    	return "successfully created ";	
	    }
	    public String getInvalidUsers(SapIdWrapper sapId) throws InvalidFormatException, IOException, ApplicationException{
		
			List<Results>results=new ArrayList<>();
			List<String>notValid=new ArrayList<>();
			for(String id :sapId.getSapId()){
				Results result=null;
				result = operations.getResults(id);
					if(result==null){
						notValid.add(id);
					}else{
						results.add(result);
					}
			}
			LOG.info("calling : getInvalidUsers -->generateExcelReports");
			generatingExcelReport(results);
			
			//generateJsonReport(results);
			try {
				LOG.info("calling: getInvalidUsers --> generateXMLReport");
				generateXMLReport(results);
			} catch (JAXBException e) {
				throw new ApplicationException("Exception while creating XML file in getInvalidUsers method", e);
				
			}
			if(notValid.size()>0){
				StringBuffer data=new StringBuffer();
				for(int count=0;count<notValid.size();count++){
					if(count==(notValid.size()-1)){
						data.append(notValid.get(count));
					}
					data.append(notValid.get(count)+",");
				}
				return data.toString()+"Not a valid SapId's";
			}
			
			return "Successfully generated reports!!!";
			
		}
	    public void generatingExcelReport(List<Results> results) throws InvalidFormatException, IOException{
	    LOG.info("Generating Excel report for the results");
	    	String date=java.time.LocalDate.now().toString();
	    	//String reportFile="C://Users//vpattuba.PARTNERS//Desktop//reports.csv";
	    	File file=new File(reportsFileLocation+"results("+date+").csv");
	    	if(!file.exists()){    		    	
	    		file.createNewFile();	
	    		LOG.info("Generating Header to the excel file");
	    		adminOperations.generateHeader(file);
	    	}
				LOG.info("Storing results into Excel file");
				adminOperations.storingResultsInExcel(file,results);
				LOG.info("Excel report is available at location:"+file.getAbsolutePath());
			
	    	
	    }
	    
	   /* @SuppressWarnings("unchecked")
		public void generateJsonReport(List<Results>results){
	    	
	    	System.out.println("*********************************************");
	    	System.out.println(results);
	    	System.out.println("*********************************************");
	    	int i=1;
	    	for(Results rs:results){
	    		JSONObject result = new JSONObject();
	    		result.put("Id",rs.getId());
	    		result.put("Name",rs.getName());
	    		result.put("SapId",rs.getSapId());
	    		result.put("Manager",rs.getManager());
	    		result.put("Manager_sapId",rs.getManagerSapId());
	    		//result.put("Topic",rs.getTopics());
	    		List<Topic> topic=rs.getTopics();
	    		for(Topic top:topic){
	    			result.put("Topic",top.getTopicName());
	    			result.put("Hard",top.getHard());
	    			result.put("Medium",top.getMedium());
	    			result.put("Easy",top.getEasy());  
	    			result.put("Marks Obtained",top.getExamineeScore());
	    			result.put("Total Marks",top.getMaximumScore());
	    		}
	    		JSONObject examinee = new JSONObject();
		        examinee.put("employee"+i+"",result);
		        writeIntoJsonFile(examinee);
		        i++;
	    	}        
	 
	    	for(Results r:results){
	    		
	    		System.out.println("Id:"+r.getId()+",\nName:"+r.getName()+",\nSapId:"+r.getSapId()+",\nManager:"+r.getManager()+",\nManagerSapId:"+r.getManagerSapId()+",\nTopics"+r.getTopics());
	    	}
	    	
	    }
	    public void writeIntoJsonFile(JSONObject obj){
	    	
	    	  try (FileWriter file = new FileWriter("results_JSON.json",true)) {
	    		  
	              file.write(obj.toJSONString());
	              file.flush();
	   
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	    	
	    	
	    }*/
	    
	    private void generateXMLReport(List<Results>results) throws JAXBException, FileNotFoundException{
	    	for(Results result:results){
	    		JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
	            Marshaller marshaller = jaxbContext.createMarshaller();
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
	            marshaller.marshal(result, new FileOutputStream("Results.xml",true));
	            marshaller.marshal(result, System.out);
	    	}
	    }
	    public void generateHeader(File file){
	    	
	    	try {
				FileOutputStream fos=new FileOutputStream(file,true);
				
				try {
					fos.write(AdminServices.writeDataToCell("Name",false));
					fos.write(AdminServices.writeDataToCell("SapId",false));
					fos.write(AdminServices.writeDataToCell("Date",false));
					List<Topic>header=new ArrayList<>();
					for(int i=0;i<15;i++){
						
						
						header.add(new Topic("Easy","Hard","Medium","Marks Obtained","Maximum Posibility","Topic"));
						
					}
					adminOperations.writeTopics(fos,header);
					fos.write("\n".getBytes());
					fos.close();
					
				} catch (IOException e) {
			
					e.printStackTrace();
				}	
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			}
	    }
	    public void storingResultsInExcel(File file,List<Results>results){
	    	
	    	try {
				
				FileOutputStream fos=new FileOutputStream(file,true);
			   	for(Results result:results){
		    		
			   		fos.write(AdminServices.writeDataToCell(result.getName(),false));		    		
			   		fos.write(AdminServices.writeDataToCell(result.getSapId(),false));
			   		fos.write(AdminServices.writeDataToCell(result.getCreationDate().toString(),false));		    		
				    List<Topic> topic=result.getTopics();
				    writeTopics(fos, topic);
				    
				    fos.write("\n".getBytes());
		    	}
			   	fos.close();
	    		
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    }

		private void writeTopics(FileOutputStream fos, List<Topic> topic) throws IOException {
			int cellNo=0;
			
			for(Topic top:topic){
				fos.write(AdminServices.writeDataToCell(top.getTopicName(),false));				    	
				fos.write(AdminServices.writeDataToCell(top.getHard(),false));
				
				fos.write(AdminServices.writeDataToCell(top.getMedium(),false));
				
				fos.write(AdminServices.writeDataToCell(top.getEasy(),false));
				
				fos.write(AdminServices.writeDataToCell(top.getExamineeScore(),false));
				boolean isLastCell=false;
				if(cellNo==topic.size()-1){
				isLastCell=true;	
				}
				fos.write(AdminServices.writeDataToCell(top.getMaximumScore(),false));
				
				String grade="Grade";
				if(!(top.getEasy().equalsIgnoreCase("easy"))){
					int score=Integer.parseInt(top.getExamineeScore());
					int totalScore=Integer.parseInt(top.getMaximumScore());
					grade=generateGrade(score, totalScore);
					
				}
				fos.write(AdminServices.writeDataToCell(grade,isLastCell));
				cellNo++;
			}
		}
		private static String generateGrade(int score,int totalScore){
			String grade=null;
			System.out.println("score:"+score+",totalScore:"+totalScore);
			double per=(double)(((double)score/(double)totalScore)*10.0);
			System.out.println("percentage:"+per);
			if(per>=6.5){
				grade="Good";
				System.out.println("------>Good!");
			}else if(per>=4.5 && per<6.5){
				grade="Average";
				System.out.println("------>Average");
			}
			else if(per<4.5){
				grade="Poor";
				System.out.println("------>Poor");
			}else{
				grade="Grade";
			}
			
			return  grade;
		}
	    private static byte[] writeDataToCell(String data,boolean isLastCell){	
	    	if(!isLastCell){
	    		data+=",";
	    	}
	    	
	    	return data.getBytes();
	    }
}
