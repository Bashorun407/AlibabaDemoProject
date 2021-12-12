package com.alibaba.domain.Dao;

import lombok.Data;

import java.util.Date;

@Data
public class ShopOwnersDto {

    private Long Id;
    private String shopName;
    private String shopNumber;
    private String shopOwnerFirstName;
    private String shopOwnerLastName;
    private String phoneNumber;
    private String emailAddress;
    private Date dateCreated;
    private Boolean updatedStatus;
    private Date dateUpdated;
}
