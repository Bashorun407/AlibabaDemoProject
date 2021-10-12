package com.alibabademo.alibaba;

import com.alibabademo.alibaba.EmailSender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class AlibabaApplication {

	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(AlibabaApplication.class, args);
	}

	//(1) I comment the method below out so I can test the MimeMessage below this method...which is (2)
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail(){
//
//		emailService.sendMail("bobolah4cush@gmail.com", "Hello Bashorun, " +
//						"\n Today is Thursday, 7th October 2021." +
//						"\n I am at AUD OSHOLAKE-LM-LA, EBUTE METTA EAST." +
//						"\n I am using mailtrap since gmail seems not to allow Nigerians do a 2 step verification." +
//						"\n Mailtrap has really made it easy to check my mail-sending-application." +
//						"\n See you later.",
//				"Simple Email Sending");
//	}

	//(2) MimeMethod to send email with attachment and html text
	@EventListener(ApplicationReadyEvent.class)
	public void sendMimeMail() throws MessagingException {
		emailService.sendMimeMail("akinolusheyi@gmail.com", "MIME MESSAGE SENDING", "Today, 7th October, 2021," +
				"I was able to write a mail with attachment...YouTube and Resilience and God's help was very key to this.",
				"C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Training Pictures\\Screenshots\\Screenshot_20210223-114440_Gallery.jpg");
	}



}
