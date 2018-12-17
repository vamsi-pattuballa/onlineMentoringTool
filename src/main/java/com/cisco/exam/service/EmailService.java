package com.cisco.exam.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cisco.exam.model.Mail;
@Service
public class EmailService {

	@Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(Mail mail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        /*helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));
        String inlineImage = "<img src=\"cid:logo.png\"></img><br/>";*/
        
        //helper.setText(inlineImage + mail.getContent(), true);
        helper.setText(mail.getContent());
        helper.setSubject(mail.getSubject());
        helper.setTo(mail.getTo());
        helper.setFrom(mail.getFrom());
        
        
       /* Map model = new HashMap();
        model.put("name", "Memorynotfound.com");
        model.put("location", "Belgium");
        model.put("signature", "https://memorynotfound.com");*/

        emailSender.send(message);
    }
	
	
}
