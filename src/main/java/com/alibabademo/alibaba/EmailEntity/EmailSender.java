package com.alibabademo.alibaba.EmailEntity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Email_Sender")
public class EmailSender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long Id;

    @Column(name = "Receiver_Email")
    private String receiverEmail;

    @Column(name = "Email_Number")
    private Long emailNumber;

    @Column(name = "Email_Title")
    private String emailHeader;

    @Column(name = "Email_Body")
    private String emailBody;

    @Column(name = "Date_Sent")
    private Date dateSent;

    @Column(name = "Sent_Status")
    private Boolean sentStatus;

    @Column(name = "draft_Status")
    private Boolean draftStatus;

    @Column(name = "Delete_Mail_Status")
    private Boolean deleteStatus;

    @Column(name = "Sender_First_Name")
    private String senderFirstName;

    @Column(name = "Sender_Last_Name")
    private String senderLastName;

    @Column(name = "Sender_Email")
    private String senderEmail;

}
