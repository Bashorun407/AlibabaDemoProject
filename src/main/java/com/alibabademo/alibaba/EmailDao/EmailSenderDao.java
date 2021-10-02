package com.alibabademo.alibaba.EmailDao;

import lombok.Data;

import java.util.Date;

@Data
public class EmailSenderDao {

    private Long Id;

    private String receiverEmail;

    private Long emailNumber;

    private String emailHeader;

    private String emailBody;

    private Date dateSent;

    private Boolean sentStatus;

    private Boolean draftStatus;

    private Boolean deleteStatus;

    private String senderFirstName;

    private String senderLastName;

    private String senderEmail;
}
