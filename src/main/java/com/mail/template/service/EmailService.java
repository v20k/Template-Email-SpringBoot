package com.mail.template.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mail.template.dto.MailRequest;
import com.mail.template.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Configuration config;
	
	public MailResponse sendEmail(MailRequest request,Map<String, Object> model) {
		MailResponse response=new MailResponse();
	    MimeMessage message = javaMailSender.createMimeMessage();   //Multipurpose Internet Mail Extensions
	    try {
			MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			helper.addAttachment("YangoosLogoHalfTransparent_blueText.png", new ClassPathResource("YangoosLogoHalfTransparent_blueText.png"));
			
			helper.setTo(request.getTo());
			
			Template t = config.getTemplate("email-template.ftl");        //Freemarker Template Language
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(t,model);
			helper.setText(html,true);
			
			helper.setSubject(request.getSubject());
			
			helper.setFrom(request.getFrom());
			
			javaMailSender.send(message);
			
			response.setMessage("mail sent to :"+ request.getTo());
			
			response.setStatus(Boolean.TRUE);
			
		
			
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return response;
	    
	}
}
