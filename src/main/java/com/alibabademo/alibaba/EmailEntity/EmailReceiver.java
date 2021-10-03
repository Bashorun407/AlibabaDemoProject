package com.alibabademo.alibaba.EmailEntity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity(name = "Email_Receiver")
public class EmailReceiver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long Id;

    @Column(name = "Sender_Email")
    private String senderEmail;

    @Column(name = "Sender_FirstName")
    private String senderFirstName;

    @Column(name = "Sender_LastName")
    private String senderLastName;

    @Column(name = "Email_Number")
    private Long emailNumber;

    @Column(name = "Email_Title")
    private String emailHeader;

    @Column(name = "Email_Body")
    private String emailBody;

    @Column(name = "Receiver_Email")
    private String receiverEmail;

    @Column(name = "Date_Received")
    private Date dateReceived;

    @Column(name = "Received_Status")
    private Boolean receivedStatus;

    @Column(name = "Delete_Status")
    private Boolean deleteStatus;

    @Column(name = "Important")
    private Boolean important;

}
