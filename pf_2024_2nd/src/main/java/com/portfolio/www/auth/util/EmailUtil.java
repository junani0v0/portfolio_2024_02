package com.portfolio.www.auth.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.portfolio.www.auth.dto.EmailDto;

@Component
public class EmailUtil {
	
	private JavaMailSender mailSender;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public String sendMail(EmailDto emailDto) {
		
		return sendMail(emailDto, false);
	}
	
	public String sendMail(EmailDto emailDto, boolean isHtml) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			//이메일 정보 설정
			messageHelper.setTo(emailDto.getReceiver());
			messageHelper.setFrom(emailDto.getFrom());
			messageHelper.setSubject(emailDto.getSubject());
			messageHelper.setText(emailDto.getText(), isHtml);
			
			//이메일 발송
			mailSender.send(message);
			
		} catch (Exception e) {
			System.out.println(e);
			
	        return "Error";
		}
		return "Sucess";
	}
}
