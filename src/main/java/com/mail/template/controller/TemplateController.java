package com.mail.template.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mail.template.dto.MailRequest;
import com.mail.template.dto.MailResponse;
import com.mail.template.service.EmailService;

@RestController
public class TemplateController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send")
	public MailResponse sendEmail(@RequestBody MailRequest mailRequest) {
		
		Map<String,Object> model=new HashMap<>();
		model.put("Name", mailRequest.getName());
		model.put("location", "Navalur,chennai");
		
		return emailService.sendEmail(mailRequest, model);
		
	}
}
