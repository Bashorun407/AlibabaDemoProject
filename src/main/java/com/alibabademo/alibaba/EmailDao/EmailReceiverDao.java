package com.alibabademo.alibaba.EmailDao;

import lombok.Data;

import java.util.Date;

@Data
public class EmailReceiverDao {

    private Long Id;

    private String senderEmail;

    private String senderFirstName;

    private String senderLastName;

    private String emailNumber;

    private String emailHeader;

    private String emailBody;

    private String receiverEmail;

    private Date dateReceived;

    private Boolean receivedStatus;

    private Boolean deleteStatus;

    private Boolean important;

}
