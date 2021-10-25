package com.alibabademo.alibaba.EmailSender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    //(1) Method to send Simple mail
    public void sendMail(String sendTo, String mailBody, String mailSubject){
        //about to send mail
        System.out.println("Email sending....");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("springbootmailcheck@gmail.com");
        mailMessage.setTo(sendTo);
        mailMessage.setText(mailBody);
        mailMessage.setSubject(mailSubject);

        javaMailSender.send(mailMessage);

        System.out.println("\n Email sent!!");

    }

    //(2) Method to send Mime Mail...i.e. mails with attachments
    public void sendMimeMail(String toEmail, String subject, String body, String attachment) throws MessagingException {

        //Just to indicate at the console that mail has started sending
        System.out.println("Email sending...");

        //To add attachments and file formats and other functionalities, MimeMessageHelper is used
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom("bashorun@gmail.com");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(body);
        messageHelper.setText("The following text is in HTML", "<p><h1>Hello Bashorun</h1>, today," +
                " I wrote <emp>MIMEMESSAGE MAIL</emp>...<b>its a good day really</b> </p>");

        //In other to add attachment, FileSystemResource class will be used to 'collect' or 'set' the file to be attached
        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

        //To obtain the attachment and add it to the messageHelper
        messageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

        //Calling on JavaMailSender to send the mail
        javaMailSender.send(mimeMessage);

        //To print to the console that mail has been sent
        System.out.println("Mime Mail sent successfully!!!");

    }
    
}
