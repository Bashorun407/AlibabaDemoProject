package com.alibabademo.alibaba.EmailService;

import com.alibabademo.alibaba.EmailDao.EmailReceiverDao;
import com.alibabademo.alibaba.EmailEntity.EmailReceiver;
import com.alibabademo.alibaba.EmailEntity.EmailSender;
import com.alibabademo.alibaba.EmailEntity.QEmailReceiver;
import com.alibabademo.alibaba.EmailEntity.QEmailSender;
import com.alibabademo.alibaba.EmailReppo.EmailReceiverReppo;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.awt.desktop.OpenFilesEvent;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmailReceiverService {

    @Autowired
    private EmailReceiverReppo emailReceiverReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to save incoming mails
    public ResponsePojo<EmailReceiver> saveEmail(EmailReceiverDao emailReceiverDao){

        EmailReceiver emailReceiver = new EmailReceiver();
        emailReceiver.setSenderEmail(emailReceiverDao.getSenderEmail());
        emailReceiver.setSenderFirstName(emailReceiverDao.getSenderFirstName());
        emailReceiver.setSenderLastName(emailReceiverDao.getSenderLastName());
        emailReceiver.setEmailNumber(emailReceiverDao.getEmailNumber());
        emailReceiver.setEmailHeader(emailReceiverDao.getEmailHeader());
        emailReceiver.setEmailBody(emailReceiverDao.getEmailBody());
        emailReceiver.setReceiverEmail(emailReceiverDao.getReceiverEmail());
        emailReceiver.setDateReceived(new Date());
        emailReceiver.setReceivedStatus(true);
        emailReceiver.setDeleteStatus(false);
        emailReceiver.setImportant(false);

        emailReceiverReppo.save(emailReceiver);

        //Response POJO
        ResponsePojo<EmailReceiver> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailReceiver);
        responsePojo.setMessage("New Email Received!!");

        return responsePojo;
    }

    //(2) Method to set 'Important' feature for a mail
    public ResponsePojo<EmailReceiver> importantMail(Long Id){

        Optional<EmailReceiver> emailReceiverOptional = emailReceiverReppo.findById(Id);
        emailReceiverOptional.orElseThrow(()->new ApiException(String.format("There is no mail with this Id!!", Id)));

        EmailReceiver mail = emailReceiverOptional.get();

        //To set the 'Important' feature of the received mail
        mail.setImportant(true);
        emailReceiverReppo.save(mail);

        //Response POJO
        ResponsePojo<EmailReceiver> responsePojo = new ResponsePojo<>();
        responsePojo.setData(mail);
        responsePojo.setMessage("Email has been tagged 'Important'.");

        return responsePojo;
    }

    //(3) Method to delete mail...fake delete anyways
    public ResponsePojo<EmailReceiver> deleteMail(Long Id){

        Optional<EmailReceiver> emailReceiverOptional = emailReceiverReppo.findById(Id);
        emailReceiverOptional.orElseThrow(()->new ApiException(String.format("Mail with this Id does not exist!!", Id)));

        EmailReceiver mail = emailReceiverOptional.get();
        mail.setDeleteStatus(true);
        emailReceiverReppo.save(mail);

        //Response POJO
        ResponsePojo<EmailReceiver> responsePojo = new ResponsePojo<>();
        responsePojo.setData(mail);
        responsePojo.setMessage("Email has been successfully deleted!!");

        return responsePojo;
    }

    //(4) Method to get all mails received
    public ResponsePojo<List<EmailReceiver>> getAllEmailsReceived(){
        QEmailReceiver qEmailReceiver = QEmailReceiver.emailReceiver;
        // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailReceiver> jpaQuery = jpaQueryFactory.selectFrom(qEmailReceiver)
                .where(qEmailReceiver.receivedStatus.eq(true))
                .orderBy(qEmailReceiver.Id.desc());

        List<EmailReceiver> emailReceiverList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailReceiver>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailReceiverList);
        responsePojo.setMessage("Email Received List Found!");

        return responsePojo;
    }

    //(5) Method to get all emails marked as 'Important'
    public ResponsePojo<List<EmailReceiver>> getImportantMails(){
        QEmailReceiver qEmailReceiver = QEmailReceiver.emailReceiver;
        // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailReceiver> jpaQuery = jpaQueryFactory.selectFrom(qEmailReceiver)
                .where(qEmailReceiver.important.eq(true))
                .orderBy(qEmailReceiver.Id.desc());

        List<EmailReceiver> emailReceiverList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailReceiver>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailReceiverList);
        responsePojo.setMessage("Email Received List Found!");

        return responsePojo;
    }

    //(6) Method to get all deleted emails from the bin
    public ResponsePojo<List<EmailReceiver>> getDeletedMails(){

        QEmailReceiver qEmailReceiver = QEmailReceiver.emailReceiver;
        // BooleanBuilder predicate = new BooleanBuilder();

        //Querydsl search
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<EmailReceiver> jpaQuery = jpaQueryFactory.selectFrom(qEmailReceiver)
                .where(qEmailReceiver.deleteStatus.eq(true))
                .orderBy(qEmailReceiver.Id.desc());

        List<EmailReceiver> emailReceiverList = jpaQuery.fetch();
        //Response POJO
        ResponsePojo<List<EmailReceiver>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(emailReceiverList);
        responsePojo.setMessage("List of Emails From Bin Found!");

        return responsePojo;

    }
}
