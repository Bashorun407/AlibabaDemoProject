package com.alibabademo.alibaba.EmailController;

import com.alibabademo.alibaba.EmailDao.EmailReceiverDao;
import com.alibabademo.alibaba.EmailEntity.EmailReceiver;
import com.alibabademo.alibaba.EmailService.EmailReceiverService;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emailReceiver")
public class EmailReceiverApi {

    @Autowired
    private EmailReceiverService emailReceiverService;

    //(1) Method to save incoming mails
    @PostMapping("/saveEmail")
    public ResponsePojo<EmailReceiver> saveEmail(@RequestBody EmailReceiverDao emailReceiverDao){

        return emailReceiverService.saveEmail(emailReceiverDao);
    }

    //(2) Method to set 'Important' feature for a mail
    @PutMapping ("/importantMail/{Id}")
    public ResponsePojo<EmailReceiver> importantMail(@PathVariable Long Id){

        return emailReceiverService.importantMail(Id);
    }

    //(3) Method to delete mail...fake delete anyways
    @DeleteMapping("/deleteMail/{Id}")
    public ResponsePojo<EmailReceiver> deleteMail(@PathVariable Long Id){

        return emailReceiverService.deleteMail(Id);
    }

    //(4) Method to get all mails received
    @GetMapping("/emailsReceived")
    public ResponsePojo<List<EmailReceiver>> getAllEmailsReceived(){

        return emailReceiverService.getAllEmailsReceived();
    }

    //(5) Method to get all emails marked as 'Important'
    @GetMapping("/importantMails")
    public ResponsePojo<List<EmailReceiver>> getImportantMails(){

        return emailReceiverService.getImportantMails();
    }

    //(6) Method to get all deleted emails from the bin
    @GetMapping("/deletedMails")
    public ResponsePojo<List<EmailReceiver>> getDeletedMails(){

        return emailReceiverService.getDeletedMails();
    }

}


