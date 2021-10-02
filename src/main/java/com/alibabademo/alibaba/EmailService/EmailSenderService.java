package com.alibabademo.alibaba.EmailService;

import com.alibabademo.alibaba.EmailDao.EmailSenderDao;
import com.alibabademo.alibaba.EmailEntity.EmailSender;
import com.alibabademo.alibaba.EmailEntity.QEmailReceiver;
import com.alibabademo.alibaba.EmailEntity.QEmailSender;
import com.alibabademo.alibaba.EmailReppo.EmailSenderReppo;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmailSenderService {

    @Autowired
    private EmailSenderReppo emailSenderReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to Compose mail
    public ResponsePojo<EmailSender> composeEmail(EmailSenderDao emailSenderDao){

        EmailSender newMail = new EmailSender();
        newMail.setReceiverEmail(emailSenderDao.getReceiverEmail());
        newMail.setEmailNumber(new Date().getTime());
        newMail.setEmailHeader(emailSenderDao.getEmailHeader());
        newMail.setEmailBody(emailSenderDao.getEmailBody());
        newMail.setSentStatus(false);
        newMail.setDraftStatus(true);
        newMail.setDeleteStatus(false);
        newMail.setSenderFirstName(emailSenderDao.getSenderFirstName());
        newMail.setSenderLastName(emailSenderDao.getSenderLastName());
        newMail.setSenderEmail(emailSenderDao.getSenderEmail());

        //saving the Email Details
        emailSenderReppo.save(newMail);

        //response POJO

        ResponsePojo<EmailSender> responsePojo = new ResponsePojo<>();
        responsePojo.setData(newMail);
        responsePojo.setMessage("Mail saved as draft");

        return responsePojo;
    }

    //(2) Method to update mail
    public ResponsePojo<EmailSender> updateMail(EmailSenderDao emailSenderDao){

        Optional<EmailSender> emailSenderOptional1 = emailSenderReppo.findById(emailSenderDao.getId());
        emailSenderOptional1.orElseThrow(()->new ApiException("There is no mail with this ID!!"));

        Optional<EmailSender> emailSenderOptional2 = emailSenderReppo.findByEmailNumber(emailSenderDao.getEmailNumber());
        emailSenderOptional2.orElseThrow(()-> new ApiException("This email Number does not exist!!"));

        EmailSender email1 = emailSenderOptional1.get();
        EmailSender email2 = emailSenderOptional2.get();

        //To check that the correct email is selected
        if(email1!=email2)
            throw new ApiException("The mail Id and Number are for different mails!!");

        EmailSender updateMail =emailSenderOptional1.get();
        updateMail.setEmailBody(emailSenderDao.getEmailBody());
        updateMail.setDraftStatus(true);
        updateMail.setSentStatus(false);

        emailSenderReppo.save(updateMail);

        //Response POJO
        ResponsePojo<EmailSender> responsePojo = new ResponsePojo<>();
        responsePojo.setData(updateMail);
        responsePojo.setMessage("Mail updated...saved as draft");

        return responsePojo;
    }

    //(3) Method to Send mail
    public ResponsePojo<EmailSender> sendMail(Long Id, Long emailNumber){

        Optional<EmailSender> emailSenderOptional1 = emailSenderReppo.findById(Id);
        emailSenderOptional1.orElseThrow(()->new ApiException(String.format("Email with this Id not found!!", Id)));

        Optional<EmailSender> emailSenderOptional2 = emailSenderReppo.findByEmailNumber(emailNumber);
        emailSenderOptional2.orElseThrow(()-> new ApiException(String.format("Email with this Email-Number not found!!", emailNumber)));

        EmailSender email1 = emailSenderOptional1.get();
        EmailSender email2 = emailSenderOptional2.get();

        //To check that the correct email is selected
        if(email1!=email2)
            throw new ApiException("The mail Id and Number are for different mails!!");

       String senderAddress = email1.getSenderEmail();
       String mailContent = email1.getEmailBody();
       String receiverAddress = email1.getReceiverEmail();

       if(senderAddress ==null)
           throw new ApiException("The mail is invalid...sender address not given!!");

       if(mailContent==null)
           throw new ApiException("This mail is invalid...mail body is empty!!");

       if(receiverAddress ==null)
           throw new ApiException("The mail is invalid...receiver address not given!!");

       //After mail is verified, the following conditions will be set
        email1.setSentStatus(true);
        email1.setDateSent(new Date());

        //To save a copy to the repository
        emailSenderReppo.save(email1);

        //Response POJO
        ResponsePojo<EmailSender> responsePojo = new ResponsePojo<>();
        responsePojo.setData(email1);
        responsePojo.setMessage("Email Sent!!");

        return responsePojo;
    }

    //(4) Method to delete mail...fake delete anyways
    public ResponsePojo<EmailSender> deleteMail(Long Id){
        Optional<EmailSender> emailSenderOptional = emailSenderReppo.findById(Id);
        emailSenderOptional.orElseThrow(()-> new ApiException("Email with this Id does not exist!!"));

        EmailSender mail = emailSenderOptional.get();

        //The record's deleted status is only changed to true...it is not deleted
        mail.setDeleteStatus(true);
        emailSenderReppo.save(mail);

        //Response POJO
        ResponsePojo<EmailSender> responsePojo = new ResponsePojo<>();
        responsePojo.setData(mail);
        responsePojo.setMessage("Email successfully deleted!");

        return responsePojo;
    }

    //(5) Method to get all sent mails...
    public ResponsePojo<List<EmailSender>> getSentMails(){

        QEmailSender qEmailSender = QEmailSender.emailSender;
       // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailSender> jpaQuery = jpaQueryFactory.selectFrom(qEmailSender)
                .where(qEmailSender.sentStatus.eq(true))
                .orderBy(qEmailSender.Id.desc());

        List<EmailSender> emailSenderList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailSender>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailSenderList);
        responsePojo.setMessage("Email Sent List Found!");

        return  responsePojo;
    }

    //(6) Method to get emails saved as draft
    public ResponsePojo<List<EmailSender>> getDraftedMails(){
        QEmailSender qEmailSender = QEmailSender.emailSender;
        // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailSender> jpaQuery = jpaQueryFactory.selectFrom(qEmailSender)
                .where(qEmailSender.draftStatus.eq(true))
                .orderBy(qEmailSender.Id.desc());

        List<EmailSender> emailSenderList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailSender>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailSenderList);
        responsePojo.setMessage("Email Draft List Found!");

        return responsePojo;
    }

    //(7) Method to get the List of deleted mails...something like the bin
    public ResponsePojo<List<EmailSender>> getMailInBin(){
        QEmailSender qEmailSender = QEmailSender.emailSender;
        // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailSender> jpaQuery = jpaQueryFactory.selectFrom(qEmailSender)
                .where(qEmailSender.deleteStatus.eq(true))
                .orderBy(qEmailSender.Id.desc());

        List<EmailSender> emailSenderList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailSender>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailSenderList);
        responsePojo.setMessage("List of Emails From Bin Found!");

        return responsePojo;
    }

}

