package com.alibabademo.alibaba.EmailController;

import com.alibabademo.alibaba.EmailDao.EmailSenderDao;
import com.alibabademo.alibaba.EmailEntity.EmailSender;
import com.alibabademo.alibaba.EmailService.EmailSenderService;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emailSender")
public class EmailSenderApi {

    @Autowired
    private EmailSenderService emailSenderService;

    //(1) Method to Compose mail
    @PostMapping("/composeEmail")
    public ResponsePojo<EmailSender> composeEmail(@RequestBody EmailSenderDao emailSenderDao){

        return emailSenderService.composeEmail(emailSenderDao);
    }

    //(2) Method to update mail
    @PutMapping("/updateMail/{Id}")
    public ResponsePojo<EmailSender> updateMail(@PathVariable Long Id, @RequestBody EmailSenderDao emailSenderDao){

        return emailSenderService.updateMail(Id, emailSenderDao);
    }

    //(3) Method to Send mail
    @PutMapping ("/sendMail/{Id}/{mailNumber}")
    public ResponsePojo<EmailSender> sendMail(@PathVariable Long Id, @PathVariable ("mailNumber") Long emailNumber){

        return emailSenderService.sendMail(Id, emailNumber);
    }

    //(4) Method to delete mail...fake delete anyways
    @DeleteMapping("/deleteMail/{Id}")
    public ResponsePojo<EmailSender> deleteMail(@PathVariable Long Id){

        return emailSenderService.deleteMail(Id);
    }

    //(5) Method to get all sent mails...
    @GetMapping("/sentMails")
    public ResponsePojo<List<EmailSender>> getSentMails(){

        return emailSenderService.getSentMails();
    }

    //(6) Method to get emails saved as draft
    @GetMapping("/draftMails")
    public ResponsePojo<List<EmailSender>> getDraftMails(){

        return emailSenderService.getDraftMails();
    }

    //(7) Method to get the List of deleted mails...something like the bin
    @GetMapping("/binMails")
    public ResponsePojo<List<EmailSender>> getMailInBin(){

        return emailSenderService.getMailInBin();
    }



}


