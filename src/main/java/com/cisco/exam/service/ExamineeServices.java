package com.cisco.exam.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cisco.exam.model.Questions;


@Service
public class ExamineeServices {

	int noOfQuestions = 0;
	Workbook workbook = null;
	Row row=null;
	Sheet sheet = null;
	//Examinee examinee;
	
	List<Questions>queset;
	List<String>duplicate;
	
	Integer quest=null;
	ExamineeServices reader;
	int totalQuestion=0;
	
	@Value("${app.admin.totalQuestions}")
	   private int totalQuestions;
	
	@Value("${app.admin.excel.coreJava}")
	   private String coreJava;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	public List<Questions> pullQuestions(String f,String stream,String topic) throws InvalidFormatException, IOException{
	
		reader=new ExamineeServices();
		queset = new ArrayList<>();
		Random random = new Random();
		duplicate=new ArrayList<>();
		int topicNum=Integer.parseInt(topic);
		workbook = WorkbookFactory.create(new File(f));
		int totalSheets=workbook.getNumberOfSheets();
		
		//int questionsFromTopic=reader.totalQuestionsForEachTopic(totalQuestions,totalSheets);
		int questionsFromTopic=3;
			System.out.println("Total questions From Topic:"+questionsFromTopic+", Topic no:"+topic);
			//questionsFromTopic=questionsFromTopic+(questionsFromTopic-1)+(questionsFromTopic-2);
			
		//int questionsFromMedium=questionsFromTopic-1;
			int questionsFromMedium=questionsFromTopic;
		//int questionFromEasyTopic=questionsFromTopic-2;
			int questionFromEasyTopic=questionsFromTopic;
			
			
		sheet = workbook.getSheetAt(topicNum);
		totalQuestion=sheet.getLastRowNum();
		String name=sheet.getSheetName();
		for(int i=1;i<=(questionsFromTopic+questionsFromMedium+questionFromEasyTopic);i++){
			
			//quest=random.nextInt(max-low)+low;
			quest=random.nextInt(totalQuestion);
			row=sheet.getRow(quest);
			
			Map<String,String> options=new LinkedHashMap<>();
			Map<String,String> answers=new LinkedHashMap<>();
			String sno=reader.selectingQuestion(row, 0);
			String question=reader.selectingQuestion(row, 1);
			String option1=reader.selectingQuestion(row, 2);
			String option2=reader.selectingQuestion(row, 3);
			String option3=reader.selectingQuestion(row, 4);
			String option4=reader.selectingQuestion(row, 5);
			String answer=reader.selectingQuestion(row, 6);
			String type=reader.selectingQuestion(row, 7).toLowerCase();
			
			options.put("option1",option1);
			options.put("option2",option2);
			options.put("option3",option3);
			options.put("option4",option4);
			
			String[] arr=answer.split(",");
			
			int sample=1;
			for(String ans:arr){
				
				answers.put(("answer"+sample),(ans));
				sample++;
			}
			
			if(duplicate.contains(question)||(sno.equalsIgnoreCase("sno"))||(sno.equalsIgnoreCase("s.no"))){
				
						i=i-1;
				
			}
			else{
				
				if(i<=questionsFromTopic && type.equalsIgnoreCase("medium")){
					queset.add(new Questions(name,totalSheets+"",stream,type,question, options,answers));
				}
				else if(i>questionsFromTopic && i<=(questionsFromTopic+questionsFromMedium) && type.equalsIgnoreCase("hard")){
					queset.add(new Questions(name,totalSheets+"",stream,type,question, options, answers));
				}
				else if(i>(questionsFromTopic+questionsFromMedium) && i<=(questionsFromTopic+questionsFromMedium+questionFromEasyTopic) && type.equalsIgnoreCase("easy")){
					queset.add(new Questions(name,totalSheets+"",stream,type,question, options, answers));
				}else{
				
					--i;
				}
				
			//queset.add(new QuestionsToExaminee(name,question, option1, option2, option3, option4, answer,type));
			}
			
		}
		LOG.info("successfully pulled questions ---->");
		//System.out.println("/////---"+queset);
		return queset;
	}
	
	public String selectingQuestion(Row row,int column){
		
		String value;
		DataFormatter formatter = new DataFormatter(Locale.US);
		
		value=formatter.formatCellValue(row.getCell(column));
		
		//System.out.println("----value:"+value);
		return value;
		
	}
	public int totalQuestionsForEachTopic(int totalQuestions,int totalSheets){
		
		for(int i=1;i<=totalQuestions;i++){
			
			//int num=totalSheets/i;
			
			//int value=num*totalSheets;
			System.out.println("total Question:"+totalQuestions+";total sheets:"+totalSheets);
			int div=totalQuestions/totalSheets;
			System.out.println("div--1:"+div);
			
			//int value=totalSheets*i;
			int sum=div*totalSheets;
			//System.out.println(value+"****"+totalQuestions);
			//if((value>=(totalQuestions-5))&& (value<=(totalQuestions+5))){
				if(sum>=totalQuestions){
				
				System.out.println("value of div:"+div);
				
				return div;
				
				}
				else if(sum<totalQuestions){
					
					div=totalSheets*(div+1);
					return div;
				}
		}
		return 0;
	}
	public String getFileBasedOnStream(String stream){
		stream=stream.toLowerCase();
		
		if(stream.equalsIgnoreCase("coreJava")){
			return coreJava;
		}
		else if(stream.equalsIgnoreCase("spring")){
			return " ";
		}else{
			return "No stream is available";
		}
		
	}
	
}
